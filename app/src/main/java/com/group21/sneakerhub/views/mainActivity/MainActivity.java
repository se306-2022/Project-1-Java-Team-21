package com.group21.sneakerhub.views.mainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.views.favouriteActivity.FavouriteActivity;
import com.group21.sneakerhub.views.searchFIlterActivity.SearchFilterActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addProductsToFireStore();

        // Initialize and assign object for nav bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        // implement event listener
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                        return true;
                }
                return false;
            }
        });


    }
    // Add product documents to Firestore
    public static List<Product> getProducts() {
        List<Product> productsList = new ArrayList<Product>();
        Map<Integer, String> name;
        int id = key;
        Product p = new Product();
        return productsList;
    }


    public static void addProductsToFireStore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Product> productsList = getProducts();
        for (Product p : productsList) {
            db.collection("numbers").document("product " + productsList.set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d("Products Collection Add", "product " + getProducts() + " added.");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Product Collection Add", "number " + getProducts() + " NOT added.");
                }
            }));
        }
    }
}