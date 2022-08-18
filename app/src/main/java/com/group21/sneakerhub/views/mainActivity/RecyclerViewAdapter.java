package com.group21.sneakerhub.views.mainActivity;

import android.content.Context;
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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Integer> mViewColors;
    private List<String> mBrands;
    private List<Integer> mImages;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private List<Integer> mProductImages;
    private List<String> mProductNames;
    private List<String> mProductColors;
    private List<Double> mProductPrices;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<Integer> colors, List<String> brands, List<Integer> images) {
        this.mInflater = LayoutInflater.from(context);
        this.mViewColors = colors;
        this.mBrands = brands;
        this.mImages = images;
    }


//    RecyclerViewAdapter(Context context, List<Integer> productImages, List<String> productNames, List<String> productColors, List<Double> productPrices) {
//        this.mInflater = LayoutInflater.from(context);
//        //this.mProductImages = productImages;
//        this.mProductNames = productNames;
//        this.mProductColors = productColors;
//        this.mProductPrices = productPrices;
//    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view2 = mInflater.inflate(R.layout.recyclerview_categories, parent, false);
        View view = mInflater.inflate(R.layout.recyclerview_featured, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = mViewColors.get(position);
        String brand = mBrands.get(position);
        int image = mImages.get(position);

//        String name1 = mProductNames.get(position);
//        //int image1 = mProductImages.get(position);
//        String color1 = mProductColors.get(position);
//        double price1 = mProductPrices.get(position);

        holder.myView.setCardBackgroundColor(color);
        holder.myTextView.setText(brand);
        holder.myImageView.setImageResource(image);

        //holder.imageTV.setImageResource(image1);
//        holder.colorTV.setText(color1);
//        holder.nameTV.setText(name1);
//        holder.priceTV.setText(String.valueOf(price1));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mBrands.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView myView;
        TextView myTextView;
        ImageView myImageView;

//        TextView priceTV;
//        TextView colorTV;
//        TextView nameTV;
        //ImageView imageTV;

        ViewHolder(View itemView) {
            super(itemView);
            myView = itemView.findViewById(R.id.brand_color);
            myTextView = itemView.findViewById(R.id.brand_name);
            myImageView = itemView.findViewById(R.id.brand_image);

//            priceTV = itemView.findViewById(R.id.price);
//            colorTV = itemView.findViewById(R.id.textView8);
//            //imageTV = itemView.findViewById(R.id.shoe_image);
//            nameTV = itemView.findViewById(R.id.textView7);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    // convenience method for getting data at click position
    public String getItem(int id) {
        return mBrands.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}