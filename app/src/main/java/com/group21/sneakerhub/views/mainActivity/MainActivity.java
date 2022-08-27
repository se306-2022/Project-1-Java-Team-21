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
    private ArrayList<String> countryList;
    private ArrayList<String> brandNames;
    private ArrayList<String> productColors;
    private ArrayList<Double> productPrices;
    private ArrayList<Integer> brandImages;

    ViewHolder vh;
    MainViewModel mainViewModel;

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
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        vh = new ViewHolder();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        /**
         * populating the category recycler view with data from the database.
         */
        countryList = new ArrayList<>();
        mainViewModel.getCategories().observe( this, categoryList -> {
            for (Category c : categoryList){
                countryList.add(c.getName());
            }
        });

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

        /**
         * Populating the Trending/ featured products recycler view with data from the
         * db.
         */
        mainViewModel.getTrendingProducts().observe(this, productList -> {

            brandNames = new ArrayList<>();
            productColors = new ArrayList<>();
            productPrices = new ArrayList<>();
            brandImages = new ArrayList<>();

            for (Product p : productList) {
                brandNames.add(p.getName());
                productColors.add(p.getColor());
                productPrices.add(p.getPrice());
                brandImages.add(getResources().getIdentifier("s" + p.getImageUrls().get(0), "drawable", getPackageName()));
            }

            LinearLayoutManager horizontalLayoutManager
                    = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
            vh.rv1.setLayoutManager(horizontalLayoutManager);
            adapter = new RecyclerViewAdapter(this, productColors, brandNames, brandImages, productPrices);
            adapter.setClickListener(this);
            vh.rv1.setAdapter(adapter);
        });


        /**
         * Toggling the visibility of all the views in the main activity,
         * while the data is being fetched from the database.
         */
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



        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        vh.rv2.setLayoutManager(verticalLayoutManager);
        adapter2 = new CategoriesAdapter(this, colorsListView, countryList, imagesListView);
        adapter2.setClickListener2(this);
        vh.rv2.setAdapter(adapter2);


        /**
         * Implementation of logic to switch activities on click of the buttons of the navigation bar,
         * fixed to the bottom of the activity.
         */
        // Set Home selected
        vh.bottomNavigationView.setSelectedItemId(R.id.home);

        // implement event listener
        vh.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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

    /**
     * On click event listener for the featured sneakers recycler view in the main activity,
     * clicking on a sneaker takes you to the details page of the sneaker.
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {
        String currentColorInput = adapter.getColourMethod(position);

        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra("sneakerName", adapter.getItem(position));
        intent.putExtra("callingActivity", "MainActivity");
        intent.putExtra("currentColour", currentColorInput);

        startActivity(intent);
    }

    /**
     * Method handles the configuration of the searchbar at the top of the main activity.
     * Pressing the enter button on the physical keyboard takes you to the searchresults list
     * activity for that query string.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        vh.searchbar.setSubmitButtonEnabled(false);
        vh.searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // when user hits return the final search string is returned
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (query != null && !(query.trim().isEmpty())) {
                    Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                    // remove white spaces on either end on query string
                    intent.putExtra("query", query.trim());
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

    /**
     * On click event listener for the categories recycler view, clickin on a category, takes you
     * to the list activity for that category.
     * @param view
     * @param position
     */
    @Override
    public void onItemClick2(View view, int position) {
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("brandName", adapter2.getItem(position));
        startActivity(intent);

    }

    /**
     * event listener which takes you to the searchfilter activity when the user performs a
     * slide motion with their finger on the screen.
     * @param touchEvent
     * @return
     */
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