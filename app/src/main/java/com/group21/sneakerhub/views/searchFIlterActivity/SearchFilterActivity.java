package com.group21.sneakerhub.views.searchFIlterActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.slider.RangeSlider;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * UI Implementation for Search Filter Activity
 */

public class SearchFilterActivity extends AppCompatActivity {

    ViewHolder vh;
    SearchFilterViewModel searchFilterVM;
    Animation shakeAnimation;

    private class ViewHolder{
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        SearchView searchView = (SearchView) findViewById(R.id.menu_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        RangeSlider rangeSlider = (RangeSlider) findViewById(R.id.range_slider);
        TextView sliderMinText = (TextView)  findViewById(R.id.slider_text_min);
        TextView sliderMaxText = (TextView)  findViewById(R.id.slider_text_max);
        Button submitButton = (Button) findViewById(R.id.button_id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);
        getSupportActionBar().hide();

        searchFilterVM = new ViewModelProvider(this).get(SearchFilterViewModel.class);

        vh = new ViewHolder();

        shakeAnimation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.button_blink);

        // Set Home selected
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

        // setting the default values for the slider touch listener if the user does not touch it
        searchFilterVM.setLowerPriceRange(200);
        searchFilterVM.setUpperPriceRange(800);

        // implement listener for range slider
        vh.rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> rangeValues = slider.getValues();
                searchFilterVM.setLowerPriceRange(Math.round(rangeValues.get(0)));
                searchFilterVM.setUpperPriceRange(Math.round(rangeValues.get(1)));
                vh.sliderMinText.setText("$" + Math.round(rangeValues.get(0)));
                vh.sliderMaxText.setText("$" + Math.round(rangeValues.get(1)));
            }
        });

        vh.rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                List<Float> rangeValues = slider.getValues();
                vh.sliderMinText.setText("$" + Math.round(rangeValues.get(0)));
                vh.sliderMaxText.setText("$" + Math.round(rangeValues.get(1)));
            }
        });

        /**
         * Enter/return key listener for the search view, if the user doesnt want to use
         * the filters and just wants to search by query string
         */
        vh.searchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    Intent searchResults = new Intent(getBaseContext(), SearchResultListActivity.class);
                    searchResults.putExtra("isFilters", false);
                    searchResults.putExtra("finalQueryString", searchFilterVM.getFinalQueryString());
                }
                return false;
            }
        });


        /**
         * Event handler for submit, this will change the activity to the searchResultsListActivity and
         * let the viewmodel class know to process the search data.
         * Until submit button is pressed, the data from the searchview and the toggle buttons is only
         * stored temporarily in the viewmodel class.
         *
         * If submit button is not pressed or the return key is not hit by the user and they navigate away
         * from page then the data collected by viewmodel is not processed and discarded.
         */

        vh.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // create an intent that navigates to NumbersActivity class
                Intent searchResults = new Intent(getBaseContext(), SearchResultListActivity.class);
                //set data across to the other activity
                // sent in a key, value format, with the key being the first argument
                // many different types of data can be sent
                searchResults.putExtra("isFilters", true);

                searchResults.putExtra("query",searchFilterVM.getLiveQueryString());
                searchResults.putExtra("lowerPrice", searchFilterVM.getLowerPriceRange());
                searchResults.putExtra("upperPrice", searchFilterVM.getUpperPriceRange());

                searchResults.putExtra("callingActivity","SearchFilterActivity");
                ArrayList<String> colours = (ArrayList<String>) searchFilterVM.getColours();
                searchResults.putExtra("colours",colours);

                ArrayList<String> brands = (ArrayList<String>) searchFilterVM.getBrandNames();
                searchResults.putExtra("brands",brands);

                //start the activity
                startActivity(searchResults);

            }
        });

    }

    /**
     * Logic to handle how the data collected from the toggle buttons and
     * the searchview will be passed to the searchresults list activity when
     * the return key is pressed by the user on the physical keyboard.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        vh.searchView.setQueryHint("");
        vh.searchView.setSubmitButtonEnabled(false);

        vh.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // when user hits return the final search string is returned
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFilterVM.setFinalQueryString(query);

                // create an intent that navigates to NumbersActivity class
                Intent searchResults = new Intent(getBaseContext(), SearchResultListActivity.class);

                searchResults.putExtra("query",searchFilterVM.getFinalQueryString());
                searchResults.putExtra("lowerPrice", searchFilterVM.getLowerPriceRange());
                searchResults.putExtra("upperPrice", searchFilterVM.getUpperPriceRange());

                searchResults.putExtra("callingActivity","SearchFilterActivity");
                ArrayList<String> colours = (ArrayList<String>) searchFilterVM.getColours();
                searchResults.putExtra("colours",colours);

                ArrayList<String> brands = (ArrayList<String>) searchFilterVM.getBrandNames();
                searchResults.putExtra("brands",brands);

                //start the activity
                startActivity(searchResults);

                return true;
            }

            // updates everytime a character changes in the searchbox
            @Override
            public boolean onQueryTextChange(String newText) {
                searchFilterVM.setLiveQueryString(newText);
                return false;
            }
        });


        // Assumes current activity is the searchable activity
        vh.searchView.setSearchableInfo(vh.searchManager.getSearchableInfo(getComponentName()));
        vh.searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    /**
     * Generic event handler for all the toggle buttons of the brands
     */

    public void brandsEventHandler(View v){

        // use typecast to change v from a generic view to of type CheckBox
        ToggleButton toggleButton = (ToggleButton) v;
        toggleButton.startAnimation(shakeAnimation);
        if(toggleButton.isChecked()){
            String text = toggleButton.getText().toString();
            // send text to viewmodel as the selected brand filter for searchfilterview
            searchFilterVM.addBrand(text);
            toggleButton.setBackgroundResource(R.drawable.togglebutton_on);
            toggleButton.setTextColor(getResources().getColor(R.color.white));

        } else{
            String text = toggleButton.getText().toString();
            // retract text from viewmodel as it has been deselected
            searchFilterVM.removeBrand(text);
            toggleButton.setBackgroundResource(R.drawable.button_border);
            toggleButton.setTextColor(getResources().getColor(R.color.blackd));
        }

    }

    /**
     * Generic event handler for all the toggle buttons of the colours
     */
    public void coloursEventHandler(View v){

        // use typecast to change v from a generic view to of type CheckBox
        ToggleButton toggleButton = (ToggleButton) v;
        toggleButton.startAnimation(shakeAnimation);
        if(toggleButton.isChecked()){
            String text = toggleButton.getText().toString();
            // send text to viewmodel as the selected colour filter for searchfilterview
            searchFilterVM.addColour(text);
            toggleButton.setBackgroundResource(R.drawable.togglebutton_on);
            toggleButton.setTextColor(getResources().getColor(R.color.white));

        } else{
            String text = toggleButton.getText().toString();
            // retract text from viewmodel as it has been deselected
            searchFilterVM.removeColour(text);
            toggleButton.setBackgroundResource(R.drawable.button_border);
            toggleButton.setTextColor(getResources().getColor(R.color.blackd));
        }

    }

}