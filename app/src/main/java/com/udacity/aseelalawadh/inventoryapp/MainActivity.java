package com.udacity.aseelalawadh.inventoryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udacity.aseelalawadh.inventoryapp.data.InventoryContract.InventoryEntry;
import com.udacity.aseelalawadh.inventoryapp.data.InventoryDBHelper;

import static com.udacity.aseelalawadh.inventoryapp.data.InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY;

public class MainActivity extends AppCompatActivity {

    private InventoryDBHelper mInventoryDBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mInventoryDBHelper = new InventoryDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @SuppressLint("SetTextI18n")
    private void displayDatabaseInfo() {

        //SQLiteDatabase
        db = mInventoryDBHelper.getReadableDatabase();
        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryEntry.COLUMN_INVENTORY_PRICE,
                COLUMN_INVENTORY_QUANTITY,
                InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME,
                InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE};
        Cursor cursor = db.query(
                InventoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        TextView displayView = findViewById(R.id.text_view_product);
        try {
            displayView.setText("The inventory table contains " + cursor.getCount() + "product. \n\n");
            displayView.append(InventoryEntry._ID + "-" +
                    InventoryEntry.COLUMN_INVENTORY_NAME + "-" +
                    InventoryEntry.COLUMN_INVENTORY_PRICE + "-" +
                    COLUMN_INVENTORY_QUANTITY + "-" +
                    InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME + "-" +
                    InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE + "\n");

            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierNme = cursor.getString(supplierColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierNme + " - " +
                        currentSupplierPhone));
            }
        } catch (Exception exp) {
            Log.v("", exp.getMessage());
        } finally {
            cursor.close();
        }
    }

    private void insertProduct() {
        // SQLiteDatabase
        db = mInventoryDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, "Chocolate");
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, 5);
        values.put(COLUMN_INVENTORY_QUANTITY, 3);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME, "Kit Kat");
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE, "0379656000");

        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void deleteProduct() {
        db.delete(InventoryEntry.TABLE_NAME, InventoryEntry._ID, null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;

            case R.id.action_delete_all_entries:
                deleteProduct();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
