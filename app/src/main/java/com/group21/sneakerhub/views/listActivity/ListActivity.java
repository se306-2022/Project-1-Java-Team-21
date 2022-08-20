package com.group21.sneakerhub.views.listActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.AirJordan;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.getCategories.GetCategories;
import com.group21.sneakerhub.usecases.getCategories.IGetCategories;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.mainActivity.MainViewModel;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.CustomListAdaptor;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    ViewHolder vh;
    CustomListAdaptor itemsAdapter;

    class ViewHolder{
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        LinearLayout headerBackground = (LinearLayout) findViewById(R.id.list_header_background);
        TextView brandNameHeader = (TextView) findViewById(R.id.brand_name_header);
        ListView listView = (ListView) findViewById(R.id.list_activity);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_list);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();

        vh = new ViewHolder();

        ListViewModel listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Intent intent = getIntent();
        String brandName = intent.getStringExtra("brandName");

        Category currentCategory = listViewModel.getCategory(brandName);

        // set the header text
        vh.brandNameHeader.setText(currentCategory.getName());
        // set header background color as a hex
        vh.headerBackground.setBackgroundColor(Color.parseColor(currentCategory.GetColour()));
        setBackButtonColorByCategory(currentCategory.getName());


        //populate the list view
        List<Product> productFromCategory = listViewModel.getProductsByCategoryId(currentCategory.getId());
        setAdaptorXmlByCategory(currentCategory.getName(),productFromCategory);
        vh.listView.setAdapter(itemsAdapter);


        // listener navigates to the detail activity for the specific sneaker than gets clicked on
        vh.listView.setOnItemClickListener((parent, view, position, id) -> {
            // create an intent that navigates to NumbersActivity class
            Intent detailPage = new Intent(getBaseContext(), DetailsActivity.class);

            // send the name of the sneaker so the detail activity can load it
            detailPage.putExtra("sneakerName", productFromCategory.get(position).getName());

            detailPage.putExtra("callingActivity", "ListActivity");

            //start the activity
            startActivity(detailPage);
        });

        /**
         * Implementing the loading screen while the data is being fetched from the database
         */
        listViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                vh.headerBackground.setVisibility(View.GONE);
                vh.listView.setVisibility(View.GONE);
                vh.loadingContainer.setVisibility(View.VISIBLE);

            } else {
                vh.headerBackground.setVisibility(View.VISIBLE);
                vh.listView.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.GONE);
            }
        });


        /**
         * back to the main activity
         */
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        vh.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0){
                    vh.headerBackground.setVisibility(View.GONE);
                } else {
                    vh.headerBackground.setVisibility(View.VISIBLE);
                }
            }
        });

        vh.bottomNavigationView.setSelectedItemId(R.id.search);

        // implement event listener for nav bar
        vh.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        return true;
                    case R.id.favourite:
                        startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // change visibility of toolbar
            // reference has to be here, cant be in viewholder
            LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper);
            navBarWrapper.setVisibility(View.GONE);

        } else {
            LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper);
            navBarWrapper.setVisibility(View.VISIBLE);

        }
    }

    private void setAdaptorXmlByCategory(String name, List<Product> products){

        if (name.equals("Nike")){
            itemsAdapter = new CustomListAdaptor(this, R.layout.nike_list_view,products);
        } else if (name.equals("Air Jordan")){
            itemsAdapter = new CustomListAdaptor(this, R.layout.airjordan_list_view,products);
        } else if (name.equals("Vans")){
            itemsAdapter = new CustomListAdaptor(this, R.layout.vans_list_view,products);
        } else {
            itemsAdapter = new CustomListAdaptor(this, R.layout.adidas_list_view,products);
        }
    }

    private void setBackButtonColorByCategory(String brand){
        if (brand.equals("Air Jordan")){
            vh.backButton.setBackgroundResource(R.drawable.aj_back_button);
        } else if (brand.equals("Nike")){
            vh.backButton.setBackgroundResource(R.drawable.nike_back_button);
        } else if (brand.equals("Vans")){
            vh.backButton.setBackgroundResource(R.drawable.vans_back_button);
        } else {
            vh.backButton.setBackgroundResource(R.drawable.adidas_back_button);
        }
    }
}