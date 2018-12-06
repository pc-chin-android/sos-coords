package com.pcchin.soscoords;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {

    // ****** MAIN FUNCTION ****** //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);
        // TODO: Initialize contact list input
        // TODO: Complete database to store saved contacts

        // Bind cancel button to return to main menu
        Button cancelButton = findViewById(R.id.contactListLeftButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );

        // Bind save & return button
        Button saveButton = findViewById(R.id.contactListRightButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Insert code to save current input
                Intent switchLayout = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );
    }


    // ****** GENERAL CONTACT GETTERS ****** //

    // Get all names of all contacts in list form
    public ArrayList<String> getContactNames() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<String> response = new ArrayList<>();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                response.add(name);
            }
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }

    // Get phone numbers of contacts in list form
    public ArrayList<ArrayList<String>> getContactNumbers() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<ArrayList<String>> response = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            temp.add(phoneNo);
                        }
                        pCur.close();
                    }
                }

                response.add(temp);
                temp.clear();
            }
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }
}