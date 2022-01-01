package com.example.bardeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;


/*
    This activity gives a general overview of the facilities
    that are supposed to be available to users.
    In the following, you will get acquainted with the details.
 */

public class WelcomingActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcoming_layout);

        ViewPager viewPager = findViewById(R.id.viewpager);
        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicatorjj);
        button = findViewById(R.id.nextButton);

        SliderAdapter sliderAdapter = new SliderAdapter(getApplicationContext());

        new MaterialAlertDialogBuilder(this)
                .setTitle("کمک برای استفاده راحت تر :)")
                .setMessage(R.string.rudd)
                .setNeutralButton("Github", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent customTabsIntent = builder.build();
                        customTabsIntent.launchUrl(WelcomingActivity.this ,
                                Uri.parse("https://github.com/Mehranalam"));
                    }
                })
                .show();

        viewPager.setAdapter(sliderAdapter);
        dotsIndicator.setViewPager(viewPager);


        getWindow().setExitTransition(new Explode());

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext()
                , R.color.purple_200));

    }

    public void clickInNextButton(View view){
        Intent intent = new Intent(this ,LoginActivity.class);
        startActivity(intent , ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }
}