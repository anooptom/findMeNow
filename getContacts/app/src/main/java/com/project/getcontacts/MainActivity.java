package com.project.getcontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CONTACTS_PERMISSION = 1;
    private EditText editTextName;
    private Button buttonSearch;
    private TextView textViewPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContactPhoneNumber();
            }
        });
    }

    private void searchContactPhoneNumber(){
        String name = editTextName.getText().toString().trim();

        if (checkContactsPermission()){
                String phoneno = getContactPhoneNumber(name);

            if (phoneno != null) {
                textViewPhoneNumber.setText(phoneno);
            } else {
                textViewPhoneNumber.setText("Phone number not found");
            }
        }
        else {
            requestContactsPermission();
        }
    }

    private boolean checkContactsPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestContactsPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_CONTACTS_PERMISSION);
    }

    private String getContactPhoneNumber(String name){
        ContentResolver contentResolver = getContentResolver();
        String phoneno = null;

        Cursor cursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+ "= ?",
                new String[]{name},
                null
        );

        if (cursor != null && cursor.moveToFirst()){
            int phoneNumberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            if (phoneNumberIndex != -1) {
                phoneno = cursor.getString(phoneNumberIndex);
            }
            cursor.close();
        }

        return phoneno;

    }


}