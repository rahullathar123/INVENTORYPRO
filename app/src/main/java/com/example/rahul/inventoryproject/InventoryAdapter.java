package com.example.rahul.inventoryproject;


import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    public static final String LOG_TAG = InventoryAdapter.class.getSimpleName();


    private ArrayList<Inventory> mInventoryList;
    private Context mContext;
    private RecyclerView mRecyclerV;
    private Cursor cursor;
    private DBHelper mDbHelper;



    // Provide a suitable constructor (depends on the kind of dataset)
    public InventoryAdapter(ArrayList<Inventory> myDataset, Context context, RecyclerView recyclerView, DBHelper dbHelper) {
        mInventoryList = myDataset;
        mContext = context;
        mRecyclerV = recyclerView;
        mDbHelper = dbHelper;
    }

    public void add(int position, Inventory inventory) {
        mInventoryList.add(position, inventory);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mInventoryList.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.display_screen, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final Inventory inventory = mInventoryList.get(position);
        mDbHelper.updateRecord(Inventory.getId(), mContext, inventory);
        holder.productNameTxtV.setText("Product Name: " + inventory.getProductName());
        holder.productPriceTxtV.setText("price: " + inventory.getPrice());
        holder.productQuantityTxtV.setText("Quantity: " + inventory.getQuantity());

        // set on click listener on sale image button

        holder.saleImageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action on click
                //get the Uri for the current item
                long currentId = Inventory.getId();

                Uri mCurrentUri = ContentUris.withAppendedId(InventoryContract.InventoryEntry.CONTENT_URI, currentId);
                //Log.w(LOG_TAG, "column_id:" + InventoryContract.InventoryEntry.COLUMN_ID);
                // Find the columns of inventory attributes that we're interested in
                int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY);

                //read the inventory attributes from the Cursor for the current item
                int quantity = cursor.getInt(quantityColumnIndex);


                // Defines an object to contain the updated values
                ContentValues updateValues = new ContentValues();
                updateValues.put(InventoryContract.InventoryEntry.COLUMN_INVENTORY_QUANTITY, quantity--);

                //update the item with the content URI mCurrentUri and pass in the new
                //content values. Pass in null for the selection and selection args
                //because mCurrentUri will already identify the correct row in the database that
                // we want to modify.
                int rowsUpdate = mContext.getContentResolver().update(mCurrentUri, updateValues, null, null);

                Intent goToUpdate = new Intent(mContext, MainActivity.class);
                goToUpdate.putExtra("Inventory_ID", rowsUpdate);
                mContext.startActivity(goToUpdate);



            }
        });
        Picasso.with(mContext).load(inventory.getImage()).placeholder(R.mipmap.ic_launcher).into(holder.productImageImgV);

        //listen to single view layout click
        holder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Update or delete inventory?");
                builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //go to update activity
                        goToUpdateActivity(Inventory.getId());

                    }
                });
                builder.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbHelper = new DBHelper(mContext);
                        dbHelper.deleteInventory(Inventory.getId(), mContext);

                        mInventoryList.remove(position);
                        mRecyclerV.removeViewAt(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mInventoryList.size());
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });


    }


    private void goToUpdateActivity(long inventoryId){
        Intent goToUpdate = new Intent(mContext, UpdateInventory.class);
        goToUpdate.putExtra("Inventory_ID", inventoryId);
        mContext.startActivity(goToUpdate);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mInventoryList.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView productNameTxtV;
        public TextView productPriceTxtV;
        public TextView productQuantityTxtV;
        public ImageView productImageImgV;
        public ImageButton saleImageV;
        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            productNameTxtV = v.findViewById(R.id.ProductName);
            productPriceTxtV = v.findViewById(R.id.ProductPrice);
            productQuantityTxtV = v.findViewById(R.id.ProductQuantity);
            productImageImgV = v.findViewById(R.id.Productimage);
            saleImageV = v.findViewById(R.id.saleButton);
        }
    }



}