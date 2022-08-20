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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.detailsActivity.DetailsActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.CustomListAdaptor;
import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    ViewHolder vh;
    private static List<Product> products;
    FavouriteViewModel favouriteViewModel;
    float x1, x2, y1, y2;

    class ViewHolder{
        ListView listView = (ListView) findViewById(R.id.list_favourites);
        TextView header = (TextView) findViewById(R.id.favourite_header);
        LinearLayout collapseReturnArrow = (LinearLayout) findViewById(R.id.collapse_item_1);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_favourites);
        TextView loadingText = (TextView)  findViewById(R.id.loading_text);
        LinearLayout loadingContainer = (LinearLayout) findViewById(R.id.loading_container);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        getSupportActionBar().hide();

        vh = new ViewHolder();


        favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        favouriteViewModel.getFavouriteProducts().observe(this, productList -> {
            products = productList;
            // declaring the arrayadapter and setting the data
            // the second argument in the ArrayAdapter is the layout you want to use
            // we use the custom one we made in the layout folder
            // simple arrayadapter takes list of strings as its default input
            FavouriteListAdaptor itemsAdapter = new FavouriteListAdaptor(this, R.layout.list_view_favourites,productList);

            // getting a reference to the ListView and setting its adapter

            vh.listView.setAdapter(itemsAdapter);


            // listener navigates to the detail activity for the specific sneaker than gets clicked on
            vh.listView.setOnItemClickListener((parent, view, position, id) -> {
                // create an intent that navigates to NumbersActivity class
                Intent detailPage = new Intent(getBaseContext(), DetailsActivity.class);

                // send the name of the sneaker so the detail activity can load it
                detailPage.putExtra("sneakerName", productList.get(position).getName());

                detailPage.putExtra("callingActivity", "FavouriteActivity");

                //start the activity
                startActivity(detailPage);
            });
        });

        // back to home screen listener
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        /**
         * Implementing the loading screen while the data is being fetched from the database
         */
        favouriteViewModel.isLoading.observe(this, isLoading -> {
            if (isLoading) {
                // set screen to progress bar
                vh.progressBar.setVisibility(View.VISIBLE);
                vh.listView.setVisibility(View.GONE);
                vh.header.setVisibility(View.GONE);
                vh.loadingText.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.VISIBLE);

            } else {
                // change from progress bar back to the activity
                vh.progressBar.setVisibility(View.GONE);
                vh.loadingText.setVisibility(View.GONE);
                vh.listView.setVisibility(View.VISIBLE);
                vh.header.setVisibility(View.VISIBLE);
                vh.loadingContainer.setVisibility(View.GONE);
            }
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
            int index = Integer.parseInt(toggleButton.getTag().toString());
            // remove product from favourite
            favouriteViewModel.removeProductFromFavourite(products.get(index));
        } else{
            toggleButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            int index = Integer.parseInt(toggleButton.getTag().toString());
            // add to favourite, incase the user has pressed heart button more than once
            favouriteViewModel.addProductToFavourite(products.get(index));
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
            vh.collapseReturnArrow.setVisibility(View.GONE);
            vh.header.setVisibility(View.GONE);

        } else {
            LinearLayout navBarWrapper = (LinearLayout) findViewById(R.id.nav_bar_wrapper_fav);
            navBarWrapper.setVisibility(View.VISIBLE);
            vh.collapseReturnArrow.setVisibility(View.VISIBLE);
            vh.header.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                if (x1 < x2) {
                    Intent intent = new Intent(getBaseContext(), SearchFilterActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                break;
        }
        return false;

    }

}