package com.udacity.aseelalawadh.inventoryapp.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.aseelalawadh.inventoryapp.data.InventoryContract.InventoryEntry ;

/**
 * Created by aseelalawadh on 29/04/2018.
 */

public class InventoryDBHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = InventoryDBHelper.class.getCanonicalName();
    //dataBase name file
    private static final String DATABASE_NAME = "shelter.db" ;
    //dataBase version
    private static final int DATABASE_VERSION = 1;

    public InventoryDBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create db table
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE "  + InventoryEntry.TABLE_NAME + "("
                +InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +InventoryEntry.COLUMN_INVENTORY_NAME + " TEXT NOT NULL, "
                +InventoryEntry.COLUMN_INVENTORY_PRICE + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
/*
        +InventoryEntry._ID + "integer primary key autoincrement, "
                +InventoryEntry.COLUMN_INVENTORY_NAME + "text not null, "
                +InventoryEntry.COLUMN_INVENTORY_PRICE + "text not null, "
                +InventoryEntry.COLUMN_INVENTORY_QUANTITY+"text not null, "
                +InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME + "text not null, "
                +InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE +"text not null); ";*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      /*  db.execSQL(" DROB TABLE IF EXIST" + InventoryEntry.TABLE_NAME);
        onCreate(db);
*/
    }
}
