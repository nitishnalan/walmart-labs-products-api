package org.walmart.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.walmart.models.Product;

/**
 * Repository to store the Product Entity
 */

@Repository
public interface ProductRepository extends JpaRepository<Product, String>, ProductRepositoryCustom {

}
