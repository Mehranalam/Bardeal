package com.example.bardeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private BrodcastedInternetReciver brodcastedInternetReciver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brodcastedInternetReciver = new BrodcastedInternetReciver(this ,
                findViewById(R.id.storage));
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        getWindow().setStatusBarColor(getResources().getColor(R.color.purple_200));

        // TODO : START NAVIGATED BOTTOM ....
        bottomNavigationView = findViewById(R.id.navigatebotton);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_tag ,StoreFragment.class ,null)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_tag, StoreFragment.class, null)
                            .commit();
                    return true;
                } else if (item.getItemId() == R.id.cash) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_tag ,CashingFragment.class ,null)
                            .commit();
                    return true;

                } else if (item.getItemId() == R.id.favorit) {
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_tag ,FavoriteFragment.class ,null)
                            .commit();
                    return true;

                } else if (item.getItemId() == R.id.dashboard) {
                    DashboardFragment dashboardFragment = new
                            DashboardFragment(getApplicationContext());
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragment_tag ,dashboardFragment ,null)
                            .commit();
                    return true;
                }

                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(brodcastedInternetReciver ,intentFilter);
    }
}