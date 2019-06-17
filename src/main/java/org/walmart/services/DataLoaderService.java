package org.walmart.services;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.walmart.models.Product;
import org.walmart.models.RestClientProducts;
import org.walmart.repository.ProductRepository;

import java.nio.charset.Charset;

/**
 * Service class which is  used to populate In-memory database
 *
 * @param DATA_LOADER_URL It is the source api url
 * @param PRODUCT_URL It is the endpoint to fetch the products
 *
 */

@Component
public class DataLoaderService {

    private static final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);
    private static final String DATA_LOADER_URL = "https://mobile-tha-server.firebaseapp.com/";
    private static final String PRODUCT_URL = "walmartproducts";


    public RestTemplate restTemplate;



    @Autowired
    ProductRepository productRepository;

    @Autowired
    CacheManager cachingManager;


    @Autowired
    public DataLoaderService(RestTemplate restTemplate){

        this.restTemplate = restTemplate;
    }

    /**
     * This method is scheduled to run every 3 hours to populate out database
     * It takes the responsibility of refreshing the data in our database
     * (3 * 60 * 60 * 1000 = 10800000)
     * @param stopFlag It represents when the refreshing job has to stop
     * @param pageCounter It represents the current page number from which we are fetching the data
     * @param productCount It represents the maximum number of products that we want to fetch from the source
     *
     */
    @Scheduled(fixedRate = 10800000)
    public void initDataLoader(){
        logger.info("DataLoaderService is initialized..");

        boolean stopFlag = false;
        int pageCounter = 1;
        int productCount = 30;
        try{
            while (!stopFlag) {

                RestClientProducts restClientProducts = fetchRestClientProducts(pageCounter, productCount);

                if (restClientProducts.getProducts().size() != 0) {
                    for (Product product : restClientProducts.getProducts()) {
                        logger.info("Inserting data with the ProductId : {}", product.getProductId());

                        product = sanitizeProductData(product);


                        productRepository.save(product);
                    }

                    pageCounter++;
                } else {
                    stopFlag = true;
                }
            }

            clearCacheOfProducts();
        }catch(Exception e){
            logger.info("The initial Data Loader Service has interrupted due to some exception");
            logger.error(e.toString());

        }

    }

    /**
     * This method is responsible for clearing the cache of Products (i.e. chacheProducts)
     */
    private void clearCacheOfProducts() {
        logger.info("Clear caching of products data!");
        cachingManager.getCache("cacheProducts").clear();
        logger.info("Products cache has beed cleared!");
    }

    /**
     * This method is sanitizes the Product Data
     * Sanitize short and long description of the product.
     * The price in string format is sanitized and converted to price float.
     * @param product
     * @return Product
     */
    public Product sanitizeProductData(Product product) {

        if(product != null && product.getLongDescription()!=null){
            String sanitizedLongDescription = convertHtmlToText(product.getLongDescription());
            product.setLongDescription(sanitizedLongDescription);
        }

        if(product != null && product.getShortDescription() != null){
            String sanitizedShortDescription = convertHtmlToText(product.getShortDescription());
            product.setShortDescription(sanitizedShortDescription);
        }

        if(product != null && product.getPrice() != null){
            String sanitizedPrice = product.getPrice().replaceAll("[,$]","");
            product.setPriceFloat(Float.parseFloat(sanitizedPrice));
        }

        return product;
    }

    /**
     * This method fetches the data from source API and returns the data in RestCientProducts Rest Template
     * @param pageCounter
     * @param productCount
     * @return RestClientProducts Object
     */
    public RestClientProducts fetchRestClientProducts(int pageCounter, int productCount){
        StringBuilder dataLoaderUrlBuilder = new StringBuilder();
        dataLoaderUrlBuilder.append(DATA_LOADER_URL).append(PRODUCT_URL).append("/").append(pageCounter).append("/").append(productCount);
        RestClientProducts restClientProducts = new RestClientProducts();
        String restClientUrl = dataLoaderUrlBuilder.toString();

        try{
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            restClientProducts = restTemplate.getForObject(restClientUrl, RestClientProducts.class);
        }catch(Exception e){
            logger.error("Error connecting to Walmart Rest Client");
            logger.error(e.toString());
        }

        return restClientProducts;
    }

    /**
     * This method is responsible to remove html tags from the description
     * @param descriptionWithHtml
     * @return text without html tags
     */
    private String convertHtmlToText(String descriptionWithHtml) {

        return Jsoup.parse(descriptionWithHtml).text();
    }
}
