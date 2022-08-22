package com.group21.sneakerhub.views.favouriteActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.addProductToFavourite.AddProductToFavourite;
import com.group21.sneakerhub.usecases.addProductToFavourite.IAddProductToFavourite;
import com.group21.sneakerhub.usecases.getFavouriteProducts.GetFavouriteProducts;
import com.group21.sneakerhub.usecases.getFavouriteProducts.IGetFavouriteProducts;
import com.group21.sneakerhub.usecases.removeProductFromFavourite.IRemoveProductFromFavourite;
import com.group21.sneakerhub.usecases.removeProductFromFavourite.RemoveProductFromFavourite;
import com.group21.sneakerhub.usecases.toggleProductIsFavourite.IToggleProductIsFavourite;
import com.group21.sneakerhub.usecases.toggleProductIsFavourite.ToggleProductIsFavourite;

import java.util.List;

public class FavouriteViewModel extends ViewModel {
    MutableLiveData<List<Product>> favouriteProducts;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> productIsFavourite = new MutableLiveData<>();

    IGetFavouriteProducts getFavouriteProducts;
    IRemoveProductFromFavourite removeProductFromFavourite;
    IAddProductToFavourite addProductToFavourite;
    IToggleProductIsFavourite toggleProductIsFavourite;

    public FavouriteViewModel(){
        getFavouriteProducts = new GetFavouriteProducts();
        removeProductFromFavourite = new RemoveProductFromFavourite();
        addProductToFavourite = new AddProductToFavourite();
        toggleProductIsFavourite = new ToggleProductIsFavourite();
    }

    /**
     * Return all the sneaker entries from the db which have the isFavourite field set to true
     */
    public LiveData<List<Product>> getFavouriteProducts(){
        if (favouriteProducts == null){
            favouriteProducts = new MutableLiveData<>();

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    isLoading.postValue(true);
                    favouriteProducts.postValue(getFavouriteProducts.getFavouriteProducts());
                    isLoading.postValue(false);
                }
            });

            thread1.start();

        }
        return favouriteProducts;
    }

    /**
     * When the user 'unhearts' a product from the favourite activity, the isFavourite boolean
     * for that product in the db is changed to false, so next time favourite activity is loaded
     * up that that product wont be on it.
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
     * Setting the value for isFavourite for product in db to true, this is incase the user unhearts
     * then hearts again in same session on activity.
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
