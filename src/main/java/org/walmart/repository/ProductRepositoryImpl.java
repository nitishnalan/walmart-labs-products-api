package org.walmart.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.walmart.models.Product;
import org.walmart.models.SearchAndFilterRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Repository Implementation class for ProductRepository
 */

@Component
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * This method is responsible for generating query from the Get Request Parameter
     * @param searchAndFilterRequestObj
     * @return List of Products
     */
    public List getSearchAndFilteredProducts(SearchAndFilterRequest searchAndFilterRequestObj){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productQuery = criteriaBuilder.createQuery(Product.class);

        Root<Product> productQueryRoot = productQuery.from(Product.class);

        List<Predicate> predicateListForQuery = new ArrayList<>();

        logger.info("Building query for the client request");
        if(searchAndFilterRequestObj.getSearch() != null){
            logger.info("The search keyword is : {}",searchAndFilterRequestObj.getSearch());
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
            logger.info("Filter for minimum price is : {}",searchAndFilterRequestObj.getMinPrice());
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("priceFloat"), searchAndFilterRequestObj.getMinPrice()));
        }

        if(searchAndFilterRequestObj.getMaxPrice() != null){
            logger.info("Filter for maximum price is : {}", searchAndFilterRequestObj.getMaxPrice());
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("priceFloat"), searchAndFilterRequestObj.getMaxPrice()));
        }

        if(searchAndFilterRequestObj.getMinReviewRating() != null){
            logger.info("Filter for minimum review rating is : {}", searchAndFilterRequestObj.getMinReviewRating());
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("reviewRating"), searchAndFilterRequestObj.getMinReviewRating()));
        }

        if(searchAndFilterRequestObj.getMaxReviewRating() != null){
            logger.info("Filter for maximum review rating is : {}", searchAndFilterRequestObj.getMaxReviewRating());
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("reviewRating"), searchAndFilterRequestObj.getMaxReviewRating()));
        }

        if(searchAndFilterRequestObj.getMaxReviewCount() != null){
            logger.info("Filter for maximum review count is : {}", searchAndFilterRequestObj.getMaxReviewRating());
            predicateListForQuery.add(criteriaBuilder.lessThanOrEqualTo(productQueryRoot.get("reviewCount"), searchAndFilterRequestObj.getMaxReviewCount()));
        }

        if(searchAndFilterRequestObj.getMinReviewCount() != null){
            logger.info("Filter for minimum review count is : {}", searchAndFilterRequestObj.getMinReviewRating());
            predicateListForQuery.add(criteriaBuilder.greaterThanOrEqualTo(productQueryRoot.get("reviewCount"), searchAndFilterRequestObj.getMinReviewCount()));
        }

        if(searchAndFilterRequestObj.getInStock() != null){
            logger.info("Filter for inStock is : {}", searchAndFilterRequestObj.getInStock());
            predicateListForQuery.add(criteriaBuilder.equal(productQueryRoot.get("inStock"), searchAndFilterRequestObj.getInStock()));
        }

        if(predicateListForQuery.isEmpty()){
            return Collections.EMPTY_LIST;
        }

         productQuery.where(criteriaBuilder.and(predicateListForQuery.toArray(new Predicate[0])));


         return entityManager.createQuery(productQuery.select(productQueryRoot)).getResultList();

    }

}
