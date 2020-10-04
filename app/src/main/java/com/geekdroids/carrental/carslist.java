package com.geekdroids.carrental;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.geekdroids.carrental.Employee;
import com.geekdroids.carrental.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class carslist extends AppCompatActivity {
    TextInputEditText carModel,CarOwner,VID;
    Button saveCar;
    DatabaseReference reff;
    Cars cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carslist);

        carModel = findViewById(R.id.car_model);
        CarOwner = findViewById(R.id.owner);
        VID = findViewById(R.id.vid);
        saveCar = findViewById(R.id.add_car);

        reff = FirebaseDatabase.getInstance().getReference().child("CAR");
        //firebase reference

        saveCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String model = carModel.getText().toString();
                String owner = CarOwner.getText().toString();
                String ID = VID.getText().toString();


                cars = new Cars(model,owner,ID);
                reff.child(ID).setValue(cars);



            }
        });



    }
}