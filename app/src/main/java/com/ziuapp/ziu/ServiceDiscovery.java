package com.ziuapp.ziu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.ziuapp.ziu.adapters.ServiceListAdapter;
import com.ziuapp.ziu.entities.Service;
import com.ziuapp.ziu.utils.Utilities;

import java.util.ArrayList;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;
import static com.ziuapp.ziu.utils.Utilities.customStyle;



public class ServiceDiscovery extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle bundle) {

        setDrawerHeaderImage(new ColorDrawable(getResources().getColor(R.color.theme_color)));

        // Categories
        MaterialSection trending = newSection("Trending", new PlaceholderFragment());

        // create sections
        this.addSection(customStyle(this,trending));

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
            mProgressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);

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

        private void showProgressWheel() {
            mProgressWheel.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mProgressWheel.spin();
        }

        private void hideProgressWheel() {
            if (mProgressWheel.isSpinning())
                mProgressWheel.stopSpinning();
            mGridView.setVisibility(View.VISIBLE);
            mProgressWheel.setVisibility(View.GONE);
        }

        // listview

        private void setupGridView() {
            if (services == null) {
                if (!Utilities.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "Check Your Network Connection!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressWheel();

                Ion
                        .with(mContext)
                        .load("http://sanchitgoel.net78.net/ziu.php")
                        .asJsonArray()
                        .setCallback(new FutureCallback<JsonArray>() {
                            @Override
                            public void onCompleted(Exception e, JsonArray result) {
                                hideProgressWheel();
                                if (e == null) {
                                    services = new ArrayList<Service>();
                                    for (int i = 0; i < result.size(); i++) {
                                        services.add(new Service(result.get(i).getAsJsonObject().get("text").getAsString(), result.get(i).getAsJsonObject().get("url").getAsString()));
                                    }
                                    mGridView.setAdapter(new ServiceListAdapter(mContext, services));
                                    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                           // startActivity( new Intent(getActivity(),BookActivity.class));
                                            ServiceListAdapter category = new ServiceListAdapter(mContext,services);
                                            Intent i = new Intent(getActivity(),BookActivity.class);
                                            i.putExtra("title",category.getItem(position).getTitle());
                                            startActivity(i);
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
                        startActivity( new Intent(getActivity(),BookActivity.class));
                    }
                });
            }
        }

    }

    }

