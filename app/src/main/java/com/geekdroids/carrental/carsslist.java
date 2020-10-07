package com.geekdroids.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class carsslist extends AppCompatActivity {

    TextInputEditText carmodell, ownerr, vehicleid;
    Button addcar;
    ProgressBar progressBar1;
    FirebaseFirestore firestore;
    DatabaseReference reff;
    Cars cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_carsslist);


        carmodell = findViewById(R.id.car_model);
        ownerr = findViewById(R.id.owner);
        vehicleid = findViewById(R.id.vid);
        addcar = findViewById(R.id.add_car);

        reff = FirebaseDatabase.getInstance().getReference().child("CAR");


        addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String model = carmodell.getText().toString();
                String owner = ownerr.getText().toString();
                String ID = vehicleid.getText().toString();


                cars = new Cars(model, owner, ID);
                reff.child(ID).setValue(cars);


            }
        });


    }
}

