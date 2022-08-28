package com.group21.sneakerhub.usecases.searchProducts;

import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.implementations.CategoryRepository;
import com.group21.sneakerhub.repository.abstractions.ICategoryRepository;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProducts implements ISearchProducts{
    private static final IProductRepository productRepository = ProductRepository.getInstance();
    private static final ICategoryRepository categoryRepository = CategoryRepository.getInstance();

    @Override
    public List<Product> searchProducts(String search) {
        // for other searches, return all products that match the search
        List<Product> products = productRepository.getDefaultColourProducts();
        if(search == null || search.isEmpty()){
            return products;
        }

        // if the search was a brand name, return all products in that brand
        List<Category> categories = categoryRepository.getCategories();
        Map<String,Long> brandNameToIdMap = new HashMap<>();
        for(Category category: categories){
            brandNameToIdMap.put(category.getName().toLowerCase(),category.getId());
        }

        if(brandNameToIdMap.containsKey(search.toLowerCase())) {
            return productRepository.getProductsByCategoryId(brandNameToIdMap.get(search.toLowerCase()));
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