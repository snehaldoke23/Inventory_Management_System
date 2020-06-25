package com.example.storemanagementsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class Bill extends AppCompatActivity {

    private IntentIntegrator qrScan;
    String scancontent;
    Bundle bundle;
    Dbhl store;
    Cursor cursor;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        store = new Dbhl(this);
        qrScan = new IntentIntegrator(Bill.this);
        qrScan.initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(Bill.this, "Result Not Found", Toast.LENGTH_LONG).show();
            }
            else
            {
                try
                {
                    JSONObject obj = new JSONObject(result.getContents());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Bill.this, result.getContents(), Toast.LENGTH_LONG).show();
                    scancontent = result.getContents();
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("\nContent:" +scancontent);
                    showMessage("Data",buffer.toString());
                    bundle = new Bundle();
                    bundle.putString("Stuff ", scancontent);
                }
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void showMessage(String title,String Message)
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(Bill.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton( "Add Item", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                if (store.Billmethod(scancontent))
                {
                    Toast.makeText(Bill.this,"Added",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Bill.this,viewbill.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Bill.this,"Out of Stock",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Bill.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        builder.show();
        builder.create().show();
    }

}
