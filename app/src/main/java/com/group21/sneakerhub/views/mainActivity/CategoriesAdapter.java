package com.group21.sneakerhub.views.mainActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.group21.sneakerhub.R;

import java.util.List;

/**
 * Categories Adapter for Displaying the different categories in the Main Activity
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<String> mViewColors;
    private List<String> mBrands;
    private List<Integer> mImages;
    private LayoutInflater mInflater;
    private ItemClickListener2 mClickListener2;

    /** data is passed into the constructor */
    CategoriesAdapter(Context context, List<String> colors, List<String> brands, List<Integer> images) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mBrands = brands;
        this.mImages = images;
    }

    /** inflates the row layout from xml when needed */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_categories, parent, false);
        return new ViewHolder(view);
    }

    /** binds the data to the view and textview in each row */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String color = mViewColors.get(position);
        String brand = mBrands.get(position);
        int image = mImages.get(position);
        holder.myView.setCardBackgroundColor(Color.parseColor(color));
        holder.myTextView.setText(brand);
        holder.myImageView.setImageResource(image);
    }

    /** total number of rows */
    @Override
    public int getItemCount() {
        return mBrands.size();
    }

    /** stores and recycles views as they are scrolled off screen */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView myView;
        TextView myTextView;
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.brand_color3);
            myTextView = itemView.findViewById(R.id.brand_name3);
            myImageView = itemView.findViewById(R.id.brand_image3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener2 != null) mClickListener2.onItemClick2(view, getAdapterPosition());
        }
    }

    /** convenience method for getting data at click position */
    public String getItem(int id) {
        return mBrands.get(id);
    }
    public int getLogoResource(int id) {
        return mImages.get(id);
    }

    /** allows clicks events to be caught */
    public void setClickListener(ItemClickListener2 itemClickListener2) {
        this.mClickListener2 = itemClickListener2;
    }

    /** parent activity will implement this method to respond to click events */
    public interface ItemClickListener2 {
        void onItemClick2(View view, int position);
    }
}