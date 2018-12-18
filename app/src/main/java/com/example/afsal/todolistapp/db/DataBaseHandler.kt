package com.example.afsal.todolistapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.afsal.todolistapp.model.GroceryObject
import java.lang.Exception

class DataBaseHandler(context: Context): SQLiteOpenHelper(context, DataBaseHandler.DB_NAME,
    null, DataBaseHandler.DB_VERSION){

    companion object {
        var DB_NAME = "ToDoApp"
        var DB_VERSION = 1
        var TABLE_NAME = "grocery"
        var ID = "id"
        var NAME = "name"
        var COUNT = "item_count"


    }

    override fun onCreate(db: SQLiteDatabase) {
        val create_table = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COUNT + " INTEGER DEFAULT 0," +
                NAME + " TEXT"+");"

        db.execSQL(create_table)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val drop_table = "DROP TABLE IF EXIST " + TABLE_NAME
        db.execSQL(drop_table)
        onCreate(db)
    }

    fun addItem(groceryObj:GroceryObject):Boolean{
        val db = this.writableDatabase
        try{

            val values = ContentValues()
            values.put(COUNT, groceryObj.count)
            values.put(NAME, groceryObj.name)
            val _success = db.insert(TABLE_NAME, null, values)
            db.close()
            return (Integer.parseInt("$_success") != -1)
        }
        catch(e: Exception){
            println(e.message)
        }
        db.close()
        return false

    }

    fun itemList(): ArrayList<GroceryObject>{
        var groceryList = ArrayList<GroceryObject>()
        var db = this.writableDatabase
        val query = "SELECT * from $TABLE_NAME order by $ID desc"
        var cursor = db.rawQuery(query, null)
        if (cursor != null){
            cursor.moveToFirst()
            if(cursor.count <= 0){
                cursor.close()
                return groceryList
            }
            do {
                var item = GroceryObject()
                item.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                item.name = cursor.getString(cursor.getColumnIndex(NAME))
                item.count = cursor.getInt(cursor.getColumnIndex(COUNT))
                groceryList.add(item)

            }while(cursor.moveToNext())
        }
        cursor.close()
        return groceryList
    }

    fun deleteItem(primaryKey: Int){
        val db = this.writableDatabase
        var deleteQueryString = "DELETE from $TABLE_NAME where $ID=$primaryKey"
        db.execSQL(deleteQueryString)
        db.close()
    }
}