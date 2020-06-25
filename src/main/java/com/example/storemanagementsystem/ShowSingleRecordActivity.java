package com.example.storemanagementsystem;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowSingleRecordActivity extends AppCompatActivity {

    TextView e1, e2, e3,e4;
    Dbhl store;
    Button Delete, Edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_single_record);
        e1 = findViewById(R.id.textViewID1);
        e2 = findViewById(R.id.textViewName);
        e3 = findViewById(R.id.textViewPhoneNumber);
        e4 = findViewById(R.id.textViewQuantity);
        Delete = (Button)findViewById(R.id.buttonDelete);
        Edit = (Button)findViewById(R.id.buttonEdit);
        store = new Dbhl(this);
        e1.setText(getIntent().getStringExtra("ID"));
        e2.setText(getIntent().getStringExtra("Name"));
        e3.setText(getIntent().getStringExtra("Phone"));
        e4.setText(getIntent().getStringExtra("Quant"));
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                store.deleteData(e1.getText().toString());
                Toast.makeText(ShowSingleRecordActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowSingleRecordActivity.this,DisplaySQLiteDataActivity.class);
                startActivity(intent);
            }
        });
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowSingleRecordActivity.this,EditRecord.class);
                intent.putExtra("ID",e1.getText().toString());
                intent.putExtra("Name",e2.getText().toString());
                intent.putExtra("Phone",e3.getText().toString());
                intent.putExtra("Quant",e4.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
