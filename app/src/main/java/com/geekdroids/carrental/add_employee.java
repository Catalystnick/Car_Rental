package com.geekdroids.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class add_employee extends AppCompatActivity {
    TextInputEditText empName,empDOB,eNIC,ePhone,ePost,empEmail;
    Button saveEmp;
    DatabaseReference reff;
    Employee employee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        empName = findViewById(R.id.emp_name);
        empDOB = findViewById(R.id.dob);
        eNIC = findViewById(R.id.enic);
        ePhone = findViewById(R.id.e_phone);
        ePost = findViewById(R.id.emp_post);
        empEmail = findViewById(R.id.emp_email);
        saveEmp = findViewById(R.id.addE_btn);

        reff = FirebaseDatabase.getInstance().getReference().child("Employee");

        saveEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String eName = empName.getText().toString();
                String eBirth = empDOB.getText().toString();
                String ENIC = eNIC.getText().toString();
                String emPhone = ePhone.getText().toString();
                String eOccu = ePost.getText().toString();
                String eEmail = empEmail.getText().toString();

                employee = new Employee(eName,eBirth,ENIC,emPhone,eOccu,eEmail);
                reff.child(eName).setValue(employee);

                startActivity(new Intent(getApplicationContext(), employee_list.class));
                finish();



            }
        });



    }
}