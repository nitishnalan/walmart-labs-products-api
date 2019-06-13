package org.walmart.repository;

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
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public List getSearchAndFilteredProducts(SearchAndFilterRequest searchAndFilterRequestObj){

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


            Predicate searchStringPredicate = criteriaBuilder.or(predicateProductName, predicateShortDescription, predicateLongDescription);

            predicateListForQuery.add(searchStringPredicate);

        }

        if(searchAndFilterRequestObj.getMinPrice() != null){
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("priceFloat"), searchAndFilterRequestObj.getMinPrice()));
        }

        if(searchAndFilterRequestObj.getMaxPrice() != null){
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("priceFloat"), searchAndFilterRequestObj.getMaxPrice()));
        }

        if(searchAndFilterRequestObj.getMinReviewRating() != null){
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("reviewRating"), searchAndFilterRequestObj.getMinReviewRating()));
        }

        if(searchAndFilterRequestObj.getMaxReviewRating() != null){
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("reviewRating"), searchAndFilterRequestObj.getMaxReviewRating()));
        }

        if(searchAndFilterRequestObj.getMaxReviewCount() != null){
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("reviewCount"), searchAndFilterRequestObj.getMaxReviewCount()));
        }

        if(searchAndFilterRequestObj.getMinReviewCount() != null){
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("reviewCount"), searchAndFilterRequestObj.getMinReviewCount()));
        }

        if(searchAndFilterRequestObj.getInStock() != null){
            predicateListForQuery.add(criteriaBuilder.equal(productQueryRoot.get("inStock"), searchAndFilterRequestObj.getInStock()));
        }

        if(predicateListForQuery.isEmpty()){
            return Collections.EMPTY_LIST;
        }

         productQuery.where(criteriaBuilder.and(predicateListForQuery.toArray(new Predicate[0])));
         return entityManager.createQuery(productQuery.select(productQueryRoot)).getResultList();

    }

}
