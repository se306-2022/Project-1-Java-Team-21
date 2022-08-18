package com.group21.sneakerhub.views.searchResultListActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.searchProductByName.ISearchProductByName;
import com.group21.sneakerhub.usecases.searchProductByName.SearchProductByName;
import com.group21.sneakerhub.usecases.searchProductWithFIlter.ISearchProductWithFIlter;
import com.group21.sneakerhub.usecases.searchProductWithFIlter.SearchProductWithFilter;
import com.group21.sneakerhub.usecases.searchProducts.ISearchProducts;
import com.group21.sneakerhub.usecases.searchProducts.SearchProducts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultListViewModel extends ViewModel {
    // fields
    MutableLiveData<List<Product>> searchResults;
    Map<String,Integer> colorsMap;

    // use case interfaces
    ISearchProductWithFIlter searchProductWithFIlter;
    ISearchProducts searchProducts;
    ISearchProductByName searchProductByName;

    // constructor
    public SearchResultListViewModel(){
        searchProductWithFIlter = new SearchProductWithFilter();
        searchProducts = new SearchProducts();
        searchProductByName = new SearchProductByName();
    }

    /**
     * Search products with filter db method implemented in new thread.
     * Because Tasks class requires method not be called on main thread.
     */
    public LiveData<List<Product>> getProductsBySearchFilter(String search, List<String> brandNames, List<String> colors, int fromPrice, int toPrice){
        if (searchResults == null){
            searchResults = new MutableLiveData<>();

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    searchResults.postValue(searchProductWithFIlter.searchProductWithFIlter(search,brandNames,colors,fromPrice,toPrice));
                }
            });

            thread1.start();

        }
        return searchResults;
    }

    public Map<String,Integer> colorAvailability(List<Product> products){

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                colorsMap = searchProductByName.colorAvailabilityBulk(products);
            }
        });

        thread1.start();

        return colorsMap;
    }
}
