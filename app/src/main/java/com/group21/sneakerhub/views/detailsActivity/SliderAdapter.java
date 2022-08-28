package com.group21.sneakerhub.views.detailsActivity;
import com.group21.sneakerhub.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder>{

    /**
     * Adapter for image slider used in Details Activity
     */

    List<Integer> images;

    public SliderAdapter(List<Integer> images){
        this.images = images;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        // Setting image based on the position
        viewHolder.imageView.setImageResource(images.get(position));
    }

    // Getting the count of how many images there are per slider
    @Override
    public int getCount() {
        return images.size();
    }

    public class Holder extends  SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);

        }
    }

}