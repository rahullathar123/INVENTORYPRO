package com.example.rahul.inventoryproject;

/**
 * Created by rahul on 2017-12-27.
 */

import android.content.Intent;
import android.net.Uri;
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
    private EditText mPhoneEditText;
    private Button mIncreaseQuantity;
    private Button mDecreaseQuantity;
    private Button mUpdateBtn;
    private Button mOrderSupplies;

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
        mPhoneEditText = (EditText) findViewById(R.id.dUpdatePhoneNumber);
        mIncreaseQuantity = (Button) findViewById(R.id.positiveButton);
        mDecreaseQuantity = (Button) findViewById(R.id.negativeButton);
        mUpdateBtn = (Button)findViewById(R.id.dupdateInventoryButton);
        mOrderSupplies = (Button) findViewById(R.id.dOrderSupplies);

        dbHelper = new DBHelper(this);

        try {
            //get intent to get person id
            receivedInventoryId = getIntent().getLongExtra("Inventory_ID", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /***populate user data before update***/
        final Inventory queriedInventory = dbHelper.getInventory(receivedInventoryId);
        //set field to this user data
        mNameEditText.setText(queriedInventory.getProductName());
        mPriceEditText.setText(queriedInventory.getPrice());
        mQuantityEditText.setText(queriedInventory.getQuantity());
        mImageEditText.setText(queriedInventory.getImage());
        mPhoneEditText.setText(queriedInventory.getPhoneNumber());


        //listen to add button click to update
        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                updateInventory();
            }
        });


        //trying to retrieve the phone number on the dialer app from sqlite
        mOrderSupplies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                dialPhoneNumber(queriedInventory.getPhoneNumber());
            }
        });


        mIncreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save person method
                sumOne();

            }
        });

        mDecreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subtractOne();
            }
        });

    }


    private void subtractOne() {
        String LastValueString = mQuantityEditText.getText().toString();
        int value;
        if (LastValueString.isEmpty()) {
            return;
        } else if (LastValueString.equals("0")) {
            return;
        } else {
            value = Integer.parseInt(LastValueString);
            mQuantityEditText.setText(String.valueOf(value - 1));
        }
    }

    private void sumOne() {
        String LastValue = mQuantityEditText.getText().toString();
        int value;
        if (LastValue.isEmpty()) {
            value = 0;
        } else {
            value = Integer.parseInt(LastValue);
        }
        mQuantityEditText.setText(String.valueOf(value + 1));
    }
    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    private void updateInventory(){
        String name = mNameEditText.getText().toString().trim();
        String price = mPriceEditText.getText().toString().trim();
        String quantity =  mQuantityEditText.getText().toString().trim();
        String image = mQuantityEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();


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
        if (phone.isEmpty()) {
            //error name is empty
            Toast.makeText(this, "You must enter an phone Number", Toast.LENGTH_SHORT).show();
        }

        //create updated person
        Inventory updatedInventory = new Inventory(name, price, quantity, image, phone);

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
