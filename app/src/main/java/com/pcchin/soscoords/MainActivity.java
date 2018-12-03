package com.pcchin.soscoords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

// Main Format: String names contain underscores, ids use camelCase, functions use

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // TODO: Complete function to store info
    public void storeInfo(String name, String value) { }

    // TODO: Complete function to read info
    public <T> ArrayList<T> readInfo(String name) {
        ArrayList<T> response = new ArrayList<>();
        return response;
    }
}
