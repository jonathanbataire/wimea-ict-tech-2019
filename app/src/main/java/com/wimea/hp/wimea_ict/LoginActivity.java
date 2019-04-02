package com.wimea.hp.wimea_ict;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {
  private static final String TAG = "LoginActivty";
  private Button btnLogin;
  private Button btnLinkToRegister;
  private EditText inputUsername;
  private EditText inputPassword;
  private ProgressDialog pDialog;
  private SessionManager session;
  private SQLiteHandler db;
  String testdum;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    inputUsername = (EditText) findViewById(R.id.username);
    inputPassword = (EditText) findViewById(R.id.password);
    btnLogin = (Button) findViewById(R.id.email_sign_in_button);
    //btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

    // Progress dialog
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    // SQLite database handler
    db = new SQLiteHandler(getApplicationContext());

    // Session manager
    session = new SessionManager(getApplicationContext());

    // Check if user is already logged in or not
    if (session.isLoggedIn()) {
      // User is already logged in. Take him to main activity
      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      startActivity(intent);
      finish();
    }

    // Login button Click Event
    btnLogin.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        String username = inputUsername.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        // Check for empty data in the form
        if (!username.isEmpty() && !password.isEmpty()) {
          // login user
          checkLogin(username, password);
          testdum = username;
        }else if(username.isEmpty()){
          Toast.makeText(getApplicationContext(),
            "Please Enter Your Username!", Toast.LENGTH_SHORT)
            .show();
        }else if(password.isEmpty()){
          Toast.makeText(getApplicationContext(),
            "Please Enter Your Password!", Toast.LENGTH_SHORT)
            .show();
        }
        else {
          // Prompt user to enter credentials
          Toast.makeText(getApplicationContext(),
            "Please Enter Your Credentials!", Toast.LENGTH_LONG)
            .show();
        }
      }

    });


  }

  @Override
  protected void onPause() {
    super.onPause();

  }

  /**
   * function to verify login details in mysql db
   * */
  private void checkLogin(final String email, final String password) {
    // Tag used to cancel the request
    String tag_string_req = "req_login";

    pDialog.setMessage("Logging in...");
    showDialog();

    StringRequest strReq = new StringRequest(Method.POST,
      AppConfig.URL_LOGIN, new Response.Listener<String>() {

      @Override
      public void onResponse(String response) {
        Log.d(TAG, "Login Response: " + response.toString());
        hideDialog();

        try {
          JSONObject jObj = new JSONObject(response);
          boolean error = jObj.getBoolean("error");

          // Check for error node in json
          if (!error) {
            // user successfully logged in
            // Create login session
            session.setLogin(true);

             //Now store the user in SQLite
            String userid = jObj.getString("Userid");

            JSONObject user = jObj.getJSONObject("user");
            String name = user.getString("UserName");
            String email = user.getString("email");
            String created_at = user.getString("created_at");
            String station_name = user.getString("station_name");
            String station_number = user.getString("station_number");
            double latitude = user.getDouble("latitude");
            double longitude = user.getDouble("longitude");

            // Inserting row in users table
            db.addUser(name, email, userid, created_at,latitude,longitude,station_name,station_number);

            // Launch main activity
            Intent intent = new Intent(LoginActivity.this,
              MainActivity.class);
            startActivity(intent);
            finish();
          } else {
            // Error in login. Get the error message
            String errorMsg = jObj.getString("error_msg");
            Toast.makeText(getApplicationContext(),
              errorMsg, Toast.LENGTH_LONG).show();
          }
        } catch (JSONException e) {
          // JSON error
          e.printStackTrace();
          //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
          Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG).show();
        }

      }
    }, new Response.ErrorListener() {

      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e(TAG, "Login Error: " + error.getMessage());
        //Toast.makeText(getApplicationContext(),"zzzzz"+ error.getMessage(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Can't Connect To Server!", Toast.LENGTH_LONG).show();
        hideDialog();
      }
    }) {

      @Override
      protected Map<String, String> getParams() {
        // Posting parameters to login url
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);

        return params;
      }

    };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
  }


  private void showDialog() {
    if (!pDialog.isShowing())
      pDialog.show();
  }

  private void hideDialog() {
    if (pDialog.isShowing())
      pDialog.dismiss();
  }

}
