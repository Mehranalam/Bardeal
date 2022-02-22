package com.example.bardeal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthProvider;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private ImageView google;
    private FirebaseAuth myAuth;
    private ImageView twtter;
    private SignUpActivity isConvert = this;
    private BrodcastedInternetReciver brodcastedInternetReciver;
    private IntentFilter intentFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);

        email = findViewById(R.id.EmailInputSignUp);
        password = findViewById(R.id.CreatePasswordSignUp);
        google = findViewById(R.id.googleAPISignUp);
        twtter = findViewById(R.id.twitterSignUp);

        brodcastedInternetReciver = new BrodcastedInternetReciver(this ,
                findViewById(R.id.storage));
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        twtter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(email, "in the next version this feature is enable", Snackbar.LENGTH_LONG)
                        .setAction("Learn More", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int color = Color.parseColor("#e74a06");
                                CustomTabColorSchemeParams schemeParams = new CustomTabColorSchemeParams
                                        .Builder().setToolbarColor(color)
                                        .build();

                                String url = "https://firebase.google.com/docs/auth/android/twitter-login?authuser=0";
                                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                CustomTabsIntent customTabsIntent = builder.build();
                                builder.setDefaultColorSchemeParams(schemeParams);
                                customTabsIntent.launchUrl(SignUpActivity.this, Uri.parse(url));
                            }
                        }).show();
            }
        });

        OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");

        myAuth = FirebaseAuth.getInstance();

        getWindow().setEnterTransition(
                new Explode()
        );

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext()
                , R.color.purple_200));

        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientID))
                .requestEmail()
                .build();

        GoogleSignInClient client = GoogleSignIn
                .getClient(getApplicationContext(), signInOptions);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn
                                    .getSignedInAccountFromIntent(data);

                            try {
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                firebaseAuthWithGoogleBardeal(account.getIdToken());
                            } catch (ApiException e) {
                                System.out.println("please check vpn connection");
                            }
                        }
                    }
                }
        );

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = client.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(brodcastedInternetReciver ,intentFilter);

        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser != null) {
            Snackbar.make(email, R.string.snackerbanner, Snackbar.LENGTH_SHORT)
                    .show();

            Intent intent = new Intent(this, Categories.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
        }
    }

    public void singUp(View view) {
        // TODO: Set this field for build a account by generous user
        if (email.getEditText().getText().toString().matches("")
                || password.getEditText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Please Complement field", Toast.LENGTH_SHORT)
                    .show();
        } else {
            myAuth.createUserWithEmailAndPassword(email.getEditText().getText().toString(),
                    password.getEditText().getText().toString()).addOnCompleteListener(this,
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = myAuth.getCurrentUser();
                                Snackbar.make(email, "Oh ok is create a account letsGo", Snackbar.LENGTH_SHORT)
                                        .show();

                                Intent intent = new Intent(isConvert, Categories.class);
                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(isConvert).toBundle());
                                finish();
                            } else {
                                Snackbar.make(email, "please check vpn connection", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
        }
    }


    public void goToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    private void firebaseAuthWithGoogleBardeal(String token) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(token, null);
        myAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = myAuth.getCurrentUser();
                            Intent intent = new Intent(SignUpActivity.this
                                    , Categories.class);

                            startActivity(intent, ActivityOptions.
                                    makeSceneTransitionAnimation(SignUpActivity.this)
                                    .toBundle());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please check VPN"
                                    , Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

}