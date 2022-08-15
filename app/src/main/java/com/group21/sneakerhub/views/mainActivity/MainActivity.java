package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener {

    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<Integer> viewColors = new ArrayList<>();
        viewColors.add(Color.BLUE);
        viewColors.add(Color.YELLOW);
        viewColors.add(Color.MAGENTA);
        viewColors.add(Color.RED);

        ArrayList<String> brandNames = new ArrayList<>();
        brandNames.add("Air Jordan");
        brandNames.add("Nike");
        brandNames.add("Adidas");
        brandNames.add("Vans");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvBrands);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RecyclerViewAdapter(this, viewColors, brandNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);



//        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//
//        mainViewModel.isLoading.observe(this, isLoading -> {
//            if (isLoading) {
//                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
//            } else {
//                findViewById(R.id.progress_bar).setVisibility(View.GONE);
//            }
//        });


//        mainViewModel.getTrendingProducts().observe(this, products -> {
//            for (Product product : products) {
//                System.out.println(product.getName());
//            }
//        });
//
//        mainViewModel.getSearchedProducts("Air JORDan").observe(this, products -> {
//            for (Product product : products) {
//                System.out.println(product.getName());
//            }
//        });

//        mainViewModel.getProductsByCategoryId(1).observe(this, products -> {
//            for (Product product : products) {
//                System.out.println(product.getName());
//            }
//        });


        // Initialize and assign object for nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // implement event listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchFilterActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favourite:
                        startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }
}