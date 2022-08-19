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

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return productsPerCategory;
    }

    /**
     * Load and return a list containing all the categories or brands of sneakers from the database.
     * @return
     */
    public Category getCategory(String brandName) {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading.postValue(true);
                System.out.println("-------------brand name -------------------");
                System.out.println(brandName);
                for (Category c : getCategories.getCategories()){
                    if (c.getName().equals(brandName)){
                        confirmedCategory = c;
                    }
                }
                isLoading.postValue(false);
            }
        });

        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(confirmedCategory.getName());
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
