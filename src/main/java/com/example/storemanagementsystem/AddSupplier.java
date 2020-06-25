package com.example.storemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSupplier extends AppCompatActivity {

    Button b;
    EditText e1,e2;
    Dbhl store;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsupplier);
        b = findViewById(R.id.addsup);
        e1 = findViewById(R.id.sname);
        store = new Dbhl(this);
        e2 = findViewById(R.id.Scontact);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.insertDData(e1.getText().toString(),e2.getText().toString());
                Toast.makeText(AddSupplier.this, "Inserted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddSupplier.this,Dealers.class);
                startActivity(intent);
            }
        });
    }
}
