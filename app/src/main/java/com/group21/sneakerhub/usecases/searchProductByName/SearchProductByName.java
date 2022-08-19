package com.group21.sneakerhub.usecases.searchProductByName;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductByName implements ISearchProductByName{
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public int colorAvailability(String name) {
        List<Product> productsMatchingName = productRepository.getProductsByName(name);
        int count = productsMatchingName.size();
        return count;
    }

    @Override
    public Map<String,Integer> colorAvailabilityBulk(List<Product> productList){
        Map<String,Integer> colorMap = new HashMap<>();

        for(Product product : productList){
            List<Product> productsMatchingName = productRepository.getProductsByName(product.getName());
            int count = productsMatchingName.size();
            colorMap.put(product.getName(),count);
        }
        System.out.println("-----------------------");
        System.out.println(colorMap);
        return colorMap;
    }

    @Override
    public List<Product> searchByName(String name) {
        List<Product> productsMatchingName = productRepository.getProductsByName(name);
        return productsMatchingName;
    }
}
