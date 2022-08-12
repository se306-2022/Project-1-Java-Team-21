package com.group21.sneakerhub.usecases.searchProducts;

import com.group21.sneakerhub.model.Product;

import java.util.List;

public interface ISearchProducts {
    List<Product> searchProducts(String search);
}
