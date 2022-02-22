package com.example.bardeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.jar.JarException;

public class ForgetPassweordAcrivity extends AppCompatActivity {

    private TextInputLayout emailRecovery;
    private Button button;
    private BrodcastedInternetReciver brodcastedInternetReciver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passweord_acrivity_layout);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext()
                , R.color.purple_200));

        brodcastedInternetReciver = new BrodcastedInternetReciver(this ,
                findViewById(R.id.storage));
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        emailRecovery = findViewById(R.id.inputEmailForSetForgetPassword);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailRecovery.getEditText().getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "oh email is empty", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    try {
                        FirebaseAuth targetUser = FirebaseAuth.getInstance();
                        String emailAddress = emailRecovery.getEditText().getText().toString();
                        targetUser.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext()
                                                    , "Send a Email for reset ... please check", Toast.LENGTH_SHORT)
                                                    .show();

                                            Intent intent = new Intent(ForgetPassweordAcrivity.this
                                                    , LoginActivity.class);
                                            startActivity(intent, ActivityOptions
                                                    .makeSceneTransitionAnimation(ForgetPassweordAcrivity.this)
                                                    .toBundle());
                                        }
                                    }
                                });
                    } catch (Exception e) {
                        // TODO : make a snacker for json pars error
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(brodcastedInternetReciver ,intentFilter);
    }
}