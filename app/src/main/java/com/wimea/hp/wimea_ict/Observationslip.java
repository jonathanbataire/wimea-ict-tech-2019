package com.wimea.hp.wimea_ict;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.BaseInputConnection;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.android.gms.signin.internal.SignInClientImpl.ACTION_START_SERVICE;
import static com.wimea.hp.wimea_ict.OfflineUpload.getStatus;
import static com.wimea.hp.wimea_ict.OfflineUpload.startBackgroundPerform;
import static com.wimea.hp.wimea_ict.OfflineUpload.stopBackgroundPerform;


public class Observationslip extends AppCompatActivity
  implements NavigationView.OnNavigationItemSelectedListener{
  SessionManager session;
  SQLiteHandler db;
  private static final String TAG = Observationslip.class.getSimpleName();
  private Button next,back,next_page3,back_page2,next_page4,back_page3,next_page5,back_page4,submit;
  private EditText form_date,station_name,station_number;
  private EditText speci_time,degree_cel,min_degree_far,max_degree_far,max_degree_cel;
  TimePickerDialog timeDialog;
  private Spinner spTime_category;
  private Spinner normal_time;
  private ViewGroup layout1;
  private ViewGroup layout2,layout3,layout4,layout5;
  private ViewGroup vGtime_normal;
  private ViewGroup vGtime_speci,vGferan,maxvGfaran;
  ProgressDialog pDialog;
  private Spinner sptotal_low_clouds,sptotal_all_clouds,spl_type1,spl_type2,spl_type3,spl_oktas1,spl_oktas2,spl_oktas3,spl_clcode1,spl_clcode2,spl_clcode3;
  private EditText edl_height1,edl_height2,edl_height3;
  private Spinner spm_type1,spm_type2,spm_type3,spm_oktas1,spm_oktas2,spm_oktas3,spm_clcode1,spm_clcode2,spm_clcode3;
  private EditText edm_height1,edm_height2,edm_height3;
  private Spinner sph_type1,sph_type2,sph_type3,sph_oktas1,sph_oktas2,sph_oktas3,sph_clcode1,sph_clcode2,sph_clcode3;
  private EditText edh_height1,edh_height2,edh_height3;

  private String time_category,input_speci_time,input_date,input_station_name,input_station_number,input_time,input_normalTime;
  private String total_low_clouds,total_all_clouds,l_type1,l_type2,l_type3,l_oktas1,l_oktas2,l_oktas3,l_height1,l_height2,l_height3;
  private String m_type1,m_type2,m_type3,m_oktas1,m_oktas2,m_oktas3,m_height1,m_height2,m_height3,m_clcode1,m_clcode2,m_clcode3;
  private String h_type1,h_type2,h_type3,h_oktas1,h_oktas2,h_oktas3,h_height1,h_height2,h_height3,h_clcode1,h_clcode2,h_clcode3;
  private String l_clcode1,l_clcode2,l_clcode3,userid;

  private Spinner sppresent_weather;
  private EditText cloud_searchlight,rainfall_mm,present_weather_code,past_weather,past_weather_code,visibility,gusting,wind_direction,wind_speed,sun_duration,dry_bulb,wet_bulb;
  private String input_cloud_searchlight,input_rainfall_mm,input_present_weather_code,input_past_weather,input_past_weather_code,input_visibility,input_gusting;
  private String input_wind_direction,input_wind_speed,input_sun_duration,input_dry_bulb,input_wet_bulb,input_present_weather;

  private EditText attd_thermo,correction,pr_as_read,clp,mslp,time_marks_b,time_marks_a,remarks,other_tmarks;
  private String input_attd_thermo,input_correction,input_pr_as_read,input_clp,input_mslp,input_time_marks_b,input_time_marks_a,input_remarks,input_other_tmarks;
  private String input_min_degree_cel,input_min_degree_far,input_max_degree_far,input_max_degree_cel,submitted_by;

  private Spinner spunit_wind,spind_omission, sptype_station, spheight_lowest;
  private EditText standard_isobaric,geopotential,precipitation,grass_min,character,beginning,indicator,sign_pressure,suplimentary,vapor_pressure,th_graph;
  private String input_spunit_wind,input_spind_omission,input_type_station,input_height_lowest;
  private String input_standard_isobaric,input_geopotential,input_precipitation,input_grass_min,input_character,input_beginning;
  private String input_indicator,input_sign_pressure,input_suplimentary,input_vapor_pressure,input_th_graph;

  private static OfflineUpload op;
  PendingFragment pendingFragment;
  private TextView usermenu,nav_txt;
  static int c = 0;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_observationslip);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    setTitle("Observationslip Form");


    pDialog = new ProgressDialog(this);
    initializer();


    setDefaultTime();
    layout1 = findViewById(R.id.layout1);
    layout2 = findViewById(R.id.layout2);
    layout3 = findViewById(R.id.layout3);
    layout4 = findViewById(R.id.layout4);
    layout5 = findViewById(R.id.layout5);
    vGtime_normal = findViewById(R.id.spinner_cover1);
    vGtime_speci = findViewById(R.id.spinner_cover2);
    vGferan = findViewById(R.id.min_faran_cover);
    maxvGfaran = findViewById(R.id.max_faran_cover);



    layout2.setVisibility(View.GONE);
    layout3.setVisibility(View.GONE);
    layout4.setVisibility(View.GONE);
    layout5.setVisibility(View.GONE);

    //sqlite
    db = new SQLiteHandler(getApplicationContext());
    // session manager
    session = new SessionManager(getApplicationContext());


    op = new OfflineUpload(getApplicationContext());

    if (db.numberOfRecords()>0 && !getStatus()){
      //Toast.makeText(getApplicationContext(),""+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
      startBackgroundPerform();
    }
    else if (db.numberOfRecords()<1 && getStatus()){
      //Toast.makeText(getApplicationContext()," @ "+db.numberOfRecords(),Toast.LENGTH_SHORT).show();
      stopBackgroundPerform();
    }

    Listeners();





    HashMap<String, String> user = db.getUserDetails();
    submitted_by = user.get("name");
    userid = user.get("uid");
    input_station_name = user.get("station_name");
    input_station_number = user.get("station_number");
    station_name.setText(input_station_name);
    usermenu.setText(submitted_by);
    nav_txt.setText(user.get("email")+" ("+input_station_name+")");
    station_number.setText(input_station_number);



    DrawerLayout drawer =  findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
      this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();


    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

  }

  public  void reset(){
    //initializer();
    //rainfall_mm.setText("testing 12");
    //Toast.makeText(c,"okay.......",Toast.LENGTH_SHORT).show();
    /*Intent intent = new Intent(this,Observationslip.class);
    startActivity(intent);
    finish();*/
    //finish();
    startActivity(new Intent(this,EditActivity.class));

  }

  public void initializer() {

    back = findViewById(R.id.back);
    next = findViewById(R.id.next);
    next_page3 = findViewById(R.id.next_page3);
    back_page2 = findViewById(R.id.back_page2);
    next_page4 = findViewById(R.id.next_page4);
    back_page3 = findViewById(R.id.back_page3);
    next_page5 = findViewById(R.id.next_page5);
    back_page4 = findViewById(R.id.back_page4);
    NavigationView navView = findViewById(R.id.nav_view);
    View headerView = navView.getHeaderView(0);
    usermenu = headerView.findViewById(R.id.dusername);
    nav_txt = headerView.findViewById(R.id.textView);

    spTime_category = findViewById(R.id.time_category_spinner);
    submit = findViewById(R.id.submit);


    /*
     *page one
     */
    form_date=findViewById(R.id.form_date);
    speci_time = findViewById(R.id.time_spinner2);
    normal_time = findViewById(R.id.time_spinner1);
    station_name = findViewById(R.id.station_name);
    station_number = findViewById(R.id.station_number);
    sptotal_all_clouds = findViewById(R.id.sptotal_all_clouds);
    sptotal_low_clouds = findViewById(R.id.sptotal_low_clouds);
    spl_type1 = findViewById(R.id.l_type1);
    spl_type2 = findViewById(R.id.l_type2);
    spl_type3 = findViewById(R.id.l_type3);
    spl_oktas1 = findViewById(R.id.l_oktas1);
    spl_oktas2 = findViewById(R.id.l_oktas2);
    spl_oktas3 = findViewById(R.id.l_oktas3);
    edl_height1 = findViewById(R.id.l_heigth1);
    edl_height2 = findViewById(R.id.l_heigth2);
    edl_height3 = findViewById(R.id.l_heigth3);
    spl_clcode1 = findViewById(R.id.l_clcode1);
    spl_clcode2 = findViewById(R.id.l_clcode2);
    spl_clcode3 = findViewById(R.id.l_clcode3);

    /*
     * page two
     * medium cloud
     * */
    spm_type1 = findViewById(R.id.m_type1);
    spm_type2 = findViewById(R.id.m_type2);
    spm_type3 = findViewById(R.id.m_type3);
    spm_oktas1 = findViewById(R.id.m_oktas1);
    spm_oktas2 = findViewById(R.id.m_oktas2);
    spm_oktas3 = findViewById(R.id.m_oktas3);
    edm_height1 = findViewById(R.id.m_height1);
    edm_height2 = findViewById(R.id.m_height2);
    edm_height3 = findViewById(R.id.m_height3);
    spm_clcode1 = findViewById(R.id.m_clcode1);
    spm_clcode2 = findViewById(R.id.m_clcode2);
    spm_clcode3 = findViewById(R.id.m_clcode3);

    sph_type1 = findViewById(R.id.h_type1);
    sph_type2 = findViewById(R.id.h_type2);
    sph_type3 = findViewById(R.id.h_type3);
    sph_oktas1 = findViewById(R.id.h_oktas1);
    sph_oktas2 = findViewById(R.id.h_oktas2);
    sph_oktas3 = findViewById(R.id.h_oktas3);
    edh_height1 = findViewById(R.id.h_height1);
    edh_height2 = findViewById(R.id.h_height2);
    edh_height3 = findViewById(R.id.h_height3);
    sph_clcode1 = findViewById(R.id.h_clcode1);
    sph_clcode2 = findViewById(R.id.h_clcode2);
    sph_clcode3 = findViewById(R.id.h_clcode3);

    /*
     * page three
     *
     */
    sppresent_weather = findViewById(R.id.sppresent_weather);
    cloud_searchlight = findViewById(R.id.cloud_searchlight);
    rainfall_mm = findViewById(R.id.rainfall_mm);
    onlyTextValidator(rainfall_mm,1);
    present_weather_code = findViewById(R.id.present_weather_code);
    onlyTextValidator(present_weather_code,1);
    past_weather = findViewById(R.id.past_weather);
    past_weather_code = findViewById(R.id.past_weather_code);
    onlyTextValidator(past_weather,1);
    onlyTextValidator(past_weather_code,1);
    visibility = findViewById(R.id.visibility);
    gusting = findViewById(R.id.gusting);
    wind_direction = findViewById(R.id.wind_direction);
    wind_speed = findViewById(R.id.wind_speed);
    onlyTextValidator(wind_speed,1);
    sun_duration = findViewById(R.id.sun_duration);
    onlyTextValidator(sun_duration,1);
    dry_bulb = findViewById(R.id.dry_bulb);
    wet_bulb = findViewById(R.id.wet_bulb);

    /*
     * page four
     *
     */
    attd_thermo = findViewById(R.id.attd_thermo);
    correction = findViewById(R.id.correction);
    pr_as_read = findViewById(R.id.pr_as_read);
    clp = findViewById(R.id.clp);
    degree_cel = findViewById(R.id.min_degrees_cel);
    min_degree_far = findViewById(R.id.min_degrees_faran);
    max_degree_cel = findViewById(R.id.max_degrees_cel);
    max_degree_far = findViewById(R.id.max_degrees_faran);
    mslp = findViewById(R.id.mslp);
    time_marks_b = findViewById(R.id.time_marks_b);
    time_marks_a = findViewById(R.id.time_marks_a);
    other_tmarks = findViewById(R.id.other_tmarks);
    onlyTextValidator(other_tmarks,0);
    remarks = findViewById(R.id.remarks);
    onlyTextValidator(remarks,0);


    /*
     * page five
     *
     */
    spunit_wind = findViewById(R.id.spunit_wind);
    spind_omission = findViewById(R.id.spind_omission);
    sptype_station = findViewById(R.id.sptype_of_station);
    spheight_lowest = findViewById(R.id.spheight_lowest);
    standard_isobaric = findViewById(R.id.standard_isobaric);
    geopotential = findViewById(R.id.geopotential);
    onlyTextValidator(geopotential,1);
    precipitation = findViewById(R.id.precipitation);
    onlyTextValidator(precipitation,1);
    grass_min = findViewById(R.id.grass_min);
    onlyTextValidator(grass_min,1);
    character = findViewById(R.id.character);
    onlyTextValidator(character,1);
    beginning = findViewById(R.id.beginning);
    onlyTextValidator(beginning,1);
    indicator = findViewById(R.id.indication);
    onlyTextValidator(indicator,1);
    sign_pressure = findViewById(R.id.sign_pressure);
    onlyTextValidator(sign_pressure,1);
    suplimentary = findViewById(R.id.suplimentary_info);
    onlyTextValidator(suplimentary,0);
    vapor_pressure = findViewById(R.id.vapor_pressure);
    th_graph = findViewById(R.id.th_graph);
    onlyTextValidator(th_graph,1);
  }


  public static void setNotification(Context c,String msg){
    long[] v = {0,200};
    Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    Notification notification = new NotificationCompat.Builder(c,"0").setContentTitle("WIMEA-ICT WDR").setSound(uri)
      .setContentText(msg).setSmallIcon(R.drawable.ic_stat_name).setVibrate(v)
      .setAutoCancel(true)
      .build();
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    NotificationManager notificationManager = (NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
    notificationManager.notify((int) System.currentTimeMillis(),notification);
  }

  public void loadFragment(){

    pendingFragment = new PendingFragment();
    pendingFragment.activityRef(this);
    android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
    pendingFragment.show(ft,"pending");
  }


  private void setDefaultTime(){

    Format f = new SimpleDateFormat("dd-MM-yyyy");
    String current_time = f.format(new Date());
    form_date.setText(current_time);

    int zulutime = zulu_time_index();
    normal_time.setSelection(zulutime);


   // speci_time.setText(new StringBuilder().append(current_time()).append("Z").toString());
  }


  public int zulu_time_index(){

    int min_index = 0;int hour_index;
    Calendar calendar = Calendar.getInstance();
    int hh = calendar.get(Calendar.HOUR_OF_DAY);
    int mm = calendar.get(Calendar.MINUTE);

    hh = hh-3;
    if (hh<0){
      hh = hh+24;
      DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
      String yesterday_time = df.format(yesterdayDate());
      form_date.setText(yesterday_time);
    }
    hour_index=hh*2;
    if (mm >= 30){
      min_index=1;
    }
    return hour_index+min_index;
  }

  private Date yesterdayDate(){
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE,-1);
    return cal.getTime();
  }

  private String current_time(){
    Date date = new Date();
    DateFormat format = new SimpleDateFormat("HH:mm");
    return format.format(date);
  }
  public double fahrenheitConverter(double celcius){
    double cel= (celcius*1.8)+32.0;
    double val = Math.round(cel*100.0)/100.0;
    return val;
  }

  public Boolean checkConnection(){
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 -W 5 "+AppConfig.SERVER_ADDRESS);
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }



  public class Submit extends AsyncTask<String,Integer,Boolean>{

    //private boolean error;
    private void submit_form() {
      // Tag used to cancel the request
      String tag_string_req = "req_submit";

      StringRequest strReq = new StringRequest(Request.Method.POST,
        AppConfig.URL_SUBMIT_FORM, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
          Log.d(TAG, "Response: " + response.toString());

          try {
            JSONObject jObj = new JSONObject(response);
            String server_msg = jObj.getString("server_msg");
            boolean error = jObj.getBoolean("error");
            if (!error){

             // Toast.makeText(getApplicationContext(),server_msg, Toast.LENGTH_LONG).show();
              setSnackBar(layout5,server_msg,R.color.success);
            }
            else{
              setSnackBar(layout5,server_msg,R.color.warning);
              //Toast.makeText(getApplicationContext(), server_msg, Toast.LENGTH_LONG).show();
            }


          } catch (JSONException e) {
            //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "An Error Occured", Toast.LENGTH_LONG).show();
          }

        }
      }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
          Log.e(TAG, "Error: " + error.getMessage());
          Toast.makeText(getApplicationContext(),"Server Error Occured", Toast.LENGTH_LONG).show();
        }
      }) {

        @Override
        protected Map<String, String> getParams() {

          return parameters();
        }

      };

      // Adding request to request queue
      AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
      if (checkConnection()){
        submit_form();

        return true;
      }else {
        db.addSlip(parameters(),input_date,input_station_name,input_station_number,input_time,time_category);
        return false;
      }
    }
    protected void onPreExecute(){
      pDialog.setMessage("Submitting Please wait..");
      pDialog.setCancelable(false);
      pDialog.show();
    }
    protected void onPostExecute(Boolean x){
      pDialog.hide();
      if (x){

      }
      else {
        if (!OfflineUpload.getStatus()){
          startBackgroundPerform();
        }

        setSnackBar(layout5,"No Internet Connection Saved to pending Records",R.color.colorPrimaryDark);
       // Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_LONG).show();
      }
    }
  }



  public void submitDialog(){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("Are you sure" +
      "" +
      " you want to Submit this form?");
    alertDialogBuilder.setCancelable(false);
      alertDialogBuilder.setPositiveButton("yes",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface arg0, int arg1) {
            Submit submitt = new Submit();
            submitt.execute();
          }
        });

    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }

  public void onlyTextValidator(final EditText editText,final int validationCode){

    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        Pattern ps;
        if (validationCode ==0){ ps = Pattern.compile("^[a-zA-Z]+$");}
        else {ps = Pattern.compile("^[a-zA-Z0-9,' .]+$");}

        Matcher ms = ps.matcher(s.toString());
        boolean bs = ms.matches();
        if (s.length() != 0){
          if(bs == false){
            //Toast.makeText(getApplicationContext(),"wrong value",Toast.LENGTH_SHORT).show();
            BaseInputConnection con = new BaseInputConnection(editText,true);
            con.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_DEL));
          }
          //else{Toast.makeText(getApplicationContext(),"correct value",Toast.LENGTH_SHORT).show();}
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
  }
  private void setSnackBar(View coordinatorLayout,String text,int color){
    Snackbar snackbar =Snackbar.make(coordinatorLayout,text,Snackbar.LENGTH_LONG).setDuration(3000);
    snackbar.getView().setBackgroundColor(getResources().getColor(color));
    snackbar.show();
    View view = snackbar.getView();
    TextView txtv = view.findViewById(android.support.design.R.id.snackbar_text);
    txtv.setTextAlignment(coordinatorLayout.TEXT_ALIGNMENT_CENTER);
    txtv.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.snackbar));
  }

  private boolean isMyServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        Log.i ("isMyServiceRunning?", true+"");
        return true;
      }
    }
    Log.i ("isMyServiceRunning?", false+"");
    return false;
  }



  public void Listeners(){

    submit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {



        input_standard_isobaric = standard_isobaric.getText().toString().trim();
        input_geopotential = geopotential.getText().toString().trim();
        input_precipitation = precipitation.getText().toString().trim();
        input_grass_min = grass_min.getText().toString().trim();
        input_character = character.getText().toString().trim();
        input_beginning = beginning.getText().toString().trim();
        input_indicator = indicator.getText().toString().trim();
        input_sign_pressure = sign_pressure.getText().toString().trim();
        input_suplimentary = suplimentary.getText().toString().trim();
        input_vapor_pressure = vapor_pressure.getText().toString().trim();
        input_th_graph = th_graph.getText().toString().trim();

        //Toast.makeText(getApplicationContext(),input_th_graph,Toast.LENGTH_SHORT).show();
        submitDialog();

      }
    });

    spheight_lowest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        input_height_lowest = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    sptype_station.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        input_type_station = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spind_omission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        input_spind_omission = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spunit_wind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        input_spunit_wind = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    back.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        //backTransition(layout2,layout1);
        /*MyService.setStopFlag(true);
        Intent stopServiceIntent = new Intent(Observationslip.this, MyService.class);
        stopService(stopServiceIntent);*/
        crossfade(layout2,layout1);
      }
    });


    next.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {

        /*MyService mSensorService = new MyService();
        Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        startService(mServiceIntent);*/

        String speciTime = speci_time.getText().toString().trim();
        input_date = form_date.getText().toString().trim();
        l_height1 = edl_height1.getText().toString().trim();
        l_height2 = edl_height2.getText().toString().trim();
        l_height3 = edl_height3.getText().toString().trim();

        //Toast.makeText(getApplicationContext(),l_clcode3,Toast.LENGTH_SHORT).show();
        if (time_category.equals("speci")){
          input_time = speciTime;
        }
        else{input_time = input_normalTime; }

        if (time_category.equals("speci") && speciTime.isEmpty()){
          Toast.makeText(getApplicationContext(),"Please Enter Speci Time",Toast.LENGTH_SHORT).show();

        }else {
          crossfade(layout1,layout2);
        }

      }
    });

    normal_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String tm =  parent.getItemAtPosition(position).toString();

        String mydate = form_date.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
          Format f = new SimpleDateFormat("dd-MM-yyyy");
          String current_time = f.format(new Date());
          Date selectedDate = sdf.parse(mydate);
          Date today = sdf.parse(current_time);
          if (position>zulu_time_index() && !selectedDate.before(today)){
            normal_time.setSelection(zulu_time_index());
            Toast.makeText(getApplicationContext(),"wrong time selected",Toast.LENGTH_SHORT).show();
            input_normalTime = tm;
          }
          else {
            input_normalTime = tm;
          }

        } catch (ParseException e) {
          e.printStackTrace();
        }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spl_clcode3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_clcode3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spl_clcode2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_clcode2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spl_clcode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_clcode1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spl_oktas3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_oktas3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spl_oktas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_oktas2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spl_oktas1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_oktas1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spl_type3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_type3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spl_type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_type2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spl_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        l_type1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    sptotal_low_clouds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        total_low_clouds = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sptotal_all_clouds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        total_all_clouds = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });


    max_degree_cel.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0){
          double cel = Double.parseDouble(s.toString());
          max_degree_far.setText(fahrenheitConverter(cel)+"");
          maxvGfaran.setVisibility(View.VISIBLE);
        }else {
          maxvGfaran.setVisibility(View.GONE);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });
    degree_cel.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {

      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() != 0){
          double cel = Double.parseDouble(s.toString());
          min_degree_far.setText(fahrenheitConverter(cel)+"");
          vGferan.setVisibility(View.VISIBLE);
        }else {
          vGferan.setVisibility(View.GONE);
        }

      }

      @Override
      public void afterTextChanged(Editable s) {

      }
    });

    next_page5.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        input_attd_thermo = attd_thermo.getText().toString().trim();
        input_correction = correction.getText().toString().trim();
        input_pr_as_read = pr_as_read.getText().toString().trim();
        input_clp = clp.getText().toString().trim();
        input_min_degree_cel = degree_cel.getText().toString().trim();
        input_min_degree_far = min_degree_far.getText().toString().trim();
        input_max_degree_cel = max_degree_cel.getText().toString().trim();
        input_max_degree_far = max_degree_far.getText().toString().trim();
        input_mslp = mslp.getText().toString().trim();
        input_time_marks_b = time_marks_b.getText().toString().trim();
        input_time_marks_a = time_marks_a.getText().toString().trim();
        input_other_tmarks = other_tmarks.getText().toString().trim();
        input_remarks = remarks.getText().toString().trim();

        //Toast.makeText(getApplicationContext(),input_remarks,Toast.LENGTH_SHORT).show();

        crossfade(layout4,layout5);
      }
    });

    back_page4.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){
        crossfade(layout5,layout4);
      }
    });
    next_page4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        input_cloud_searchlight = cloud_searchlight.getText().toString().trim();
        input_rainfall_mm = rainfall_mm.getText().toString().trim();
        input_present_weather_code = present_weather_code.getText().toString().trim();
        input_past_weather = past_weather.getText().toString().trim();
        input_past_weather_code = past_weather_code.getText().toString().trim();
        input_visibility = visibility.getText().toString().trim();
        input_gusting = gusting.getText().toString().trim();
        input_wind_direction = wind_direction.getText().toString().trim();
        input_wind_speed = wind_speed.getText().toString().trim();
        input_sun_duration = sun_duration.getText().toString().trim();
        input_dry_bulb = dry_bulb.getText().toString().trim();
        input_wet_bulb = wet_bulb.getText().toString().trim();

        //Toast.makeText(getApplicationContext(),input_wet_bulb,Toast.LENGTH_SHORT).show();

        crossfade(layout3,layout4);
      }
    });

    sppresent_weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        input_present_weather = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    back_page3.setOnClickListener(new View.OnClickListener(){
      public  void onClick(View v){
        crossfade(layout4,layout3);
      }
    });

    next_page3.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        m_height1 = edm_height1.getText().toString().trim();
        m_height2 = edm_height2.getText().toString().trim();
        m_height3 = edm_height3.getText().toString().trim();

        h_height1 = edh_height1.getText().toString().trim();
        h_height2 = edh_height2.getText().toString().trim();
        h_height3 = edh_height3.getText().toString().trim();

        //Toast.makeText(getApplicationContext(),h_clcode3,Toast.LENGTH_SHORT).show();

        crossfade(layout2,layout3);
      }
    });


    sph_clcode3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_clcode3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    sph_clcode2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_clcode2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sph_clcode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_clcode1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sph_oktas3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_oktas3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sph_oktas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_oktas2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sph_oktas1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_oktas1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    sph_type3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_type3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    sph_type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_type2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    sph_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        h_type1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });




    spm_clcode3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_clcode3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spm_clcode2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_clcode2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spm_clcode1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_clcode1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spm_oktas3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_oktas3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spm_oktas2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_oktas2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spm_oktas1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_oktas1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spm_type3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_type3 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spm_type2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_type2 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spm_type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        m_type1 = parent.getItemAtPosition(position).toString();

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    back_page2.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View v) {
        crossfade(layout3,layout2);
      }
    });
    speci_time.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Toast.makeText(getApplicationContext(),"speci time",Toast.LENGTH_SHORT).show();
        Calendar mcurent_time = Calendar.getInstance();
        int hour = mcurent_time.get(Calendar.HOUR_OF_DAY);
        int min = mcurent_time.get(Calendar.MINUTE);

        timeDialog =  new TimePickerDialog(Observationslip.this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener(){
          public void onTimeSet(TimePicker tm,int selectedHour, int selectedMin){

            DateFormat format = new SimpleDateFormat("HH:mm");
            try {
              /*Format f = new SimpleDateFormat("dd-MM-yyyy");
              String current_time = f.format(new Date());
              Date selectedDate = sdf.parse(mydate);
              Date today = sdf.parse(current_time);*/
              Date date = format.parse(selectedHour+":"+selectedMin);
              String zulu_time= format.format(date);
              speci_time.setText(zulu_time+"Z");
            } catch (ParseException e) {
              e.printStackTrace();
            }

          }
        },hour,min,true);
        timeDialog.show();
      }
    });

    form_date.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Observationslip.this,R.style.datepicker,
          new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
              //int m = month + 1;
              SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
              Calendar newDate = Calendar.getInstance();
              newDate.set(year,month,day);
              String dd = sdf.format(newDate.getTime());
              form_date.setText(dd);
              normal_time.setSelection(zulu_time_index());
            }
          }, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
      }
    });

    spTime_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String time_type = parent.getItemAtPosition(position).toString();
        //Toast.makeText(getApplicationContext(),time_type,Toast.LENGTH_SHORT).show();
        if(time_type.equals("speci")){
          time_category = "speci";
          vGtime_normal.setVisibility(View.GONE);
          vGtime_speci.setVisibility(View.VISIBLE);
        }
        if(time_type.equals("normal")){
          time_category = "normal";
          vGtime_normal.setVisibility(View.VISIBLE);
          vGtime_speci.setVisibility(View.GONE);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });






  }

  private Map<String, String> parameters() {
    Map<String, String> params = new HashMap<String, String>();
    params.put("Date",input_date);  params.put("TIME",input_time); params.put("TotalAmountOfAllClouds",total_all_clouds);
    params.put("station_name",input_station_name); params.put("station_number",input_station_number);
    params.put("TotalAmountOfLowClouds",total_low_clouds); params.put("TypeOfLowClouds1",l_type1);
    params.put("OktasOfLowClouds1",l_oktas1); params.put("HeightOfLowClouds1",l_height1);
    params.put("CLCODEOfLowClouds1",l_clcode1); params.put("TypeOfLowClouds2",l_type2);
    params.put("OktasOfLowClouds2",l_oktas2); params.put("HeightOfLowClouds2",l_height2);
    params.put("CLCODEOfLowClouds2",l_clcode2); params.put("TypeOfLowClouds3",l_type3);
    params.put("OktasOfLowClouds3",l_oktas3); params.put("HeightOfLowClouds3",l_height3); params.put("CLCODEOfLowClouds3",l_clcode3);

    params.put("TypeOfMediumClouds1",m_type1); params.put("OktasOfMediumClouds1",m_oktas1);
    params.put("HeightOfMediumClouds1",m_height1); params.put("CLCODEOfMediumClouds1",m_clcode1);
    params.put("TypeOfMediumClouds2",m_type2); params.put("OktasOfMediumClouds2",m_oktas2);
    params.put("HeightOfMediumClouds2",m_height2); params.put("CLCODEOfMediumClouds2",m_clcode2);
    params.put("TypeOfMediumClouds3",m_type3); params.put("OktasOfMediumClouds3",m_oktas3);
    params.put("HeightOfMediumClouds3",m_height3); params.put("CLCODEOfMediumClouds3",m_clcode3);

    params.put("TypeOfHighClouds1",h_type1); params.put("OktasOfHighClouds1",h_oktas1);
    params.put("HeightOfHighClouds1",h_height1); params.put("CLCODEOfHighClouds1",h_clcode1);
    params.put("TypeOfHighClouds2",h_type2); params.put("OktasOfHighClouds2",h_oktas2);
    params.put("HeightOfHighClouds2",h_height2); params.put("CLCODEOfHighClouds2",h_clcode2);
    params.put("TypeOfHighClouds3",h_type3); params.put("OktasOfHighClouds3",h_oktas3);
    params.put("HeightOfHighClouds3",h_height3); params.put("CLCODEOfHighClouds3",h_clcode3);

    params.put("CloudSearchLightReading",input_cloud_searchlight); params.put("Rainfall",input_rainfall_mm);
    params.put("Dry_Bulb",input_dry_bulb); params.put("Wet_Bulb",input_wet_bulb);
    params.put("Present_Weather",input_present_weather); params.put("Present_WeatherCode",input_present_weather_code);
    params.put("Past_Weather",input_past_weather);params.put("Past_Weather_code",input_past_weather_code);
    params.put("Visibility",input_visibility);
    params.put("Wind_Direction",input_wind_direction); params.put("Wind_Speed",input_wind_speed);
    params.put("Gusting",input_gusting); params.put("AttdThermo",input_attd_thermo);
    params.put("PrAsRead",input_pr_as_read); params.put("Correction",input_correction);
    params.put("CLP",input_clp); params.put("MSLPr",input_mslp);

    params.put("TimeMarksBarograph",input_time_marks_b); params.put("TimeMarksAnemograph",input_time_marks_a);
    params.put("OtherTMarks",input_other_tmarks); params.put("Remarks",input_remarks);
    params.put("O_SubmittedBy",submitted_by); params.put("sunduration",input_sun_duration);
    params.put("Max_temp",input_max_degree_cel); params.put("Min_temp",input_min_degree_cel);
    params.put("speciormetar",time_category); params.put("UnitOfWindSpeed",input_spunit_wind);
    params.put("IndOrOmissionOfPrecipitation",input_spind_omission); params.put("TypeOfStation_Present_Past_Weather",input_type_station);
    params.put("HeightOfLowestCloud",input_height_lowest); params.put("StandardIsobaricSurface",input_standard_isobaric);
    params.put("DurationOfPeriodOfPrecipitation",input_precipitation); params.put("GrassMinTemp",input_grass_min);
    params.put("CI_OfPrecipitation",input_character); params.put("BE_OfPrecipitation",input_beginning);
    params.put("IndicatorOfTypeOfIntrumentation",input_indicator); params.put("SignOfPressureChange",input_sign_pressure);
    params.put("Supp_Info",input_suplimentary); params.put("VapourPressure",input_vapor_pressure);
    params.put("T_H_Graph",input_th_graph); params.put("Userid",userid);

    return params;
  }


  public void crossfade(View from,View to){

    Animation animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
    from.startAnimation(animFadeOut);
    from.setVisibility(View.GONE);
    to.setVisibility(View.VISIBLE);
    Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
    to.startAnimation(animSlideDown);
  }


  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }


  protected void onResume() {

    super.onResume();
    // Toast.makeText(getApplicationContext(),"Resumed observation",Toast.LENGTH_SHORT).show();
  }

  protected void onPause() {

    super.onPause();
    pDialog.dismiss();
    PendingFragment p= new PendingFragment();
    p.stopcheck();
    //timerAsync.cancel();
    //timerTaskAsync.cancel();
   // Toast.makeText(getApplicationContext(),"paused observation",Toast.LENGTH_SHORT).show();
  }

  protected void onStart() {

    super.onStart();
    //Toast.makeText(getApplicationContext(),"Started here...",Toast.LENGTH_SHORT).show();
  }


  private static long exitTime;
  @Override
  public void onBackPressed() {
    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
         if (exitTime + 2000 > System.currentTimeMillis()){
          super.onBackPressed();
        }else {
          Toast.makeText(getBaseContext(),"press again to Exit",Toast.LENGTH_SHORT).show();
          exitTime =System.currentTimeMillis();
        }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.example, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      //logoutUser();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.form) {
      /*finish();
      Intent intent = new Intent(Observationslip.this,Observationslip.class);
      startActivity(intent);*/

    } else if (id == R.id.rPending) {

      loadFragment();

    }else if (id == R.id.logout){
      logoutUser();
    }

    DrawerLayout drawer = findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }


  private void logoutUser() {
    session.setLogin(false);
    db.deleteUsers();

    // Launching the login activity
    Intent intent = new Intent(Observationslip.this, LoginActivity.class);
    startActivity(intent);
    finish();
  }

}
