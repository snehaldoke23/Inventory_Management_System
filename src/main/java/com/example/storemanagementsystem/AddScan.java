package com.example.storemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddScan extends AppCompatActivity {
    EditText name,id,quantity,price;
    Dbhl store;
    Button add, p, m;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addscan);
        store = new Dbhl(this);
        name = findViewById(R.id.nameA);
        id = findViewById(R.id.idA);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("Stuff ");
        id.setText(message);
        quantity = findViewById(R.id.quantityA);
        price = findViewById(R.id.priceA);
        add = findViewById(R.id.add2);
        p = findViewById(R.id.p);
        m = findViewById(R.id.m);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.insertData(name.getText().toString(),id.getText().toString(),quantity.getText().toString(),price.getText().toString());
                Toast.makeText(AddScan.this, "Added", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AddScan.this, MainActivity.class);
                startActivity(intent);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtractOneToQuantity();
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sumOneToQuantity();
            }
        });
    }
    private void sumOneToQuantity() {
        String previousValueString = quantity.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            previousValue = 0;
        } else {
            previousValue = Integer.parseInt(previousValueString);
        }
        quantity.setText(String.valueOf(previousValue + 1));
    }
    private void subtractOneToQuantity() {
        String previousValueString = quantity.getText().toString();
        int previousValue;
        if (previousValueString.isEmpty()) {
            return;
        } else if (previousValueString.equals("0")) {
            return;
        } else {
            previousValue = Integer.parseInt(previousValueString);
            quantity.setText(String.valueOf(previousValue - 1));
        }

    }
}
