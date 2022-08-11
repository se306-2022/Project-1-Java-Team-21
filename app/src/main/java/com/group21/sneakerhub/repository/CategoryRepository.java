package com.group21.sneakerhub.repository;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group21.sneakerhub.model.Category;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{


    List<Category> categoryList;
    //private static CategoryRepository categoriesRepo;
    /**
     * Create instance of the firestone database.
     * Should be limited to only one instance.
     */
    FirebaseFirestore db;

    /**
     * Using singleton design pattern limit the number of instances
     * created to only one.
     */
    /*
    private CategoryRepository() {
        db = FirebaseFirestore.getInstance();
    }
    */
    /**
     * Any other class that wants to use this class does so by calling this getter method.
     * If there already exists an instance, the getter returns it, if not then it creates one.
     * @return
     */
    /*
    public static CategoryRepository getInstance() {

        // create object if it's not already created
        if(categoriesRepo == null) {
            categoriesRepo = new CategoryRepository();
        }

        // returns the singleton object
        return categoriesRepo;
    }
    */

    // required for the firestore connection
    public CategoryRepository(){

    }


    /**
     * Fetch all the Documents inside the categories collection
     */

    public void getCategories(){
        categoryList = new ArrayList<Category>();
        db = FirebaseFirestore.getInstance();

        db.collection("Categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot results = task.getResult();
                    for (Category categoryItem : task.getResult().toObjects(Category.class)) {
                        categoryList.add(categoryItem);

                    }
                    if (categoryList.size() > 0) {
                        System.out.println("Success !!!");
                        printCategories();

                    } else
                        System.out.println("The Collection was empty!");

                } else
                    System.out.println("Loading Category collection failed from Firestore!");
            }
        });



    }


    public void printCategories(){
        for (Category category : categoryList){
            System.out.println(category.GetName());
            System.out.println(category.GetColour());
            System.out.println(category.GetId());
            System.out.println(category.GetLayout());
            System.out.println(category.GetURI());
        }
    }

    public void print(){
        System.out.println("Hello");
    }


    /**
     * Fetch all the
     */

}
