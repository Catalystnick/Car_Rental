package com.geekdroids.carrental;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMode extends AppCompatActivity {

    Button viewemp;
    Button viewcarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mode);

        viewemp = findViewById(R.id.viewemployee);
        viewcarr = findViewById(R.id.viewcar);


        viewemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), employee_list.class));
                finish();
            }
        });


        viewcarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), carsslist.class));
                finish();
            }
        });
    }

}

