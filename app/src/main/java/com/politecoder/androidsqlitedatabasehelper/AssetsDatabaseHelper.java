package com.politecoder.androidsqlitedatabasehelper;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AssetsDatabaseHelper {

  private Context context;
  private String dbName = "MaktabAmal";
  public AssetsDatabaseHelper(Context context){
    this(context, "MaktabAmal");
  }

  public AssetsDatabaseHelper(Context context, String dbName){
    this.context = context;
    this.dbName = dbName;
  }

  public boolean checkDb(){
    File dbfile = context.getDatabasePath(dbName);
    if(! dbfile.exists()){
      try {
        copyDatabase(dbfile);
        Log.i("AssetsDatabaseHelper", "database copied.");
        return true;
      } catch (IOException e) {
        throw new RuntimeException("Error creating source database." , e);
      }
    }else {
      Log.i("AssetsDatabaseHelper", "database is exists.");
      return false;
    }
  }

  public void copyDatabase(File dbfile) throws IOException{
    InputStream is = context.getAssets().open("databases/"+dbName);
    dbfile.getParentFile().mkdirs();
    OutputStream os = new FileOutputStream(dbfile);

    int len = 0;
    byte[] buffer = new byte[1024];

    while((len = is.read(buffer)) > 0){
      os.write(buffer, 0, len);
    }

    os.flush();
    os.close();
    is.close();
  }


  private void copyAttachedDatabase(Context context, String databaseName) {
    final File dbPath = context.getDatabasePath(databaseName);

    // If the database already exists, return
    if (dbPath.exists()) {
      return;
    }

    // Make sure we have a path to the file
    dbPath.getParentFile().mkdirs();

    // Try to copy database file
    try {
      final InputStream inputStream = context.getAssets().open("databases/" + databaseName);
      final OutputStream output = new FileOutputStream(dbPath);

      byte[] buffer = new byte[8192];
      int length;

      while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
        output.write(buffer, 0, length);
      }

      output.flush();
      output.close();
      inputStream.close();
    }
    catch (IOException e) {
      Log.d("DATABASE", "Failed to open file", e);
      e.printStackTrace();
    }
  }

}

