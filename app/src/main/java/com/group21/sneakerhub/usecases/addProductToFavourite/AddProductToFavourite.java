package com.group21.sneakerhub.usecases.addProductToFavourite;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

public class AddProductToFavourite implements IAddProductToFavourite{
    private static final IProductRepository productRepository = ProductRepository.getInstance();
    @Override
    public void addProductToFavourite(Product product) {
        product.changeIsFavourite();
        productRepository.updateProductIsFavourite(product);
    }
}
