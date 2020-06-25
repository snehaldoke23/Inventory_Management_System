package com.example.storemanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText username,password,rpassword,name;
    Button r;
    Dbhl store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        username = findViewById(R.id.usernameedit);
        rpassword = findViewById(R.id.rp);
        store=new Dbhl(this);
        name = findViewById(R.id.n);
        password = findViewById(R.id.passwordedit);
        r = findViewById(R.id.register);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.length()==0 || password.length()==0 || rpassword.length()==0 || name.length()==0)
                {
                    Toast.makeText(Register.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if (username.length()<3 || password.length()<3 || rpassword.length()<3)
                {
                    Toast.makeText(Register.this,"Too Short",Toast.LENGTH_SHORT).show();
                }
                else if (!password.getText().toString().equals(rpassword.getText().toString()))
                {
                    Toast.makeText(Register.this,"Passwords should match",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    store.insertDataR(username.getText().toString(),password.getText().toString(),name.getText().toString());
                    Toast.makeText(Register.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(Register.this,Login.class);
                    startActivity(i);
                }
            }
        });
    }
}
