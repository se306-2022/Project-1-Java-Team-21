package com.group21.sneakerhub.usecases.searchProductWithFIlter;

import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.implementations.CategoryRepository;
import com.group21.sneakerhub.repository.abstractions.ICategoryRepository;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;
import com.group21.sneakerhub.repository.implementations.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchProductWithFilter implements ISearchProductWithFIlter{
    private static final IProductRepository productRepository = ProductRepository.getInstance();
    private static final ICategoryRepository categoryRepository = CategoryRepository.getInstance();

    @Override
    public List<Product> searchProductWithFIlter(String search, List<String> brandNames, List<String> colors, int fromPrice, int toPrice) {
        List<Product> products = productRepository.getProducts();
        Set<Integer> unwantedProductIndex = new HashSet<>();

        Product product;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);
            if(search != null && !search.isEmpty()){
                if(!product.getName().toLowerCase().contains(search.toLowerCase())){
                    unwantedProductIndex.add(i);
                    continue;
                }
            }
            if(brandNames != null && !brandNames.isEmpty()){
                List<Category> categories = categoryRepository.getCategories();

                Map<Long,String> brandIdtoNameMap = new HashMap<>();
                for(Category category: categories){
                    brandIdtoNameMap.put(category.getId(), category.getName());
                }

                if(!brandNames.contains(brandIdtoNameMap.get(product.getCategoryId()))) {
                    unwantedProductIndex.add(i);
                    continue;
                }
            }

            if(colors != null && !colors.isEmpty()){
                if(!colors.contains(product.getColor())){
                    unwantedProductIndex.add(i);
                    continue;
                }
            }

            if(product.getPrice() < fromPrice || product.getPrice() > toPrice){
                unwantedProductIndex.add(i);
            }
        }

        List<Product> filteredProducts = new ArrayList<>();
        for(int i = 0; i < products.size(); i++){
            if(!unwantedProductIndex.contains(i)){
                filteredProducts.add(products.get(i));
            }
        }

        System.out.println(filteredProducts.size());
        return filteredProducts;
    }
}