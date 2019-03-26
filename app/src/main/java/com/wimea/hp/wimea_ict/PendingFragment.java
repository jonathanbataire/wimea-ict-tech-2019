package com.wimea.hp.wimea_ict;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PendingFragment extends android.app.DialogFragment {
  public static String TAG = "FullScreenDialog";
  RecyclerView recyclerView;
  TextView emptyMsg ;
  RecyclerView.Adapter mAdapter;
  RecyclerView.LayoutManager layoutManager;
  String test;

  List<PendingUtils> pendingUtilsList;
  private static Timer timerAsync;
  private static TimerTask timerTaskAsync;
  static int c = 0;
  static Observationslip slip;

  public static void activityRef(Observationslip ob){
    slip = ob;
  }
  public  void startCheck() {

    final Handler handler = new Handler();
    timerAsync = new Timer();
    timerTaskAsync = new TimerTask() {
      @Override
      public void run() {
        handler.post(new Runnable() {
          public void run() {
            c++;
            try {



              layoutManager = new LinearLayoutManager(getActivity());

              recyclerView.setLayoutManager(layoutManager);

              pendingUtilsList = new ArrayList<>();
              SQLiteHandler db = new SQLiteHandler(getActivity());
              if (db.numberOfRecords()>0){
                //Adding Data into ArrayList
                emptyMsg.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                pendingUtilsList = db.pendingRecords(pendingUtilsList);
              }
              else {
                emptyMsg.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                stopcheck();
              }





              mAdapter = new CustomRecyclerAdapter(getActivity(), pendingUtilsList,getFragmentManager(),slip);

              recyclerView.setAdapter(mAdapter);

              //Toast.makeText(getActivity(),""+c,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
      }
    };
    timerAsync.schedule(timerTaskAsync,0, 1000);
  }

  public void dismissFrag(){
    dismiss();
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    stopcheck();
  }

  public void stopcheck(){
    if (timerTaskAsync != null){
      timerTaskAsync.cancel();
      //Toast.makeText(getActivity(),"stopped",Toast.LENGTH_SHORT).show();
      Log.d("FragDialogTimer","Stopped Timer");
    }
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(PendingFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    //test = getArguments().getString("test");
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.pending_fragment,container,false);
    android.support.v7.widget.Toolbar toolbar = view.findViewById(R.id.frag_toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_action_back);
    toolbar.setTitle("Pending Slip Forms");
    /*toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        return true;
      }
    });*/
    //toolbar.inflateMenu(R.menu.example);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });


    recyclerView = view.findViewById(R.id.recycleViewContainer);
    emptyMsg = view.findViewById(R.id.empty_list);
    recyclerView.setHasFixedSize(true);
    startCheck();


    return view;
  }



  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null){
      int width = ViewGroup.LayoutParams.MATCH_PARENT;
      int height = ViewGroup.LayoutParams.MATCH_PARENT;
      dialog.getWindow().setLayout(width,height);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        dialog.getWindow().setStatusBarColor(getResources().getColor(R.color.status));
      }
    }
  }
}
