package com.example.rahul.inventoryproject;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by rahul on 2018-01-03.
 */

public class InventoryContract {


    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.rahul.inventoryproject.Inventory";
    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_Inventory = "Inventory";

    public InventoryContract() {
    }

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single object.
     */
    public static final class InventoryEntry implements BaseColumns {

        /**
         * The content URI to access the pet data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_Inventory);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of inventory.
         */
        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Inventory;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single object.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_Inventory;

        /**
         * Name of database table for pets
         */
        public static final String TABLE_NAME = "Inventory";

        /**
         * Unique ID number for the pet (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         * <p>
         * Type: TEXT
         */
        public static final String COLUMN_ID = "_id";


        public static final String COLUMN_INVENTORY_NAME = "name";


        public static final String COLUMN_INVENTORY_PRICE = "price";

        public static final String COLUMN_INVENTORY_QUANTITY = "quantity";


        public static final String COLUMN_INVENTORY_IMAGE = "image";


        public static final String COLUMN_INVENTORY_PHONE = "phoneInfo";


        public static final String COLUMN_INVENTORY_SOLD = "soldItems";

    }

}
