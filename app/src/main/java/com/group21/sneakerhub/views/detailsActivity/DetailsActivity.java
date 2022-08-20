package com.group21.sneakerhub.views.detailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.searchProductByName.SearchProductByName;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    ViewHolder vh;
    private ArrayList<Integer> colours; // [white, black, red]
    private ArrayList<ArrayList<String>> allImages; // [ [20_1, 20_2, 20_3], [21_1, 21_2, 21_3], [22_1, 22_2, 22_3] ]
    private ArrayList<ArrayList<String>> features; // [ [synthetic, fast], [authentic, limited], [synthetic, slow] ]
    private String description; // fast shoe created in ...
    private ArrayList<ArrayList<Integer>> availableSizes; // [ [8,9], [10, 11, 12], [8, 11] ]
    private ArrayList<Integer> rating; // [4.5, 3.5, 5]
    private ArrayList<Integer> numberOfUsersRated; // [17, 11, 4]

    class ViewHolder{
        TextView productName = (TextView) findViewById(R.id.productName);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_details);
    }


    SliderView sliderView;
    int[] images = {R.drawable.s61_1,
            R.drawable.s61_2,
            R.drawable.s61_3};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

        sliderView = findViewById(R.id.image_slider);

        SliderAdapter sliderAdapter = new SliderAdapter(images);

        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        //sliderView.startAutoCycle();





        vh = new ViewHolder();

        Intent intent = getIntent();
        String sneakerName = intent.getStringExtra("sneakerName");
        String callingActivity = intent.getStringExtra("callingActivity");
        System.out.println(callingActivity);

        DetailsViewModel detailsVM = new ViewModelProvider(this).get(DetailsViewModel.class);

        detailsVM.getDetailPageProduct(sneakerName).observe(this, productColors ->{
            // returns the product variations, so the sneaker with the same name but
            // in different colours

            vh.productName.setText(productColors.get(0).getName());

            System.out.println("==============================================");
            for(Product product : productColors){
                System.out.println(product.getName() + product.getColor());
            }
            System.out.println("==============================================");

        });



        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals("SearchResultListActivity")){
                    startActivity(new Intent(getApplicationContext(), SearchResultListActivity.class));
                } else if(callingActivity.equals("FavouriteActivity")){
                    startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), ListView.class));
                }
            }
        });


        vh.bottomNavigationView.setSelectedItemId(R.id.search);

        // implement event listener for nav bar
        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    public void sizingClicked(View view) {
        TextView sizing = (TextView)findViewById(R.id.SizingText);
        sizing.setVisibility(View.VISIBLE);
        Button expand = (Button)findViewById(R.id.expand);
        expand.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expanded);
        expanded.setVisibility(View.VISIBLE);
    }

    public void sizingClosed(View view) {
        TextView sizing = (TextView)findViewById(R.id.SizingText);
        sizing.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expanded);
        expanded.setVisibility(View.GONE);
        Button expand = (Button)findViewById(R.id.expand);
        expand.setVisibility(View.VISIBLE);
    }

    public void descriptionClicked(View view) {
        TextView description = (TextView)findViewById(R.id.DescriptionText);
        description.setVisibility(View.VISIBLE);
        Button expandDescription = (Button)findViewById(R.id.expandDescription);
        expandDescription.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedDescription);
        expanded.setVisibility(View.VISIBLE);
    }

    public void descriptionClosed(View view) {
        TextView sizing = (TextView)findViewById(R.id.DescriptionText);
        sizing.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedDescription);
        expanded.setVisibility(View.GONE);
        Button expand = (Button)findViewById(R.id.expandDescription);
        expand.setVisibility(View.VISIBLE);
    }


    public void detailsClicked(View view) {
        TextView sizing = (TextView)findViewById(R.id.DetailsText);
        sizing.setVisibility(View.VISIBLE);
        Button expand = (Button)findViewById(R.id.expandDetails);
        expand.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedDetails);
        expanded.setVisibility(View.VISIBLE);
    }

    public void detailsClosed(View view) {
        TextView sizing = (TextView)findViewById(R.id.DetailsText);
        sizing.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedDetails);
        expanded.setVisibility(View.GONE);
        Button expand = (Button)findViewById(R.id.expandDetails);
        expand.setVisibility(View.VISIBLE);
    }

    public void ratingClicked(View view) {
        TextView sizing = (TextView)findViewById(R.id.RatingText);
        sizing.setVisibility(View.VISIBLE);
        Button expand = (Button)findViewById(R.id.expandRating);
        expand.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedRating);
        expanded.setVisibility(View.VISIBLE);
    }

    public void ratingClosed(View view) {
        TextView sizing = (TextView)findViewById(R.id.RatingText);
        sizing.setVisibility(View.GONE);
        Button expanded = (Button)findViewById(R.id.expandedRating);
        expanded.setVisibility(View.GONE);
        Button expand = (Button)findViewById(R.id.expandRating);
        expand.setVisibility(View.VISIBLE);
    }

}