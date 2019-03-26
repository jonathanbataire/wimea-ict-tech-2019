package com.wimea.hp.wimea_ict;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteHandler extends SQLiteOpenHelper {

  private static final String TAG = SQLiteHandler.class.getSimpleName();

  // All Static variables
  // Database Version
  private static final int DATABASE_VERSION = 1;

  // Database Name
  private static final String DATABASE_NAME = "wimeaDB";

  // Login table name
  private static final String TABLE_USER = "user";
  private static final String TABLE_OB_SLIP = "observationSlip";

  // Login Table Columns names
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_EMAIL = "email";
  private static final String KEY_UID = "uid";
  private static final String KEY_CREATED_AT = "created_at";
  private static final String KEY_LATITUDE ="latitude";
  private static final String KEY_LONGITUDE = "longitude";
  private static final String KEY_STATION_NAME = "station_name";
  private static final String KEY_STATION_NUMBER = "station_number";

  private static final String KEY_DATE = "Date";
  private static final String KEY_DATAID = "id";
  private static final String KEY_USERID = "Userid";
  private static final String KEY_TIME = "TIME";
  private static final String KEY_TotalAmountOfAllClouds = "TotalAmountOfAllClouds";
  private static final String KEY_TotalAmountOfLowClouds = "TotalAmountOfLowClouds";

  private static final String KEY_TypeOfLowClouds1  = "TypeOfLowClouds1";
  private static final String KEY_OktasOfLowClouds1  = "OktasOfLowClouds1";
  private static final String KEY_HeightOfLowClouds1   = "HeightOfLowClouds1";
  private static final String KEY_CLCODEOfLowClouds1   = "CLCODEOfLowClouds1";
  private static final String KEY_TypeOfLowClouds2  = "TypeOfLowClouds2";
  private static final String KEY_OktasOfLowClouds2  = "OktasOfLowClouds2";
  private static final String KEY_HeightOfLowClouds2   = "HeightOfLowClouds2";
  private static final String KEY_CLCODEOfLowClouds2   = "CLCODEOfLowClouds2";
  private static final String KEY_TypeOfLowClouds3  = "TypeOfLowClouds3";
  private static final String KEY_OktasOfLowClouds3  = "OktasOfLowClouds3";
  private static final String KEY_HeightOfLowClouds3   = "HeightOfLowClouds3";
  private static final String KEY_CLCODEOfLowClouds3   = "CLCODEOfLowClouds3";

  private static final String KEY_TypeOfMediumClouds1  = "TypeOfMediumClouds1";
  private static final String KEY_OktasOfMediumClouds1  = "OktasOfMediumClouds1";
  private static final String KEY_HeightOfMediumClouds1   = "HeightOfMediumClouds1";
  private static final String KEY_CLCODEOfMediumClouds1   = "CLCODEOfMediumClouds1";
  private static final String KEY_TypeOfMediumClouds2  = "TypeOfMediumClouds2";
  private static final String KEY_OktasOfMediumClouds2  = "OktasOfMediumClouds2";
  private static final String KEY_HeightOfMediumClouds2   = "HeightOfMediumClouds2";
  private static final String KEY_CLCODEOfMediumClouds2   = "CLCODEOfMediumClouds2";
  private static final String KEY_TypeOfMediumClouds3  = "TypeOfMediumClouds3";
  private static final String KEY_OktasOfMediumClouds3  = "OktasOfMediumClouds3";
  private static final String KEY_HeightOfMediumClouds3   = "HeightOfMediumClouds3";
  private static final String KEY_CLCODEOfMediumClouds3   = "CLCODEOfMediumClouds3";

  private static final String KEY_TypeOfHighClouds1  = "TypeOfHighClouds1";
  private static final String KEY_OktasOfHighClouds1  = "OktasOfHighClouds1";
  private static final String KEY_HeightOfHighClouds1   = "HeightOfHighClouds1";
  private static final String KEY_CLCODEOfHighClouds1   = "CLCODEOfHighClouds1";
  private static final String KEY_TypeOfHighClouds2  = "TypeOfHighClouds2";
  private static final String KEY_OktasOfHighClouds2  = "OktasOfHighClouds2";
  private static final String KEY_HeightOfHighClouds2   = "HeightOfHighClouds2";
  private static final String KEY_CLCODEOfHighClouds2   = "CLCODEOfHighClouds2";
  private static final String KEY_TypeOfHighClouds3  = "TypeOfHighClouds3";
  private static final String KEY_OktasOfHighClouds3  = "OktasOfHighClouds3";
  private static final String KEY_HeightOfHighClouds3   = "HeightOfHighClouds3";
  private static final String KEY_CLCODEOfHighClouds3   = "CLCODEOfHighClouds3";

  private static final String KEY_searchlight = "CloudSearchLightReading";
  private static final String KEY_Rainfall = "Rainfall";
  private static final String KEY_Dry_Bulb = "Dry_Bulb";
  private static final String KEY_Wet_Bulb = "Wet_Bulb";
  private static final String KEY_Present_Weather = "Present_Weather";
  private static final String KEY_Present_WeatherCode = "Present_WeatherCode";
  private static final String KEY_Past_Weather = "Past_Weather";
  private static final String KEY_Visibility = "Visibility";
  private static final String KEY_Wind_Direction = "Wind_Direction";
  private static final String KEY_Wind_Speed = "Wind_Speed";
  private static final String KEY_Gusting = "Gusting";
  private static final String KEY_AttdThermo = "AttdThermo";
  private static final String KEY_PrAsRead = "PrAsRead";
  private static final String KEY_Correction = "Correction";
  private static final String KEY_CLP = "CLP";
  private static final String KEY_MSLPr = "MSLPr";
  private static final String KEY_TimeMarksBarograph = "TimeMarksBarograph";
  private static final String KEY_TimeMarksAnemograph = "TimeMarksAnemograph";
  private static final String KEY_OtherTMarks = "OtherTMarks";
  private static final String KEY_Remarks = "Remarks";
  private static final String KEY_O_SubmittedBy = "O_SubmittedBy";
  private static final String KEY_sunduration = "sunduration";
  private static final String KEY_Max_temp = "Max_temp";
  private static final String KEY_Min_temp = "Min_temp";
  private static final String KEY_speciormetar = "speciormetar";
  private static final String KEY_UnitOfWindSpeed = "UnitOfWindSpeed";
  private static final String KEY_IndOrOmissionOfPrecipitation = "IndOrOmissionOfPrecipitation";
  private static final String KEY_TypeOfStation_Present_Past_Weather = "TypeOfStation_Present_Past_Weather";
  private static final String KEY_Heightlowest = "HeightOfLowestCloud";
  private static final String KEY_StandardIso = "StandardIsobaricSurface";
  private static final String KEY_GPM = "GPM";
  private static final String KEY_dopop = "DurationOfPeriodOfPrecipitation";
  private static final String KEY_GrassMin = "GrassMinTemp";
  private static final String KEY_CI = "CI_OfPrecipitation";
  private static final String KEY_BE = "BE_OfPrecipitation";
  private static final String KEY_Indication = "IndicatorOfTypeOfIntrumentation";
  private static final String KEY_sign = "SignOfPressureChange";
  private static final String KEY_Supp = "Supp_Info";
  private static final String KEY_Vapor = "VapourPressure";
  private static final String KEY_T_H_Graph = "T_H_Graph";



  public SQLiteHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  // Creating Tables
  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
      + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"+ KEY_LATITUDE + " DOUBLE,"+ KEY_LONGITUDE + " DOUBLE ,"
      + KEY_EMAIL + " TEXT ," + KEY_UID + " TEXT,"+ KEY_STATION_NAME + " TEXT,"+ KEY_STATION_NUMBER + " TEXT,"
      + KEY_CREATED_AT + " TEXT" + ");";
    db.execSQL(CREATE_LOGIN_TABLE);

    String CREATE_SLIP_TABLE = "CREATE TABLE " + TABLE_OB_SLIP + "("
      + KEY_DATAID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"+ KEY_USERID + " TEXT,"+ KEY_STATION_NUMBER + " TEXT ,"
      + KEY_STATION_NAME + " TEXT ," + KEY_TIME + " TEXT,"+ KEY_TotalAmountOfAllClouds+ " TEXT,"+ KEY_TotalAmountOfLowClouds + " TEXT,"

      + KEY_TypeOfLowClouds1 + " TEXT," + KEY_OktasOfLowClouds1 + " TEXT,"+ KEY_HeightOfLowClouds1 + " TEXT,"+ KEY_CLCODEOfLowClouds1 + " TEXT,"
      + KEY_TypeOfLowClouds2 + " TEXT ," + KEY_OktasOfLowClouds2 + " TEXT,"+ KEY_HeightOfLowClouds2 + " TEXT,"+ KEY_CLCODEOfLowClouds2 + " TEXT,"
      + KEY_TypeOfLowClouds3 + " TEXT ," + KEY_OktasOfLowClouds3 + " TEXT,"+ KEY_HeightOfLowClouds3 + " TEXT,"+ KEY_CLCODEOfLowClouds3 + " TEXT,"

      + KEY_TypeOfMediumClouds1 + " TEXT," + KEY_OktasOfMediumClouds1 + " TEXT,"+ KEY_HeightOfMediumClouds1 + " TEXT,"+ KEY_CLCODEOfMediumClouds1 + " TEXT,"
      + KEY_TypeOfMediumClouds2 + " TEXT ," + KEY_OktasOfMediumClouds2 + " TEXT,"+ KEY_HeightOfMediumClouds2 + " TEXT,"+ KEY_CLCODEOfMediumClouds2 + " TEXT,"
      + KEY_TypeOfMediumClouds3 + " TEXT ," + KEY_OktasOfMediumClouds3 + " TEXT,"+ KEY_HeightOfMediumClouds3 + " TEXT,"+ KEY_CLCODEOfMediumClouds3 + " TEXT,"

      + KEY_TypeOfHighClouds1 + " TEXT," + KEY_OktasOfHighClouds1 + " TEXT,"+ KEY_HeightOfHighClouds1 + " TEXT,"+ KEY_CLCODEOfHighClouds1 + " TEXT,"
      + KEY_TypeOfHighClouds2 + " TEXT ," + KEY_OktasOfHighClouds2 + " TEXT,"+ KEY_HeightOfHighClouds2 + " TEXT,"+ KEY_CLCODEOfHighClouds2 + " TEXT,"
      + KEY_TypeOfHighClouds3 + " TEXT ," + KEY_OktasOfHighClouds3 + " TEXT,"+ KEY_HeightOfHighClouds3 + " TEXT,"+ KEY_CLCODEOfHighClouds3 + " TEXT,"

      + KEY_searchlight + " TEXT," + KEY_Rainfall + " TEXT,"+ KEY_Dry_Bulb + " TEXT,"+ KEY_Wet_Bulb + " TEXT,"
      + KEY_Present_Weather + " TEXT ," + KEY_Present_WeatherCode + " TEXT,"+ KEY_Past_Weather + " TEXT,"+ KEY_Visibility + " TEXT,"
      + KEY_Wind_Direction + " TEXT ," + KEY_Wind_Speed + " TEXT,"+ KEY_Gusting + " TEXT,"+ KEY_AttdThermo + " TEXT,"

      + KEY_PrAsRead + " TEXT," + KEY_Correction + " TEXT,"+ KEY_CLP + " TEXT,"+ KEY_MSLPr + " TEXT,"
      + KEY_TimeMarksBarograph + " TEXT ," + KEY_TimeMarksAnemograph + " TEXT,"+ KEY_OtherTMarks + " TEXT,"+ KEY_Remarks + " TEXT,"
      + KEY_O_SubmittedBy + " TEXT ," + KEY_sunduration + " TEXT,"+ KEY_Max_temp + " TEXT,"+ KEY_Min_temp + " TEXT,"

      + KEY_speciormetar + " TEXT," + KEY_UnitOfWindSpeed + " TEXT,"+ KEY_IndOrOmissionOfPrecipitation + " TEXT,"+ KEY_TypeOfStation_Present_Past_Weather + " TEXT,"
      + KEY_Heightlowest + " TEXT ," + KEY_StandardIso + " TEXT,"+ KEY_dopop + " TEXT,"+ KEY_GrassMin + " TEXT,"
      + KEY_CI + " TEXT ," + KEY_BE + " TEXT,"+ KEY_Indication + " TEXT,"+ KEY_sign + " TEXT,"

      + KEY_Supp + " TEXT," + KEY_Vapor + " TEXT,"
      + KEY_T_H_Graph + " TEXT" + ");";
    db.execSQL(CREATE_SLIP_TABLE);

    Log.d(TAG, "Database tables created");
  }

  // Upgrading database
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Drop older table if existed
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_OB_SLIP);

    // Create tables again
    onCreate(db);
  }

  public void deleteSlip(String Date,String Userid,String TIME,String speciormetar) {
    SQLiteDatabase db = this.getWritableDatabase();
    // Delete All Rows
   // db.delete(TABLE_OB_SLIP, null, null);
    String deleteQuery = "DELETE FROM " + TABLE_OB_SLIP+" WHERE "+KEY_DATE+"='"+Date+"' AND "+KEY_USERID
      +"='"+Userid+"' AND "+KEY_TIME+"='"+TIME+"' AND "+KEY_speciormetar+"='"+speciormetar+"'";
    db.execSQL(deleteQuery);
    db.close();

    Log.d(TAG, "Deleted a record info from sqlite");
  }
  /**
   * Getting user data from database
   * */
  public HashMap<String, String> getUserDetails() {
    HashMap<String, String> user = new HashMap<String, String>();
    String selectQuery = "SELECT  * FROM " + TABLE_USER;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    // Move to first row
    cursor.moveToFirst();
    if (cursor.getCount() > 0) {
      user.put("name", cursor.getString(1));
      user.put("email", cursor.getString(4));
      user.put("uid", cursor.getString(5));
      user.put("created_at", cursor.getString(8));
      user.put("latitude", cursor.getString(2));
      user.put("longitude", cursor.getString(3));
      user.put("station_name", cursor.getString(6));
      user.put("station_number", cursor.getString(7));

    }
    cursor.close();
    db.close();
    // return user
    Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

    return user;
  }
  public HashMap<String,String> getSlip(){
    HashMap<String, String> slip = new HashMap<>();
    String selectQuery = "SELECT  * FROM " + TABLE_OB_SLIP+" ORDER BY ID DESC LIMIT 1";

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    // Move to first row
    cursor.moveToFirst();
    if (cursor.getCount() > 0) {
      //slip.put("name", cursor.getString(1));
      int col = cursor.getColumnCount();
      for (int i=0;i<col;i++){
        slip.put(cursor.getColumnName(i),cursor.getString(i));
      }

    }
    cursor.close();
    db.close();
    // return slip
    Log.d(TAG, "Fetching slip from Sqlite: " + slip.toString());

    return slip;
  }

  public  List<PendingUtils> pendingRecords(List<PendingUtils> list){
    //list.add(new PendingUtils("Normal Form","08-12-2018","12:30Z"));
    String selectQuery = "SELECT  * FROM " + TABLE_OB_SLIP;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    try {
      while (cursor.moveToNext()){
        String category = cursor.getString(cursor.getColumnIndex("speciormetar"));
        String time = cursor.getString(cursor.getColumnIndex("TIME"));
        String date = cursor.getString(cursor.getColumnIndex("Date"));
        list.add(new PendingUtils(category,date,time));
      }
    }
    finally {
      cursor.close();
      db.close();
    }

    // return user
    Log.d(TAG, "Fetched Pending records");

    return list;

  }

  public int numberOfRecords(){
    String selectQuery = "SELECT  * FROM " + TABLE_OB_SLIP;

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);
    // Move to first row cursor.moveToFirst();
    int num = cursor.getCount();
    cursor.close();
    db.close();
    // return user
    Log.d(TAG, "Fetching number of records: "+ num);

    return num;
  }

  public boolean isDuplicate(String date,String station_name,String station_number,String TIME,String metarOrSpeci){

    boolean check;
    SQLiteDatabase db = this.getReadableDatabase();
    String sql = "SELECT * FROM "+TABLE_OB_SLIP+" WHERE "+KEY_TIME+"='"+TIME+"' AND "+KEY_DATE+"='"+date+"' AND "+KEY_STATION_NAME+"='"
      +station_name+"' AND "+KEY_STATION_NUMBER+"='"+station_number+"' AND "+KEY_speciormetar+"='"+metarOrSpeci+"'";
    Cursor cursor = db.rawQuery(sql,null);

    if (cursor.getCount() > 0) {
      check = true;
    }
    else{
      check = false;
    }
    cursor.close();
    db.close();
    Log.d(TAG,"checking for duplicate record");
    return check;
  }

  public void addSlip(Map<String, String> data,String date,String station_name,String station_number,String TIME,String metarOrSpeci) {

    if (!isDuplicate(date,station_name,station_number,TIME,metarOrSpeci)){

      SQLiteDatabase db = this.getWritableDatabase();

      ContentValues values = new ContentValues();
      for (Map.Entry<String,String> entry : data.entrySet()){
        values.put(entry.getKey(),entry.getValue());
      }
      // Inserting Row
      long id = db.insert(TABLE_OB_SLIP, null, values);
      db.close(); // Closing database connection

      Log.d(TAG, "New slip data inserted into sqlite: " + id);
    }
    else{
      Log.d(TAG, "Duplicate Record....");
    }

  }

  /**
   * Storing user details in database
   * */
  public void addUser(String name, String email, String uid, String created_at,double lat,double lon,String station_name,String station_number) {
    SQLiteDatabase db = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(KEY_NAME, name); // Name
    values.put(KEY_EMAIL, email); // Email
    values.put(KEY_UID, uid); // Email
    values.put(KEY_CREATED_AT, created_at); // Created At
    values.put(KEY_LATITUDE, lat);
    values.put(KEY_LONGITUDE,lon);
    values.put(KEY_STATION_NAME,station_name);
    values.put(KEY_STATION_NUMBER,station_number);

    // Inserting Row
    long id = db.insert(TABLE_USER, null, values);
    db.close(); // Closing database connection

    Log.d(TAG, "New user inserted into sqlite: " + id);
  }



  /**
   * Re crate database Delete all tables and create them again
   * */
  public void deleteUsers() {
    SQLiteDatabase db = this.getWritableDatabase();
    // Delete All Rows
    db.delete(TABLE_USER, null, null);
    db.close();

    Log.d(TAG, "Deleted all user info from sqlite");
  }

}
