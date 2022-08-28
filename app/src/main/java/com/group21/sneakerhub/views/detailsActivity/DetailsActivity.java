package com.group21.sneakerhub.views.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.listActivity.ListActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for UI implementation of Details Activity
 */

public class DetailsActivity extends AppCompatActivity {

    /**
     * Variables
     */
    private ViewHolder vh;
    private List<Product> selectedProducts = new ArrayList<>();
    private Product selectedProduct;
    List<List<Integer>> allImages = new ArrayList<>();
    private String sneakerName;
    private String callingActivity;
    private String productColour;
    private String query;
    private ArrayList<String> colours;
    private ArrayList<String> brands;
    private int lowerPrice;
    private int upperPrice;
    private String brandName;

    boolean[] rated = new boolean[3];
    double[] newRatings = new double[3];
    boolean ratingExpanded = false;

    /**
     * ViewHolder initialization
     */
    private class ViewHolder{
        DetailsViewModel viewModel = new ViewModelProvider(DetailsActivity.this).get(DetailsViewModel.class);;
        TextView productName = (TextView) findViewById(R.id.productName);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_details);
        ImageButton shareButton = (ImageButton) findViewById(R.id.share_button);
        ToggleButton heartButton = (ToggleButton) findViewById(R.id.heart_button);
        LottieAnimationView likeAnimation = findViewById(R.id.like_animation);
        LottieAnimationView unlikeAnimation = findViewById(R.id.unlike_animation);
        SliderView imageSlider = findViewById(R.id.image_slider);
        SliderAdapter firstProductColourImageSlider;
        SliderAdapter secondProductColourImageSlider;
        SliderAdapter thirdProductColourImageSlider;
        RadioButton firstColourRadioButton = (RadioButton) findViewById(R.id.colorButton1);
        RadioButton secondColourRadioButton = (RadioButton) findViewById(R.id.colorButton2);
        RadioButton thirdColourRadioButton = (RadioButton) findViewById(R.id.colorButton3);
        TextView currentColor = (TextView)findViewById(R.id.currentColor);
        TextView currentPrice = (TextView)findViewById(R.id.currentPrice);
        TextView sizingText = (TextView)findViewById(R.id.SizingText);
        TextView descriptionText = (TextView)findViewById(R.id.DescriptionText);
        TextView featuresText = (TextView)findViewById(R.id.DetailsText);
        TextView ratingText = (TextView)findViewById(R.id.RatingText);
        RatingBar firstRatingRatingBar = (RatingBar) findViewById(R.id.rating_bar);
        RatingBar secondRatingRatingBar = (RatingBar) findViewById(R.id.rating_bar2);
        RatingBar thirdRatingRatingBar = (RatingBar) findViewById(R.id.rating_bar3);
        TextView firstRatingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View);
        TextView secondRatingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View2);
        TextView thirdRatingDisplayTextView = (TextView) findViewById(R.id.rating_display_text_View3);
        Button firstRatingSubmitButton = (Button) findViewById(R.id.submit_button);
        Button secondRatingSubmitButton = (Button) findViewById(R.id.submit_button2);
        Button thirdRatingSubmitButton = (Button) findViewById(R.id.submit_button3);

        RelativeLayout r1 = (RelativeLayout)findViewById(R.id.rating_holder);
        RelativeLayout r2 = (RelativeLayout)findViewById(R.id.rating_holder2);
        RelativeLayout r3 = (RelativeLayout)findViewById(R.id.rating_holder3);

        List<RadioButton> colourRadioButtons = new ArrayList<>();
        List<SliderAdapter> colourImageSliders = new ArrayList<>();
        List<RatingBar> ratingBars = new ArrayList<>();
        List<TextView> ratingDisplayTextViews = new ArrayList<>();
        List<Button> ratingSubmitButtons = new ArrayList<>();
        List<RelativeLayout> ratingHolders = new ArrayList<>();

        Button expandSize = (Button)findViewById(R.id.expand);
        Button expandedSize = (Button)findViewById(R.id.expanded);
        Button expand = (Button)findViewById(R.id.expandRating);
        Button expanded = (Button)findViewById(R.id.expandedRating);
        Button expandDetails = (Button)findViewById(R.id.expandDetails);
        Button expandedDetails = (Button)findViewById(R.id.expandedDetails);
        Button expandDescription = (Button)findViewById(R.id.expandDescription);
        Button expandedDescription = (Button)findViewById(R.id.expandedDescription);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();


        vh = new ViewHolder();
        vh.colourRadioButtons.add(vh.firstColourRadioButton);
        vh.colourRadioButtons.add(vh.secondColourRadioButton);
        vh.colourRadioButtons.add(vh.thirdColourRadioButton);
        vh.colourImageSliders.add(vh.firstProductColourImageSlider);
        vh.colourImageSliders.add(vh.secondProductColourImageSlider);
        vh.colourImageSliders.add(vh.thirdProductColourImageSlider);
        vh.ratingDisplayTextViews.add(vh.firstRatingDisplayTextView);
        vh.ratingDisplayTextViews.add(vh.secondRatingDisplayTextView);
        vh.ratingDisplayTextViews.add(vh.thirdRatingDisplayTextView);
        vh.ratingSubmitButtons.add(vh.firstRatingSubmitButton);
        vh.ratingSubmitButtons.add(vh.secondRatingSubmitButton);
        vh.ratingSubmitButtons.add(vh.thirdRatingSubmitButton);
        vh.ratingBars.add(vh.firstRatingRatingBar);
        vh.ratingBars.add(vh.secondRatingRatingBar);
        vh.ratingBars.add(vh.thirdRatingRatingBar);
        vh.ratingHolders.add(vh.r1);
        vh.ratingHolders.add(vh.r2);
        vh.ratingHolders.add(vh.r3);

        Intent intent = getIntent();
        sneakerName = intent.getStringExtra("sneakerName");
        callingActivity = intent.getStringExtra("callingActivity");
        productColour = intent.getStringExtra("currentColour");
        query = intent.getStringExtra("query");
        colours = intent.getStringArrayListExtra("colours");
        brands = intent.getStringArrayListExtra("brands");
        lowerPrice = intent.getIntExtra("lowerPrice",0);
        upperPrice = intent.getIntExtra("upperPrice",0);
        brandName = intent.getStringExtra("brandName");

        vh.viewModel.getDetailPageProduct(sneakerName).observe(this, products ->{
            // returns the product variations, so the sneaker with the same name but
            // in different colours
            List<Integer> imageOfEachColour;
            Product p;

            vh.productName.setText(products.get(0).getName());
            for (int i = 0 ; i < products.size(); i++){
                p = products.get(i);
                selectedProducts.add(p);

                // add image of each colour to the list of images
                imageOfEachColour = new ArrayList<>();
                for (String url : p.getImageUrls()){
                    imageOfEachColour.add(this.getResources().getIdentifier("s" + url, "drawable", this.getPackageName()));
                }
                allImages.add(imageOfEachColour);
                // set the image slider
                vh.colourImageSliders.set(i , new SliderAdapter(imageOfEachColour));

                // set the colours
                vh.colourRadioButtons.get(i).setButtonTintList(ColorStateList.valueOf(Color.parseColor(returnColorValue(p.getColor()))));

                // set other contents
                if (p.getColor().equals(productColour)){
                    selectedProduct = p;
                    vh.colourRadioButtons.get(i).setChecked(true);
                    vh.imageSlider.setSliderAdapter(vh.colourImageSliders.get(i));
                    vh.currentColor.setText(p.getColor());
                    vh.currentPrice.setText("$" + String.format("%.2f", p.getPrice()));
                    vh.sizingText.setText(parseSize(p.getAvailableSizes()));
                    vh.descriptionText.setText(p.getDescription());
                    vh.featuresText.setText(parseFeature(p.getFeatures()));
                    vh.ratingText.setText(parseRating(p.getRating()));
                } else {
                    vh.colourRadioButtons.get(i).setChecked(false);
                }

            }

            // set the like button
            if (selectedProduct.getIsFavourite()){
                vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);;
            } else {
                vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            }
        });

        // set the image sliders and animation
        vh.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        vh.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);

        // set up the back button on click listener
        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (callingActivity) {
                    case "SearchResultListActivity": {
                        Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                        intent.putExtra("callingActivity","DetailsActivity");
                        intent.putExtra("query", query);
                        intent.putExtra("lowerPrice", lowerPrice);
                        intent.putExtra("upperPrice", upperPrice);
                        intent.putExtra("colours", colours);
                        intent.putExtra("brands", brands);

                        startActivity(intent);
                        break;
                    }
                    case "FavouriteActivity":
                        startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                        break;
                    case "ListActivity": {
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("brandName", brandName);

                        startActivity(intent);
                        break;
                    }
                    default: {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
;
        // share button
        vh.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
                txtIntent.setType("text/plain");
                txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out the " + selectedProduct.getColor()+" "+selectedProduct.getName()+" on Sneaker Hub!");
                txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml("<h1>Current Price:</h1>"+"<p>"+selectedProduct.getPrice()+"</p>"+"<h1>Product Description:</h1>"+"<p>"+selectedProduct.getDescription()+"</p>"+"<h1>Product Rating:</h1>"+"<p>"+selectedProduct.getRating()+"</p>"));
                startActivity(Intent.createChooser(txtIntent ,"Share"));
            }
      });

        //heart toggle button
        vh.heartButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vh.viewModel.toggleProductIsFavourite(selectedProduct).observe(DetailsActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean currentIsFavourite) {
                        if (currentIsFavourite) {
                            vh.unlikeAnimation.setVisibility(View.INVISIBLE);
                            vh.likeAnimation.setVisibility(View.VISIBLE);
                            vh.likeAnimation.playAnimation();
                        } else {
                            vh.likeAnimation.setVisibility(View.INVISIBLE);
                            vh.unlikeAnimation.setVisibility(View.VISIBLE);
                            vh.unlikeAnimation.playAnimation();
                            vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                        }
                    }
                });
            }
        });


        vh.bottomNavigationView.setSelectedItemId(R.id.search);
        // implement event listener for nav bar
        vh.bottomNavigationView.setOnItemSelectedListener(item -> {
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
        });

        // set up rating event listener
        for (int i = 0; i < vh.ratingSubmitButtons.size(); i++){
            int index = i;
            vh.ratingSubmitButtons.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vh.ratingSubmitButtons.get(index).setVisibility(View.GONE);
                    vh.ratingBars.get(index).setVisibility(View.GONE);
                    vh.ratingDisplayTextViews.get(index).setText("Thank you for your rating.");

                    vh.viewModel.addRating(selectedProduct, vh.ratingBars.get(index).getRating());
                    rated[index] = true;
                    double newRating = ((selectedProduct.getRating()*selectedProduct.getNumberOfUsersRated())+vh.ratingBars.get(index).getRating())/(selectedProduct.getNumberOfUsersRated()+1);
                    newRatings[index] = newRating;

                    vh.ratingText.setText("Average rating: " + String.format("%.2f", newRating) + "/5");
                }
            });
        }
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int page = 0;
        for (int i = 0; i < vh.colourRadioButtons.size(); i++){
            if (vh.colourRadioButtons.get(i).equals(view)){
                selectedProduct = selectedProducts.get(i);

                page = vh.imageSlider.getCurrentPagePosition();
                vh.imageSlider.setSliderAdapter(vh.colourImageSliders.get(i));
                vh.imageSlider.setCurrentPagePosition(page);
                vh.productName.setText(selectedProduct.getName());
                vh.currentColor.setText(selectedProduct.getColor());
                vh.currentPrice.setText("$" + String.format("%.2f", selectedProduct.getPrice()));
                vh.sizingText.setText(parseSize(selectedProduct.getAvailableSizes()));
                vh.descriptionText.setText(selectedProduct.getDescription());
                vh.featuresText.setText(parseFeature(selectedProduct.getFeatures()));

                if (!rated[i]) {
                    vh.ratingText.setText(parseRating(selectedProduct.getRating()));
                }
                else {
                    vh.ratingText.setText(parseRating(newRatings[i]));
                }

                if (ratingExpanded) {
                    vh.ratingHolders.get(i).setVisibility(View.VISIBLE);                }
            }else{
                vh.ratingHolders.get(i).setVisibility(View.GONE);
            }
        }

        if (selectedProduct.getIsFavourite()) {
            vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        } else {
            vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            vh.likeAnimation.setVisibility(View.INVISIBLE);

        }
    }

    /**
     * Methods for toggling sizing, description, features and rating
     * @param view
     */

    public void sizingClicked(View view) {
        vh.sizingText.setVisibility(View.VISIBLE);
        vh.expandSize.setVisibility(View.GONE);
        vh.expandedSize.setVisibility(View.VISIBLE);
    }

    public void sizingClosed(View view) {
        vh.sizingText.setVisibility(View.GONE);
        vh.expandedSize.setVisibility(View.GONE);
        vh.expandSize.setVisibility(View.VISIBLE);
    }

    public void descriptionClicked(View view) {
        vh.descriptionText.setVisibility(View.VISIBLE);
        vh.expandDescription.setVisibility(View.GONE);
        vh.expandedDescription.setVisibility(View.VISIBLE);
    }

    public void descriptionClosed(View view) {
        vh.descriptionText.setVisibility(View.GONE);
        vh.expandDescription.setVisibility(View.VISIBLE);
        vh.expandedDescription.setVisibility(View.GONE);
    }

    public void detailsClicked(View view) {
        vh.featuresText.setVisibility(View.VISIBLE);
        vh.expandDetails.setVisibility(View.GONE);
        vh.expandedDetails.setVisibility(View.VISIBLE);
    }

    public void detailsClosed(View view) {
        vh.featuresText.setVisibility(View.GONE);
        vh.expandedDetails.setVisibility(View.GONE);
        vh.expandDetails.setVisibility(View.VISIBLE);
    }

    public void ratingClicked(View view) {
        vh.ratingText.setVisibility(View.VISIBLE);
        vh.expand.setVisibility(View.GONE);
        ratingExpanded = true;
        vh.expanded.setVisibility(View.VISIBLE);
        vh.ratingText.setVisibility(View.VISIBLE);

        vh.ratingHolders.get(selectedProducts.indexOf(selectedProduct)).setVisibility(View.VISIBLE);

    }

    public void ratingClosed(View view) {
        vh.ratingText.setVisibility(View.GONE);
        vh.expanded.setVisibility(View.GONE);
        vh.expand.setVisibility(View.VISIBLE);

        for (int i = 0 ; i < vh.ratingHolders.size(); i++){
            vh.ratingHolders.get(i).setVisibility(View.GONE);
        }

        vh.ratingText.setVisibility(View.GONE);
        ratingExpanded = false;
    }

    /**
     * Method that
     * @param sizeList
     * @return sizeString
     */
    public String parseSize(List<Integer> sizeList) {
        String sizeString = "Available sizes:";
        for (Integer size : sizeList) {
            sizeString = sizeString.concat(" US " + String.valueOf(size) + ",");
        }
        sizeString = sizeString.substring(0, sizeString.length() - 1);
        return sizeString;
    }

    public String parseFeature(List<String> features) {
        String featureString = "";
        for (String feature : features) {
            featureString = featureString.concat(" " + feature + ",");
        }
        featureString = featureString.substring(0, featureString.length() - 1);
        return featureString;
    }

    public String parseRating(double rating) {
        String ratingString = "";
        ratingString = ratingString.concat("Average rating: " + String.format("%.2f", rating) + "/5");

        return ratingString;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // change visibility of toolbar
            // reference has to be here, cant be in viewholder
            vh.bottomNavigationView.setVisibility(View.INVISIBLE);
        } else {

            vh.bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    public String returnColorValue(String colorString) {
        switch (colorString.toLowerCase()) {
            case "white":
                return "#dbdbdb";
            case "black":
                return "#FF000000";
            case "red":
                return "#a90000";
            case "blue":
                return "#0026a9";
            case "yellow":
                return "#eadc2b";
            case "orange":
                return "#ea9f2b";
            case "purple":
                return "#b92bea";
            case "green":
                return "#49a026";
            case "grey":
                return "#a9a6a6";
            default:
                return "#999696";
        }
    }

}