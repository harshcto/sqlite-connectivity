package com.example.codehead.sqlite_example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME="students.db";
    private static final String TABLE_NAME="student_table";
    private static final String COL_1="Id";
    private static final String COL_2="Name";
    private static final String COL_3="Surname";
    private static final String COL_4="Marks";


    //creating database using constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    //creating table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY ,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME); //delete existing table on upgrade
        onCreate(db); //create new table
    }

    //inserting data
    public boolean insertData(String s_name,String s_surname,String s_marks,String s_id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,s_id);
        contentValues.put(COL_2,s_name); //putting data into colunms
        contentValues.put(COL_3,s_surname);
        contentValues.put(COL_4,s_marks);
       long result= db.insert(TABLE_NAME,null,contentValues);
       if (result==-1)
           return false;
           else return true;
    }

    //getting data
    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String s_name,String s_surname,String s_marks,String s_id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,s_name); //putting data into colunms
        contentValues.put(COL_3,s_surname);
        contentValues.put(COL_4,s_marks);
        contentValues.put(COL_1,s_id);
        db.update(TABLE_NAME,contentValues,"id=?",new String[]{s_id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }
}
