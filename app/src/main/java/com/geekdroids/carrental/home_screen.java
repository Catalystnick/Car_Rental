package com.geekdroids.carrental;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class home_screen extends AppCompatActivity {

    public static final String TAG = "tag";
    TextView fullname, email, nic,phoneNo, Startdate, Enddate, startTime, endTime;
    ImageView profileImage;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    StorageReference storageReference;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_screen);


        fullname = findViewById(R.id.fname);
        email = findViewById(R.id.profileemail);
        nic = findViewById(R.id.nic);
        phoneNo = findViewById(R.id.phone);
        profileImage = findViewById(R.id.profile_image);
        Startdate = findViewById(R.id.startdate);
        Enddate = findViewById(R.id.returndate);
        startTime = findViewById(R.id.StartTime);
        endTime = findViewById(R.id.EndTime);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/" + firebaseAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        userId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot.exists()) {
                    fullname.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                    nic.setText(documentSnapshot.getString("Nic"));
                    phoneNo.setText(documentSnapshot.getString("PhoneNo"));
                } else {
                    Log.d(TAG, "onEvent: Document does not exist");
                }


            }
        });


//
        firestore = FirebaseFirestore.getInstance();
        final DocumentReference StartDate = firestore.collection("Date").document(userId).collection("Start Date").document("date");
        StartDate.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot.exists()) {
                    Startdate.setText(documentSnapshot.getString("Start Date"));
                }else {
                    Log.d(TAG, "on Event: document doesnt exist");
                }
            }

        });

        final  DocumentReference EndDate = firestore.collection("Date").document(userId).collection("End Date").document("date");
        EndDate.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot.exists()) {
                    Enddate.setText(documentSnapshot.getString("End Date"));
                }else {
                    Log.d(TAG, "on Event: document doesnt exist");
                }
            }

        });



        final  DocumentReference starttime = firestore.collection("Time").document(userId).collection("Start Time").document("Start Time");
        starttime.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot.exists()) {
                    startTime.setText(documentSnapshot.getString("Start Time"));
                }else {
                    Log.d(TAG, "on Event: document doesnt exist");
                }
            }

        });

        final  DocumentReference returntime = firestore.collection("Time").document(userId).collection("Return Time").document("End Time");
        returntime.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

                if (documentSnapshot.exists()) {
                    endTime.setText(documentSnapshot.getString("End Time"));
                }else {
                    Log.d(TAG, "on Event: document doesnt exist");
                }
            }

        });

    }




    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), login_signup.class));
        finish();
    }

    public void ManageProfile(View view) {

        Intent intent = new Intent(view.getContext(), Profile.class);
        intent.putExtra("fullname", fullname.getText().toString());
        intent.putExtra("email",  email.getText().toString());
        intent.putExtra("nic",  nic.getText().toString());
        intent.putExtra("phone", phoneNo.getText().toString());
        startActivity(intent);

    }

    public void BookACar(View view){
        Intent intent = new Intent(view.getContext(), book_a_car.class);
        startActivity(intent);
    }
}