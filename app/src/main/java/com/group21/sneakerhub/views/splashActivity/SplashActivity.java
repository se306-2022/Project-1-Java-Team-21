package com.group21.sneakerhub.views.splashActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Category;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.mainActivity.EntryViewModel;

import java.util.ArrayList;

/**
 * Splash activity loading screen
 */

public class SplashActivity extends AppCompatActivity {

    private class ViewHolder{
        Animation animation;
        TextView textView;
        EntryViewModel viewModel;

        public ViewHolder() {
            this.animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fade_in);
            this.textView = findViewById(R.id.title);
            viewModel = new ViewModelProvider(SplashActivity.this).get(EntryViewModel.class);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // make it full screen
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get the view holder
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textView.startAnimation(viewHolder.animation);

        // Start your app main activity
        Intent i = new Intent(SplashActivity.this, MainActivity.class);

        // retrieve all needed data from ViewModel
        ArrayList<String> categoryNames = new ArrayList<>();
        ArrayList<String> categoryColours = new ArrayList<>();
        ArrayList<Integer> brandImages = new ArrayList<>();
        viewHolder.viewModel.getCategories().observe( this, categories ->
            {
                for (Category category : categories) {
                    categoryNames.add(category.getName());
                    categoryColours.add(category.getColour());
                    brandImages.add(this.getResources().getIdentifier(category.getImageURI(), "drawable", this.getPackageName()));
                }
            }
        );

        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<String> productColours = new ArrayList<>();
        ArrayList<Double> productPrices = new ArrayList<>();
        ArrayList<Integer> productsDefaultImage = new ArrayList<>();
        viewHolder.viewModel.getTrendingProducts().observe(this, products -> {
            for (Product p : products) {
                productNames.add(p.getName());
                productColours.add(p.getColor());
                productPrices.add(p.getPrice());
                productsDefaultImage.add(getResources().getIdentifier("s" + p.getImageUrls().get(0), "drawable", getPackageName()));
            }
        });

        // change to main activity once all needed data has been loaded
        viewHolder.viewModel.isFinished.observe(this, isFinished -> {
            if (isFinished) {
                i.putExtra("callingActivity", "SplashActivity");
                i.putStringArrayListExtra("categoryNames", categoryNames);
                i.putStringArrayListExtra("categoryColours", categoryColours);
                i.putIntegerArrayListExtra("brandImages", brandImages);
                i.putStringArrayListExtra("productNames", productNames);
                i.putStringArrayListExtra("productColours", productColours);
                i.putExtra("productPrices", productPrices);
                i.putIntegerArrayListExtra("productsDefaultImage", productsDefaultImage);

                // set screen to progress bar
                startActivity(i);
                // close this activity
                finish();
            }
        });
    }
}