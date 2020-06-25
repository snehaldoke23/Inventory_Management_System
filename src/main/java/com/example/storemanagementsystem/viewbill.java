package com.example.storemanagementsystem;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class viewbill extends AppCompatActivity {

    Button b,b1;
    Dbhl sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor,cur;
    EditText e;
    ListAdapter1 listAdapter;
    ListView LISTVIEW;
    ArrayList<String> ID_Array;
    int total;
    ArrayList<String> NAME_Array;
    ArrayList<String> PHONE_NUMBER_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewbill);
        e = (EditText) findViewById(R.id.t);
        b1 = findViewById(R.id.ami);
        LISTVIEW = (ListView) findViewById(R.id.listView1);
        b = findViewById(R.id.bill);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showMessage("Data","Do you want a copy of bill ?");
                }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(viewbill.this,Bill.class);
                startActivity(intent);
            }
        });

        ID_Array = new ArrayList<String>();

        NAME_Array = new ArrayList<String>();

        PHONE_NUMBER_Array = new ArrayList<String>();

        sqLiteHelper = new Dbhl(this);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cur = sqLiteDatabase.rawQuery("SELECT SUM(" + Dbhl.BCOL_4 + ") as Total FROM " + Dbhl.TABLE_NAME3, null);

        if (cur.moveToFirst()) {

            total = cur.getInt(cur.getColumnIndex("Total"));
        }
        e.setText(Integer.toString(total));
        cur.close();

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(),ShowSingleRecordActivity.class);
                intent.putExtra("ListViewClickedItemValue", ListViewClickItemArray.get(position).toString());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        ShowSQLiteDBdata() ;
        super.onResume();
    }
    private void ShowSQLiteDBdata() {
        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.rawQuery("SELECT ItemName1,ItemId1,Price1 FROM "+Dbhl.TABLE_NAME3+"", null);
        ID_Array.clear();
        NAME_Array.clear();
        PHONE_NUMBER_Array.clear();
        if (cursor.moveToFirst()) {
            do {
                ID_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_2)));
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_2)));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_1)));
                PHONE_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.BCOL_4)));
            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter1(viewbill.this,ID_Array,NAME_Array,PHONE_NUMBER_Array);

        LISTVIEW.setAdapter(listAdapter);
        cursor.close();
    }

    public void showMessage(String title,String Message)
    {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                Intent i = new Intent(viewbill.this,sendbill.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton( "No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                sqLiteHelper.delete();
                Intent i = new Intent(viewbill.this,MainActivity.class);
                startActivity(i);
            }
        });
        builder.show();
        builder.create().show();
    }

    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(viewbill.this, MainActivity.class));
        finish();
    }
}