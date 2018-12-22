package com.pcchin.soscoords;

import android.Manifest;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.pcchin.soscoords.contactlist.ContactListDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    String[] permissionList = new String[] {Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION};

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
    }

    // Show exit popup
    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitAlertBuilder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);
        exitAlertBuilder.setTitle(R.string.exit_popup_title);
        exitAlertBuilder.setMessage(getString(R.string.exit_popup_message));
        // Bind quit button
        exitAlertBuilder.setPositiveButton(getString(R.string.exit_popup_right_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface exitPopup, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                });
        exitAlertBuilder.setNegativeButton(getString(R.string.exit_popup_left_button),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface exitPopup, int which) {
                        exitPopup.dismiss();
                    }
                });
        AlertDialog exitAlert = exitAlertBuilder.create();
        exitAlert.show();
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

    @Override
    public void onResume(){
        super.onResume();

        // Receive permission to read SMS
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> checkPermissionList = new ArrayList<>();
            for (String permission : permissionList) {
                if (this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    checkPermissionList.add(permission);
                }
            }
            String[] checkPermissionListArray = new String[checkPermissionList.size()];
            if (checkPermissionListArray.length != 0) {
                requestPermissions(checkPermissionList.toArray(checkPermissionListArray), 100);
            }
        }

        // ****** UPDATE CODE ****** //
        SharedPreferences appKeys = this.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
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

        // ****** UPDATE CONTACT LIST (WHOLE THING IS INSIDE A THREAD + TRY LOOP) ****** //
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Initialize id of contacts needed to be shown and reference array for id to name
                    ContactListDatabase contactListDatabase = Room.databaseBuilder(getApplicationContext(), ContactListDatabase.class, "current_contact_list").build();
                    List<String> contactsDisplay = contactListDatabase.daoAccess().getAllContactsId();
                    List<List<String>> contactsReference = GeneralFunctions.getContactNames(getApplicationContext());
                    final StringBuilder displayText = new StringBuilder(getString(R.string.current_placeholder));
                    final TextView mainMenuBottomText = findViewById(R.id.mainMenuBottomButtonContent);
                    // Check name array with id to find name
                    for (int i = 0; i < contactsDisplay.size(); i++) {
                        for (int j = 0; j < contactsReference.size(); j++) {
                            // Check if id is the same
                            if (Objects.equals(contactsReference.get(j).get(0), contactsDisplay.get(i))) {
                                displayText.append(" ").append(contactsReference.get(j).get(1)).append(",");
                                break;
                            }
                        }
                    }
                    if (contactsDisplay.size() != 0) {
                        // Removes last comma
                        displayText.deleteCharAt(displayText.length() - 1);
                    }
                    // Go back to main thread to display text
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainMenuBottomText.setText(displayText);
                        }
                    });
                    contactListDatabase.close();
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView mainMenuBottomText = findViewById(R.id.mainMenuBottomButtonContent);
                            mainMenuBottomText.setText(R.string.current_placeholder);
                        }
                    });
                }
            }

        }).start();

        Intent intent = new Intent(this, SmsMonitorService.class);
        intent.setAction("START");
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "This permission needs to be granted for the app to function properly. Please try again.", Toast.LENGTH_LONG).show();
        }
    }
}