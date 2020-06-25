package com.example.storemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DisplaySQLiteDataActivity extends AppCompatActivity {
    Dbhl sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView LISTVIEW;
    String s1,s2,s3,s4;
    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;
    ArrayList<String> PHONE_NUMBER_Array;
    ArrayList<String> QUANTITY_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sqlite_data);
        LISTVIEW = (ListView) findViewById(R.id.listView1);
        ID_Array = new ArrayList<String>();
        NAME_Array = new ArrayList<String>();
        PHONE_NUMBER_Array = new ArrayList<String>();
        QUANTITY_Array = new ArrayList<String>();
        sqLiteHelper = new Dbhl(this);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s1 = ID_Array.get(position).toString();
                s2 = NAME_Array.get(position).toString();
                s3 = PHONE_NUMBER_Array.get(position).toString();
                s4 = QUANTITY_Array.get(position).toString();
                    Intent intent = new Intent(DisplaySQLiteDataActivity.this, ShowSingleRecordActivity.class);
                    intent.putExtra("ID", s1);
                    intent.putExtra("Name", s2);
                    intent.putExtra("Phone", s3);
                    intent.putExtra("Quant", s4);
                    startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        ShowSQLiteDBdata() ;
        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+Dbhl.TABLE_NAME1+"", null);

        ID_Array.clear();
        NAME_Array.clear();
        PHONE_NUMBER_Array.clear();
        QUANTITY_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.COL_2)));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.COL_1)));
                PHONE_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.COL_4)));
                QUANTITY_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.COL_3)));

            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter(DisplaySQLiteDataActivity.this,ID_Array,NAME_Array,PHONE_NUMBER_Array,QUANTITY_Array);
        LISTVIEW.setAdapter(listAdapter);
        cursor.close();
    }
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(DisplaySQLiteDataActivity.this, MainActivity.class));
        finish();
    }
}