package com.group21.sneakerhub.usecases.getProductsByCategoryId;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class GetProductsByCategoryId implements IGetProductsByCategoryId {
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public List<Product> getProductsByCategoryId(long id) {
        List<Product> products = productRepository.getProductsByCategoryId(id);
        List<Product> results = new ArrayList<>();
        // only return products that are in default colour
        for(Product product : products){
            if(product.getIsFirst()){
                results.add(product);
            }
        }
        return results;
    }

}
