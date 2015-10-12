package com.ziuapp.ziu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;
import com.ziuapp.ziu.utils.Utilities;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 07-10-2015.
 */
public class Book extends Fragment {
    EditText nameedit;
    EditText mobileedit;
    EditText dealedit;
    EditText salonedit;
    Button book;

    private ProgressWheel mProgressWheel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.book, container, false);
        //setup the strict policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        nameedit = (EditText) rootView.findViewById(R.id.nameedit);
        mobileedit = (EditText) rootView.findViewById(R.id.mobileedit);
        dealedit = (EditText) rootView.findViewById(R.id.dealedit);
        salonedit = (EditText) rootView.findViewById(R.id.salonedit);
        mProgressWheel = (ProgressWheel) rootView.findViewById(R.id.progress_wheel);
        book = (Button) rootView.findViewById(R.id.submit);

        book.setOnClickListener(new View.OnClickListener() {
           // private Context mContext;
            InputStream is=null;
            public void onClick(View view) {
             /*   if (!Utilities.isNetworkAvailable(mContext)) {
                    Toast.makeText(mContext, "Check Your Network Connection!", Toast.LENGTH_LONG).show();
                    return;
                }*/

                String name = nameedit.getText().toString();
                String mobile = mobileedit.getText().toString();
                String deal = dealedit.getText().toString();
                String salon = salonedit.getText().toString();

                if (!isValidUsername(name)) {
                    nameedit.setError("Please Enter this Field");
                }

                if (!isValidMobile(mobile)) {
                    mobileedit.setError("Please Enter the valid number");
                }

                if (isValidUsername(name) && isValidMobile(mobile)) {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("name", name));
                    nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
                    nameValuePairs.add(new BasicNameValuePair("deal", deal));
                    nameValuePairs.add(new BasicNameValuePair("salon", salon));
                    try {
                        showProgressWheel();
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://sanchitgoel.net78.net/login2.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        hideProgressWheel();
                        nameedit.setText("");
                        mobileedit.setText("");
                        dealedit.setText("");
                        salonedit.setText("");
                        Toast.makeText(getActivity(), "Booked Successfully",
                                Toast.LENGTH_LONG).show();

                    } catch (ClientProtocolException e) {
                        Log.e("ClientProtocol", "Log_tag");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e("Log_tag", "IOException");
                        e.printStackTrace();
                    }
                }
            }
        });
       return rootView;
    }

    private boolean isValidUsername(String user) {
        if (user != null && user.length() >= 1 && user.length() <50) {
            return true;
        }
        return false;
    }

    private boolean isValidMobile(String mobile) {
        if (mobile != null && mobile.length() >= 10 && mobile.length() <14) {
            return true;
        }
        return false;
    }

    // progress

    private void showProgressWheel() {
        mProgressWheel.setVisibility(View.VISIBLE);
        book.setVisibility(View.GONE);
        mProgressWheel.spin();
    }

    private void hideProgressWheel() {
        if (mProgressWheel.isSpinning())
            mProgressWheel.stopSpinning();
        book.setVisibility(View.VISIBLE);
        mProgressWheel.setVisibility(View.GONE);
    }


}
