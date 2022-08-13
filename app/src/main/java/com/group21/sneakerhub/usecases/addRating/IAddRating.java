package com.group21.sneakerhub.usecases.addRating;

import com.group21.sneakerhub.model.Product;

public interface IAddRating {
    void addRating(Product product, double newRating);
}
