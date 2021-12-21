package com.example.bardeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import pl.droidsonroids.gif.GifAnimationMetaData;


public class Launcher extends AppCompatActivity {


    private static final int DURATION_SPLASH = 1000;
    private Launcher isConvert = this;
    private FirebaseAuth myAuth;
    private TextView discription;
    private ImageView tryagin;
    private TextView lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setExitTransition(new Explode());
        tryagin = findViewById(R.id.imageView3);
        discription = findViewById(R.id.textView2);
        lang = findViewById(R.id.language);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOnline() && isEnLang()) {
                    checking();
                } else {
                    if (!isOnline() && !isEnLang()) {
                        lang.setText(R.string.lang);
                        lang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checking();
                            }
                        });
                        discription.setText(R.string.tryl);
                        discription.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checking();
                            }
                        });
                    } else if (!isEnLang()) {
                        lang.setText(R.string.lang);
                        lang.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checking();
                            }
                        });
                    } else if (!isOnline()) {
                        discription.setText(R.string.tryl);
                        discription.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checking();
                            }
                        });
                    }
                }
            }
        }, DURATION_SPLASH);

    }

    public boolean isEnLang() {
        String lang = Locale.getDefault().getDisplayLanguage();
        if (lang.equals("English")) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public void checking() {
        myAuth = FirebaseAuth.getInstance();
        FirebaseUser user = myAuth.getCurrentUser();

        if (user != null && isOnline() && isEnLang()) {
            Intent intent = new Intent(isConvert, MainActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(isConvert).toBundle());

            finish();
        } else if (isOnline() && isEnLang()) {
            Intent intent = new Intent(isConvert, WelcomingActivity.class);
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(isConvert).toBundle());

            finish();
        }
    }
}