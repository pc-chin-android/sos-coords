package com.pcchin.soscoords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeinput);
        // TODO: Initialize code input

        // Bind cancel button to return to main menu
        Button cancelButton = findViewById(R.id.codeMenuLeftButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(CodeActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );

        // Bind save & return button
        Button saveButton = findViewById(R.id.codeMenuRightButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Insert code to save current input
                Intent switchLayout = new Intent(CodeActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );
    }
}
