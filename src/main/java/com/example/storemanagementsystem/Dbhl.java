package com.example.storemanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Dbhl extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Store.db";
    public static final String TABLE_NAME1 = "Stock";
    public static final String TABLE_NAME2 ="Register";
    public static final String TABLE_NAME3 ="Bill";
    public static final String TABLE_NAME4 ="Dealers";
    public static final String COL_1 = "ItemName";
    public static final String COL_2 = "ItemId";
    public static final String COL_3 = "ItemQuantity";
    public static final String COL_4 = "Price";
    public static final String LCOL_1 = "Username";
    public static final String LCOL_2 = "Password";
    public static final String LCOL_3 = "Name";
    public static final String BCOL_1 = "ItemName1";
    public static final String BCOL_2 = "ItemId1";
    public static final String BCOL_3 = "ItemQuantity1";
    public static final String BCOL_4 = "Price1";
    public static final String DCOL_1 = "DName";
    public static final String DCOL_2 = "DContact";


    public Dbhl(@Nullable Context context) {
        super(context,DATABASE_NAME,null,10);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ItemName TEXT,ItemId TEXT,ItemQuantity TEXT,Price TEXT)");
        Log.d("myTag", "T1 created");
        db.execSQL("create table " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,Username TEXT,Password TEXT,Name TEXT)");
        Log.d("myTag", "T2 created");
        db.execSQL("create table " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ItemName1 TEXT,ItemId1 TEXT,ItemQuantity1 TEXT,Price1 TEXT)");
        Log.d("myTag", "T3 created");
        db.execSQL("create table " + TABLE_NAME4 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DName TEXT,DContact TEXT)");
        Log.d("myTag", "T4 created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        onCreate(db);
    }

    public boolean insertDataR(String Username, String Password, String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LCOL_1, Username);
        contentValues.put(LCOL_2, Password);
        contentValues.put(LCOL_3, Name);
        long result1 = db.insert(TABLE_NAME2, null, contentValues);
        if (result1 == -1)
            return false;
        else
            return true;
    }

    public boolean Loginmethod(String username, String password) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2 + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public boolean updatedata(String username, String password ,String name,SQLiteDatabase db) throws SQLException
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LCOL_1,username);
        contentValues.put(LCOL_2,password);
        contentValues.put(LCOL_3,name);
        sqLiteDatabase.update(TABLE_NAME2, contentValues, null, null);
        Log.e("DATABASE OPERATIONS","Information updated");
        return true;
    }

    public boolean insertData(String ItemName, String ItemId, String ItemQuantity,String Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, ItemName);
        contentValues.put(COL_2, ItemId);
        contentValues.put(COL_3, ItemQuantity);
        contentValues.put(COL_4,Price);
        long result = db.insert(TABLE_NAME1, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertBData(String ItemName, String ItemId, String Price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BCOL_1, ItemName);
        contentValues.put(BCOL_2, ItemId);
        contentValues.put(BCOL_4, Price);
        long result = db.insert(TABLE_NAME3, null, contentValues);
        Log.e(" ","Inserted in Bill");
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean Billmethod(String arg1) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int flag = 0;
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE ItemId=?", new String[]{arg1});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                if (mCursor.moveToFirst())
                {
                    do {
                        if (mCursor.getInt(mCursor.getColumnIndex(COL_3))!=0) {
                            insertBData(mCursor.getString(mCursor.getColumnIndex(COL_1)), mCursor.getString(mCursor.getColumnIndex(COL_2)), mCursor.getString(mCursor.getColumnIndex(COL_4)));
                            String mc = mCursor.getString(mCursor.getColumnIndex(COL_2));
                            db.execSQL("UPDATE " + TABLE_NAME1 + " SET " + COL_3 + "=" + COL_3 + "-1" + " WHERE ItemId=?", new String[]{mc});
                            flag = 1;
                        }
                        else if (mCursor.getInt(mCursor.getColumnIndex(COL_3))==0)
                        {
                            String mc = mCursor.getString(mCursor.getColumnIndex(COL_2));
                            db.execSQL("UPDATE " + TABLE_NAME1 + " SET " + COL_3 + "=" + "0" + " WHERE ItemId=?", new String[]{mc});
                            flag = 0;
                        }
                    }while (mCursor.moveToNext());
                }
                mCursor.close();
                if (flag==1)
                {
                    return true;
                }
                else if (flag==0)
                {
                    return false;
                }
            }
        }
        return false;
    }

    public void delete()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME3);
    }

    public boolean insertDData(String DName, String DContact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DCOL_1, DName);
        contentValues.put(DCOL_2, DContact);
        long result = db.insert(TABLE_NAME4, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean updateRdata(String ItemName, String ItemId, String ItemQuantity,String Price,SQLiteDatabase db) throws SQLException
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ItemName);
        contentValues.put(COL_3,ItemQuantity);
        contentValues.put(COL_4,Price);
        sqLiteDatabase.update(TABLE_NAME1, contentValues, "ItemId = ?" , new String[] { ItemId });
        Log.e("DATABASE OPERATIONS","Information updated");
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1, "ItemId = ?",new String[] {id});
    }
}
