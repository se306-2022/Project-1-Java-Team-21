package com.group21.sneakerhub.views.searchResultListActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import java.util.ArrayList;
import java.util.List;

public class SearchResultListActivity extends AppCompatActivity {

    ViewHolder vh;


    class ViewHolder{
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper);
        LinearLayout collapseItem1 = (LinearLayout) findViewById(R.id.collapse_item_1);
        TextView collapseItem2 = (TextView) findViewById(R.id.collapse_item_2);
        TextView collapseItem3 = (TextView) findViewById(R.id.collapse_item_3);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_list);
        // hide the top tool bar in the activity
        getSupportActionBar().hide();

        vh = new ViewHolder();

         /*
            Getting the data and converting it to a list of Products, hard coded data for now
         */
        List<Product> productList = new ArrayList<Product>();

        // Product(long id, long categoryId, String imageURL, double price, String color, List<Integer> availableSizes, double rating, int numberOfUsersRated,
        // boolean isFavourite, List<String> imageUrls, String description, List<String> features, boolean isFirst)

        List<Integer> availableSizes = new ArrayList<Integer>();
        availableSizes.add(10);
        availableSizes.add(11);
        availableSizes.add(12);

        List<String> imageUrls = new ArrayList<String>();
        imageUrls.add("dqwdw");

        List<String> features = new ArrayList<String>();
        features.add("very nice");

        productList.add(new Product("Adidas Yeezy 450",1,1,"yeezy_img_1",200.22,"Wine Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("Air Force One",1,1,"airforce_1",230.00,"Snow White",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar3",1,1,"yeezy_img_1",200.22,"Orange",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar4",1,1,"yeezy_img_1",219.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar5",1,1,"yeezy_img_1",200.22,"Zebra",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar6",1,1,"yeezy_img_1",300.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar7",1,1,"yeezy_img_1",200.22,"Crimson",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar8",1,1,"yeezy_img_1",200.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar9",1,1,"yeezy_img_1",400.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar10",1,1,"yeezy_img_1",200.22,"Indigo",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar11",1,1,"yeezy_img_1",200.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar12",1,1,"yeezy_img_1",200.2222,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar13",1,1,"yeezy_img_1",200.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar14",1,1,"yeezy_img_1",200.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));
        productList.add(new Product("ar15",1,1,"yeezy_img_1",200.22,"Red",availableSizes,10.22,5,true,imageUrls, "very nice shoe", features, true));

        // declaring the arrayadapter and setting the data
        // the second argument in the ArrayAdapter is the layout you want to use
        // we use the custom one we made in the layout folder
        // simple arrayadapter takes list of strings as its default input
        CustomListAdaptor itemsAdapter = new CustomListAdaptor(this, R.layout.list_view_row_results,productList);

        // getting a reference to the ListView and setting its adapter
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {

            System.out.println("hi");
            if (position == 0){
                System.out.println("Adidas Yeezy 450");
            }
        });


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

        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem != 0){
                    vh.collapseItem1.setVisibility(View.GONE);
                    vh.collapseItem2.setVisibility(View.GONE);
                   vh.collapseItem3.setVisibility(View.GONE);
                } else {
                    vh.collapseItem1.setVisibility(View.VISIBLE);
                    vh.collapseItem2.setVisibility(View.VISIBLE);
                    vh.collapseItem3.setVisibility(View.VISIBLE);
                }
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


}