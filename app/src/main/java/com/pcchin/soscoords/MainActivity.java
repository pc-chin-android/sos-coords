package com.pcchin.soscoords;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.pcchin.soscoords.Checkbox.CheckboxDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        // ****** UPDATE CHECKBOXES ****** //

        // Import table from existing one
        CheckboxDatabase chk = Room.databaseBuilder(getApplicationContext(), CheckboxDatabase.class, "checkbox-status").build();
        // Default table if nonexistent
        if(Objects.equals(chk.checkboxDao().checkDatabaseLen(), 0)) {
            chk.checkboxDao().addCheckboxState(true);
            chk.checkboxDao().addCheckboxState(true);
        }
        // Update checkbox states
        CheckBox sendCheck = findViewById(R.id.mainMenuTopCheck);
        sendCheck.setChecked(chk.checkboxDao().getCheckboxState(0));
        CheckBox receiveCheck = findViewById(R.id.mainMenuBottomCheck);
        receiveCheck.setChecked(chk.checkboxDao().getCheckboxState(1));


        // ****** UPDATE CODE ****** //
        // TODO: Update secret code

        // ****** UPDATE CONTACT LIST ****** //
        // TODO: Update contact list
    }
}
