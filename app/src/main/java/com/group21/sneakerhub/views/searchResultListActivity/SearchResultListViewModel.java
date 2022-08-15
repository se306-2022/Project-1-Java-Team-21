package com.group21.sneakerhub.views.searchResultListActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.searchProductWithFIlter.ISearchProductWithFIlter;
import com.group21.sneakerhub.usecases.searchProductWithFIlter.SearchProductWithFilter;
import com.group21.sneakerhub.usecases.searchProducts.ISearchProducts;
import com.group21.sneakerhub.usecases.searchProducts.SearchProducts;

import java.util.List;

public class SearchResultListViewModel extends ViewModel {
    // fields
    MutableLiveData<List<Product>> searchResults;

    // use case interfaces
    ISearchProductWithFIlter searchProductWithFIlter;
    ISearchProducts searchProducts;

    // constructor
    public SearchResultListViewModel(){
        searchProductWithFIlter = new SearchProductWithFilter();
        searchProducts = new SearchProducts();
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
                    searchProductWithFIlter.searchProductWithFIlter(search,brandNames,colors,fromPrice,toPrice);
                }
            });

            thread1.start();

        }
        return searchResults;
    }


}
