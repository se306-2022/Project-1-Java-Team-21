package com.group21.sneakerhub.usecases.selectProductByColour;

import com.group21.sneakerhub.model.Product;

import java.util.List;

public interface ISelectProductByColour {
    Product selectProductByColour(long brandId, String sneakerName, String color);
}
