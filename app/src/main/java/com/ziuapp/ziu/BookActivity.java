package com.ziuapp.ziu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
public class BookActivity extends Activity {
    EditText nameedit;
    EditText mobileedit;
    EditText dealedit;
    EditText salonedit;
    Button book;
    String newdeal;

    private ProgressWheel mProgressWheel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setup the strict policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.book);
        nameedit = (EditText) findViewById(R.id.nameedit);
        mobileedit = (EditText) findViewById(R.id.mobileedit);
        dealedit = (EditText) findViewById(R.id.dealedit);
        salonedit = (EditText) findViewById(R.id.salonedit);
        mProgressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        book = (Button) findViewById(R.id.submit);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newdeal= null;
            } else {
                newdeal= extras.getString("title");
            }
        } else {
            newdeal= (String) savedInstanceState.getSerializable("title");
        }
        String[] arr = newdeal.split(" ");
        dealedit.setText(arr[0].trim());
        salonedit.setText(arr[1].trim());
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
                        Toast.makeText(getApplicationContext(), "Booked Successfully",
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}