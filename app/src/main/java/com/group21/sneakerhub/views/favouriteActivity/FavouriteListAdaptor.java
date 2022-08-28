package com.group21.sneakerhub.views.favouriteActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;
import com.group21.sneakerhub.usecases.searchProductByName.ISearchProductByName;
import com.group21.sneakerhub.usecases.searchProductByName.SearchProductByName;

import java.util.List;

/**
 * List Adaptor for the favourites activity
 */

public class FavouriteListAdaptor extends ArrayAdapter {
    ViewHolder vh;
    int mLayoutId;
    List<Product> products;
    Context mContext;

    public FavouriteListAdaptor(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        mLayoutId = resource; // layout xml file we made (custom)
        mContext = context; // the class it is in
        products = objects; // the data to show in the list view
        vh = new ViewHolder();
    }

    static class ViewHolder{
        ImageView iconImageView;
        View currentListViewItem;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        ToggleButton favButton;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get a reference to the current ListView item
        vh.currentListViewItem = convertView;

        // checks if the view that the list view is about to show has been created already, and
        // therefore just needs to be recycled or does it need to be created from scratch

        // Check if the existing view is being reused, otherwise inflate the view
        if (vh.currentListViewItem == null) {
            vh.currentListViewItem = LayoutInflater.from(getContext()).inflate(mLayoutId, parent, false);
        }
        //Get the Number object for the current position
        // if the view is already been created and just needs to be recycled
        Product currentProduct = products.get(position);

        //Set the attributed of list_view_number_item views
        //setting the image view for the icon inside the about to be displayed list view
        vh.iconImageView = (ImageView) vh.currentListViewItem.findViewById(R.id.sneaker_preview_img);
        int i = mContext.getResources().getIdentifier(
                "s" + currentProduct.getImageUrls().get(0), "drawable",
                mContext.getPackageName());

        //Setting the icon
        vh.iconImageView.setImageResource(i);

        // display the number of colors the shoe is available in
        vh.nameTextView = (TextView) vh.currentListViewItem.findViewById(R.id.shoe_description);
        vh.nameTextView.setText(currentProduct.getColor());

        // set the description of the shoe
        vh.descriptionTextView = (TextView) vh.currentListViewItem.findViewById(R.id.shoe_name);
        vh.descriptionTextView.setText(currentProduct.getName());

        // set the price
        vh.priceTextView = (TextView) vh.currentListViewItem.findViewById(R.id.price_text);
        vh.priceTextView.setText("$" + String.format("%.2f", currentProduct.getPrice()));

        // set tag for favourite button
        vh.favButton = (ToggleButton) vh.currentListViewItem.findViewById(R.id.heart_button);
        vh.favButton.setTag(position);
        if (products.get(position).getIsFavourite()){
            vh.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        } else {
            vh.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }


        // set animation for card view
        vh.currentListViewItem.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.list));

        return vh.currentListViewItem;
    }

}
