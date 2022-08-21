package com.group21.sneakerhub.views.detailsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import java.util.concurrent.atomic.AtomicReference;

public class DetailsActivity extends AppCompatActivity {

    ViewHolder vh;

    private String query;
    private ArrayList<String> colours;
    private ArrayList<String> brands;
    private int lowerPrice;
    private int upperPrice;
    private String brandName;
    private List<Product> favProducts;
    private String favCurrentColor;
    private String favCurrentName;


    class ViewHolder{
        TextView productName = (TextView) findViewById(R.id.productName);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_details);
        ToggleButton heartButton = (ToggleButton) findViewById(R.id.heart_button);
        TextView currentColor = (TextView)findViewById(R.id.currentColor);
        TextView currentPrice = (TextView)findViewById(R.id.currentPrice);
    }


    SliderView sliderView;
    SliderAdapter sa1;
    SliderAdapter sa2;
    SliderAdapter sa3;
    int indexOfFirst;
    int indexOfSecond;
    int indexOfThird;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> colours2 = new ArrayList<>();
    ArrayList<ArrayList<String>> allImages = new ArrayList<>();
    ArrayList<ArrayList<String>> features = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();
    ArrayList<List<Integer>> availableSizes = new ArrayList<>();
    ArrayList<Double> rating = new ArrayList<>();
    ArrayList<Integer> numberOfUsersRated = new ArrayList<>();
    ArrayList<Double> prices = new ArrayList<>();
    ArrayList<Boolean> isFirst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();


        //sliderView.startAutoCycle();


//        ArrayList<Integer> colours; // [white, black, red]
//        ArrayList<ArrayList<String>> allImages; // [ [20_1, 20_2, 20_3], [21_1, 21_2, 21_3], [22_1, 22_2, 22_3] ]
//        ArrayList<ArrayList<String>> features; // [ [synthetic, fast], [authentic, limited], [synthetic, slow] ]
//        String description; // fast shoe created in ...
//        ArrayList<ArrayList<Integer>> availableSizes; // [ [8,9], [10, 11, 12], [8, 11] ]
//        ArrayList<Integer> rating; // [4.5, 3.5, 5]
//        ArrayList<Integer> numberOfUsersRated; // [17, 11, 4]
        //double price;




        vh = new ViewHolder();

        Intent intent = getIntent();
        String sneakerName = intent.getStringExtra("sneakerName");
        String callingActivity = intent.getStringExtra("callingActivity");
        String defaultColour = intent.getStringExtra("currentColour");
        System.out.println(callingActivity);

        query = intent.getStringExtra("query");
        colours = intent.getStringArrayListExtra("colours");
        brands = intent.getStringArrayListExtra("brands");
        lowerPrice = intent.getIntExtra("lowerPrice",0);
        upperPrice = intent.getIntExtra("upperPrice",0);
        brandName = intent.getStringExtra("brandName");


        DetailsViewModel detailsVM = new ViewModelProvider(this).get(DetailsViewModel.class);

        detailsVM.getDetailPageProduct(sneakerName).observe(this, productColors ->{
            // returns the product variations, so the sneaker with the same name but
            // in different colours

            vh.productName.setText(productColors.get(0).getName());
            favProducts = productColors;
            System.out.println("==============================================");
            for(Product p : productColors){
                // names
                names.add(p.getName());
                // colours
                colours2.add(p.getColor());
                // allImages
                List<String> temp = p.getImageUrls();
                ArrayList<String> temp2 = new ArrayList<>(temp);
                allImages.add(temp2);
                // features
                List<String> temp3 = p.getFeatures();
                ArrayList<String> temp4 = new ArrayList<>(temp3);
                features.add(temp4);
                // description
                description.add(p.getDescription());
                // sizes
                List<Integer> temp5 = p.getAvailableSizes();
                ArrayList<Integer> temp6 = new ArrayList<>(temp5);
                availableSizes.add(temp6);
                // rating
                rating.add(p.getRating());
                // numberOfUsersRated
                numberOfUsersRated.add(p.getNumberOfUsersRated());
                // prices
                prices.add(p.getPrice());
                //isFirst
                isFirst.add(p.getIsFirst());
            }
            System.out.println("==============================================");
            System.out.println(availableSizes);


            String firstColor = null;
            ArrayList<String> otherColours = new ArrayList<>();
            ArrayList<Integer> otherIndexes = new ArrayList<>();
            // Finding first color
            indexOfFirst = 0;
            for (int i = 0; i < isFirst.size(); i++) {
                if (isFirst.get(i) == true) {
                    indexOfFirst = i;
                    String temp = colours2.get(i);
                    firstColor = returnColorValue(temp);
                } else {
                    otherColours.add(colours2.get(i));
                    otherIndexes.add(i);
                }
            }
            indexOfSecond = otherIndexes.get(0);
            indexOfThird = otherIndexes.get(1);

            ArrayList<Integer> firstColorInt = new ArrayList<>();
            ArrayList<Integer> secondColorInt = new ArrayList<>();
            ArrayList<Integer> thirdColorInt = new ArrayList<>();



            for (String url : allImages.get(indexOfFirst)) {
                int i = this.getResources().getIdentifier("s" + url, "drawable", this.getPackageName());
                firstColorInt.add(i);
            }

            for (String url : allImages.get(indexOfSecond)) {
                int i = this.getResources().getIdentifier("s" + url, "drawable", this.getPackageName());
                secondColorInt.add(i);
            }

            for (String url : allImages.get(indexOfThird)) {
                int i = this.getResources().getIdentifier("s" + url, "drawable", this.getPackageName());
                thirdColorInt.add(i);
            }

            int[] firstImageSet = convertIntegers(firstColorInt);
            int[] secondImageSet = convertIntegers(secondColorInt);
            int[] thirdImageSet = convertIntegers(thirdColorInt);



            sliderView = findViewById(R.id.image_slider);
            SliderAdapter sliderAdapter1 = new SliderAdapter(firstImageSet);
            SliderAdapter sliderAdapter2 = new SliderAdapter(secondImageSet);
            SliderAdapter sliderAdapter3 = new SliderAdapter(thirdImageSet);

            sa1 = sliderAdapter1;
            sa2 = sliderAdapter2;
            sa3 = sliderAdapter3;

            sliderView.setSliderAdapter(sliderAdapter2);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
            sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);




            RadioButton raPrivate = (RadioButton) findViewById(R.id.colorButton1);
            int textColor = Color.parseColor(firstColor);
            raPrivate.setButtonTintList(ColorStateList.valueOf(textColor));

            RadioButton raPrivate2 = (RadioButton) findViewById(R.id.colorButton2);
            int textColor2 = Color.parseColor(returnColorValue(otherColours.get(0)));
            raPrivate2.setButtonTintList(ColorStateList.valueOf(textColor2));

            RadioButton raPrivate3 = (RadioButton) findViewById(R.id.colorButton3);
            int textColor3 = Color.parseColor(returnColorValue(otherColours.get(1)));
            raPrivate3.setButtonTintList(ColorStateList.valueOf(textColor3));

            TextView currentColor = (TextView)findViewById(R.id.currentColor);


            if (raPrivate.isChecked()) {
                sliderView.setSliderAdapter(sliderAdapter1);
                currentColor.setText(colours2.get(indexOfFirst));
                favCurrentColor = colours2.get(indexOfFirst);
                favCurrentName = names.get(indexOfFirst);
            } else if (raPrivate2.isChecked()) {
                sliderView.setSliderAdapter(sliderAdapter2);
                currentColor.setText(colours2.get(indexOfSecond));
                favCurrentColor = colours2.get(indexOfSecond);
                favCurrentName = names.get(indexOfSecond);
            } else {
                sliderView.setSliderAdapter(sliderAdapter3);
                currentColor.setText(colours2.get(indexOfThird));
                favCurrentColor = colours2.get(indexOfThird);
                favCurrentName = names.get(indexOfThird);
            }
        });



        vh.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callingActivity.equals("SearchResultListActivity")){
                    Intent intent = new Intent(getApplicationContext(), SearchResultListActivity.class);
                    intent.putExtra("query",query);
                    intent.putExtra("lowerPrice", lowerPrice);
                    intent.putExtra("upperPrice", upperPrice);
                    intent.putExtra("colours",colours);
                    intent.putExtra("brands",brands);
                    
                    startActivity(intent);
                } else if(callingActivity.equals("FavouriteActivity")){
                    startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                } else {
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    intent.putExtra("brandName", brandName);

                    startActivity(intent);
                }
            }
        });

        //heart toggle button

        vh.heartButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);

                    Product currentProduct = null;
                    for (Product p : favProducts){
                        if (p.getColor().equals(favCurrentColor)){
                            currentProduct = p;
                        }
                    }

                    detailsVM.addProductToFavourite(currentProduct);
                } else {
                    vh.heartButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);

                    Product currentProduct = null;
                    for (Product p : favProducts){
                        if (p.getColor().equals(favCurrentColor)){
                            currentProduct = p;
                        }
                    }
                    detailsVM.removeProductFromFavourite(currentProduct);
                }
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
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.colorButton1:
                if (checked)
                    sliderView.setSliderAdapter(sa1);
                    vh.productName.setText(names.get(indexOfFirst));
                    vh.currentColor.setText(colours2.get(indexOfFirst));
                    favCurrentColor = colours2.get(indexOfFirst);
                    favCurrentName = names.get(indexOfFirst);
                    break;
            case R.id.colorButton2:
                if (checked)
                    sliderView.setSliderAdapter(sa2);
                    vh.productName.setText(names.get(indexOfSecond));
                    vh.currentColor.setText(colours2.get(indexOfSecond));
                    favCurrentColor = colours2.get(indexOfSecond);
                    favCurrentName = names.get(indexOfSecond);
                break;
            case R.id.colorButton3:
                if (checked)
                    sliderView.setSliderAdapter(sa3);
                    vh.productName.setText(names.get(indexOfThird));
                    vh.currentColor.setText(colours2.get(indexOfThird));
                    favCurrentColor = colours2.get(indexOfThird);
                    favCurrentName = names.get(indexOfThird);
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

    public static int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
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