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

/**
 * Class for View Model of Favourites Activity
 */

public class FavouriteViewModel extends ViewModel {
    MutableLiveData<List<Product>> favouriteProducts;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<Boolean> productIsFavourite = new MutableLiveData<>();

    IGetFavouriteProducts getFavouriteProducts;
    IToggleProductIsFavourite toggleProductIsFavourite;

    public FavouriteViewModel(){
        getFavouriteProducts = new GetFavouriteProducts();
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

    public LiveData<Boolean> toggleProductIsFavourite(Product product) {

        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                toggleProductIsFavourite.toggleProductIsFavourite(product);
                productIsFavourite.postValue(product.getIsFavourite());
            }
        });

        thread1.start();
        return productIsFavourite;
    }
}
