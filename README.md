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

  * The idea here is the to load the data which available on "https://mobile-tha-server.firebaseapp.com/" in our In-Memory Database.
  I am using HSQLDB here as an in-Memory DB.
  * Assuming the data needs to be refreshed every 3 hours, I have scheduled the DataLoaderService to run every 3 hours.
  * Since the product data contains html tags, I am sanitized the short description and the long description of the product data 
  by removing the html tags.
  * Since we receive the price in string format with '$' and ',' characters, I am removing those characters from the price and storing
  the price in float format. This new attribute "priceFloat" will be used while filtering the data using minPrice or maxPrice.
  
With the assumption that the data we are to run every 3
