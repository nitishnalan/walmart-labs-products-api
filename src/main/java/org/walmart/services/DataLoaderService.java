package org.walmart.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.walmart.models.Product;
import org.walmart.models.RestClientProducts;
import org.walmart.repository.ProductRepository;
import org.jsoup.Jsoup;
import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

@Component
public class DataLoaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataLoaderService.class);
    private static final String dataLoaderUrl = "https://mobile-tha-server.firebaseapp.com/";
    private static final String PRODUCT_URL = "walmartproducts";
    private RestTemplate restTemplate;



    @Autowired
    ProductRepository productRepository;


    @Autowired
    public DataLoaderService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    //will use this class to make REST call to Firebase repository and initialize our database
    public void initDataLoader() throws Exception {
        System.out.println("DataLoader class has been called!");

        boolean stopFlag = false;
        int pageCounter = 1;
        int productCount = 30;

        while (!stopFlag) {

            RestClientProducts restClientProducts = fetchRestClientProducts(pageCounter, productCount);

            if (restClientProducts.getProducts().size() != 0) {
                for (Product product : restClientProducts.getProducts()) {
                    LOGGER.info("Inserting data with the ProductId : {}", product.getProductId());

                    product = sanitizeProductData(product);

                    productRepository.save(product);
                }

                pageCounter++;
            } else {
                stopFlag = true;
            }

        }
    }

    private Product sanitizeProductData(Product product) {

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

    public RestClientProducts fetchRestClientProducts(int pageCounter, int productCount){
        StringBuilder dataLoaderUrlBuilder = new StringBuilder();
        dataLoaderUrlBuilder.append(dataLoaderUrl).append(PRODUCT_URL).append("/").append(pageCounter).append("/").append(productCount);
        RestClientProducts restClientProducts = new RestClientProducts();
        String restClientUrl = dataLoaderUrlBuilder.toString();

        try{
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            restClientProducts = restTemplate.getForObject(restClientUrl, RestClientProducts.class);
        }catch(Exception e){
            LOGGER.error("Error connecting to Walmart Rest Client");
            LOGGER.error(e.toString());
        }

        return restClientProducts;
    }

    private String convertHtmlToText(String longDescriptionWithHtml) {

        return Jsoup.parse(longDescriptionWithHtml).text();
    }
}
