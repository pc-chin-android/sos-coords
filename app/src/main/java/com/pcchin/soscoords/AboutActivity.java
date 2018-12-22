package com.pcchin.soscoords;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        // Binding button to return to main menu
        Button aboutButton = findViewById(R.id.aboutMenuButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchLayout = new Intent(AboutActivity.this, MainActivity.class);
                startActivity(switchLayout);
            }
        } );
    }
}
