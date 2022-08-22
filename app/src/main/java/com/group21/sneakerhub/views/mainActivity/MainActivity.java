package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.LinearLayout;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.repository.CategoryRepository;
import com.group21.sneakerhub.repository.ProductRepository;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteViewModel;
import com.group21.sneakerhub.views.listActivity.ListActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, CategoriesAdapter.ItemClickListener2 {


    private RecyclerViewAdapter adapter;
    private CategoriesAdapter adapter2;
    private List<Product> featured;
    ViewHolder vh;

    float x1, x2, y1, y2;

    class ViewHolder {
        TextView textView1 = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView4);
        TextView textView3 = (TextView) findViewById(R.id.textView5);
        TextView textView4 = (TextView) findViewById(R.id.textView6);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        RecyclerView rv1 = (RecyclerView) findViewById(R.id.rvBrands);
        RecyclerView rv2 = (RecyclerView) findViewById(R.id.rvBrands2);
        SearchView searchbar = (SearchView) findViewById(R.id.menu_search_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        vh = new ViewHolder();


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
        mainViewModel.getTrendingProducts().observe(this, productList -> {


            ArrayList<String> brandNames = new ArrayList<>();
            ArrayList<String> productColors = new ArrayList<>();
            ArrayList<Double> productPrices = new ArrayList<>();
            ArrayList<Integer> brandImages = new ArrayList<>();

            for (Product p : productList) {
                brandNames.add(p.getName());
                productColors.add(p.getColor());
                productPrices.add(p.getPrice());
                brandImages.add(getResources().getIdentifier("s" + p.getImageUrls().get(0), "drawable", getPackageName()));
            }


            RecyclerView recyclerView = findViewById(R.id.rvBrands);
            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(horizontalLayoutManager);
            adapter = new RecyclerViewAdapter(this, productColors, brandNames, brandImages, productPrices);
            //adapter = new RecyclerViewAdapter(this, productImages, productNames, productColors, productPrices);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
        });


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

        // set up the loading screen
        mainViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                // set screen to progress bar
                vh.textView1.setVisibility(View.GONE);
                vh.textView2.setVisibility(View.GONE);
                vh.textView3.setVisibility(View.GONE);
                vh.textView4.setVisibility(View.GONE);
                vh.searchbar.setVisibility(View.GONE);
                vh.loadingContainer.setVisibility(View.VISIBLE);
                vh.rv1.setVisibility(View.GONE);
                vh.rv2.setVisibility(View.GONE);

            } else {
                // change from progress bar back to the activity
                vh.textView1.setVisibility(View.VISIBLE);
                vh.textView2.setVisibility(View.VISIBLE);
                vh.textView3.setVisibility(View.VISIBLE);
                vh.textView4.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.GONE);
                vh.searchbar.setVisibility(View.VISIBLE);
                vh.rv1.setVisibility(View.VISIBLE);
                vh.rv2.setVisibility(View.VISIBLE);
            }
        });


        RecyclerView recyclerView2 = findViewById(R.id.rvBrands2);
        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(verticalLayoutManager);
        adapter2 = new CategoriesAdapter(this, colorsListView, countryList, imagesListView);
        adapter2.setClickListener2(this);
        recyclerView2.setAdapter(adapter2);

        // searchbar edit text listener
        /*
        vh.searchbar.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // keyevent on return key pressed by user
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // checks whether the search is empty
                    if (vh.searchbar.getText().toString() != null && !(vh.searchbar.getText().toString().trim().isEmpty())) {
                        Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                        // remove white spaces on either end on query string
                        intent.putExtra("query", vh.searchbar.getText().toString().trim());
                        intent.putExtra("callingActivity", "MainActivity");
                        startActivity(intent);
                    } else {
                        // informs user that search is empty
                        Toast.makeText(getApplicationContext(), "Search query is empty", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
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

        System.out.println("Position: " + position + " Name: " + adapter.getItem(position));

        String currentColorInput = adapter.getColourMethod(position);

        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra("sneakerName", adapter.getItem(position));
        intent.putExtra("callingActivity", "MainActivity");
        intent.putExtra("currentColour", currentColorInput);

        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        vh.searchbar.setQueryHint("Search for sneaker...");
        vh.searchbar.setSubmitButtonEnabled(false);

        vh.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // when user hits return the final search string is returned
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query != null && !(query.trim().isEmpty())) {
                    Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                    // remove white spaces on either end on query string
                    intent.putExtra("query", query);
                    intent.putExtra("callingActivity", "MainActivity");
                    startActivity(intent);
                } else {
                    // informs user that search is empty
                    Toast.makeText(getApplicationContext(), "Search query is empty", Toast.LENGTH_LONG).show();
                }
                return true;
            }

            // updates everytime a character changes in the searchbox
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });


        // Assumes current activity is the searchable activity
        vh.searchbar.setSearchableInfo(vh.searchManager.getSearchableInfo(getComponentName()));
        vh.searchbar.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    @Override
    public void onItemClick2(View view, int position) {
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("brandName", adapter2.getItem(position));
        startActivity(intent);

    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 > x2){
                    Intent intent = new Intent(getBaseContext(), SearchFilterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                break;
        }
        return false;

    }

}