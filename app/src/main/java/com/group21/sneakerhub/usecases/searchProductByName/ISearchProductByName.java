package com.group21.sneakerhub.usecases.searchProductByName;

import com.group21.sneakerhub.model.Product;

import java.util.List;
import java.util.Map;

public interface ISearchProductByName {

    int colorAvailability(String name);

    Map<String,Integer> colorAvailabilityBulk(List<Product> productList);

    List<Product> searchByName(String name);
}
