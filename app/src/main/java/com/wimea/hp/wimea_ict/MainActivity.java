package com.wimea.hp.wimea_ict;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.lang.Math.*;

import static com.wimea.hp.wimea_ict.OfflineUpload.getStatus;
import static com.wimea.hp.wimea_ict.OfflineUpload.startBackgroundPerform;
import static com.wimea.hp.wimea_ict.OfflineUpload.stopBackgroundPerform;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.location_result)
  TextView txtLocationResult;

  @BindView(R.id.updated_on)
  TextView txtUpdatedOn;

  /*@BindView(R.id.btn_start_location_updates)
  Button btnStartUpdates;

  @BindView(R.id.btn_stop_location_updates)
  Button btnStopUpdates;*/


  private TextView txtName;
  private TextView distance_ui;
  private TextView txtEmail;
  private Button btnLogout;
  private Button btnStartUpdates;

  private SQLiteHandler db;
  private SessionManager session;
  private static final String TAG = MainActivity.class.getSimpleName();

  // location last updated time
  private String mLastUpdateTime;

  // location updates interval - 10sec
  private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

  // fastest updates interval - 5 sec
  // location updates will be received if another app is requesting the locations
  // than your app can handle
  private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

  private static final int REQUEST_CHECK_SETTINGS = 100;

  private double DISTANCE_BETWEEN_POINTS;


  // bunch of location related apis
  private FusedLocationProviderClient mFusedLocationClient;
  private SettingsClient mSettingsClient;
  private LocationRequest mLocationRequest;
  private LocationSettingsRequest mLocationSettingsRequest;
  private LocationCallback mLocationCallback;
  private Location mCurrentLocation;
  private ProgressDialog pDialog;
  // boolean flag to toggle the ui
  private Boolean mRequestingLocationUpdates;
  private static OfflineUpload op;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Progress dialog
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(true);
    pDialog.setMessage("Authenticating Location...");
    pDialog.show();

    setContentView(R.layout.activity_main);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    txtName = (TextView) findViewById(R.id.name);
    distance_ui = (TextView) findViewById(R.id.distance);
    txtEmail = (TextView) findViewById(R.id.email);
    btnLogout = (Button) findViewById(R.id.btnLogout);
    String htmlLogout = "<u>Log out</u>";
    btnLogout.setText(Html.fromHtml(htmlLogout));
    btnStartUpdates = findViewById(R.id.refresh_location);


    // SqLite database handler
    db = new SQLiteHandler(getApplicationContext());

    // session manager
    session = new SessionManager(getApplicationContext());

    if (!session.isLoggedIn()) {
      logoutUser();
    }else {
      op = new OfflineUpload(getApplicationContext());

      if (db.numberOfRecords()>0 && !getStatus()){
        //Toast.makeText(getApplicationContext(),""+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
        startBackgroundPerform();
      }
      else if (db.numberOfRecords()<1 && getStatus()){
        //Toast.makeText(getApplicationContext()," @ "+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
        stopBackgroundPerform();
      }
    }


    // Fetching user details from sqlite
    HashMap<String, String> user = db.getUserDetails();

    String name = user.get("name");
    String email = user.get("email");

    // Displaying the user details on the screen
    txtName.setText(name);
    txtEmail.setText(email);



    // Logout button click event
    btnLogout.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        logoutUser();
      }
    });

    //refresh location
    btnStartUpdates.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        startLocationUpdates();
      }
    });



    //gps stuff
    // initialize the necessary libraries
    init();


    // restore the values from saved instance state
    restoreValuesFromBundle(savedInstanceState);

    startLocationButtonClick();
    //search for location;



  }



  private double distanceInMeters(){

    HashMap<String, String> user = db.getUserDetails();
    double stationlat = Double.parseDouble(user.get("latitude"));
    double stationlon = Double.parseDouble(user.get("longitude"));
    double lat1 = mCurrentLocation.getLatitude();
    double lon1 = mCurrentLocation.getLongitude();

    LocationDistance distance_x = new LocationDistance();
    return distance_x.distance(stationlat,stationlon,lat1,lon1,"Meters");
  }
  private void startLocationUpdates() {
    mSettingsClient
      .checkLocationSettings(mLocationSettingsRequest)
      .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
        @SuppressLint("MissingPermission")
        @Override
        public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
          Log.i(TAG, "All location settings are satisfied.");

         // Toast.makeText(getApplicationContext(), "Enabled Successfully!", Toast.LENGTH_SHORT).show();

          //noinspection MissingPermission
          mFusedLocationClient.requestLocationUpdates(mLocationRequest,
            mLocationCallback, Looper.myLooper());

          updateLocationUI();
        }
      })
      .addOnFailureListener(this, new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          int statusCode = ((ApiException) e).getStatusCode();
          switch (statusCode) {
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
              Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                "location settings ");
              try {
                // Show the dialog by calling startResolutionForResult(), and check the
                // result in onActivityResult().
                ResolvableApiException rae = (ResolvableApiException) e;
                rae.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
              } catch (IntentSender.SendIntentException sie) {
                Log.i(TAG, "PendingIntent unable to execute request.");
              }
              break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
              String errorMessage = "Location settings are inadequate, and cannot be " +
                "fixed here. Fix in Settings.";
              Log.e(TAG, errorMessage);

              Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
          }

          updateLocationUI();
        }
      });
  }

  private void init() {
    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    mSettingsClient = LocationServices.getSettingsClient(this);

    mLocationCallback = new LocationCallback() {
      @Override
      public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        // location is received
        mCurrentLocation = locationResult.getLastLocation();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

        updateLocationUI();
      }
    };

    mRequestingLocationUpdates = false;

    mLocationRequest = new LocationRequest();
    mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
    mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    builder.addLocationRequest(mLocationRequest);
    mLocationSettingsRequest = builder.build();
  }

  public void startLocationButtonClick() {
    // Requesting ACCESS_FINE_LOCATION using Dexter library
    Dexter.withActivity(this)
      .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
      .withListener(new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
          mRequestingLocationUpdates = true;
          startLocationUpdates();
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
          if (response.isPermanentlyDenied()) {
            // open device settings when the permission is
            // denied permanently
            openSettings();
          }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
          token.continuePermissionRequest();
        }
      }).check();
  }
  private void openSettings() {
    Intent intent = new Intent();
    intent.setAction(
      Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    Uri uri = Uri.fromParts("package",
      BuildConfig.APPLICATION_ID, null);
    intent.setData(uri);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  public void stopLocationUpdates() {
    // Removing location updates
    mFusedLocationClient
      .removeLocationUpdates(mLocationCallback)
      .addOnCompleteListener(this, new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
          //Toast.makeText(getApplicationContext(), "Stopped Your Location Updates!", Toast.LENGTH_SHORT).show();
        }
      });
  }
  /**
   * Update the UI displaying the location data
   * and toggling the buttons
   */
  private void updateLocationUI() {
    if (mCurrentLocation != null) {

      if(distanceInMeters() <= AppConfig.DISTANCE_FROM_STATION){
        //Toast.makeText(getApplicationContext(), "its less", Toast.LENGTH_SHORT).show();

        stopLocationUpdates();
        if (pDialog.isShowing()){ pDialog.dismiss();}
        // Launch main activity
        Intent intent = new Intent(MainActivity.this,
          Observationslip.class);
        startActivity(intent);
        finish();
      }else {
        int dist = (int) distanceInMeters();
        distance_ui.setText(dist+"m");
      /*txtLocationResult.setText(
        "Lat: " + mCurrentLocation.getLatitude() + ", " +
          "Lgt: " + mCurrentLocation.getLongitude()
      );*/

        // giving a blink animation on TextView
        distance_ui.setAlpha(0);
        distance_ui.animate().alpha(1).setDuration(300);
        txtLocationResult.setAlpha(0);
        txtLocationResult.animate().alpha(1).setDuration(300);

        // location last updated time
        txtUpdatedOn.setText("Last updated on: " + mLastUpdateTime);

        if (pDialog.isShowing()){ pDialog.dismiss();}
      }

    }

  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
    outState.putParcelable("last_known_location", mCurrentLocation);
    outState.putString("last_updated_on", mLastUpdateTime);

  }
  private void restoreValuesFromBundle(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
      if (savedInstanceState.containsKey("is_requesting_updates")) {
        mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
      }

      if (savedInstanceState.containsKey("last_known_location")) {
        mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
      }

      if (savedInstanceState.containsKey("last_updated_on")) {
        mLastUpdateTime = savedInstanceState.getString("last_updated_on");
      }
    }

    updateLocationUI();
  }


  /**
   * Logging out the user. Will set isLoggedIn flag to false in shared
   * preferences Clears the user data from sqlite users table
   * */
  private void logoutUser() {
    session.setLogin(false);
    stopLocationUpdates();
    db.deleteUsers();

    // Launching the login activity
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    startActivity(intent);
    finish();
  }
  @Override
  public void onResume() {
    super.onResume();

    // Resuming location updates depending on button state and
    // allowed permissions
    if (mRequestingLocationUpdates && checkPermissions()) {
      startLocationUpdates();
    }

    updateLocationUI();
  }

  private boolean checkPermissions() {
    int permissionState = ActivityCompat.checkSelfPermission(this,
      Manifest.permission.ACCESS_FINE_LOCATION);
    return permissionState == PackageManager.PERMISSION_GRANTED;
  }


  @Override
  protected void onPause() {
    super.onPause();

    if (mRequestingLocationUpdates) {
      // pausing location updates
      stopLocationUpdates();
    }
  }




}
