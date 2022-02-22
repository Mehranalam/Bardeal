package com.example.bardeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;


public class Launcher extends AppCompatActivity {


    private static final int DURATION_SPLASH = 1000;
    private FirebaseAuth myAuth;
    BrodcastedInternetReciver brodcastedInternetReciver;
    IntentFilter intentFilter;
    public ImageView logo;
    public FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();
        brodcastedInternetReciver = new BrodcastedInternetReciver(this ,
                findViewById(R.id.rootview));

        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        logo = findViewById(R.id.imageView3);


        // use brodcastrecicver for check in second internet connection ...


        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());

        new Handler().postDelayed(new Runnable() {
                                      @Override
                                      public void run() {
                                          if (brodcastedInternetReciver.getConnection()) {
                                              checking();
                                          }
                                      }
                                  }
                , DURATION_SPLASH);
    }


    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(brodcastedInternetReciver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(brodcastedInternetReciver);
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }


    public void checking() {

        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();

        if (user != null && isOnline()) {
            Intent intent = new Intent(Launcher.this, MainActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(Launcher.this).toBundle());

            finish();
        } else if (isOnline()) {
            Intent intent = new Intent(Launcher.this, WelcomingActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(Launcher.this).toBundle());

            finish();
        }

    }
}