package com.pcchin.soscoords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    // ****** MAIN FUNCTION ****** //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactlist);

        // Initialize contact list input
        List<List<String>> allContacts = GeneralFunctions.getContactNames(this);
        LinearLayout contactsDisplay = findViewById(R.id.contactlistbox);
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

