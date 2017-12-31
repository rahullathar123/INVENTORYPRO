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


public class AddInventoryRecord extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mImageEditText;
    private EditText mPhoneEditText;
    private Button mAddBtn;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_inventory_record);

        //init
        mNameEditText = (EditText)findViewById(R.id.addProductName);
        mPriceEditText = (EditText)findViewById(R.id.addProductPrice);
        mQuantityEditText = (EditText)findViewById(R.id.addProductQuantity);
        mImageEditText = (EditText)findViewById(R.id.userProfileImageLink);
        mPhoneEditText = (EditText) findViewById(R.id.addPhone);
        mAddBtn = (Button)findViewById(R.id.addNewUserButton);

        //listen to add button click
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call the save method
                saveInventory();
            }
        });

    }

    private void saveInventory(){
        String name = mNameEditText.getText().toString().trim();
        String price = mPriceEditText.getText().toString().trim();
        String quantity = mQuantityEditText.getText().toString().trim();
        String image = mImageEditText.getText().toString().trim();
        String phone = mPhoneEditText.getText().toString().trim();
        dbHelper = new DBHelper(this);

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
            Toast.makeText(this, "You must enter an phone", Toast.LENGTH_SHORT).show();
        }

        //create new person
        Inventory inventory = new Inventory(name, price, quantity, image, phone);
        dbHelper.addNewInventory(inventory);

        //finally redirect back home
        // NOTE you can implement an sqlite callback then redirect on success delete
        goBackHome();

    }

    private void goBackHome(){
        startActivity(new Intent(AddInventoryRecord.this, MainActivity.class));
    }
}

