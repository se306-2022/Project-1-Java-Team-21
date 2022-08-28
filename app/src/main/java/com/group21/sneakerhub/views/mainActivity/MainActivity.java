package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.LinearLayout;

import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.listActivity.ListActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * UI implementation for Main Activity
 */

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemClickListener, CategoriesAdapter.ItemClickListener2 {

    ViewHolder vh;

    List<String> categoryNames;
    List<String> categoryColours;
    List<Integer> brandImages;
    List<String> productNames ;
    List<String> productColours;
    List<Double> productPrices;
    List<Integer> productsDefaultImage;

    float x1, x2, y1, y2;

    class ViewHolder {
        EntryViewModel viewModel = new ViewModelProvider(MainActivity.this).get(EntryViewModel.class);
        TextView appName = (TextView) findViewById(R.id.app_name);
        TextView appDesc = (TextView) findViewById(R.id.app_desc);
        TextView featuredTitle = (TextView) findViewById(R.id.featured_title);
        TextView categoriesTitle = (TextView) findViewById(R.id.categories_title);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        RecyclerView featuredRecyclerView = (RecyclerView) findViewById(R.id.featured_recyclerview);
        RecyclerView categoriesRecyclerView = (RecyclerView) findViewById(R.id.categories_recyclerview);
        SearchView searchbar = (SearchView) findViewById(R.id.menu_search_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        RecyclerViewAdapter trendingProductsAdapter;
        CategoriesAdapter categoriesAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        vh = new ViewHolder();
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        vh.featuredRecyclerView.setLayoutManager(horizontalLayoutManager);

        LinearLayoutManager verticalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        vh.categoriesRecyclerView.setLayoutManager(verticalLayoutManager);

        // retrieve all data from splash activity
        Intent intent = getIntent();
        brandImages = new ArrayList<>();

        if (!"SplashActivity".equals(intent.getStringExtra("callingActivity"))){
            // retrieve all needed data from ViewModel
            categoryNames = new ArrayList<>();
            categoryColours = new ArrayList<>();
            vh.viewModel.getCategories().observe( this, categories ->
                    {
                        for (Category category : categories) {
                            categoryNames.add(category.getName());
                            categoryColours.add(category.getColour());
                            brandImages.add(this.getResources().getIdentifier(category.getImageURI(), "drawable", this.getPackageName()));
                        }

                        // set adapter for categories recycler view
                        vh.categoriesAdapter = new CategoriesAdapter(this, categoryColours, categoryNames, brandImages);
                        vh.categoriesAdapter.setClickListener(this);
                        vh.categoriesRecyclerView.setAdapter(vh.categoriesAdapter);                    }
            );

            productNames = new ArrayList<>();
            productColours = new ArrayList<>();
            productPrices = new ArrayList<>();
            productsDefaultImage = new ArrayList<>();
            vh.viewModel.getTrendingProducts().observe(this, products -> {
                for (Product p : products) {
                    productNames.add(p.getName());
                    productColours.add(p.getColor());
                    productPrices.add(p.getPrice());
                    productsDefaultImage.add(getResources().getIdentifier("s" + p.getImageUrls().get(0), "drawable", getPackageName()));
                }

                // set adapter for featured products recycler view
                vh.trendingProductsAdapter = new RecyclerViewAdapter(this, productColours, productNames, productsDefaultImage, productPrices);
                vh.trendingProductsAdapter.setClickListener(this);
                vh.featuredRecyclerView.setAdapter(vh.trendingProductsAdapter);
            });

            vh.viewModel.isFinished.observe(this, isFinished -> {
                if (!isFinished) {
                    // set screen to progress bar
                    vh.appName.setVisibility(View.GONE);
                    vh.appDesc.setVisibility(View.GONE);
                    vh.featuredTitle.setVisibility(View.GONE);
                    vh.categoriesTitle.setVisibility(View.GONE);
                    vh.searchbar.setVisibility(View.GONE);
                    vh.loadingContainer.setVisibility(View.VISIBLE);
                    vh.featuredRecyclerView.setVisibility(View.GONE);
                    vh.categoriesRecyclerView.setVisibility(View.GONE);

                } else {
                    // change from progress bar back to the activity
                    vh.appName.setVisibility(View.VISIBLE);
                    vh.appDesc.setVisibility(View.VISIBLE);
                    vh.featuredTitle.setVisibility(View.VISIBLE);
                    vh.categoriesTitle.setVisibility(View.VISIBLE);
                    vh.loadingContainer.setVisibility(View.GONE);
                    vh.searchbar.setVisibility(View.VISIBLE);
                    vh.featuredRecyclerView.setVisibility(View.VISIBLE);
                    vh.categoriesRecyclerView.setVisibility(View.VISIBLE);
                }
            });

        } else {
            categoryNames = intent.getStringArrayListExtra("categoryNames");
            categoryColours = intent.getStringArrayListExtra("categoryColours");
            brandImages = intent.getIntegerArrayListExtra("brandImages");
            productNames = intent.getStringArrayListExtra("productNames");
            productColours = intent.getStringArrayListExtra("productColours");
            productPrices = (ArrayList<Double>) intent.getSerializableExtra("productPrices");
            productsDefaultImage = intent.getIntegerArrayListExtra("productsDefaultImage");

            // set adapter for featured products recycler view
            vh.trendingProductsAdapter = new RecyclerViewAdapter(this, productColours, productNames, productsDefaultImage, productPrices);
            vh.trendingProductsAdapter.setClickListener(this);
            vh.featuredRecyclerView.setAdapter(vh.trendingProductsAdapter);


            // set adapter for categories recycler view
            vh.categoriesAdapter = new CategoriesAdapter(this, categoryColours, categoryNames, brandImages);
            vh.categoriesAdapter.setClickListener(this);
            vh.categoriesRecyclerView.setAdapter(vh.categoriesAdapter);
        }


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
        String currentColorInput = vh.trendingProductsAdapter.getColourMethod(position);

        Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
        intent.putExtra("sneakerName", vh.trendingProductsAdapter.getItem(position));
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
                Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                // remove white spaces on either end on query string
                intent.putExtra("query", query.trim());
                intent.putExtra("callingActivity", "MainActivity");
                startActivity(intent);
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
     * On click event listener for the categories recycler view, clicking on a category, takes you
     * to the list activity for that category.
     * @param view
     * @param position
     */
    @Override
    public void onItemClick2(View view, int position) {
        Intent intent = new Intent(getBaseContext(), ListActivity.class);
        intent.putExtra("brandName", vh.categoriesAdapter.getItem(position));
        intent.putExtra("brandLogo", vh.categoriesAdapter.getLogoResource(position));
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