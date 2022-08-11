package com.group21.sneakerhub.views.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.repository.CategoryRepository;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // testing if the data is being retrieved from firestore
        CategoryRepository gc = CategoryRepository.getInstance();
        gc.getCategories();
        gc.printCategories();


    }
}