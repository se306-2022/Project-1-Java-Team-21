package com.group21.sneakerhub.views.detailsActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.addProductToFavourite.AddProductToFavourite;
import com.group21.sneakerhub.usecases.addProductToFavourite.IAddProductToFavourite;
import com.group21.sneakerhub.usecases.addRating.AddRating;
import com.group21.sneakerhub.usecases.addRating.IAddRating;
import com.group21.sneakerhub.usecases.removeProductFromFavourite.IRemoveProductFromFavourite;
import com.group21.sneakerhub.usecases.removeProductFromFavourite.RemoveProductFromFavourite;
import com.group21.sneakerhub.usecases.searchProductByName.ISearchProductByName;
import com.group21.sneakerhub.usecases.searchProductByName.SearchProductByName;
import com.group21.sneakerhub.usecases.toggleProductIsFavourite.IToggleProductIsFavourite;
import com.group21.sneakerhub.usecases.toggleProductIsFavourite.ToggleProductIsFavourite;

import java.util.List;

/**
 * Class for View Model of Details Activity
 */

public class DetailsViewModel extends ViewModel {

    // Variables
    MutableLiveData<List<Product>> detailProduct;
    MutableLiveData<Boolean> productIsFavourite = new MutableLiveData<>();

    ISearchProductByName searchProductByName;
    IRemoveProductFromFavourite removeProductFromFavourite;
    IAddProductToFavourite addProductToFavourite;
    IAddRating addRating;
    IToggleProductIsFavourite toggleProductIsFavourite;

    // Constructor
    public DetailsViewModel(){
        searchProductByName = new SearchProductByName();
        removeProductFromFavourite = new RemoveProductFromFavourite();
        addProductToFavourite = new AddProductToFavourite();
        addRating = new AddRating();
        toggleProductIsFavourite = new ToggleProductIsFavourite();
    }

    // Live data method
    public LiveData<List<Product>> getDetailPageProduct(String name){
        if (detailProduct == null) {
            detailProduct = new MutableLiveData<>();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    detailProduct.postValue(searchProductByName.searchByName(name));
                }
            });

            thread1.start();
        }
        return detailProduct;
    }

    /**
     * Removes product from favourites in the database
     * @param product
     */
    public void removeProductFromFavourite(Product product){

        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                removeProductFromFavourite.removeProductFromFavourite(product);
            }
        });

        thread1.start();
    }

    /**
     * Adds the product to favourite in the database
     * @param product
     */
    public void addProductToFavourite(Product product){
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                addProductToFavourite.addProductToFavourite(product);
            }
        });

        thread1.start();
    }

    /**
     * Adds a rating to the product
     * @param product
     * @param rating
     */
    public void addRating(Product product, double rating){
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                addRating.addRating(product, rating);
            }
        });

        thread1.start();
    }

    /**
     * Toggle functionality for favourites
     * @param product
     * @return isFavourite boolean
     */
    public LiveData<Boolean> toggleProductIsFavourite(Product product) {
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                productIsFavourite.postValue(!product.getIsFavourite());
                toggleProductIsFavourite.toggleProductIsFavourite(product);
            }
        });

        thread1.start();
        return productIsFavourite;
    }
}
