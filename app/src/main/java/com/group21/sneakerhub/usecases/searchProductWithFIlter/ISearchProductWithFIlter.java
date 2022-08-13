package com.group21.sneakerhub.usecases.searchProductWithFIlter;

import com.group21.sneakerhub.model.Product;

import java.util.List;

public interface ISearchProductWithFIlter {
    List<Product> searchProductWithFIlter(String search, List<String> brandNames, List<String> colors, int fromPrice, int toPrice);
}
