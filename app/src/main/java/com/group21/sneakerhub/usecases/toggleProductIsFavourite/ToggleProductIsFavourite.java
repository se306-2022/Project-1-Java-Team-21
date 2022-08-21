package com.group21.sneakerhub.usecases.toggleProductIsFavourite;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

public class ToggleProductIsFavourite implements IToggleProductIsFavourite{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public void toggleProductIsFavourite(Product product) {
        product.toggleIsFavourite();
        productRepository.updateProductIsFavourite(product);
    }
}
