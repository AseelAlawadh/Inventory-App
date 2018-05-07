package com.udacity.aseelalawadh.inventoryapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.udacity.aseelalawadh.inventoryapp.data.InventoryContract.InventoryEntry;
import com.udacity.aseelalawadh.inventoryapp.data.InventoryDBHelper;

/**
 * Created by aseelalawadh on 29/04/2018.
 */

public class EditorActivity extends AppCompatActivity {
    SQLiteDatabase db;
    private EditText mNameEditText;
    private EditText mPrice;
    private EditText mQuantity;
    private EditText mSupplierName;
    private EditText mSupplierPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mNameEditText = findViewById(R.id.edit_product_name);
        mPrice = findViewById(R.id.edit_product_price);
        mQuantity = findViewById(R.id.edit_product_quantity);
        mSupplierName = findViewById(R.id.edit_product_supplier_name);
        mSupplierPhone = findViewById(R.id.edit_supplier_phone);
    }

    /**
     * Get user input from editor and save new product into database.
     */
    private void insertProduct() {
        // Read from input fields
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPrice.getText().toString().trim();
        String quantityString = mQuantity.getText().toString().trim();
        String supplierNameString = mSupplierName.getText().toString().trim();
        String supplierPhoneString = mSupplierPhone.getText().toString().trim();

        int price = Integer.parseInt(priceString);
        int quantity = Integer.parseInt(quantityString);

        InventoryDBHelper mDbHelper = new InventoryDBHelper(this);

        db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(InventoryEntry.COLUMN_INVENTORY_NAME, nameString);
        values.put(InventoryEntry.COLUMN_INVENTORY_PRICE, price);
        values.put(InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantity);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_NAME, supplierNameString);
        values.put(InventoryEntry.COLUMN_INVENTORY_SUPPLIER_PHONE, supplierPhoneString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(InventoryEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving product", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Product saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_save:
                insertProduct();
                finish();
                return true;

            case R.id.action_delete:
                return true;

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
