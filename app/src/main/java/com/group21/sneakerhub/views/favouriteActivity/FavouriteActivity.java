package com.group21.sneakerhub.views.favouriteActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.CustomListAdaptor;
import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    ViewHolder vh;
    private String currentItemSelectedName;
    private static List<Product> products;

    class ViewHolder{
        ListView listView = (ListView) findViewById(R.id.list_favourites);
        ToggleButton heartShoe = (ToggleButton) findViewById(R.id.heart_button);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().hide();

        vh = new ViewHolder();


        FavouriteViewModel favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        favouriteViewModel.getFavouriteProducts().observe(this, productList -> {
            //products = productList;
            // declaring the arrayadapter and setting the data
            // the second argument in the ArrayAdapter is the layout you want to use
            // we use the custom one we made in the layout folder
            // simple arrayadapter takes list of strings as its default input
            CustomListAdaptor itemsAdapter = new CustomListAdaptor(this, R.layout.list_view_favourites,productList);

            // getting a reference to the ListView and setting its adapter

            vh.listView.setAdapter(itemsAdapter);

            vh.listView.setOnItemClickListener((parent, view, position, id) -> {
                currentItemSelectedName = productList.get(position).getName();
            });
        });

        // Initialize and assign object for nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.favourite);

        // implement event listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchFilterActivity.class));
                        overridePendingTransition(0,0);
                    case R.id.favourite:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });


    }




    public void heartButtonToggle(View v){

        // use typecast to change v from a generic view to of type togglebutton
        ToggleButton toggleButton = (ToggleButton) v;
        if(toggleButton.isChecked()){
            toggleButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
           // int index = Integer.parseInt(toggleButton.getTag().toString());
           // System.out.println(index);
           // System.out.println(products.get(index).getName());

        } else{
            toggleButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

        }

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

}