package com.group21.sneakerhub.repository;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.group21.sneakerhub.model.Adidas;
import com.group21.sneakerhub.model.AirJordan;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Nike;
import com.group21.sneakerhub.model.Vans;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
            categoryList = new ArrayList<Category>();
            for (Category categoryItem : categories) {
                switch (categoryItem.getName()) {
                    case "Nike":
                        categoryList.add(new Nike(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(), categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "Adidas":
                        categoryList.add(new Adidas(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(), categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "Vans":
                        categoryList.add(new Vans(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(), categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        break;
                    case "AirJordan":
                        categoryList.add(new AirJordan(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(), categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        break;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    /**
     * Get a specific Category document by querying its id field
     * and map it to a single Category object
     */
    @Override
    public Category getCategoryById(String inputId){
        try {
            return Tasks.await(db.collection("Categories").document(inputId).get()).toObject(Category.class);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Test method to see that data is actually fetched and stored in the list
     */
    private void printCategories(List<Category> categoryList){
        for (Category category : categoryList){
            System.out.println(category.getName());
            System.out.println(category.GetColour());
            System.out.println(category.getId());
            System.out.println(category.GetURI());
            System.out.println(category.getLayoutInformation());
        }
    }


}
