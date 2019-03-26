package com.wimea.hp.wimea_ict;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.wimea.hp.wimea_ict.Observationslip.setNotification;

public class OfflineUpload extends AppCompatActivity{

  static Context context;
  static SQLiteHandler db;


  private static Timer timerAsync;
  private static TimerTask timerTaskAsync;
  static int c = 0;
  private static boolean xnet;
  private static boolean status=false;

  public OfflineUpload(Context con){
    context=con;
    db = new SQLiteHandler(con);

  }

  private static void sqliteSubmit(){
    final HashMap<String,String> map = db.getSlip();

    // Tag used to cancel the request
    String tag_string_req = "req_submitsqlite";

    StringRequest strReq = new StringRequest(Request.Method.POST,
      AppConfig.URL_SUBMIT_FORM, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {

        try {
          JSONObject jObj = new JSONObject(response);
          boolean dup_code = jObj.getBoolean("dup_code");
          boolean error = jObj.getBoolean("error");
          if (!error || dup_code){
            String Date = map.get("Date");
            String Userid = map.get("Userid");
            String TIME = map.get("TIME");
            String speciormetar = map.get("speciormetar");
            String msg;
            String cat = speciormetar.substring(0,1).toUpperCase()+speciormetar.substring(1);
            if(dup_code){
              msg = cat+" form for "+TIME+"("+Date+") Already Submitted!";
            }else {
              msg = cat+" form for "+TIME+"("+Date+") Submitted!";
            }

            setNotification(context,msg);
            db.deleteSlip(Date,Userid,TIME,speciormetar);

            //Toast.makeText(context, server_msg, Toast.LENGTH_LONG).show();
          }
          else{
           //Toast.makeText(context, server_msg, Toast.LENGTH_LONG).show();
          }


        } catch (JSONException e) {
          //Toast.makeText(context, "JSON: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        //Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

      }
    }) {

      @Override
      protected Map<String, String> getParams() {
        return map;
      }

    };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }


  public static void startBackgroundPerform() {

    final Handler handler = new Handler();
    timerAsync = new Timer();
    timerTaskAsync = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          public void run() {
            status = true;
            c++;
            try {
              if (c<721){
                BackNet Task= new BackNet();
                Task.execute();

              }else {
                c=0;
                stopBackgroundPerform();
              }

            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    };
    timerAsync.schedule(timerTaskAsync, 2000, 5000);
  }

  public static boolean getStatus(){
    return status;
  }

  public static void stopBackgroundPerform(){
    if (timerTaskAsync != null){
      status = false;
      timerTaskAsync.cancel();
      //Toast.makeText(context,"stopped",Toast.LENGTH_SHORT).show();
      Log.d("Timer","timer cancelled");
    }
  }


  public static class BackNet extends AsyncTask<String,String,Boolean> {

    public BackNet() {

    }

    int f =0;
    @Override
    protected Boolean doInBackground(String... strings) {
      if (checkConnection()){
        return true;
      }
      return false;
    }
    protected void onPostExecute(Boolean x){
      //net(x);
      if (x && db.numberOfRecords()>0){
        sqliteSubmit();
        Toast.makeText(context,"yes running"+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
      }
      else if (db.numberOfRecords()<1){
        stopBackgroundPerform();
      }
      else{
        Toast.makeText(context,"No running internet"+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
      }
    }
  }

  public static Boolean checkConnection(){
    Runtime runtime = Runtime.getRuntime();
    try {

      Process ipProcess = runtime.exec("/system/bin/ping -c 1 -W 5 "+AppConfig.SERVER_ADDRESS);
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }


}
