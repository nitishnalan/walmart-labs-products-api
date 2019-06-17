package org.walmart.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.repository.ProductRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchClientController {

    private static final Logger logger = LoggerFactory.getLogger(SearchClientController.class);

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/searchClient")
    @Cacheable(value = "cacheProducts", key = "#searchAndFilterRequestBody.toString()")
    public ResponseEntity<List<Object>> searchAndFilterProducts(@Valid SearchAndFilterRequest searchAndFilterRequestBody, BindingResult bindingResult){

        logger.info("Got Request with search client");

        logger.info("Client Request Information : " + searchAndFilterRequestBody.toString());
        if(bindingResult.hasErrors()){
            List<FieldError> requestErrors = bindingResult.getFieldErrors();
            List<Object> validationErrorMessage = new ArrayList<>();

            validationErrorMessage.add("Bad Request Error : ");

            for(FieldError reqError : requestErrors){
                validationErrorMessage.add(reqError.getField().toUpperCase() + " : " + reqError.getDefaultMessage());
            }

            return new ResponseEntity<>(validationErrorMessage,HttpStatus.BAD_REQUEST);
        }

        List<Object> resultSet = productRepository.getSearchAndFilteredProducts(searchAndFilterRequestBody);

        if(resultSet == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(resultSet, HttpStatus.OK);
    }

}
