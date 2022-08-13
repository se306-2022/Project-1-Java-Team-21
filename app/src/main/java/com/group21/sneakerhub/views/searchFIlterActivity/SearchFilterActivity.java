package com.group21.sneakerhub.views.searchFIlterActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
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

public class SearchFilterActivity extends AppCompatActivity {

    ViewHolder vh;
    ArrayList<String> brandNames;
    ArrayList<String> colours;

    class ViewHolder{
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

        // retrieve data from Viewholder for toggle button values here
        retrieveDataFromDb();

        vh = new ViewHolder();

        // instantiate the viewmodel class
        SearchFilterViewModel viewModel = new ViewModelProvider(this).get(SearchFilterViewModel.class);

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

        // implement listener for range slider
        vh.rangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> rangeValues = slider.getValues();

                vh.sliderMinText.setText(rangeValues.get(0).toString());
                vh.sliderMaxText.setText(rangeValues.get(1).toString());
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                List<Float> rangeValues = slider.getValues();

                vh.sliderMinText.setText(rangeValues.get(0).toString());
                vh.sliderMaxText.setText(rangeValues.get(1).toString());
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
                searchResults.putExtra("messageFromMainActivity","This message came from the main activity");
                //start the activity
                startActivity(searchResults);
            }
        });

    }

    private void retrieveDataFromDb() {
        // NOTE: instead of hard coding here the data should come from database
        if (brandNames == null && colours == null) {
            brandNames = new ArrayList<String>();
            brandNames.add("Air Jordan");
            brandNames.add("Nike");
            brandNames.add("Vans");
            brandNames.add("Adidas");

            colours = new ArrayList<String>();
            colours.add("White");
            colours.add("Black");
            colours.add("Red");
            colours.add("Blue");
            colours.add("Yellow");
            colours.add("Orange");
            colours.add("Purple");
            colours.add("Green");
            colours.add("Grey");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.options_menu, menu);

        vh.searchView.setQueryHint("Search for sneaker...");

        vh.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // when user hits return the final search string is returned
            @Override
            public boolean onQueryTextSubmit(String query) {
                // send to viewmodel
                doMySearch(query);
                return false;
            }

            // updates everytime a character changes in the searchbox
            @Override
            public boolean onQueryTextChange(String newText) {
                doMySearch(newText);
                return false;
            }
        });


        // Assumes current activity is the searchable activity
        vh.searchView.setSearchableInfo(vh.searchManager.getSearchableInfo(getComponentName()));
        vh.searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    public void doMySearch(String query){
        // correctly retrieves query to screen
        System.out.println(query);
    }

    /**
     * Generic event handler for all the toggle buttons of the brands
     */

    public void brandsEventHandler(View v){

        // use typecast to change v from a generic view to of type CheckBox
        ToggleButton toggleButton = (ToggleButton) v;
        if(toggleButton.isChecked()){
            String text = toggleButton.getText().toString();
            // send text to viewmodel as the selected brand filter for searchfilterview
            System.out.println("brand selected: " + text);
            toggleButton.setBackgroundResource(R.drawable.togglebutton_on);
            toggleButton.setTextColor(getResources().getColor(R.color.white));

        } else{
            String text = toggleButton.getText().toString();
            // retract text from viewmodel as it has been deselected
            System.out.println("cancelled brand: " + text);
            toggleButton.setBackgroundResource(R.drawable.button_border);
            toggleButton.setTextColor(getResources().getColor(R.color.black));
        }

    }

    /**
     * Generic event handler for all the toggle buttons of the colours
     */

    public void coloursEventHandler(View v){

        // use typecast to change v from a generic view to of type CheckBox
        ToggleButton toggleButton = (ToggleButton) v;
        if(toggleButton.isChecked()){
            String text = toggleButton.getText().toString();
            // send text to viewmodel as the selected colour filter for searchfilterview
            System.out.println("selected colour: " + text);
            toggleButton.setBackgroundResource(R.drawable.togglebutton_on);
            toggleButton.setTextColor(getResources().getColor(R.color.white));

        } else{
            String text = toggleButton.getText().toString();
            // retract text from viewmodel as it has been deselected
            System.out.println("cancelled colour: " + text);
            toggleButton.setBackgroundResource(R.drawable.button_border);
            toggleButton.setTextColor(getResources().getColor(R.color.black));
        }

    }




}