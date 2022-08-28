package com.group21.sneakerhub.usecases.addRating;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

public class AddRating implements IAddRating{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public void addRating(Product product, double newRating) {
        product.changeRating(newRating);
        productRepository.updateProductRating(product);
    }
}
