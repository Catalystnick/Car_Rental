package com.geekdroids.carrental;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class employee_list extends AppCompatActivity {
    ListView myListView;
    DatabaseReference ref;
    List<Employee> employeeList;
    Button toAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        myListView = findViewById(R.id.emp_listview);
        employeeList = new ArrayList<>();
        toAdd = findViewById(R.id.toadd);

        ref = FirebaseDatabase.getInstance().getReference("Employee");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employeeList.clear();
                for (DataSnapshot empSnap : snapshot.getChildren()){
                    Employee employee = empSnap.getValue(Employee.class);
                    employeeList.add(employee);
                }
                EmpListAdapter empListAdapter = new EmpListAdapter(employee_list.this,employeeList);
                myListView.setAdapter(empListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), add_employee.class));
                finish();
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Employee employee = employeeList.get(position);
                showUpdateDialog(employee.getName());

            }
        });





    }

    private void showUpdateDialog(final String name){
        final AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.empup_custom,null);

        mDialog.setView(mDialogView);

        final EditText upEmail = mDialogView.findViewById(R.id.upMail);
        final EditText upNo = mDialogView.findViewById(R.id.upPhone);
        final EditText upOcc = mDialogView.findViewById(R.id.upPost);
        final EditText updob = mDialogView.findViewById(R.id.upDOB);
        final EditText upnic = mDialogView.findViewById(R.id.upNIC);
        final EditText upname = mDialogView.findViewById(R.id.upName);


        final Button up = mDialogView.findViewById(R.id.upBtn);
        Button del =mDialogView.findViewById(R.id.delBtn);

        mDialog.setTitle("Updating " + name + "'s record");
        mDialog.show();

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMail = upEmail.getText().toString();
                String newOcc = upOcc.getText().toString();
                String newPhone = upNo.getText().toString();
                String newNIC = upnic.getText().toString();
                String newName = upname.getText().toString();
                String newDOB = updob.getText().toString();


                updateData(newName,newPhone,newOcc,newMail,newDOB,newNIC);
                Toast.makeText(employee_list.this, "Record Update", Toast.LENGTH_SHORT).show();

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = upname.getText().toString();
                deleteRecord(newName);

            }
        });
    }

    private void updateData(String name,String phone,String post,String mail,String dob,String nic){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employee").child(name);
        Employee employee = new Employee(name,dob,nic,phone,post,mail);
        reference.setValue(employee);
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void deleteRecord(String name){
        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("Employee").child(name);

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

