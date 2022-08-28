package com.group21.sneakerhub.repository.implementations;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group21.sneakerhub.model.Adidas;
import com.group21.sneakerhub.model.AirJordan;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Nike;
import com.group21.sneakerhub.model.Vans;
import com.group21.sneakerhub.repository.abstractions.ICategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Category repository class which implements methods from the category database
 */

public class CategoryRepository implements ICategoryRepository {

    private List<Category> categoryList;
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

    @Override
    public List<Category> getCategories(){
        try {
            List<Category> categories = Tasks.await(db.collection("Categories").get()).toObjects(Category.class);
            categoryList = new ArrayList<>();
            for (Category categoryItem : categories) {
                switch (categoryItem.getName()) {
                    case "Nike":
                        categoryList.add(new Nike(categoryItem.getName(), categoryItem.getId(), categoryItem.getImageURI(), categoryItem.getColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "Adidas":
                        categoryList.add(new Adidas(categoryItem.getName(), categoryItem.getId(), categoryItem.getImageURI(), categoryItem.getColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "Vans":
                        categoryList.add(new Vans(categoryItem.getName(), categoryItem.getId(), categoryItem.getImageURI(), categoryItem.getColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "Air Jordan":
                        categoryList.add(new AirJordan(categoryItem.getName(), categoryItem.getId(), categoryItem.getImageURI(), categoryItem.getColour(), categoryItem.getLayoutInformation()));
                        break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

}
