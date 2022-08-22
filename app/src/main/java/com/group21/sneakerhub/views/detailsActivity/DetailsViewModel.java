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

public class DetailsViewModel extends ViewModel {
    MutableLiveData<List<Product>> detailProduct;
    MutableLiveData<Product> currentlySelectedProduct;
    MutableLiveData<Boolean> productIsFavourite = new MutableLiveData<>();

    ISearchProductByName searchProductByName;
    IRemoveProductFromFavourite removeProductFromFavourite;
    IAddProductToFavourite addProductToFavourite;
    IAddRating addRating;
    IToggleProductIsFavourite toggleProductIsFavourite;

    public DetailsViewModel(){
        searchProductByName = new SearchProductByName();
        removeProductFromFavourite = new RemoveProductFromFavourite();
        addProductToFavourite = new AddProductToFavourite();
        addRating = new AddRating();
        toggleProductIsFavourite = new ToggleProductIsFavourite();
    }

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

    public void removeProductFromFavourite(Product product){

        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                removeProductFromFavourite.removeProductFromFavourite(product);
            }
        });

        thread1.start();
    }

    public void addProductToFavourite(Product product){
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                addProductToFavourite.addProductToFavourite(product);
            }
        });

        thread1.start();
    }


    public void addRating(Product product, double rating){
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {
                addRating.addRating(product, rating);
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
