# Rest Client API Application

# Project Task

Take a look at https://mobile-tha-server.firebaseapp.com/. The endpoint returns a list of products. 
Your task is to build out a REST endpoint that provides Search and Filter capability over the products info. 
Here are the query parameters/capabilities we expect to see:

  * search
  * minPrice
  * maxPrice
  * minReviewRating
  * maxReviewRating
  * minReviewCount
  * maxReviewCount
  * inStock

The clients of this API should be able to provide any combination of these parameters and get the products matching all the criteria.

Please create a repo for your project. Please keep it tidy as we will go over your code-base. The final submission is a link to this repo. This is your chance to show off your engineering best practices, such as well decomposed code, unit testing, documentation, clean commit messages, etc. Also think about efficiency of your solution, any caching and other considerations.

# Assumptions & Strategies:

  1. The idea here is to load the data which available on "https://mobile-tha-server.firebaseapp.com/" into our In-Memory Database. I am using HSQLDB here as an in-Memory DB.
  2. Assuming the data needs to be refreshed every 3 hours, I have scheduled the DataLoaderService to run every 3 hours.
  3. Since the product data contains html tags, I am sanitizing the short description and the long description of the product data 
  by removing the html tags.
  4. Since we receive the price in string format with '$' and ',' characters, I am removing these characters from the price and storing the price in float format. This new attribute "priceFloat" will be used while filtering the data using minPrice or maxPrice.
  
  5. Assumptions for each fields in request parameter:<br/>
    - **search by keyword**: It accepts string and would search the attributes productName, shortDescription or longDescription to find the products which contain this keyword. <br/>
    - **filter by minimum price**: It accepts number greater than equal to 0, and would apply filter on priceFloat attribute of the products. <br/>
    - **filter by maximum price**: It accepts number, and would apply filter on priceFloat attribute of the products.<br/>
    - **filter by minimum review rating**: It accepts any number between 0 to 5, and would apply filter on reviewRating attribute.<br/>
    - **filter by maximum review rating**: It accepts any number between 0 to 5, and would apply filter on revewRating attribute.<br/>
    - **filter by minimum review count**: It accepts number greater than equal to 0, and would apply filter on reviewCount attribute.<br/>
    - **filter by maximum review count**: It accepts number greater than equal to 0. <br/>
    - **filter by in stock**: It accepts a boolean value. <br/>
    
   6. Testing Strategy: <br/>
    - I have used JUnit and Mockito for Unit Testing. <br/>
    - To test my ProductRepositoryImpl class, I am using a test DB which is configured using ProductJpaConfig.class in "org.walmart.config" package, which is in the test module.<br/>
    - ProductJpaConfig.class reads the database configurations from the application.properties file and reads the dummy products data from the "productRepoTestFile.txt" which is present inside the "resourcesForTesting" folder inside the test module.<br/>
    
   7. Caching Strategy: <br/>
   - I am using CacheManager provided by the Spring framework. <br/>
   - The cache is stored as "cacheProducts", where the lookup key is requested parameter converted to string.<br/>
   - Whenever the refreshing of the data happens in the DataLoaderService class, we clear the existing cache.<br/>
   - Hence, if the data is changed in the source API, the Rest Client would give the updated data to the requester.<br/>
   
   8. API documentation: <br/>
   - I have added Swagger documentation. You can refer the documentation here : http://localhost:8080/walmartlabsapi/swagger-ui.html#/search-client-controller
   
# Architecture Diagram:
![alt text](https://github.com/nitishnalan/walmart-labs-products-api/blob/master/Architecture%20diagram.PNG)

# Class Diagram:
![alt text](https://github.com/nitishnalan/walmart-labs-products-api/blob/master/Class%20Diagram.PNG)
    
# Technology Stack Used:
  - Maven
  - Java Spring Boot - Framework.
  - HSQLDB used as In-Memory database to store the products.
  - JUnit & Mockito used for Unit Testing.
  - Swagger used for API documentation.

# Prerequisite for this project
  - Java version 8
  - Maven
  
# How to setup this application:
  1. git clone the given repository in your system.
  2. Go to the project path where you can find the pom.xml
  3. To run the test cases execute "mvn test" in the command prompt
  4. Use the maven run command to run the project "mvn spring-boot:run".
  5. Refer Swagger Documentation: http://localhost:8080/walmartlabsapi/swagger-ui.html#/search-client-controller
  
# Sample Request & Response:
 - GET Request url: http://localhost:8080/walmartlabsapi/searchClient?inStock=true&maxPrice=10&maxReviewRating=4.45&search=hdmi
```
[
  {
    "productId": "66ad1d64-7930-41a3-be2d-167480ce9f16",
    "productName": "VIZIO 12' and 6' Premium High-Speed HDMI Cables",
    "shortDescription": "VIZIO TXCHMD-C1K 12 Ft & 6Ft Premium High-Speed HDMI® Cable",
    "longDescription": "This combo pack of 12' & 6' HDMI® cable can be used for high definition video and audio. The 1080p and 4k x 2k Ultra High Definition capabilities are ideal for bringing sports, movies and games to life in vivid detail on your HDTV. It also supports 7.1",
    "price": "$6.71",
    "productImage": "/images/image7.jpeg",
    "priceFloat": 6.71,
    "reviewRating": 4.4286,
    "reviewCount": 7,
    "inStock": true
  }
]
```

 - Similarly you can use any combination of search and filter request to fetch the data. If there is any error binding the data then you shall receive a bad request error.
  

