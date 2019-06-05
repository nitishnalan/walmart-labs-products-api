package org.walmart.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.walmart.models.Product;
import org.walmart.repository.ProductRepository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class DataLoaderService {
    final static String dataLoaderUrl = "https://mobile-tha-server.firebaseapp.com/";


    @Autowired
    ProductRepository productRepository;

    @PostConstruct
    //will use this class to make REST call to Firebase repository and initialize our database
    public void initDataLoader() throws Exception {
        System.out.println("DataLoader class has been called!");

        boolean stopFlag = false;
        int pageCounter =1;

        while(!stopFlag){
            StringBuilder dataLoaderUrlBuilder = new StringBuilder();
            dataLoaderUrlBuilder.append(dataLoaderUrl).append("walmartproducts/").append(pageCounter).append("/30");

            System.out.println("url : " + dataLoaderUrlBuilder.toString());
            URL obj = new URL(dataLoaderUrlBuilder.toString());
            HttpURLConnection
                    con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            System.out.println(response.toString());

            //Read JSON response and print
            JSONObject urlResponse = new JSONObject(response.toString());
            System.out.println("PRODUCTS response: " + urlResponse.getJSONArray("products"));

            System.out.println("PRODUCTS response SIZE: " + urlResponse.getJSONArray("products").length());

            if(urlResponse.getJSONArray("products").length() != 0){
                JSONArray productArray = urlResponse.getJSONArray("products");

                for(int i=0; i < productArray.length();i++){
                    JSONObject eachProductObj = productArray.getJSONObject(i);

                    //validate the product values

                    // insert into the database
//                    System.out.println("productId : " + eachProductObj.getString("productId") + " long_description size : " + eachProductObj.getString("longDescription").length());
                    Product productObj = new Product(eachProductObj.has("productId") ? eachProductObj.getString("productId") : "",
                            eachProductObj.has("productName") ? eachProductObj.getString("productName") : "",
                            eachProductObj.has("shortDescription") ? eachProductObj.getString("shortDescription") : "",
                            eachProductObj.has("longDescription") ? eachProductObj.getString("longDescription") : "",
                            eachProductObj.has("price") ? eachProductObj.getString("price") : "",
                            eachProductObj.has("productImage") ? eachProductObj.getString("productImage") : "",
                            eachProductObj.has("reviewRating") ? Float.parseFloat(eachProductObj.getString("reviewRating")) : 0,
                            eachProductObj.has("reviewCount") ? eachProductObj.getInt("reviewCount") : 0,
                            eachProductObj.has("inStock") ? eachProductObj.getBoolean("inStock") : false);

                    productRepository.save(productObj);

                }
            }else{
                stopFlag = true;
            }

            pageCounter++;
        }

    }

}
