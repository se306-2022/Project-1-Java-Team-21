package com.group21.sneakerhub.usecases.getTrendingProducts;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

import java.util.List;

public class GetTrendingProducts implements IGetTrendingProducts{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public List<Product> getTrendingProducts() {
        List<Product> products =  productRepository.getTrendingProducts();
        // only return the top 10 products
        return products.subList(0, 10);
    }
}
