package com.example.storemanagementsystem;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class sendbill extends AppCompatActivity {
    EditText e1;
    Button b;
    String msg, phn;
    SQLiteDatabase sqLiteDatabase;
    Dbhl sqLiteHelper;
    Cursor cursor;
    int total;
    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    ArrayList<String> PHONE_NUMBER_Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendbill);
        e1 = findViewById(R.id.num4);
        ID_Array = new ArrayList<String>();
        NAME_Array = new ArrayList<String>();
        PHONE_NUMBER_Array = new ArrayList<String>();
        msg = "Thank you for purchasing! Here's your Bill.";
        phn = e1.getText().toString();
        b = findViewById(R.id.send);
        sqLiteHelper = new Dbhl(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT ItemName1,ItemId1,Price1 FROM " + Dbhl.TABLE_NAME3 + "", null);
        ID_Array.clear();
        NAME_Array.clear();
        PHONE_NUMBER_Array.clear();
        if (cursor.moveToFirst()) {
            do {
                ID_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_2)));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_1)));
                PHONE_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_4)));
            } while (cursor.moveToNext());
        }
        Cursor cur = sqLiteDatabase.rawQuery("SELECT SUM(" + Dbhl.BCOL_4 + ") as Total FROM " + Dbhl.TABLE_NAME3, null);

        if (cur.moveToFirst()) {

            total = cur.getInt(cur.getColumnIndex("Total"));
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String num = e1.getText().toString();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + num));
                    intent.putExtra("sms_body", bill());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                    sqLiteHelper.delete();
                }
                catch (Exception e){
                    Toast.makeText(sendbill.this, "SMS Failed to Send, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public String bill() {
        String b = "Thank you for purchasing!\nTotal amount to be paid is :\t " + total + "\n" + NAME_Array.toString().replace("[", "").replace(",", "\n").replace("]", "") + "\n" + "\nDo Visit Again :)";
        return b;
    }

}