package com.group21.sneakerhub.views.searchResultListActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CustomListAdaptor extends ArrayAdapter {

    int mLayoutId;
    List<Product> products;
    Context mContext;

    public CustomListAdaptor(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        mLayoutId = resource; // layout xml file we made (custom)
        mContext = context; // the class it is in
        products = objects; // the data to show in the list view
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
        //Get the Number object for the current position
        // if the view is already been created and just needs to be recycled
        Product currentProduct = products.get(position);

        //Set the attributed of list_view_number_item views
        //setting the image view for the icon inside the about to be displayed list view
        ImageView iconImageView = (ImageView) currentListViewItem.findViewById(R.id.sneaker_preview_img);
        int i = mContext.getResources().getIdentifier(
                "s"+currentProduct.getImageUrls().get(0), "drawable",
                mContext.getPackageName());

        //Setting the icon
        iconImageView.setImageResource(i);

        // set the name of the shoe
        TextView nameTextView = (TextView) currentListViewItem.findViewById(R.id.shoe_description);
        nameTextView.setText(currentProduct.getColor());

        // set the description of the shoe
        TextView descriptionTextView = (TextView) currentListViewItem.findViewById(R.id.shoe_name);
        descriptionTextView.setText(currentProduct.getName());

        // set the price
        TextView priceTextView = (TextView) currentListViewItem.findViewById(R.id.price_text);
        priceTextView.setText(String.valueOf(currentProduct.getPrice()) + "+");

        return currentListViewItem;
    }

}

