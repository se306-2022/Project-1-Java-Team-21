package com.group21.sneakerhub.views.searchResultListActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
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

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * List adaptor for Search Result List Activity
 */

public class CustomListAdaptor extends ArrayAdapter {

    ViewHolder vh;

    int mLayoutId;
    List<Product> products;
    Context mContext;

    public CustomListAdaptor(@NonNull Context context, int resource, @NonNull List<Product> objects) {
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

        public ViewHolder(View currentListView){
            iconImageView = currentListView.findViewById(R.id.sneaker_preview_img);
            nameTextView = currentListView.findViewById(R.id.shoe_description);
            descriptionTextView = currentListView.findViewById(R.id.shoe_name);
            priceTextView = currentListView.findViewById(R.id.price_text);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get a reference to the current ListView item
        View currentListView = convertView;

        // checks if the view that the list view is about to show has been created already, and
        // therefore just needs to be recycled or does it need to be created from scratch
        if (currentListView == null) {
            currentListView = LayoutInflater.from(getContext()).inflate(mLayoutId, parent, false);
        }

        //Get the product object for the current position
        // if the view is already been created and just needs to be recycled
        Product currentProduct = products.get(position);

        vh = new ViewHolder(currentListView);
        //Set the attributed of list_view_number_item views
        //setting the image view for the icon inside the about to be displayed list view
        int i = mContext.getResources().getIdentifier(
                "s"+currentProduct.getImageUrls().get(0), "drawable",
                mContext.getPackageName());

        //Setting the icon
        vh.iconImageView.setImageResource(i);

        // set the name of the shoe
        vh.nameTextView.setText(currentProduct.getColor());

        // set the description of the shoe
        vh.descriptionTextView.setText(currentProduct.getName());

        // set the price
        vh.priceTextView.setText("$" + String.format("%.2f", currentProduct.getPrice()));

        currentListView.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.list));

        return currentListView;
    }

}

