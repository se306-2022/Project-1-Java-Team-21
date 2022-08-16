package com.group21.sneakerhub.usecases.searchProductWithFIlter;

import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.CategoryRepository;
import com.group21.sneakerhub.repository.ICategoryRepository;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

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

        System.out.println("use case");
        System.out.println(brandNames.get(0));
       // System.out.println(brandNames.get(1));
        System.out.println(colors.get(0));
      //  System.out.println(colors.get(1));
        System.out.println(search);
        System.out.println(fromPrice);
        System.out.println(toPrice);

        Product product;
        for (int i = 0; i < products.size(); i++) {
            product = products.get(i);
            if(search != null){
                if(!product.getName().toLowerCase().contains(search.toLowerCase())&&!product.getDescription().toLowerCase().contains(search.toLowerCase())){
                    unwantedProductIndex.add(i);
                    continue;
                }
            }
            if(brandNames != null){
                List<Category> categories = categoryRepository.getCategories();

                Map<Long,String> brandIdtoNameMap = new HashMap<>();
                for(Category category: categories){
                    brandIdtoNameMap.put(category.getId(), category.getName());
                }
                System.out.println("map values:");
                for (String values : brandIdtoNameMap.values())
                    System.out.println(values);
                System.out.println("==================================");

                if(!brandNames.contains(brandIdtoNameMap.get(product.getCategoryId()))) {
                    unwantedProductIndex.add(i);
                    continue;
                }
            }

            if(colors != null){
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

        for (Integer itnnqw : unwantedProductIndex){
            System.out.println(itnnqw);
        }
        //System.out.println(filteredProducts.get(0));
        return filteredProducts;
    }
}