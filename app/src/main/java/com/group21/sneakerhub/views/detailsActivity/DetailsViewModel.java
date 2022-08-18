package com.group21.sneakerhub.views.detailsActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.searchProductByName.ISearchProductByName;
import com.group21.sneakerhub.usecases.searchProductByName.SearchProductByName;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    MutableLiveData<List<Product>> detailProduct;

    ISearchProductByName searchProductByName;

    public DetailsViewModel(){
        searchProductByName = new SearchProductByName();
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
}
