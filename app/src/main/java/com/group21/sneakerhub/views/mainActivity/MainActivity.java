package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        /*
        mainViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {

            } else {

            }
        });


        mainViewModel.getTrendingProducts().observe(this, products -> {
            for (Product product : products) {
                System.out.println(product.getName());
            }
        });

        mainViewModel.getSearchedProducts("Air JORDan").observe(this, products -> {
            for (Product product : products) {
                System.out.println(product.getName());
            }
        });

        mainViewModel.getProductsByCategoryId(1).observe(this, products -> {
            for (Product product : products) {
                System.out.println(product.getName());
            }
        });
        */




        // Initialize and assign object for nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // implement event listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchFilterActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.favourite:
                        startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

    }

}