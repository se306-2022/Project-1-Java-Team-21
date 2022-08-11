package com.group21.sneakerhub.views.mainActivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.repository.CategoryRepository;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test Category Respository class
        CategoryRepository cr = CategoryRepository.getInstance();

        // have to test getCategoryById on a new thread
        // because the tasks class prevents the method from being called on the main thread
        Thread thread1 = new Thread(new Runnable(){
            @Override
            public void run() {

                Category category = cr.getCategoryById(312412);
                System.out.println(category.GetName());
                System.out.println(category.GetColour());
                System.out.println(category.GetId());
                System.out.println(category.GetLayout());
                System.out.println(category.GetURI());
            }

        });

        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}