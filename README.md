# Rest Client API Application

# Prerequisite for this project
  - Java version 8
  - Maven
  
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

  1. The idea here is the to load the data which available on "https://mobile-tha-server.firebaseapp.com/" in our In-Memory Database. I am using HSQLDB here as an in-Memory DB.
  2. Assuming the data needs to be refreshed every 3 hours, I have scheduled the DataLoaderService to run every 3 hours.
  3. Since the product data contains html tags, I am sanitized the short description and the long description of the product data 
  by removing the html tags.
  4. Since we receive the price in string format with '$' and ',' characters, I am removing those characters from the price and storing the price in float format. This new attribute "priceFloat" will be used while filtering the data using minPrice or maxPrice.
  
  5. Assumptions for each fields in request parameter:<br/>
    - **search by keyword**: It accepts string and would search the attributes productName, shortDescription or longDescription to find the products which contain this keyword. <br/>
    - **filter by minimum price**: It accepts number greater than equal to 0, and would apply filter on priceFloat attribute of the products. <br/>
    - **filter by maximum price**: It accepts number, and would apply filter on priceFloat attribute of the products.<br/>
    - **filter by minimum review rating**: It accepts any number between 0 to 5, and would apply filter on reviewRating attribute.<br/>
    - **filter by maximum review rating**: It accepts any number between 0 to 5, and would apply filter on revewRating attribute.<br/>
    - **filter by minimum review count**: It accepts number greater than equal to 0, and would apply filter on reviewCount attribute.<br/>
    - **filter by maximum review count**: It accpets number greater than equal to 0. <br/>
    - **filter by in stock**: It accepts a boolean value. <br/>
    
   6. Testing Strategy: <br/>
    - I have used JUnit and Mockito for Unit Testing. <br/>
    - To test my ProductRepositoryImpl class, I am using a test DB which configured using ProductJpaConfig.class in "org.walmart.config" in the test folder.<br/>
    - ProductJpaConfig.class reads the database configurations from the application.properties file and reads the dummy products data from the "productRepoTestFile.txt" which is present inside the "resourcesForTesting" folder inside the test module.<br/>
    
   7. Caching Strategy: <br/>
   - I am using CacheManager provided by the Spring framework. <br/>
   - The cache is stored as "cacheProducts", where the lookup key is requested parameter converted to string.<br/>
   - Whenever the refreshing of the data happens in the DataLoaderService class, we clear the existing cache.<br/>
   - Hence, if the data is changed in the source API, the Rest Client would give the updated data to the requester.<br/>
   
   8. API documentation: <br/>
   - I have added Swagger documentation. You can refer the documentation here : http://localhost:8080/walmartlabsapi/swagger-ui.html#/search-client-controller
   
    
  

