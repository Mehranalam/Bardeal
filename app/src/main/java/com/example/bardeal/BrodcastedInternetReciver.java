package com.example.bardeal;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseUser;
import com.sergivonavi.materialbanner.Banner;
import com.sergivonavi.materialbanner.BannerInterface;



public class BrodcastedInternetReciver extends BroadcastReceiver {

    private ConstraintLayout rootview;
    private Activity activity;
    private boolean isEnglish;
    public FirebaseUser myAuth;
    private Context context;

    public BrodcastedInternetReciver(Activity activity , boolean isEnglish , FirebaseUser myAuth){
        this.activity = activity;
        this.isEnglish = isEnglish;
        this.myAuth = myAuth;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if (!isOnline(context)) {
            rootview = this.activity.findViewById(R.id.rootview);
            Banner banner = new Banner.Builder(context).setParent(rootview)
                    .setIcon(R.drawable.ic_baseline_network_check_24)
                    .setMessage("connection is filed ... please back to connection ")
                    .setLeftButton("Dismiss", new BannerInterface.OnClickListener() {
                        @Override
                        public void onClick(BannerInterface banner) {
                            banner.dismiss();
                        }
                    })
                    .create();
            banner.setButtonsTextColor(R.color.white);
            banner.setBackgroundColor(this.activity.getResources().getColor(R.color.purple_200));
            banner.show();
        } else {
            checking();
        }
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void checking(){

        if (myAuth != null && isOnline(context) && isEnglish) {
            Intent intent = new Intent(activity, MainActivity.class);
            this.activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());

            this.activity.finish();
        } else if (isOnline(context) && isEnglish) {
            Intent intent = new Intent(activity, WelcomingActivity.class);
            this.activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());

            this.activity.finish();
        }
    }

}
