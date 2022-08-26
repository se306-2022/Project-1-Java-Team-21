package com.group21.sneakerhub.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.group21.sneakerhub.model.Product;

import java.util.List;

public interface IProductRepository {

   List<Product> getProducts();

   Product getProductById(long id);

   List<Product> getProductsByCategoryId(long id);

   List<Product> getFavouriteProducts();

   List<Product> getProductsByName(String name);

   void updateProductIsFavourite(Product product);

   void updateProductRating(Product product);

   List<Product> getTrendingProducts();

   List<Product> getDefaultColourProducts();
}
