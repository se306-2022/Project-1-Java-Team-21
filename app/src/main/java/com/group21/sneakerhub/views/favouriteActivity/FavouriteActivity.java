package com.group21.sneakerhub.views.favouriteActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
    private List<Product> productList = new ArrayList<Product>();
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
        CustomListAdaptor itemsAdapter = new CustomListAdaptor(this, R.layout.list_view_favourites,productList);

        // getting a reference to the ListView and setting its adapter

        vh.listView.setAdapter(itemsAdapter);

        vh.listView.setOnItemClickListener((parent, view, position, id) -> {

            currentItemSelectedName = productList.get(position).getName();
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

            System.out.println(currentItemSelectedName);

        } else{
            toggleButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            System.out.println("favourite button unclicked");
        }

    }

    //ic_baseline_favorite_border_24

}