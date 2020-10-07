package com.geekdroids.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_signup extends AppCompatActivity {
    //variables
    Button login_btn,signup;
    ImageButton adminmode;
    ImageView carimage;
    TextView welcome, signintext;
    TextInputEditText Email,Password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_signup);


        //Hooks

        signup = findViewById(R.id.signup);
        carimage =  findViewById(R.id.logoImage);
        welcome =  findViewById(R.id.welcomeback);
        signintext =  findViewById(R.id.signin);
        Email =  findViewById(R.id.email_signin);
        Password =  findViewById(R.id.password);
        login_btn =  findViewById(R.id.loginbtn);
        adminmode = findViewById(R.id.customermode);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), home_screen.class));
            finish();
        }


        login_btn.setOnClickListener(new View.OnClickListener() {
                                         @Override
                 public void onClick(View v) {
                     String email = Email.getText().toString().trim();
                     String password = Password.getText().toString().trim();


                     if (TextUtils.isEmpty(email)) {
                         Email.setError("Email is Required");
                         return;
                     }

                     if (TextUtils.isEmpty(password)) {
                         Password.setError("Password is required");
                         return;
                     }

                     if (Password.length() <= 6) {
                         Password.setError("Password must be 6 or longer characters");
                         return;
                     }

                     progressBar.setVisibility(View.VISIBLE);


                     firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {

                                 Toast.makeText(login_signup.this, "Logged in successfully"  , Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(getApplicationContext(), home_screen.class));
                             } else {

                                 Toast.makeText(login_signup.this, "Error incorrect password or email" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.GONE);

                             }
                         }
                     });
                 }
             }
             );


        adminmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminMode.class));
                finish();
            }
        });




        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(login_signup.this, signup.class);

                        Pair[] pairs = new Pair[7];
                        pairs[0] = new Pair<View,String >(carimage, "logoImage");
                        pairs[1] = new Pair<View,String >(welcome, "logoText");
                        pairs[2] = new Pair<View,String >(signintext, "desc_transition");
                        pairs[3] = new Pair<View,String >(Email, "email_transiton");
                        pairs[4] = new Pair<View,String >(Password, "password_transiton");
                        pairs[5] = new Pair<View,String >(login_btn, "loginbtn_transiton");
                        pairs[6] = new Pair<View,String >(signup, "signupbtn_transiton");

                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login_signup.this, pairs);
                        startActivity(intent, options.toBundle());
                        finish();
                    }
                }
        );
    }
}