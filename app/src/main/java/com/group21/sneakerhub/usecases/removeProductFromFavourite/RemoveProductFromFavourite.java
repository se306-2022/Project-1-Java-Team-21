package com.group21.sneakerhub.usecases.removeProductFromFavourite;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

public class RemoveProductFromFavourite implements IRemoveProductFromFavourite{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public void removeProductFromFavourite(Product product) {
        product.setIsFavourite(true);
        productRepository.updateProductIsFavourite(product);
    }
}
