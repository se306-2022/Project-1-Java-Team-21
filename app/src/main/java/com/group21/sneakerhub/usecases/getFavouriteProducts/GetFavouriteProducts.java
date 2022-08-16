package com.group21.sneakerhub.usecases.getFavouriteProducts;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

import java.util.List;

public class GetFavouriteProducts implements IGetFavouriteProducts{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public List<Product> getFavouriteProducts() {
        return productRepository.getFavouriteProducts();
    }
}