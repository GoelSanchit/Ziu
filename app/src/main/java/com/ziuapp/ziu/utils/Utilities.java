package com.ziuapp.ziu.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.ziuapp.ziu.R;

import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class Utilities {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static MaterialSection customStyle(Context mContext,MaterialSection section){
        TextView sectionView = (TextView)section.getView().findViewById(R.id.section_text);
        sectionView.setTextAppearance(mContext,R.style.TextAppearance_SectionText);
        return section;
    }

    public static boolean share(Context context,String title,String subject,String body){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = body;
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, title));
        return true;
    }
}