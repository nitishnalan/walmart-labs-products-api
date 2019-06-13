package org.walmart.repository;

import org.walmart.models.SearchAndFilterRequest;

import java.util.List;

public interface ProductRepositoryCustom {

    public List getSearchAndFilteredProducts(SearchAndFilterRequest searchAndFilterRequestObj);
}
