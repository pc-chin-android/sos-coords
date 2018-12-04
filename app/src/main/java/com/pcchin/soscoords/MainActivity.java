package com.pcchin.soscoords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: Initialize all stored values

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

        // TODO: Update checkbox states
    }
}
