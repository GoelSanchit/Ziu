package com.ziuapp.ziu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.ziuapp.ziu.BookActivity;
import com.ziuapp.ziu.R;
import com.ziuapp.ziu.entities.Service;

import java.util.ArrayList;

/**
 * Created by ABHIJEET on 23-05-2015.
 */
public class ServiceListAdapter extends ArrayAdapter<Service> {
    public ServiceListAdapter(Context context, ArrayList<Service> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Service service = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_service,null);
        }

        TextView title = (TextView)convertView.findViewById(R.id.service_title);
        ImageView imageView = (ImageView)convertView.findViewById(R.id.service_image);

        title.setText(service.getTitle());
        Picasso.with(getContext()).load(service.getImageUrl()).fit().centerCrop().into(imageView);


        return convertView;

    }

}