package com.pcchin.soscoords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Import checkbox and code status
        SharedPreferences appKeys = this.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor appKeysEditor = appKeys.edit();


        // ****** BUTTON BINDING ****** //

        // Bind code input button to go to code activity
        Button codeButton = findViewById(R.id.mainMenuTopButton);
        codeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(MainActivity.this, CodeActivity.class);
                startActivity(switchLayout);
            }
        } );

        // Bind contact list button to go to code activity
        Button contactListButton = findViewById(R.id.mainMenuBottomButton);
        contactListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(MainActivity.this, ContactListActivity.class);
                startActivity(switchLayout);
            }
        } );

        // Bind about button to go to about activity
        Button aboutButton = findViewById(R.id.mainMenuAboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(switchLayout);
            }
        } );


        // ****** BIND CHECKBOXES ****** //

        // Check if value for top checkbox exists
        int sendCheckboxState = appKeys.getInt(getString(R.string.send_checkbox_checked), -1);
        if (appKeys.contains(getString(R.string.send_checkbox_checked))) {
            CheckBox check1 = findViewById(R.id.mainMenuTopCheck);
            check1.setChecked(sendCheckboxState != 0); // Convert int to bool
        } else {
            // Set default value
            appKeysEditor.putInt(getString(R.string.send_checkbox_checked), 1);
            appKeysEditor.apply();
            CheckBox check1 = findViewById(R.id.mainMenuTopCheck);
            check1.setChecked(true); // Convert int to bool
        }
        // Check if value for bottom checkbox exists
        int receiveCheckboxState = appKeys.getInt(getString(R.string.receive_checkbox_checked), -1);
        if (appKeys.contains(getString(R.string.receive_checkbox_checked))) {
            CheckBox check2 = findViewById(R.id.mainMenuBottomCheck);
            check2.setChecked(receiveCheckboxState != 0); // Convert int to bool
        } else {
            // Set default value
            appKeysEditor.putInt(getString(R.string.receive_checkbox_checked), 1);
            appKeysEditor.apply();
            CheckBox check2 = findViewById(R.id.mainMenuBottomCheck);
            check2.setChecked(true); // Convert int to bool
        }


        // ****** UPDATE CODE ****** //
        String currentCode = appKeys.getString(getString(R.string.secret_code_input), null);
        TextView codeText = findViewById(R.id.mainMenuTopButtonContent);
        // Check if default value exists
        if (! Objects.equals(currentCode, null)) {
            // Set values
            String contents = getString(R.string.main_menu_top_button_content, currentCode);
            codeText.setText(contents);
        } else {
            //Set default value
            codeText.setText(R.string.current_placeholder);
        }


        // ****** UPDATE CONTACT LIST ****** //
        // TODO: Update contact list
    }

    // Update the status of the checkboxes to their corresponding values
    public void updateCheckboxDatabase(View view) {
        // Import checkbox and code status
        SharedPreferences appKeys = this.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor appKeysEditor = appKeys.edit();
        switch (view.getId()) {
            case R.id.mainMenuTopCheck:
                CheckBox check1 = findViewById(R.id.mainMenuTopCheck);
                int checked1 = check1.isChecked() ? 1 : 0; // Convert int to bool
                appKeysEditor.putInt(getString(R.string.send_checkbox_checked), checked1);
                appKeysEditor.apply();
            case R.id.mainMenuBottomCheck:
                CheckBox check2 = findViewById(R.id.mainMenuBottomCheck);
                int checked2 = check2.isChecked() ? 1 : 0; // Convert int to bool
                appKeysEditor.putInt(getString(R.string.receive_checkbox_checked), checked2);
                appKeysEditor.apply();
        }

    }
}