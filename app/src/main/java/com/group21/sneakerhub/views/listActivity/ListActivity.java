package com.group21.sneakerhub.views.listActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.CustomListAdaptor;

import java.util.List;

/**
 * UI Implementation class for List Activity
 */

public class ListActivity extends AppCompatActivity {
    ViewHolder vh;
    CustomListAdaptor itemsAdapter;
    ListViewModel listViewModel;

    private class ViewHolder{
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        LinearLayout headerBackground = (LinearLayout) findViewById(R.id.list_header_background);
        TextView brandNameHeader = (TextView) findViewById(R.id.brand_name_header);
        ListView listView = (ListView) findViewById(R.id.list_activity);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_list);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper);
        ImageView brandLogo = (ImageView) findViewById(R.id.list_brand_logo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getSupportActionBar().hide();

        vh = new ViewHolder();

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Intent intent = getIntent();
        String brandName = intent.getStringExtra("brandName");
        int brandLogoImage = intent.getIntExtra("brandLogo",0);

        Category currentCategory = listViewModel.getCategory(brandName);

        // set the header text
        vh.brandNameHeader.setText(currentCategory.getName());
        // set header background color as a hex
        vh.headerBackground.setBackgroundColor(Color.parseColor(currentCategory.getColour()));
        setBackButtonColorByCategory(currentCategory.getName());
        // set the brand logo image
        vh.brandLogo.setBackgroundResource(brandLogoImage);

        //populate the list view
        List<Product> productFromCategory = listViewModel.getProductsByCategoryId(currentCategory.getId());
        setAdaptorXmlByCategory(currentCategory.getName(),productFromCategory);
        vh.listView.setAdapter(itemsAdapter);


        /**
         * listener navigates to the detail activity for the specific sneaker than gets clicked on
         */
        vh.listView.setOnItemClickListener((parent, view, position, id) -> {
            // create an intent that navigates to NumbersActivity class
            Intent detailPage = new Intent(getBaseContext(), DetailsActivity.class);

            // send the name of the sneaker so the detail activity can load it
            detailPage.putExtra("sneakerName", productFromCategory.get(position).getName());
            detailPage.putExtra("currentColour", productFromCategory.get(position).getColor());
            detailPage.putExtra("callingActivity", "ListActivity");

            detailPage.putExtra("brandName", brandName);

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


        /**
         * Implementation of logic to switch activities on click of the buttons of the navigation bar,
         * fixed to the bottom of the activity.
         */
        vh.bottomNavigationView.setSelectedItemId(R.id.home);
        // implement event listener for nav bar
        vh.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
            vh.navBarWrapper.setVisibility(View.GONE);
            vh.headerBackground.setVisibility(View.GONE);

        } else {
            vh.navBarWrapper.setVisibility(View.VISIBLE);
            vh.headerBackground.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Inserting the xml for items for the list view adaptor according to the current category
     * @param name
     * @param products
     */
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

    /**
     * Setting the background colour of the back arrow button according to the category, as the top
     * banner has a distinct colour for each category
     * @param brand
     */
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