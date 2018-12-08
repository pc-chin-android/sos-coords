package com.pcchin.soscoords;

import android.app.Dialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.pcchin.soscoords.contactlist.ContactListDatabase;
import com.pcchin.soscoords.contactlist.ContactListEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ContactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        // ****** INITIALIZE CONTACT INPUT (DATABASE INVOLVED) ****** //
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Get ids from database
                ContactListDatabase contactListDatabase = Room.databaseBuilder(getApplicationContext(), ContactListDatabase.class, "current_contact_list").build();
                final List<String> savedContactsId = contactListDatabase.daoAccess().getAllContactsId();
                // Return to main thread
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        // Initialize contact list input
                        List<List<String>> allContacts = GeneralFunctions.getContactNames(getApplicationContext());
                        LinearLayout contactsDisplay = findViewById(R.id.contactlistbox);
                        // Set each widget's height to wrap content and it's width to match parent
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        for (int i = 0; i < allContacts.size(); i++) {
                            // Initialize checkboxes
                            CheckBox currentContact = new CheckBox(getApplicationContext());
                            currentContact.setTextSize(2,22);
                            currentContact.setTextColor(-1); // White
                            // Change checkbox text for each contact
                            currentContact.setText(allContacts.get(i).get(1));
                            // Check checkbox if stored
                            for (int j = 0; j < savedContactsId.size(); j++) {
                                if (Objects.equals(allContacts.get(i).get(0), savedContactsId.get(j))) {
                                    currentContact.setChecked(true);
                                    break;
                                }
                            }
                            contactsDisplay.addView(currentContact, params);
                        }
                    }
                });
                contactListDatabase.close();
            }
        }).start();


        // ****** BUTTON BINDING ****** //

        // Bind cancel button to return to main menu
        Button cancelButton = findViewById(R.id.contactListLeftButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        });

        // Bind save & return button
        Button saveButton = findViewById(R.id.contactListRightButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store data to database
                new Thread(new Runnable() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        // Get database stuff
                        ContactListDatabase contactListDatabase = Room.databaseBuilder(getApplicationContext(), ContactListDatabase.class, "current_contact_list").build();
                        List<List<String>> allContactNames = GeneralFunctions.getContactNames(getApplicationContext());
                        HashMap allContactNumbers = GeneralFunctions.getContactNumbers(getApplicationContext());

                        List<String> SavedIds = new ArrayList<>();
                        LinearLayout checkboxParent = findViewById(R.id.contactlistbox);
                        // Check which checkboxes are checked
                        for (int i=0; i < checkboxParent.getChildCount(); i++) {
                            View currentChild = checkboxParent.getChildAt(i);
                            if (((CheckBox) currentChild).isChecked()) {
                                // Add id to list that needs to be stored
                                SavedIds.add(allContactNames.get(i).get(0));
                            }
                        }
                        // Clear database
                        contactListDatabase.daoAccess().nukeAllContacts();
                        for (int i=0; i < SavedIds.size(); i++) {
                            // Add new contacts to database with data from SavedIds
                            ContactListEntity currentContact = new ContactListEntity();
                            String currentKey = SavedIds.get(i);
                            currentContact.setContactId(currentKey);
                            List<String> currentContactNum = (List<String>)allContactNumbers.get(currentKey);
                            StringBuilder currentContactNumString = new StringBuilder();
                            // Prevent null pointer exception
                            if (currentContactNum != null) {
                                for (int j = 0; j < currentContactNum.size(); i++) {
                                    currentContactNumString.append(currentContactNum.get(j)).append(", ");
                                }
                                // Remove last comma
                                if (currentContactNum.size() != 0) {
                                    currentContactNumString.deleteCharAt(currentContactNumString.length() - 1);
                                    currentContactNumString.deleteCharAt(currentContactNumString.length() - 1);
                                }
                                // Update values to database
                                currentContact.setContactNumList(currentContactNumString.toString());
                                contactListDatabase.daoAccess().insertContact(currentContact);
                            }
                        }
                        contactListDatabase.close();
                        Intent switchLayout = new Intent(ContactListActivity.this, MainActivity.class);
                        startActivity(switchLayout);
                    }
                }).start();
                // Disable user input
                Button cancelButton = findViewById(R.id.contactListLeftButton);
                cancelButton.setEnabled(false);
                Button saveButton = findViewById(R.id.contactListRightButton);
                saveButton.setEnabled(false);
                // Show spinner
                Dialog loadingDialog = new Dialog(ContactListActivity.this);
                loadingDialog.setContentView(R.layout.popup_loading);
                Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loadingDialog.show();
            }
        });
    }
}

