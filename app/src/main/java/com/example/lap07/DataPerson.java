package com.example.lap07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataPerson extends SQLiteOpenHelper {
    public DataPerson(@Nullable Context context
            , @Nullable String name
            , @Nullable SQLiteDatabase.CursorFactory factory
            , int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Person ("+" id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                                                " name TEXT NOT NULL)";
        db.execSQL(sql);
    }
    public void addPerson(Person person){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", person.getName());

        db.insert("person", null, values);
    }
    public List<Person> getAll(){
        List<Person> personList = new ArrayList<>();
        String sql = "select * from Person";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do {
                Person person = new Person();
                person.setId(cursor.getInt(0));
                person.setName(cursor.getString(1));

                personList.add(person);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  personList;
    }
    public int removePerson(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Person", "ID =?", new String[]{String.valueOf(id)});

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
