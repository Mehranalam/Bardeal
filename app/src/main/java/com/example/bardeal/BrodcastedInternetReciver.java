package com.example.bardeal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseUser;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;



public class BrodcastedInternetReciver extends BroadcastReceiver {

    private ConstraintLayout rootview;
    private Activity activity;
    public FirebaseUser myAuth;
    private Context context;
    private boolean isonlineThisDevice;
    private Banner banner;

    public BrodcastedInternetReciver(Activity activity , ConstraintLayout rootview){
        this.activity = activity;
        this.rootview = rootview;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (!isOnline(context)) {
            banner = new Banner.Builder(context).setParent(rootview)
                    .setIcon(R.drawable.ic_baseline_signal_cellular_connected_no_internet_4_bar_24)
                    .setMessage("Internet connection is broken")
                    .create();
            banner.setButtonsTextColor(R.color.white);
            banner.setBackgroundColor(this.activity.getResources().getColor(R.color.backgroundForError));
            banner.show();
        } else {
            isonlineThisDevice = true;
            if (banner != null){
                banner.dismiss();
                activity.finish();
                activity.startActivity(activity.getIntent());
            }
        }
    }

    public boolean getConnection(){
        return isonlineThisDevice;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }
//
//    public void checking(){
//
//        if (myAuth != null && isOnline(context) && isEnglish) {
//            Intent intent = new Intent(activity, MainActivity.class);
//            this.activity.startActivity(intent,
//                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
//
//            this.activity.finish();
//        } else if (isOnline(context) && isEnglish) {
//            Intent intent = new Intent(activity, WelcomingActivity.class);
//            this.activity.startActivity(intent,
//                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
//
//            this.activity.finish();
//        }
//    }

}
