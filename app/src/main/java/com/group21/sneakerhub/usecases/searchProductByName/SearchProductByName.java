package com.group21.sneakerhub.usecases.searchProductByName;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductByName implements ISearchProductByName{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public List<Product> searchByName(String name) {
        List<Product> productsMatchingName = productRepository.getProductsByName(name);
        return productsMatchingName;
    }
}
