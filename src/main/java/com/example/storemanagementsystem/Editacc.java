package com.example.storemanagementsystem;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Editacc extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b;
    Dbhl store;
    SQLiteDatabase s;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.editacc);
        store=new Dbhl(this);
        e1=findViewById(R.id.nu);
        e2=findViewById(R.id.np);
        e3=findViewById(R.id.n);
        b=findViewById(R.id.u);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e1.length()==0 || e2.length()==0 || e3.length()==0)
                {
                    Toast.makeText(Editacc.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    store.updatedata(e1.getText().toString(),e2.getText().toString(),e3.getText().toString(),s);
                    Toast.makeText(Editacc.this,"Details Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent i= new Intent(Editacc.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
