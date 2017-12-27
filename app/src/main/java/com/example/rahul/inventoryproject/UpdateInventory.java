package com.example.rahul.inventoryproject;

/**
 * Created by rahul on 2017-12-27.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateInventory extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mImageEditText;
    private Button mUpdateBtn;

    private DBHelper dbHelper;
    private long receivedInventoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_inventory_record);

        //init
        mNameEditText = (EditText)findViewById(R.id.duserNameUpdate);
        mPriceEditText = (EditText)findViewById(R.id.dpriceUpdate);
        mQuantityEditText = (EditText)findViewById(R.id.dquantityUpdate);
        mImageEditText = (EditText)findViewById(R.id.duserProfileImageLinkUpdate);
        mUpdateBtn = (Button)findViewById(R.id.dupdateInventoryButton);

        dbHelper = new DBHelper(this);

        try {
            //get intent to get person id
            receivedInventoryId = getIntent().getLongExtra("Inventory_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        Inventory queriedInventory = dbHelper.getInventory(receivedInventoryId);
        //set field to this user data
        mNameEditText.setText(queriedInventory.getProductName());
        mPriceEditText.setText(queriedInventory.getPrice());
        mQuantityEditText.setText(queriedInventory.getQuantity());
        mImageEditText.setText(queriedInventory.getImage());



        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateInventory();
            }
        });

    }

    private void updateInventory(){
        String name = mNameEditText.getText().toString().trim();
        String price = mPriceEditText.getText().toString().trim();
        String quantity =  mQuantityEditText.getText().toString().trim();
        String image = mQuantityEditText.getText().toString().trim();


        if(name.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter a name", Toast.LENGTH_SHORT).show();
        }

        if(price.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an price", Toast.LENGTH_SHORT).show();
        }

        if(quantity.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an quantity", Toast.LENGTH_SHORT).show();
        }

        if(image.isEmpty()){
            //error name is empty
            Toast.makeText(this, "You must enter an image link", Toast.LENGTH_SHORT).show();
        }

        //create updated person
        Inventory updatedInventory = new Inventory(name,price,quantity,image);

        //call dbhelper update
        dbHelper.updateRecord(receivedInventoryId, this, updatedInventory);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
