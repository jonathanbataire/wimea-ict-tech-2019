package com.wimea.hp.wimea_ict;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

  private View layout1;
  private View layout2,layout3,layout4,layout5;
  Button back;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit);
    Toolbar toolbar = findViewById(R.id.edit_toolbar);
    setSupportActionBar(toolbar);
    setTitle("Edit Form");
  back = toolbar.findViewById(R.id.back_button);
    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(getApplicationContext(),"hey..",Toast.LENGTH_SHORT);
      }
    });
    layout1 = findViewById(R.id.layout1);
    layout2 = findViewById(R.id.layout2);
    layout3 = findViewById(R.id.layout3);
    layout4 = findViewById(R.id.layout4);
    layout5 = findViewById(R.id.layout5);

    layout2.setVisibility(View.GONE);
    layout3.setVisibility(View.GONE);
    layout4.setVisibility(View.GONE);
    layout5.setVisibility(View.GONE);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }
}
