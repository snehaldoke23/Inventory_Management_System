package com.example.storemanagementsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText usernamel, passwordl;
    Button login;
    TextView register1;
    Dbhl store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = findViewById(R.id.login1);
        store= new Dbhl(this);
        usernamel= findViewById(R.id.usernameedit);
        passwordl= findViewById(R.id.passwordedit);
        register1= findViewById(R.id.register);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernamel.getText().toString();
                String password = passwordl.getText().toString();
                if (username.length()==0 || password.length()==0)
                {
                    Toast.makeText(Login.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
                else if (username.length() > 0 && password.length() > 0)
                {
                    if (store.Loginmethod(username,password))
                    {
                        Toast.makeText(Login.this,"Successfully Logged In", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(Login.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(Login.this, Login.class));
        finish();
    }
}
