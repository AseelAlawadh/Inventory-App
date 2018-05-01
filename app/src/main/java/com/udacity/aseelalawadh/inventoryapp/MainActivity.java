package com.udacity.aseelalawadh.inventoryapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.udacity.aseelalawadh.inventoryapp.data.InventoryContract.InventoryEntry;
import com.udacity.aseelalawadh.inventoryapp.data.InventoryDBHelper;

public class MainActivity extends AppCompatActivity {

    private InventoryDBHelper mInventoryDBHelper ;
    private SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton) ;
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , EditorActivity.class) ;
                startActivity(intent);
            }
        });

        mInventoryDBHelper = new InventoryDBHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo() ;
    }

    @SuppressLint("SetTextI18n")
    private void displayDatabaseInfo(){

        //SQLiteDatabase
                db = mInventoryDBHelper.getReadableDatabase();
        String[] projection= {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_INVENTORY_NAME,
                InventoryEntry.COLUMN_INVENTORY_PRICE,
              /*  InventoryEntry.COLUMN_INVENTORY_QUANTITY,
                InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME,
                InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE*/
        };
        Cursor cursor = db.query(InventoryEntry.TABLE_NAME ,
                projection,
                null,
                null,
                null,
                null,
                null);
        TextView displayView = findViewById(R.id.text_view_product) ;
        try {
            displayView.setText("The inventory table contains " +cursor.getCount() + "product. \n\n");
            displayView.append(InventoryEntry._ID + "-" +
            InventoryEntry.COLUMN_INVENTORY_NAME +"-"+
            InventoryEntry.COLUMN_INVENTORY_PRICE +"-"+"\n"
            /*InventoryEntry.COLUMN_INVENTORY_QUANTITY+"-"+
            InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME +"-"+
            InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE + "\n" */
            );

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_NAME);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_PRICE);
            /*int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME);
            int suppliePhoneColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE);
*/
            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
             /*   int currentGender = cursor.getInt(quantityColumnIndex);
                int currentWeight = cursor.getInt(supplierColumnIndex);*/
/*

                String currentQuantity = cursor.getString(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentSupplierPhone = cursor.getString(suppliePhoneColumnIndex);
*/


                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice
                     /*   currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentSupplierPhone*/
                ));
            }
        } finally {
            cursor.close();
    }
    }

    private void insertProduct(){
       // SQLiteDatabase
                db = mInventoryDBHelper.getWritableDatabase() ;
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, "Choclate");
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, 13);
      /*  values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, "Terrier");
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME, "Terrier");
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE, "Terrier");
*/
        // Insert a new row for Toto in the database, returning the ID of that new row.
        // The first argument for db.insert() is the pets table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for Toto.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertProduct();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
