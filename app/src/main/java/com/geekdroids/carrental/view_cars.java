package com.geekdroids.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_cars extends AppCompatActivity {

    ListView myListView;
    DatabaseReference ref;
    List<Cars> carsList;
    Button carAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cars);

        myListView = findViewById(R.id.car_listview);
        carsList = new ArrayList<>();
        carAdd = findViewById(R.id.caradd);

        ref = FirebaseDatabase.getInstance().getReference("CAR");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                carsList.clear();
                for (DataSnapshot carSnap : snapshot.getChildren()){
                    Cars cars = carSnap.getValue(Cars.class);
                    carsList.add(cars);
                }
                CarListAdapter carListAdapter = new CarListAdapter(view_cars.this,carsList);
                myListView.setAdapter(carListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        carAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), carsslist.class));
                finish();
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cars cars = carsList.get(position);
                showUpdateDialog(cars.getModel());

            }
        });
    }


    private void showUpdateDialog(final String model) {
        final AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.carup_custom, null);

        mDialog.setView(mDialogView);

        final EditText upModel = mDialogView.findViewById(R.id.upModel);
        final EditText upOwner = mDialogView.findViewById(R.id.upOwner);
        final EditText upID = mDialogView.findViewById(R.id.upID);
        final Button upCar = mDialogView.findViewById(R.id.upCarBtn);
        Button delCar =mDialogView.findViewById(R.id.delCarBtn);

        mDialog.setTitle("Updating " + model + "'s record");
        mDialog.show();


        upCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newModel = upModel.getText().toString();
                String newOwner = upOwner.getText().toString();
                String newID = upID.getText().toString();



                updateData(newModel,newOwner,newID);
                Toast.makeText(view_cars.this, "Record Update", Toast.LENGTH_SHORT).show();

            }
        });


        delCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newModel = upModel.getText().toString();
                deleteRecord(newModel);

            }
        });

    }

    private void updateData(String model,String owner,String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CAR").child(model);
        Cars cars = new Cars(model,owner,id);
        reference.setValue(cars);
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void deleteRecord(String model){
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("CAR").child(model);

        Task<Void> mTask = dref.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                showToast("Deleted");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Nothing to Delete");

            }
        });
    }



}