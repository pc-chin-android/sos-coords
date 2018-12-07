package com.pcchin.soscoords;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ContactListActivity extends AppCompatActivity {

    // ****** MAIN FUNCTION ****** //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        // Initialize contact list input
        ArrayList<ArrayList<String>> allContacts = getContactNames();
        LinearLayout contactsDisplay= findViewById(R.id.contactlistbox);
        // Set each widget's height to wrap content and it's width to match parent
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < allContacts.size(); i++) {
            // Initialize checkboxes
            CheckBox currentContact = new CheckBox(this);
            currentContact.setTextSize(20);
            currentContact.setTextColor(-1); // White
            // Change checkbox text for each contact
            currentContact.setText(allContacts.get(i).get(1));
            // TODO: Check checkbox if stored
            contactsDisplay.addView(currentContact, params);
        }

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

    // Get all names of all contacts in list form (sorted)
    public ArrayList<ArrayList<String>> getContactNames() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        ArrayList<ArrayList<String>> response = new ArrayList<>();
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur.moveToNext()) {
                ArrayList<String> temp = new ArrayList<>();
                // Add <id, name> to array
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                temp.add(id);
                temp.add(name);
                response.add(temp);
            }
            // Sort array by name
            Collections.sort(response, new NameComparator ());
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }

    // Get phone numbers of contacts in dictionary
    public HashMap getContactNumbers() {
        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        HashMap <String, ArrayList<String>> response = new HashMap<>();
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

                response.put(id, temp);
                temp.clear();
            }
        }
        if(cur!=null){
            cur.close();
        }
        return response;
    }
}

