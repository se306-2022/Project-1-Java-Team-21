package com.group21.sneakerhub.views.searchResultListActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * UI implementation for Search Result List Activity
 */

public class SearchResultListActivity extends AppCompatActivity {

    ViewHolder vh;
    private String query;
    private ArrayList<String> colours;
    private ArrayList<String> brands;
    private int lowerPrice;
    private int upperPrice;
    private String callingActivity;
    SearchResultListViewModel searchResultVM;

    private class ViewHolder{
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper);
        LinearLayout collapseItem1 = (LinearLayout) findViewById(R.id.collapse_item_1);
        TextView collapseItem2 = (TextView) findViewById(R.id.collapse_item_2);
        TextView collapseItem3 = (TextView) findViewById(R.id.collapse_item_3);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_search_result);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        ListView listView = (ListView) findViewById(R.id.list);
        TextView loadingText = (TextView)  findViewById(R.id.loading_text);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);
        // hide the top tool bar in the activity
        getSupportActionBar().hide();

        vh = new ViewHolder();

        searchResultVM = new ViewModelProvider(this).get(SearchResultListViewModel.class);

        Intent intent = getIntent();

        /**
         * Getting the user input data from the previous activity which is the searchfilter activity
         */
        query = intent.getStringExtra("query") != null ? intent.getStringExtra("query").trim() : null;
        colours = intent.getStringArrayListExtra("colours");
        brands = intent.getStringArrayListExtra("brands");
        lowerPrice = intent.getIntExtra("lowerPrice",0);
        upperPrice = intent.getIntExtra("upperPrice",1000);
        callingActivity = intent.getStringExtra("callingActivity");

        if ("SearchFilterActivity".equals(callingActivity) || ("DetailsActivity".equals(callingActivity))) {
            searchResultVM.getProductsBySearchFilter(query, brands, colours, lowerPrice, upperPrice).observe(this, searchResults -> {

                // Add a toast message if the search result is empty
                if (searchResults.size() == 0){
                    Toast.makeText(getApplicationContext(), "There are no sneakers matching query", Toast.LENGTH_LONG).show();
                }

                CustomListAdaptor itemsAdapter = new CustomListAdaptor(this, R.layout.list_view_row_results, searchResults);

                // getting a reference to the ListView and setting its adapter
                vh.listView.setAdapter(itemsAdapter);

                // set the header text based on the query
                vh.collapseItem2.setText("Showing " + searchResults.size() + " search results for");
                String searchText = query == null ? "Empty Search Input" : "\"" + query + "\"";
                vh.collapseItem3.setText(searchText);
                // listener navigates to the detail activity for the specific sneaker than gets clicked on
                vh.listView.setOnItemClickListener((parent, view, position, id) -> {
                    // create an intent that navigates to NumbersActivity class
                    Intent detailPage = new Intent(getBaseContext(), DetailsActivity.class);

                    // send the name of the sneaker so the detail activity can load it
                    detailPage.putExtra("sneakerName", searchResults.get(position).getName());
                    detailPage.putExtra("currentColour", searchResults.get(position).getColor());

                    detailPage.putExtra("callingActivity", "SearchResultListActivity");

                    // sending listview arguments to the details activity, so the details activity can
                    // send back if and when the back button is pressed
                    detailPage.putExtra("query", query);
                    detailPage.putExtra("colours", colours);
                    detailPage.putExtra("brands", brands);
                    detailPage.putExtra("lowerPrice", lowerPrice);
                    detailPage.putExtra("upperPrice", upperPrice);

                    //start the activity
                    startActivity(detailPage);
                });

            });
        } else {
            searchResultVM.getProductsbySearchString(query).observe(this, searchResults -> {
                CustomListAdaptor itemsAdapter = new CustomListAdaptor(this, R.layout.list_view_row_results, searchResults);

                // Add a toast message if the search result is empty
                if (searchResults.size() == 0){
                    Toast.makeText(getApplicationContext(), "There are no sneakers matching query", Toast.LENGTH_LONG).show();
                }

                // getting a reference to the ListView and setting its adapter
                vh.listView.setAdapter(itemsAdapter);

                vh.collapseItem2.setText("Showing " + searchResults.size() + " search results for");
                String searchText = query == null ? "Empty Search Input" : "\"" + query + "\"";
                vh.collapseItem3.setText(searchText);

                // listener navigates to the detail activity for the specific sneaker than gets clicked on
                vh.listView.setOnItemClickListener((parent, view, position, id) -> {
                    // create an intent that navigates to NumbersActivity class
                    Intent detailPage = new Intent(getBaseContext(), DetailsActivity.class);

                    // send the name of the sneaker so the detail activity can load it
                    detailPage.putExtra("sneakerName", searchResults.get(position).getName());
                    detailPage.putExtra("currentColour", searchResults.get(position).getColor());

                    detailPage.putExtra("callingActivity", "SearchResultListActivity");

                    // sending listview arguments to the details activity, so the details activity can
                    // send back if and when the back button is pressed
                    detailPage.putExtra("query", query);
                    detailPage.putExtra("colours", colours);
                    detailPage.putExtra("brands", brands);
                    detailPage.putExtra("lowerPrice", lowerPrice);
                    detailPage.putExtra("upperPrice", upperPrice);

                    //start the activity
                    startActivity(detailPage);
                });
            });
        }


        /**
         * back to the search filter screen button listener
         */
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals("SearchFilterActivity")) {
                    startActivity(new Intent(getApplicationContext(), SearchFilterActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });

        /**
         * Implementing the loading screen while the data is being fetched from the database
         */
        searchResultVM.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                // set screen to progress bar
                vh.progressBar.setVisibility(View.VISIBLE);
                vh.listView.setVisibility(View.GONE);
                vh.collapseItem2.setVisibility(View.GONE);
                vh.collapseItem3.setVisibility(View.GONE);
                vh.loadingText.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.VISIBLE);

            } else {
                // change from progress bar back to the activity
                vh.progressBar.setVisibility(View.GONE);
                vh.loadingText.setVisibility(View.GONE);
                vh.listView.setVisibility(View.VISIBLE);
                vh.collapseItem2.setVisibility(View.VISIBLE);
                vh.collapseItem3.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.GONE);
            }
        });


        /**
         * Implementation of logic to switch activities on click of the buttons of the navigation bar,
         * fixed to the bottom of the activity.
         */
        vh.bottomNavigationView.setSelectedItemId(R.id.search);

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

    /**
     * Toggling the visibility of the navbar at the bottom of the activity when the user changes
     * the orientation to landscape, this is to ensure the screen has more real estate to show the
     * items in the list view.
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // change visibility of toolbar
            vh.navBarWrapper.setVisibility(View.GONE);
        } else {

            vh.navBarWrapper.setVisibility(View.VISIBLE);
        }
    }

}