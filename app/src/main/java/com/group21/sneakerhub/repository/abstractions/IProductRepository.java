package com.group21.sneakerhub.repository.abstractions;

import com.google.firebase.firestore.FirebaseFirestore;
import com.group21.sneakerhub.model.Product;

import java.util.List;

/**
 * Interface for ProductRepository
 */

public interface IProductRepository {

   List<Product> getProducts();

   List<Product> getProductsByCategoryId(long id);

   List<Product> getFavouriteProducts();

   List<Product> getProductsByName(String name);

   void updateProductIsFavourite(Product product);

   void updateProductRating(Product product);

   List<Product> getTrendingProducts();

   List<Product> getDefaultColourProducts();
}
