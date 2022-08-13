package com.group21.sneakerhub.usecases.removeProductFromFavourite;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

public class RemoveProductFromFavourite implements IRemoveProductFromFavourite{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public void removeProductFromFavourite(Product product) {
        product.changeIsFavourite();
        productRepository.updateProductIsFavourite(product);
    }
}
