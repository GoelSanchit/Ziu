package com.ziuapp.ziu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.ziuapp.ziu.R;
import com.ziuapp.ziu.entities.Service;

import java.util.ArrayList;

/**
 * Created by ABHIJEET on 23-05-2015.
 */
public class ServiceDetailAdapter extends ArrayAdapter<String> {

    public ServiceDetailAdapter(Context context, ArrayList<String> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String headingText = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_service_detail,null);
        }

        TextView heading = (TextView)convertView.findViewById(R.id.tv_heading);
        heading.setText(headingText);



        return convertView;

    }

}