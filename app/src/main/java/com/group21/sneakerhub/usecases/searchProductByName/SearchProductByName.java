package com.group21.sneakerhub.usecases.searchProductByName;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

import java.util.List;

public class SearchProductByName implements ISearchProductByName{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public List<Product> searchByName(String name) {
        List<Product> productsMatchingName = productRepository.getProductsByName(name);
        return productsMatchingName;
    }
}
