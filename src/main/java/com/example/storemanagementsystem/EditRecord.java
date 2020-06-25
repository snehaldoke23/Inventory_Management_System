package com.example.storemanagementsystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class EditRecord extends AppCompatActivity {

    EditText e1,e2,e3,e4;
    Button b;
    Dbhl store;
    SQLiteDatabase s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editrecord);
        store = new Dbhl(this);
        b = findViewById(R.id.urecord);
        e1 = findViewById(R.id.ed1);
        e2 = findViewById(R.id.ed2);
        e3 = findViewById(R.id.ed3);
        e4 = findViewById(R.id.ed4);
        e1.setText(getIntent().getStringExtra("ID"));
        e2.setText(getIntent().getStringExtra("Name"));
        e3.setText(getIntent().getStringExtra("Phone"));
        e4.setText(getIntent().getStringExtra("Quant"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.updateRdata(e2.getText().toString(),e1.getText().toString(),e4.getText().toString(),e3.getText().toString(),s);
                Toast.makeText(EditRecord.this,"Updated",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditRecord.this,DisplaySQLiteDataActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(EditRecord.this, DisplaySQLiteDataActivity.class));
        finish();
    }
}
