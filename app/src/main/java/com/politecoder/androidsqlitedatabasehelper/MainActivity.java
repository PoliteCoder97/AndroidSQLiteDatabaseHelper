package com.politecoder.androidsqlitedatabasehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AssetsDatabaseHelper(this,"productions.sqlite").checkDb();
    }
}
