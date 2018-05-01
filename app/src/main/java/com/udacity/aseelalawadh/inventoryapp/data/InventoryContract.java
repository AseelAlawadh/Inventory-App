package com.udacity.aseelalawadh.inventoryapp.data;

import android.provider.BaseColumns;


/**
 * Created by aseelalawadh on 29/04/2018.
 */

public final class InventoryContract {
    private InventoryContract() {}

    public static final class InventoryEntry implements BaseColumns {
        //table name
        public final static String TABLE_NAME = "inventory" ;
        // Unique ID number for the pet (only for use in the database table).
        public final static String _ID = BaseColumns._ID ;
        // Product Name, Price, Quantity, Supplier Name, and Supplier Phone Number.
        public final static String COLUMN_INVENTORY_NAME = "name" ;
        public final static String COLUMN_INVENTORY_PRICE = "price";
       /* public final static String COLUMN_INVENTORY_QUANTITY = "quantity";
        public final static String COLUMN_INVENTORY_SUPPLIER_NAME = "supplier name";
        public final static String COLUMN_INVENTORY_SUPPLIER_PHONE = "supplier phone";
*/

    }




}
