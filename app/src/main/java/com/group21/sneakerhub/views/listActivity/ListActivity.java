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
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
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
        listViewModel.setCurrentBrandName(brandName);

        Category currentCategory = listViewModel.getCategory();

        // set the header text
        vh.brandNameHeader.setText(currentCategory.getName());
        // set header background color as a hex
        vh.headerBackground.setBackgroundColor(Color.parseColor(currentCategory.GetColour()));

        //populate the list view
        List<Product> productFromCategory = listViewModel.getProductsByCategoryId(currentCategory.getId());
        setAdaptorXmlByCategory(currentCategory.getName(),productFromCategory);
        vh.listView.setAdapter(itemsAdapter);

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
            LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper_fav);
            navBarWrapper.setVisibility(View.GONE);

        } else {
            LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper_fav);
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
}