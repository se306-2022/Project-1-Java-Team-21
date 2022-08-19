package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import android.widget.LinearLayout;

import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.listActivity.ListActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, CategoriesAdapter.ItemClickListener2 {

    private RecyclerViewAdapter adapter;
    private CategoriesAdapter adapter2;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        ArrayList<String> countryList = new ArrayList<>();
        countryList.add("Air Jordan");
        countryList.add("Nike");
        countryList.add("Adidas");
        countryList.add("Vans");

        ArrayList<Integer> colorsListView = new ArrayList<>();
        colorsListView.add(Color.rgb(242,228,255));
        colorsListView.add(Color.rgb(255,199,195));
        colorsListView.add(Color.rgb(218,255,208));
        colorsListView.add(Color.rgb(195,226,255));

        ArrayList<Integer> imagesListView = new ArrayList<>();
        imagesListView.add(R.drawable.airjordan_logo);
        imagesListView.add(R.drawable.nike_logo);
        imagesListView.add(R.drawable.adidas_logo);
        imagesListView.add(R.drawable.vans_logo);




        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


//
//        mainViewModel.isLoading.observe(this, isLoading -> {
//            if (isLoading) {
//                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
//            } else {
//                findViewById(R.id.progress_bar).setVisibility(View.GONE);
//           }
//        });


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


//        mainViewModel.getProductsByCategoryId(1).observe(this, products -> {
//            for (Product product : products) {
//                System.out.println(product.getName());
//            }
//        });



        ArrayList<Product> products = new ArrayList<>();
        List<Integer> sizes = new ArrayList<Integer>() {{
            add(7);
            add(8);
        } };

        List<String> featuress = new ArrayList<String>() {{
            add("z");
            add("b");
        } };

        List<String> images = new ArrayList<String>() {{
            add("test_1");
            add("test_11");
        } };

        List<String> images2 = new ArrayList<String>() {{
            add("test_2");
            add("test_11");
        } };

        List<String> images3 = new ArrayList<String>() {{
            add("test_3");
            add("test_11");
        } };
//
        Product p1 = new Product("Air Jordan 4", 100, 1, 450.00, "White", sizes, 4.5, 7, false, images, "good",  featuress, true);
        Product p2 = new Product("Yeezy 350", 100, 1, 520.00, "Black", sizes, 4.5, 7, false, images2, "good",  featuress, false);
        Product p3 = new Product("Air Jordan 1", 100, 1, 550.00, "Red", sizes, 4.5, 7, false, images3, "good",  featuress, true);

        ArrayList<String> brandNames = new ArrayList<>();
        brandNames.add(p1.getName());
        brandNames.add(p2.getName());
        brandNames.add(p3.getName());

        ArrayList<String> productColors = new ArrayList<>();
        productColors.add(p1.getColor());
        productColors.add(p2.getColor());
        productColors.add(p3.getColor());

        ArrayList<Double> productPrices = new ArrayList<>();
        productPrices.add(p1.getPrice());
        productPrices.add(p2.getPrice());
        productPrices.add(p3.getPrice());

        ArrayList<Integer> brandImages = new ArrayList<>();
        brandImages.add(getResources().getIdentifier(p1.getImageUrls().get(0), "drawable", getPackageName()));
        brandImages.add(getResources().getIdentifier(p2.getImageUrls().get(0), "drawable", getPackageName()));
        brandImages.add(getResources().getIdentifier(p3.getImageUrls().get(0), "drawable", getPackageName()));

         //data to populate the RecyclerView with




//        ArrayList<String> brandNames = new ArrayList<>();
//        brandNames.add("Air Jordan");
//        brandNames.add("Nike");
//        brandNames.add("Adidas");
       // brandNames.add("Vans");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvBrands);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new RecyclerViewAdapter(this, productColors, brandNames, brandImages, productPrices);
        //adapter = new RecyclerViewAdapter(this, productImages, productNames, productColors, productPrices);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        RecyclerView recyclerView2 = findViewById(R.id.rvBrands2);
        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(verticalLayoutManager);
        adapter2 = new CategoriesAdapter(this, colorsListView, countryList, imagesListView);
        adapter2.setClickListener2(this);
        recyclerView2.setAdapter(adapter2);

//        RecyclerView recyclerView2 = findViewById(R.id.rvBrands2);
//        LinearLayoutManager horizontalLayoutManager2
//                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView2.setLayoutManager(horizontalLayoutManager2);
//        adapter2 = new CategoriesAdapter(this, colorsListView, countryList, imagesListView);
//        adapter2.setClickListener2(this);
//        recyclerView2.setAdapter(adapter2);

//        RecyclerView recyclerView = findViewById(R.id.rvFeatured);
//        LinearLayoutManager verticalLayoutManager
//                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(horizontalLayoutManager);
//        adapter = new RecyclerViewAdapter(this, productImages, productNames, productColors, productPrices);
//        //adapter = new RecyclerViewAdapter(this, viewColors, brandNames, brandImages);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);



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

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getBaseContext(), ListActivity.class);
//        intent.putExtra("brandName", adapter.getItem(position));
//        startActivity(intent);
        startActivity(new Intent(getApplicationContext(), DetailsActivity.class));
        overridePendingTransition(0, 0);
    }

    @Override
    public void onItemClick2(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter2.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();

//=======
//
//        Intent intent = new Intent(getBaseContext(), ListActivity.class);
//        intent.putExtra("brandName", adapter.getItem(position));
//        startActivity(intent);
//>>>>>>> development
    }


}