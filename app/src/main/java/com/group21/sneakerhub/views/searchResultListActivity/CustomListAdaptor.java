package com.group21.sneakerhub.views.searchResultListActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.model.Product;

import java.util.List;

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
        ImageView iconImageView = (ImageView) currentListViewItem.findViewById(R.id.icon_image_view);
        int i = mContext.getResources().getIdentifier(
                currentProduct.getImageURL(), "drawable",
                mContext.getPackageName());

        //Setting the icon
        iconImageView.setImageResource(i);

        // set the maori text for the textview for the current list item using the Number model class
        TextView maoriTextView = (TextView) currentListViewItem.findViewById(R.id.maori_text_view);
        maoriTextView.setText(currentProduct.getName());

        //Getting the audio resource id for the current Number object
        // they have to be declared final to be able to be accessed inside the event listener
        final ImageView play = (ImageView) currentListViewItem.findViewById(R.id.play_image_view);

        //Setting the image click handler for the play button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return currentListViewItem;
    }
}

