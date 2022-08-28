package com.group21.sneakerhub.views.mainActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group21.sneakerhub.R;

import java.util.List;

/**
 * Recycler View Adapter for Top Rated products in Main Activity
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<String> mViewColors;
    private List<String> mBrands;
    private List<Integer> mImages;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<Double> mProductPrices;

    /** data is passed into the constructor*/
    RecyclerViewAdapter(Context context, List<String> colors, List<String> brands, List<Integer> images, List<Double> productPrices) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mBrands = brands;
        this.mImages = images;
        this.mProductPrices = productPrices;
    }

    /** inflates the row layout from xml when needed */
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_featured, parent, false);
        return new ViewHolder(view);
    }

    /** binds the data to the view and textview in each row */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String color = mViewColors.get(position);
        String brand = mBrands.get(position);
        int image = mImages.get(position);
        double price1 = mProductPrices.get(position);

        holder.colorText.setText(color);
        holder.myTextView.setText(brand);
        holder.myImageView.setImageResource(image);
        holder.priceText.setText("$" + String.format("%.2f",price1));
    }

    /** total number of rows */
    @Override
    public int getItemCount() {
        return mBrands.size();
    }

    /** stores and recycles views as they are scrolled off screen */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView colorText;
        TextView myTextView;
        ImageView myImageView;
        TextView priceText;

        ViewHolder(View itemView) {
            super(itemView);
            colorText = itemView.findViewById(R.id.brand_color_featured);
            myTextView = itemView.findViewById(R.id.brand_name);
            myImageView = itemView.findViewById(R.id.brand_image);
            priceText = itemView.findViewById(R.id.price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /** convenience method for getting data at click position */
    public String getItem(int id) {
        return mBrands.get(id);
    }

    public String getColourMethod(int id) {
        return mViewColors.get(id);
    }

    /** allows clicks events to be caught */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /** parent activity will implement this method to respond to click events */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}