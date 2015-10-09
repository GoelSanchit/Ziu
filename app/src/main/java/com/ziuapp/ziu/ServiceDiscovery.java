package com.ziuapp.ziu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.melnykov.fab.ObservableScrollView;
import com.melnykov.fab.FloatingActionButton;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.ziuapp.ziu.adapters.ServiceListAdapter;
import com.ziuapp.ziu.entities.Service;
import com.ziuapp.ziu.utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;

import android.os.Handler;


import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

import static com.ziuapp.ziu.utils.Utilities.customStyle;
import static com.ziuapp.ziu.utils.Utilities.share;


public class ServiceDiscovery extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {

        setDrawerHeaderImage(new ColorDrawable(getResources().getColor(R.color.theme_color)));

        // Categories
        MaterialSection sample1 = newSection("Sample Category1", new PlaceholderFragment());
        MaterialSection sample2 = newSection("Sample Category2", new PlaceholderFragment());
        MaterialSection sample3 = newSection("Sample Category3", new PlaceholderFragment());
        MaterialSection sample4 = newSection("Sample Category4", new PlaceholderFragment());
        MaterialSection sample5 = newSection("Sample Category5", new PlaceholderFragment());
        MaterialSection sample6 = newSection("Sample Category6", new PlaceholderFragment());
        MaterialSection sample7 = newSection("Sample Category7", new PlaceholderFragment());
        MaterialSection sample8 = newSection("Sample Category8", new PlaceholderFragment());


        // create sections
        this.addSection(customStyle(this,sample1));
        this.addSection(customStyle(this,sample2));
        this.addSection(customStyle(this,sample3));
        this.addSection(customStyle(this,sample4));
        this.addSection(customStyle(this,sample5));
        this.addSection(customStyle(this,sample6));
        this.addSection(customStyle(this,sample7));
        this.addSection(customStyle(this,sample8));

        // setting selected materal section, putting sections in an array
        //this.setSection();

    }

    @Override
    protected void onStart() {
        super.onStart();
        this.closeDrawer();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private Context mContext;
        private ProgressWheel mProgressWheel;
        private GridView mGridView;
        private ArrayList<Service> services;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_service_discovery, container, false);
            // Progress
            mProgressWheel = (ProgressWheel)rootView.findViewById(R.id.progress_wheel);

            // Listview
            mGridView = (GridView) rootView.findViewById(R.id.gridview);
            setupGridView();
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            mContext = activity;
        }

        // progress

        private void showProgressWheel () {
            mProgressWheel.setVisibility (View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mProgressWheel.spin ();
        }

        private void hideProgressWheel () {
            if(mProgressWheel.isSpinning())
                mProgressWheel.stopSpinning ();
            mGridView.setVisibility(View.VISIBLE);
            mProgressWheel.setVisibility (View.GONE);
        }

        // listview

        private void setupGridView() {
            if(services==null) {
                if(!Utilities.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "Check Your Network Connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressWheel();

                Ion
                        .with(mContext)
                        .load("https://api.github.com/users/voidabhi/repos")
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                hideProgressWheel();
                                if (e == null) {
                                    services = new ArrayList<Service>();
                                    for (int i = 0; i < result.size(); i++) {
                                        services.add(new Service(result.get(i).getAsJsonObject().get("name").getAsString(), "http://i.imgur.com/4GfhqNA.jpg"));
                                    }
                                    mGridView.setAdapter(new ServiceListAdapter(mContext, services));
                                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            startActivity(new Intent(getActivity(),Book.class));
                                        }
                                    });
                                } else {
                                    hideProgressWheel();
                                    Toast.makeText(mContext, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            } else {
                mGridView.setAdapter(new ServiceListAdapter(mContext, services));
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(getActivity(),Book.class));
                    }
                });
            }
        }

    }


    /* Child Fragment */

    public static class ChildFragment extends Fragment {

        private Handler handler;

        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.activity_listview,container,false);
            handler = new Handler();

            handler.post(new Runnable() {
                @Override
                public void run() {

                    ObservableScrollView scrollView = (ObservableScrollView)rootView.findViewById(R.id.sv_service_detail);
                    LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.ll_service_detail);
                    FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
                    SliderLayout sliderShow = (SliderLayout) rootView.findViewById(R.id.slider);
                    TextSliderView textSliderView1 = new TextSliderView(getActivity());
                    textSliderView1
                            .description("Game of Thrones")
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .empty(R.drawable.ny_light)
                            .image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

                    TextSliderView textSliderView2 = new TextSliderView(getActivity());
                    textSliderView2
                            .description("Hannibal")
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .empty(R.drawable.ny_light)
                            .image("http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");

                    TextSliderView textSliderView3 = new TextSliderView(getActivity());
                    textSliderView3
                            .description("House of Cards")
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .empty(R.drawable.ny_light)
                            .image("http://cdn3.nflximg.net/images/3093/2043093.jpg");

                    TextSliderView textSliderView4 = new TextSliderView(getActivity());
                    textSliderView4
                            .description("Big Bang Theory")
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .empty(R.drawable.ny_light)
                            .image("http://tvfiles.alphacoders.com/100/hdclearart-10.png");

                    sliderShow.addSlider(textSliderView1);
                    sliderShow.addSlider(textSliderView2);
                    sliderShow.addSlider(textSliderView3);
                    sliderShow.addSlider(textSliderView4);

                    fab.attachToScrollView(scrollView);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            share(getActivity(),"Share Via","This is subject","This is body");

                        }
                    });


                    ArrayList<String> items = loadItems(R.raw.headings);

                    for(int i=0;i<items.size();i++)
                    {
                        View serviceDetail = inflater.inflate(R.layout.item_service_detail,container,false);
                        TextView heading = (TextView)serviceDetail.findViewById(R.id.tv_heading);
                        TextView text = (TextView)serviceDetail.findViewById(R.id.tv_service_title);

                        heading.setText(items.get(i));
                        text.setText(items.get(i));
                        linearLayout.addView(serviceDetail);
                    }


                }
            });
            return rootView;
        }

        private ArrayList<String> loadItems(int rawResourceId) {
            try {
                ArrayList<String> countries = new ArrayList<String>();
                InputStream inputStream = getResources().openRawResource(rawResourceId);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    countries.add(line);
                }
                reader.close();
                return countries;
            } catch (IOException e) {
                return null;
            }
        }

    }
}
