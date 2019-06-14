package org.walmart.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.repository.ProductRepositoryImpl;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchClientController {

    @Autowired
    ProductRepositoryImpl productRepositoryImpl;

    //// TODO: 6/7/2019 add caching later

    @GetMapping("/searchClient")
    public ResponseEntity<List<Object>> searchAndFilterProducts(@Valid SearchAndFilterRequest searchAndFilterRequestBody, BindingResult bindingResult){

        System.out.println("GOT RESPONSE INSIDE SEARCH CLIENT");

        System.out.println("REQUEST HEADER : " + searchAndFilterRequestBody.toString());
        if(bindingResult.hasErrors()){

            List<FieldError> requestErrors = bindingResult.getFieldErrors();
            List<Object> validationErrorMessage = new ArrayList<>();

            validationErrorMessage.add("Bad Request Error : ");

            for(FieldError reqError : requestErrors){
                validationErrorMessage.add(reqError.getField().toUpperCase() + " : " + reqError.getDefaultMessage());
            }

            return new ResponseEntity<>(validationErrorMessage,HttpStatus.BAD_REQUEST);
        }

        List<Object> resultSet = productRepositoryImpl.getSearchAndFilteredProducts(searchAndFilterRequestBody);

        if(resultSet == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(resultSet, HttpStatus.OK);


    }

}
