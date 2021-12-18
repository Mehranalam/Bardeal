package com.example.bardeal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout password;
    private TextView clickRules;
    private FirebaseAuth myAuth;
    private SignUpActivity isConvert = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);

        email = findViewById(R.id.EmailInputSignUp);
        password = findViewById(R.id.CreatePasswordSignUp);

        myAuth = FirebaseAuth.getInstance();

        getWindow().setEnterTransition(
                new Explode()
        );

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext()
                , R.color.purple_200));
    }

    @Override
    protected void onStart() {
        super.onStart();

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
            Toast.makeText(getApplicationContext() ,"Please Complement field" ,Toast.LENGTH_SHORT)
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
                                Snackbar.make(email, "oh have a wrong", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    });
        }
    }


    public void goToLogin(View view){
        Intent intent = new Intent(this ,LoginActivity.class);
        startActivity(intent ,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}