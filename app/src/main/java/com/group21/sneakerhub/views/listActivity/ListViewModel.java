package com.group21.sneakerhub.views.listActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;
import com.group21.sneakerhub.usecases.getCategories.IGetCategories;
import com.group21.sneakerhub.usecases.getProductsByCategoryId.GetProductsByCategoryId;
import com.group21.sneakerhub.usecases.getProductsByCategoryId.IGetProductsByCategoryId;

import java.util.ArrayList;
import java.util.List;

public class ListViewModel extends ViewModel {
    /**
     * fields
     */
    private String currentBrandName;
    private List<Category> categoryList;
    private List<Product> productsPerCategory;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private Category confirmedCategory;

    /**
     * interfaces
     */
    IGetCategories getCategories;
    IGetProductsByCategoryId getProductsByCategoryId;

    /**
     * constructor
     */
    public ListViewModel(){
        getCategories = new GetCategories();
        getProductsByCategoryId = new GetProductsByCategoryId();
    }

    /**
     * getters and setters
     */
    public String getCurrentBrandName() {
        return currentBrandName;
    }

    public void setCurrentBrandName(String currentBrandName) {
        this.currentBrandName = currentBrandName;
    }

    /**
     * Load and return a list of all the products in a specific category,
     * parameter is the category id which is a unique field.
     * @param id
     * @return
     */
    public List<Product> getProductsByCategoryId(long id) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading.postValue(true);
                productsPerCategory = getProductsByCategoryId.getProductsByCategoryId(id);
                isLoading.postValue(false);
            }
        });

        thread1.start();

        return productsPerCategory;
    }

    /**
     * Load and return a list containing all the categories or brands of sneakers from the database.
     * @return
     */
    public Category getCategory() {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading.postValue(true);
                categoryList = getCategories.getCategories();
                for (Category category : categoryList){
                    System.out.println(category.getName());
                    System.out.println("-------------------------");
                    if (category.getName().equals(currentBrandName)) {
                        confirmedCategory = category;
                    }
                }
                isLoading.postValue(false);
            }
        });

        thread1.start();

        return confirmedCategory;
    }

    /**
     * Use the name of the category to get its object
     * public Category getCategory(){
     *
     *         for (Category category : getCategories()){
     *             if (category.getName().equals(currentBrandName)){
     *                 return category;
     *             }
     *         }
     *
     *         return null;
     *     }
     */




}
