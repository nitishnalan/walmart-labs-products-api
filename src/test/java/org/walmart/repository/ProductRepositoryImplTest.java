package org.walmart.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.walmart.config.ProductJpaConfig;
import org.walmart.models.Product;
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.restapi.RestApiApplicationTests;

import javax.annotation.Resource;

import java.io.*;
import java.util.List;

@Profile("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {ProductJpaConfig.class },
        loader = AnnotationConfigContextLoader.class)

public class ProductRepositoryImplTest extends RestApiApplicationTests {

    @Resource
    private ProductRepository productRepository;


    private String fileLocation = "src\\test\\resourcesForTesting\\productRepoTestFile.txt";

    @Before
    public void setUp() throws IOException{

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new FileInputStream(fileLocation);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine();
            StringBuilder stringBuilder = new StringBuilder();

            while(line != null){
                stringBuilder.append(line).append("\n");
                line = br.readLine();
            }

            String jsonData = stringBuilder.toString();

            System.out.println(jsonData);

            List<Product> productArray = mapper.readValue(jsonData, new TypeReference<List<Product>>(){});

            System.out.println(productArray.size());

            for(Product p : productArray){
                System.out.println(p.getProductId());
                productRepository.save(p);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void getSearchAndFilterProductsTest(){
        SearchAndFilterRequest searchAndFilterRequestObj = new SearchAndFilterRequest("TV", (float) 0, (float) 50,
                        0, 5, 1, null , true);
        List<Product> testResult = productRepository.getSearchAndFilteredProducts(searchAndFilterRequestObj);

        Assert.assertEquals(testResult.size(), 13);
    }

}
