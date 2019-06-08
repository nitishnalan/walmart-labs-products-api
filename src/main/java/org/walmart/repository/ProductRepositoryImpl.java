package org.walmart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.walmart.models.Product;
import org.walmart.models.SearchAndFilterRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ProductRepositoryImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List getSearchAndFilteredProducts(SearchAndFilterRequest searchAndFilterRequestObj){

//        StringBuilder queryBuilder = new StringBuilder();
//
//        String selectQuery



        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productQueryRoot = productQuery.from(Product.class);

        List<Predicate> predicateListForQuery = new ArrayList<>();

        if(searchAndFilterRequestObj.getSearch() != null){
            Expression<String> expressionProductName = productQueryRoot.get("productName");
            String queryParameters = "%" + searchAndFilterRequestObj.getSearch() + "%";
            Predicate predicateProductName = criteriaBuilder.like(expressionProductName,queryParameters);

            Expression<String> expressionProductShortDescription = productQueryRoot.get("shortDescription");
            Predicate predicateShortDescription = criteriaBuilder.like(expressionProductShortDescription, queryParameters);

            Expression<String> expressionProductLongDescription = productQueryRoot.get("longDescription");
            Predicate predicateLongDescription = criteriaBuilder.like(expressionProductLongDescription, queryParameters);


            Predicate searchString = criteriaBuilder.or(predicateProductName, predicateShortDescription, predicateLongDescription);

            productQuery.where(searchString);


        }

        return entityManager.createQuery(productQuery.select(productQueryRoot)).getResultList();
    }

}
