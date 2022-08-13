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
        categoryList = new ArrayList<Category>();

        db.collection("Categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot results = task.getResult();
                    for (Category categoryItem : task.getResult().toObjects(Category.class)) {
                        if (categoryItem.getName().equals("Nike")){
                            categoryList.add(new Nike(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(),categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        } else if (categoryItem.getName().equals("Adidas")){
                            categoryList.add(new Adidas(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(),categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        } else if (categoryItem.getName().equals("Vans")){
                            categoryList.add(new Vans(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(),categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        } else if (categoryItem.getName().equals("AirJordan")){
                            categoryList.add(new AirJordan(categoryItem.getName(), categoryItem.getId(), categoryItem.GetURI(),categoryItem.GetColour(), categoryItem.getLayoutInformation()));
                        }

                    }
                    if (categoryList.size() > 0) {
//                        System.out.println("Success !!!");
//                        printCategories(categoryList);

                    } else
                        System.out.println("The Collection was empty!");

                } else
                    System.out.println("Loading Category collection failed from Firestore!");
            }
        });

        return categoryList;
    }

    /**
     * Get a specific Category document by querying its id field
     * and map it to a single Category object
     */
    @Override
    public Category getCategoryById(String inputId){
        try {
            Category selectedCategory = Tasks.await(db.collection("Categories").document(inputId).get()).toObject(Category.class);
            return selectedCategory;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e){
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
