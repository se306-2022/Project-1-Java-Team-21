package com.group21.sneakerhub.views.mainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.repository.CategoryRepository;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // test Category Respository class
        CategoryRepository cr = CategoryRepository.getInstance();
        cr.getCategories();


    }
}