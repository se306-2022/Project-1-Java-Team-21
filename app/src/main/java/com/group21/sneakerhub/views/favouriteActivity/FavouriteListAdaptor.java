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
import com.group21.sneakerhub.views.searchResultListActivity.CustomListAdaptor;

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
        mLayoutId = resource;
        mContext = context;
        products = objects;
    }

    private class ViewHolder{
        ImageView iconImageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        ToggleButton favButton;

        public ViewHolder(View currentListView){
            iconImageView = currentListView.findViewById(R.id.sneaker_preview_img);
            nameTextView = currentListView.findViewById(R.id.shoe_description);
            descriptionTextView = currentListView.findViewById(R.id.shoe_name);
            priceTextView = currentListView.findViewById(R.id.price_text);
            favButton = currentListView.findViewById(R.id.heart_button);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get a reference to the current ListView item
        View currentListViewItem = convertView;

        // checks if the view that the list view is about to show has been created already, and
        // therefore just needs to be recycled or does it need to be created from scratch

        // Check if the existing view is being reused, otherwise inflate the view
        if (currentListViewItem == null) {
            currentListViewItem = LayoutInflater.from(getContext()).inflate(mLayoutId, parent, false);
        }

        vh = new ViewHolder(currentListViewItem);

        //Get the Product object for the current position
        // if the view is already been created and just needs to be recycled
        Product currentProduct = products.get(position);

        //Set the attributed of list_view_number_item views
        //setting the image view for the icon inside the about to be displayed list view

        int i = mContext.getResources().getIdentifier(
                "s" + currentProduct.getImageUrls().get(0), "drawable",
                mContext.getPackageName());

        //Setting the icon
        vh.iconImageView.setImageResource(i);

        // display the number of colors the shoe is available in

        vh.nameTextView.setText(currentProduct.getColor());

        // set the description of the shoe

        vh.descriptionTextView.setText(currentProduct.getName());

        // set the price

        vh.priceTextView.setText("$" + String.format("%.2f", currentProduct.getPrice()));

        // set tag for favourite button
        vh.favButton.setTag(position);
        if (products.get(position).getIsFavourite()){
            vh.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        } else {
            vh.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }


        // set animation for card view
        currentListViewItem.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.list));

        return currentListViewItem;
    }

}
