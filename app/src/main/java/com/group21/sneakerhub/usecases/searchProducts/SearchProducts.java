package com.group21.sneakerhub.usecases.searchProducts;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SearchProducts implements ISearchProducts{
    private static final IProductRepository productRepository = ProductRepository.getInstance();
    @Override
    public List<Product> searchProducts(String search) {
        List<Product> products = productRepository.getDefaultColourProducts();

        if(search.isEmpty()){
            return products;
        }

        List<Product> searchProducts = new ArrayList<>();
        // filter products by search matching either name or description
        for(Product product : products){
            if(product.getName().toLowerCase().contains(search.toLowerCase())||product.getDescription().toLowerCase().contains(search.toLowerCase())){
                searchProducts.add(product);
            }
        }
        return searchProducts;
    }
}
