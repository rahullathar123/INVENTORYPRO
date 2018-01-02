package com.example.rahul.inventoryproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by rahul on 2017-12-27.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final String TABLE_NAME = "Inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INVENTORY_NAME = "name";
    public static final String COLUMN_INVENTORY_PRICE = "price";
    public static final String COLUMN_INVENTORY_QUANTITY = "quantity";
    public static final String COLUMN_INVENTORY_IMAGE = "image";
    public static final String COLUMN_INVENTORY_PHONE = "phoneInfo";
    public static final String COLUMN_INVENTORY_SOLD = "soldItems";


    //Content URI


    public static final String CONTENT_AUTHORITY = "com.example.rahul.inventoryproject";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME);



    private static final String TAG = DBHelper.class.getSimpleName();
    private static final int DATABASE_VERSION = 6;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+ TABLE_NAME + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_INVENTORY_NAME + " TEXT NOT NULL, "+
                COLUMN_INVENTORY_PRICE +" INTEGER NOT NULL, "+
                COLUMN_INVENTORY_QUANTITY+" INTEGER NOT NULL, "+
                COLUMN_INVENTORY_IMAGE + " BLOB NOT NULL, " +
                COLUMN_INVENTORY_PHONE + " INTEGER NOT NULL, " +
                COLUMN_INVENTORY_SOLD + " DEFAULT 0);" // add new column
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        this.onCreate(db);
    }


    public void addNewInventory(Inventory inventory){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_INVENTORY_NAME,inventory.getProductName());
        values.put(COLUMN_INVENTORY_PRICE,inventory.getPrice());
        values.put(COLUMN_INVENTORY_QUANTITY,inventory.getQuantity());
        values.put(COLUMN_INVENTORY_IMAGE,inventory.getImage());
        values.put(COLUMN_INVENTORY_PHONE, inventory.getPhoneNumber());
        values.put(COLUMN_INVENTORY_SOLD, inventory.getSoldItems());

        db.insert(TABLE_NAME,null,values);
        db.close();
        Log.i(TAG, "name:" + COLUMN_INVENTORY_IMAGE + ",price:" + COLUMN_INVENTORY_PRICE + ",quantity: " + COLUMN_INVENTORY_QUANTITY + ",image: " + COLUMN_INVENTORY_IMAGE + ",phoneNumber:"
                + COLUMN_INVENTORY_PHONE + ",sold: " + COLUMN_INVENTORY_SOLD);

    }

    public ArrayList<Inventory> inventoryArrayList(String filter){

        String query;
        if(filter.equals("")){
            query = "SELECT * FROM " + TABLE_NAME;

        }else {

            query = "SELECT * FROM "+ TABLE_NAME +" ORDER BY "+ filter;
        }

        ArrayList<Inventory>  inventoryArrayList = new ArrayList<>();
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Inventory  inventory;

        if(cursor.moveToFirst()){


            do{

                inventory = new Inventory();

                inventory.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                inventory.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_NAME)));
                inventory.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_PRICE)));
                inventory.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_QUANTITY)));
                inventory.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_IMAGE)));
                // inventory.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_PHONE)));
                inventoryArrayList.add(inventory);
            }while (cursor.moveToNext());
        }

        return inventoryArrayList;
    }
    /**Query only 1 record**/
    public Inventory getInventory(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Inventory receivedInventory = new Inventory();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            receivedInventory.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_NAME)));
            receivedInventory.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_PRICE)));
            receivedInventory.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_QUANTITY)));
            receivedInventory.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_IMAGE)));
            receivedInventory.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_PHONE)));
            receivedInventory.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_INVENTORY_SOLD)));
        }



        return receivedInventory;


    }

    public void deleteInventory(long id, Context context){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE _id='" + id + "'");
        Toast.makeText(context, "Deleted Successfully.", Toast.LENGTH_SHORT).show();

    }


    public void updateRecord(long id, Context context, Inventory updateRecord) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET name ='" + updateRecord.getProductName() +
                "', price ='" + updateRecord.getPrice() +
                "', quantity ='" + updateRecord.getQuantity() +
                "', image ='" + updateRecord.getImage() +
                "', phoneInfo ='" + updateRecord.getPhoneNumber() +
                "', soldItems ='" + updateRecord.getSoldItems() +
                "'  WHERE _id='" + id + "'");

            Toast.makeText(context,"Updated successfully.", Toast.LENGTH_SHORT).show();
    }


}
