package com.example.storemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class myacc extends AppCompatActivity {

    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myacc);
        b = findViewById(R.id.editacc);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(myacc.this,Editacc.class);
                startActivity(intent);
            }
        });
    }
}
