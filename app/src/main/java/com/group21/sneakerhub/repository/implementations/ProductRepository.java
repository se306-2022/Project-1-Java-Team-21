package com.group21.sneakerhub.repository.implementations;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.abstractions.IProductRepository;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Product repository class which implements methods from the product database
 */

public class ProductRepository implements IProductRepository {
    private final FirebaseFirestore db;
    private static ProductRepository instance;
    private static final String COLLECTION_NAME = "Products";
    public ProductRepository(){
        db = FirebaseFirestore.getInstance();
    }

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    /**
     * Methods to retrieve and update the database
     *
     */

    @Override
    public List<Product> getProducts() {
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getProductsByCategoryId(long id) {
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).whereEqualTo("categoryId", id).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getProductsByName(String name){
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).whereEqualTo("name", name).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getFavouriteProducts() {
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).whereEqualTo("isFavourite", true).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateProductIsFavourite(Product product) {
        try{
            Tasks.await(db.collection(COLLECTION_NAME).document(String.valueOf(product.getId())).update("isFavourite", product.getIsFavourite()));
        }catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProductRating(Product product) {
        try{
            // update the rating and numberOfUsersRated of the product
            Tasks.await(db.collection(COLLECTION_NAME).document(String.valueOf(product.getId())).update("rating", product.getRating(),"numberOfUsersRated", product.getNumberOfUsersRated()));
        }catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getTrendingProducts() {
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).orderBy("rating", Query.Direction.DESCENDING).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getDefaultColourProducts() {
        try {
            return Tasks.await(db.collection(COLLECTION_NAME).whereEqualTo("isFirst", true).get()).toObjects(Product.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
