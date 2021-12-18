package com.example.bardeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private ImageView appleApi;
    private ImageView google;
    private Button finishOfLogin;
    private FirebaseAuth myAuth;
    private View ConstraintLayout;
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
                                        Snackbar.make(email, R.string.Toast, Snackbar.LENGTH_SHORT)
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
