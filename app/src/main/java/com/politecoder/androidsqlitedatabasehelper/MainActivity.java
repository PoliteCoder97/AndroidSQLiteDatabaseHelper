package com.politecoder.androidsqlitedatabasehelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(new AssetsDatabaseHelper(this,"productions.sqlite").checkDbIsExistsInExternalStorage()){
            Toast.makeText(this, "Database is Exists!", Toast.LENGTH_SHORT).show();
            return;
        }else {
            new AssetsDatabaseHelper(this,"productions.sqlite").copyDatabaseToExternalStorage();
        }
    }
}
