package com.geekdroids.carrental;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class book_a_car extends AppCompatActivity implements AdapterView.OnItemSelectedListener{


    public static final String TAG = "TAG";
    Button savebtn;
    Spinner spinner, selectlocation;
    TextView selectcartxt;
    TextInputEditText Datestarttxt, DateEndTxt, TimeStarttxt, Timeendtxt;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    FirebaseUser user;
    String item, location;
    SelectCar selectCar;
    Location selectLocation;


    String[] carList = {"Choose a car", "Toyota Corolla" , "Mitsubishi Lancer", "Nissan - Gtr", "Subaru Impreza"};
    String[] locationList = {"Choose a location", "Colombo branch", "Negombo branch","Jaffna branch", "BIA - Bandaranayake International Airport", "Mattala Airport"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_book_a_car);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        user = firebaseAuth.getCurrentUser();

        selectcartxt = findViewById(R.id.selectCarTxt);
        Datestarttxt= findViewById(R.id.setDate);
        DateEndTxt = findViewById(R.id.setendDate);
        TimeStarttxt = findViewById(R.id.setTime);
        Timeendtxt = findViewById(R.id.setendTime);
        savebtn = findViewById(R.id.save);

        spinner = findViewById(R.id.selectcar);
        spinner.setOnItemSelectedListener(this);

        selectlocation= findViewById(R.id.selectLocation);
        selectlocation.setOnItemSelectedListener(this);

        selectCar = new SelectCar();
        selectLocation = new Location();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, carList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        ArrayAdapter LocationAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, locationList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectlocation.setAdapter(LocationAdapter);




        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save(item);
                SaveLocation(location);
                SaveDate();
                SaveTime();

                startHomescreen();




            }
        });

    }





    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = spinner.getSelectedItem().toString();
        location = selectLocation.getLocationList();
        //selectcartxt.setText(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void startHomescreen(){
        startActivity(new Intent(getApplicationContext(),home_screen.class));
        finish();
    }

    void Save(String item) {

        if (item == "Choose a car") {
            Toast.makeText(this, "Select a car", Toast.LENGTH_SHORT).show();
        } else {
            selectCar.setCarList(item);

            item = spinner.getSelectedItem().toString();

            DocumentReference documentReference = firestore.collection("Selected car").document(user.getUid());
            final Map<String, Object> carList = new HashMap<>();
            carList.put("Car model", item);

            final String finalItem = item;
            documentReference.set(carList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(book_a_car.this, finalItem + " has been added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String error = e.getMessage();

                    Toast.makeText(book_a_car.this, "error" + error, Toast.LENGTH_SHORT).show();
                }
            });



        }
    }

    void SaveLocation( String location){

        if (location == "Choose a location") {
            Toast.makeText(this, "Select a location", Toast.LENGTH_SHORT).show();
        } else {
            selectLocation.setLocationList(location);

            location = selectlocation.getSelectedItem().toString();

            DocumentReference documentReference = firestore.collection("Selected location").document(user.getUid());
            final Map<String, Object> locationList = new HashMap<>();
            locationList.put("Location", location);

            final String finalItem = location;
            documentReference.set(locationList).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(book_a_car.this, finalItem + " has been added", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String error = e.getMessage();

                    Toast.makeText(book_a_car.this, "error" + error, Toast.LENGTH_SHORT).show();
                }
            });


        }

    }



void SaveDate(){
    String date = Datestarttxt.getText().toString();
    DocumentReference documentReference = firestore.collection("Date").document(user.getUid()).collection("Start Date").document("date");

    Map<String,Object> setdate = new HashMap<>();
    setdate.put("Start Date", date);

    documentReference.set(setdate).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(book_a_car.this, "Start date added", Toast.LENGTH_SHORT);
        }
    });

    String endDate = DateEndTxt.getText().toString();
    DocumentReference documentReference1 = firestore.collection("Date").document(user.getUid()).collection("End Date").document("date");

    Map<String, Object> enddate = new HashMap<>();
    enddate.put("End Date", endDate);

    documentReference1.set(enddate).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(book_a_car.this, "End date added", Toast.LENGTH_SHORT);
        }
    });
}

void SaveTime(){
    String time = TimeStarttxt.getText().toString();
    DocumentReference documentReference2 = firestore.collection("Time").document(user.getUid()).collection("Start Time").document("Start Time");

    Map<String,Object> settime = new HashMap<>();
    settime.put("Start Time", time);

    documentReference2.set(settime).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(book_a_car.this, "Start time added", Toast.LENGTH_SHORT);
        }
    });

    String Endtime = Timeendtxt.getText().toString();
    DocumentReference documentReference3 = firestore.collection("Time").document(user.getUid()).collection("Return Time").document("End Time");

    Map<String,Object> endtime = new HashMap<>();
    endtime.put("End Time", Endtime);

    documentReference3.set(endtime).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Toast.makeText(book_a_car.this, "End time added", Toast.LENGTH_SHORT);
        }
    });

}




}