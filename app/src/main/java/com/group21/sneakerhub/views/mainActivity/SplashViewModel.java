package com.group21.sneakerhub.views.mainActivity;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;
import com.group21.sneakerhub.usecases.getCategories.IGetCategories;
import com.group21.sneakerhub.usecases.getProductsByCategoryId.GetProductsByCategoryId;
import com.group21.sneakerhub.usecases.getProductsByCategoryId.IGetProductsByCategoryId;
import com.group21.sneakerhub.usecases.getTrendingProducts.GetTrendingProducts;
import com.group21.sneakerhub.usecases.getTrendingProducts.IGetTrendingProducts;
import com.group21.sneakerhub.usecases.searchProducts.ISearchProducts;
import com.group21.sneakerhub.usecases.searchProducts.SearchProducts;

import java.util.List;

public class SplashViewModel extends ViewModel {
    MutableLiveData<List<Product>> trendingProducts;
    MutableLiveData<List<Category>> categories;
    public MutableLiveData<Boolean> isFinished = new MutableLiveData<>(false);

    IGetProductsByCategoryId getProductsByCategoryId;
    IGetTrendingProducts getTrendingProducts;
    ISearchProducts searchProducts;
    IGetCategories getCategories;


    public SplashViewModel() {
        getTrendingProducts = new GetTrendingProducts();
        searchProducts = new SearchProducts();
        getCategories = new GetCategories();

        getProductsByCategoryId = new GetProductsByCategoryId();
    }

    public LiveData<List<Product>> getTrendingProducts() {
        if (trendingProducts == null) {
            trendingProducts = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    trendingProducts.postValue(getTrendingProducts.getTrendingProducts());
                    isFinished.postValue(true);
                }
            }).start();
        }
        return trendingProducts;
    }

    public LiveData<List<Category>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    categories.postValue(getCategories.getCategories());
                }
            }).start();
        }
        return categories;
    }
}
