package com.pcchin.soscoords;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.pcchin.soscoords.contactlist.ContactListDatabase;

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
                            currentContact.setTextSize(2,20);
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
                // TODO: Insert code to save current input
                Intent switchLayout = new Intent(ContactListActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        });
    }
}

