package com.group21.sneakerhub.repository;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group21.sneakerhub.model.Product;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProductRepository implements IProductRepository{
    private FirebaseFirestore db;
    private static ProductRepository instance;

    private ProductRepository(){
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public ProductRepository getInstance() {
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    @Override
    public List<Product> getProducts() {
        try {
            return Tasks.await(db.collection("products").get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getProductById(long id) {
        try {
            return Tasks.await(db.collection("products").document(String.valueOf(id)).get()).toObject(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getProductsByCategoryId(long id) {
        try {
            return Tasks.await(db.collection("products").whereEqualTo("categoryId", id).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getFavouriteProducts() {
        try {
            return Tasks.await(db.collection("products").whereEqualTo("isFavourite", true).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductIsFavourite(Product product) {
        try{
            Tasks.await(db.collection("products").document(String.valueOf(product.getId())).update("isFavourite", product.getIsFavourite()));
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProductRating(Product product) {
        try{
            // update the rating and numberOfUsersRated of the product
            Tasks.await(db.collection("products").document(String.valueOf(product.getId())).update("rating", product.getRating(),"numberOfUsersRated", product.getNumberOfUsersRated()));
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
