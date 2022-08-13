package com.group21.sneakerhub.usecases.selectProductByColour;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.IProductRepository;
import com.group21.sneakerhub.repository.ProductRepository;

import java.util.List;

public class SelectProductByColour implements ISelectProductByColour {
    private static final IProductRepository productRepository = ProductRepository.getInstance();

    @Override
    public Product selectProductByColour(long brandId, String sneakerName, String color) {
        List<Product> products = productRepository.getProducts();
        for (Product product : products) {
            if (product.getCategoryId() == brandId && product.getName().equals(sneakerName) && product.getColor().equals(color)) {
                return product;
            }
        }
        return null;
    }
}
