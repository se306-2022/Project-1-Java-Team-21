package com.group21.sneakerhub.usecases.getProductsByCategoryId;

import com.group21.sneakerhub.model.Product;

import java.util.List;

public interface IGetProductsByCategoryId {
    List<Product> getProductsByCategoryId(long id);
}
