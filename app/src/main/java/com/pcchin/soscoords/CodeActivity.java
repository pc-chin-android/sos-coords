package com.pcchin.soscoords;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codeinput);

        // Initialize code input
        SharedPreferences appKeys = this.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        EditText codeInput = findViewById(R.id.codeMenuEditText);
        String defaultInput = appKeys.getString(getString(R.string.secret_code_input), null);
        if (! Objects.equals(defaultInput, null)) {
            codeInput.setText(defaultInput);
        } else {
            codeInput.setText("");
        }

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
                // Save value to database
                TextView inputVal = findViewById(R.id.codeMenuEditText);
                updateCode(inputVal.getText());
                // Return to main menu
                Intent switchLayout = new Intent(CodeActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );
    }

    private void updateCode(CharSequence val) {
        SharedPreferences appKeys = this.getSharedPreferences(getString(R.string.shared_pref_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor appKeysEditor = appKeys.edit();
        appKeysEditor.putString(getString(R.string.secret_code_input), String.valueOf(val));
        appKeysEditor.apply();
    }
}
