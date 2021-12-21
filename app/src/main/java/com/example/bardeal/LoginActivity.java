package com.example.bardeal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabColorSchemeParams;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
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

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private ImageView google;
    private ImageView twitter;
    private FirebaseAuth myAuth;
    private LoginActivity forEnableActivity = this;
    private TextView forgetText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiti_layout);

        getWindow().setEnterTransition(new Explode());
        getWindow().setExitTransition(new Slide());

        myAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.EmailInput);
        password = findViewById(R.id.passwordInput);
        forgetText = findViewById(R.id.forgetPasswordTextView);
        google = findViewById(R.id.googleAPI);
        twitter = findViewById(R.id.twitter);
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(email ,"in the next version this feature is enable" ,Snackbar.LENGTH_LONG)
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
                                customTabsIntent.launchUrl(LoginActivity.this ,Uri.parse(url));
                            }
                        }).show();
            }
        });

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.clientID))
                .requestEmail()
                .build();

        GoogleSignInClient mg = GoogleSignIn.getClient(this, googleSignInOptions);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Task<GoogleSignInAccount> task = GoogleSignIn
                                    .getSignedInAccountFromIntent(data);

                            try {
                                GoogleSignInAccount account = task.getResult(ApiException.class);
                                firebaseAuthWithGoogle(account.getIdToken());
                            } catch (ApiException e){
                                System.out.println("please check vpn connection");
                            }
                        }
                    }
                }
        );

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mg.getSignInIntent();
                activityResultLauncher.launch(intent);
            }
        });



        forgetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this
                        ,ForgetPassweordAcrivity.class);
                startActivity(intent ,
                        ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());

                finish();
            }
        });

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext()
                , R.color.purple_200));


    }

    public void firebaseAuthWithGoogle(String token){
        AuthCredential credential = GoogleAuthProvider.getCredential(token ,null);
        myAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = myAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this
                                    ,Categories.class);

                            startActivity(intent ,ActivityOptions.
                                    makeSceneTransitionAnimation(LoginActivity.this)
                                    .toBundle());
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext() , "please check vpn connection"
                                    ,Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser isCurrentUser = myAuth.getCurrentUser();
        if (isCurrentUser != null) {
            Snackbar.make(email, R.string.snackerbanner, Snackbar.LENGTH_SHORT)
                    .show();

            Intent intent = new Intent(this, Categories.class);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
        }
    }


    public void signIn(View view) {
        if (email.getEditText().getText().toString().matches("") ||
                password.getEditText().getText().toString().matches("")) {
            Toast.makeText(getApplicationContext() ,"Please Complement field" ,Toast.LENGTH_SHORT)
                    .show();
        } else {
            myAuth.signInWithEmailAndPassword(email.getEditText().getText().toString(),
                    password.getEditText().getText().toString())
                    .addOnCompleteListener(this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = myAuth.getCurrentUser();

                                        // TODO : this part start activity for MainActivity and ago..
                                        Snackbar.make(email, R.string.snackerbannerSucssfule,
                                                Snackbar.LENGTH_SHORT)
                                                .show();
                                        Intent intentForEndOfLogin = new Intent(forEnableActivity,
                                                Categories.class);
                                        startActivity(intentForEndOfLogin,
                                                ActivityOptions.makeSceneTransitionAnimation(forEnableActivity).toBundle());

                                        finish();
                                    } else {
                                        Snackbar.make(email,"please check vpn connection", Snackbar.LENGTH_SHORT)
                                                .show();
                                    }
                                }
                            });
        }
    }


    public void goToSingUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        finish();
    }
}
