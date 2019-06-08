package org.walmart.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.walmart.models.Product;
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.repository.ProductRepositoryImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SearchClientController {

    @Autowired
    ProductRepositoryImpl productRepositoryImpl;

    //// TODO: 6/7/2019 add caching later
    @PostMapping("/searchClient")

    public ResponseEntity<List<Object>> searchAndFilterProducts(@Valid @RequestBody SearchAndFilterRequest searchAndFilterRequestBody, BindingResult bindingResult){

        System.out.println("GOT RESPONSE INSIDE SEARCH CLIENT");

        System.out.println("REQUEST HEADER : " + searchAndFilterRequestBody.toString());
        if(!bindingResult.hasErrors()){
            //// TODO: 6/7/2019 add search functionality for multiple words

        }

        List<Object> resultSet = productRepositoryImpl.getSearchAndFilteredProducts(searchAndFilterRequestBody);

        if(resultSet == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(resultSet, HttpStatus.OK);
    }

}
