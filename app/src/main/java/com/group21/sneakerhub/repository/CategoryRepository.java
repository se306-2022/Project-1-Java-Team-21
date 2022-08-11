package com.group21.sneakerhub.repository;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository implements ICategoryRepository{


    List<String> CategoriesList = new ArrayList<String>();
    private static CategoryRepository categoriesRepo;
    /**
     * Create instance of the firestone database.
     * Should be limited to only one instance.
     */
    FirebaseFirestore db;

    /**
     * Using singleton design pattern limit the number of instances
     * created to only one.
     */

    private CategoryRepository() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Any other class that wants to use this class does so by calling this getter method.
     * If there already exists an instance, the getter returns it, if not then it creates one.
     * @return
     */
    public static CategoryRepository getInstance() {

        // create object if it's not already created
        if(categoriesRepo == null) {
            categoriesRepo = new CategoryRepository();
        }

        // returns the singleton object
        return categoriesRepo;
    }

    /**
     * Fetch all the Documents inside the categories collection
     */

    public void getCategories(){
        db.collection("Categories").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println(e.getMessage());
                }
                CategoriesList.clear();
                for (DocumentSnapshot doc : documentSnapshots) {
                    CategoriesList.add(doc.getString("name"));
                }
            }
        });
    }


    public void printCategories(){
        for (String categoryNames : CategoriesList){
            System.out.println(categoryNames);
        }
    }


    /**
     * Fetch all the
     */

}
