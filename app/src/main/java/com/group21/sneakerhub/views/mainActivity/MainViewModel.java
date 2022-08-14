package com.group21.sneakerhub.views.mainActivity;

import static java.lang.Thread.sleep;

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

public class MainViewModel extends ViewModel {
    MutableLiveData<List<Product>> trendingProducts;
    MutableLiveData<List<Product>> searchedProducts;
    MutableLiveData<List<Category>> categories;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    MutableLiveData<List<Product>> products;
    IGetProductsByCategoryId getProductsByCategoryId;

    IGetTrendingProducts getTrendingProducts;
    ISearchProducts searchProducts;
    IGetCategories getCategories;


    public MainViewModel() {
        getTrendingProducts = new GetTrendingProducts();
        searchProducts = new SearchProducts();
        getCategories = new GetCategories();

        getProductsByCategoryId = new GetProductsByCategoryId();
    }

    public LiveData<List<Product>> getProductsByCategoryId(long id){
        if (products == null) {
            products = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isLoading.postValue(true);
                    products.postValue(getProductsByCategoryId.getProductsByCategoryId(id));
                    isLoading.postValue(false);
                }
            }).start();
        }
        return products;
    }

    public LiveData<List<Product>> getTrendingProducts() {
        if (trendingProducts == null) {
            trendingProducts = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isLoading.postValue(true);
                    trendingProducts.postValue(getTrendingProducts.getTrendingProducts());
                    isLoading.postValue(false);
                }
            }).start();
        }
        return trendingProducts;
    }

    public LiveData<List<Product>> getSearchedProducts(String search) {
        if (searchedProducts == null) {
            searchedProducts = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isLoading.postValue(true);
                    searchedProducts.postValue(searchProducts.searchProducts(search));
                    isLoading.postValue(false);
                }
            }).start();
        }
        return searchedProducts;
    }

    public LiveData<List<Category>> getCategories() {
        if (categories == null) {
            categories = new MutableLiveData<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isLoading.postValue(true);
                    categories.postValue(getCategories.getCategories());
                    isLoading.postValue(false);
                }
            }).start();
        }
        return categories;
    }
}
