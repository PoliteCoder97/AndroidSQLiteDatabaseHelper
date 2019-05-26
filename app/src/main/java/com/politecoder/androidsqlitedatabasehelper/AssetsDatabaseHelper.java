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
  private String dbName = "DEFUALT_DATABASE.sqlite";

  public AssetsDatabaseHelper(Context context) {
    this(context, "DEFUALT_DATABASE.sqlite");
  }

  public AssetsDatabaseHelper(Context context, String dbName) {
    this.context = context;
    this.dbName = dbName;
  }

  //-------------------------------------- CHECK DATABASE IS EXISTS ---------------------------------------

  public boolean checkDbIsExistsInExternalStorage() {

    try {
      //database path in external storag/Android/context.getPakageName()
      String dbPath = context.getExternalFilesDir(null).getAbsolutePath() + "/" + context.getPackageName() + "/" + dbName;
      File dbfile = new File(dbPath);
      if (dbfile.exists()) {
        Log.i("AssetsDatabaseHelper", "database is exists.");
        return true;
      } else {
        Log.i("AssetsDatabaseHelper", "database isn't exists.");
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean checkDbIsExistsInInternalStorage() {
    try {
      File dbfile = context.getDatabasePath(dbName);
      if (dbfile.exists()) {
        Log.i("AssetsDatabaseHelper", "database is exists.");
        return true;
      } else {
        Log.i("AssetsDatabaseHelper", "database isn't exists.");
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  //-------------------------------------- COPY DATABASE ---------------------------------------
  public void copyDatabaseToExternalStorage() {
    try {
      //database path in external storag/Android/context.getPakageName()
      String dbPath = context.getExternalFilesDir(null).getAbsolutePath() + "/" + context.getPackageName() + "/" + dbName;
      File dbfile = new File(dbPath);
      if (!dbfile.exists()) {
        try {
          copyDatabase(dbfile);
          Log.i("AssetsDatabaseHelper", "database copied.");
        } catch (IOException e) {
          throw new RuntimeException("Error creating source database.", e);
        }
      } else {
        Log.i("AssetsDatabaseHelper", "database is exists.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      copyDatabaseToInternalStorage();
    }
  }

  public void copyDatabaseToInternalStorage() {
    try {
      File dbfile = context.getDatabasePath(dbName);
      if (!dbfile.exists()) {
        try {
          copyDatabase(dbfile);
          Log.i("AssetsDatabaseHelper", "database copied.");
        } catch (IOException e) {
          throw new RuntimeException("Error creating source database.", e);
        }
      } else {
        Log.i("AssetsDatabaseHelper", "database is exists.");
      }
    } catch (Exception e) {
      e.printStackTrace();
      copyDatabaseToInternalStorage();
    }
  }

  private void copyDatabase(File dbfile) throws IOException {
    // If the database already exists, return
    if (dbfile.exists()) {
      return;
    }

    // Make sure we have a path to the file
    dbfile.getParentFile().mkdirs();

    // Try to copy database file
    try {
      InputStream is = context.getAssets().open("databases/" + dbName);
      OutputStream os = new FileOutputStream(dbfile);

      int len = 0;
      byte[] buffer = new byte[8192];

      while ((len = is.read(buffer)) > 0) {
        os.write(buffer, 0, len);
      }

      os.flush();
      os.close();
      is.close();
    } catch (IOException e) {
      Log.d("DATABASE", "Failed to open file", e);
      e.printStackTrace();
    }
  }

//
//  private void copyAttachedDatabase(Context context, String databaseName) {
//    final File dbPath = context.getDatabasePath(databaseName);
//
//    // If the database already exists, return
//    if (dbPath.exists()) {
//      return;
//    }
//
//    // Make sure we have a path to the file
//    dbPath.getParentFile().mkdirs();
//
//    // Try to copy database file
//    try {
//      final InputStream inputStream = context.getAssets().open("databases/" + databaseName);
//      final OutputStream output = new FileOutputStream(dbPath);
//
//      byte[] buffer = new byte[8192];
//      int length;
//
//      while ((length = inputStream.read(buffer, 0, 8192)) > 0) {
//        output.write(buffer, 0, length);
//      }
//
//      output.flush();
//      output.close();
//      inputStream.close();
//    } catch (IOException e) {
//      Log.d("DATABASE", "Failed to open file", e);
//      e.printStackTrace();
//    }
//  }

}

