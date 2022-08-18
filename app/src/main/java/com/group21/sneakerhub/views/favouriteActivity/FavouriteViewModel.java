package com.group21.sneakerhub.views.favouriteActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.getFavouriteProducts.GetFavouriteProducts;
import com.group21.sneakerhub.usecases.getFavouriteProducts.IGetFavouriteProducts;

import java.util.List;

public class FavouriteViewModel extends ViewModel {
    MutableLiveData<List<Product>> favouriteProducts;

    IGetFavouriteProducts getFavouriteProducts;

    public FavouriteViewModel(){
        getFavouriteProducts = new GetFavouriteProducts();
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
                    favouriteProducts.postValue(getFavouriteProducts.getFavouriteProducts());
                }
            });

            thread1.start();

        }
        return favouriteProducts;
    }
}
