package com.example.storemanagementsystem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Dealers extends AppCompatActivity {

    Dbhl sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter3 listAdapter1;
    ListView LISTVIEW;
    String contact;
    Button b1;
    ArrayList<String> ID_Array;
    String c;
    ArrayList<String> NAME_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealers);

        LISTVIEW = (ListView) findViewById(R.id.listView2);
        b1 = findViewById(R.id.adds);
        ID_Array = new ArrayList<String>();

        NAME_Array = new ArrayList<String>();

        sqLiteHelper = new Dbhl(this);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               c = ID_Array.get(position).toString();
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +c));
                startActivity(i);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dealers.this,AddSupplier.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata();

        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + Dbhl.TABLE_NAME4 + "", null);

        ID_Array.clear();
        NAME_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.DCOL_2)));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(Dbhl.DCOL_1)));
            } while (cursor.moveToNext());
        }


    listAdapter1 =new

    ListAdapter3(Dealers .this, ID_Array, NAME_Array);

        LISTVIEW.setAdapter(listAdapter1);
        cursor.close();
}

}
