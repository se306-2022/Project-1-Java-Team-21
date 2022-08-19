package com.group21.sneakerhub.views.detailsActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.listActivity.ListActivity;
import com.group21.sneakerhub.views.mainActivity.MainActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListActivity;
import com.group21.sneakerhub.views.searchResultListActivity.SearchResultListViewModel;

import org.w3c.dom.Text;

public class DetailsActivity extends AppCompatActivity {

    ViewHolder vh;


    class ViewHolder{
        TextView productName = (TextView) findViewById(R.id.productName);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        ImageButton backButton = (ImageButton) findViewById(R.id.back_button_details);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().hide();

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
                    startActivity(new Intent(getApplicationContext(), SearchFilterActivity.class));
                } else if(callingActivity.equals("FavouriteActivity")){
                    startActivity(new Intent(getApplicationContext(), FavouriteActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

}