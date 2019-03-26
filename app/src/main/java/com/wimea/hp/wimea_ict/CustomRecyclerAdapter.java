package com.wimea.hp.wimea_ict;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import static com.wimea.hp.wimea_ict.PendingFragment.TAG;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {

  private Context context;
  private List<PendingUtils> pendingUtils;
  private FragmentManager fm;
  private Observationslip slip;

  public CustomRecyclerAdapter(Context context, List personUtils,FragmentManager fm,Observationslip slip) {
    this.context = context;
    this.pendingUtils = personUtils;
    this.fm = fm;
    this.slip = slip;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
    ViewHolder viewHolder = new ViewHolder(v);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.itemView.setTag(pendingUtils.get(position));
    holder.btn.setTag(pendingUtils.get(position));
    holder.edit.setTag(pendingUtils.get(position));

    PendingUtils pu = pendingUtils.get(position);

    holder.rCategory.setText(pu.getCategory());
    holder.rDate.setText(pu.getDate());
    holder.recordTime.setText(pu.getTime());

  }

  @Override
  public int getItemCount() {
    return pendingUtils.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView rCategory;
    public TextView rDate;
    public TextView recordTime;
    public Button btn,edit;
    private ProgressBar bar;

    public ViewHolder(View itemView) {
      super(itemView);

      rCategory = itemView.findViewById(R.id.tvCategory);
      recordTime = itemView.findViewById(R.id.tvTime);
      rDate = itemView.findViewById(R.id.tvDate);
      btn = itemView.findViewById(R.id.btn);
      edit = itemView.findViewById(R.id.edit);

      btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          //Toast.makeText(v.getContext(),"hey hey hey", Toast.LENGTH_SHORT).show();
          PendingUtils cpu = (PendingUtils) v.getTag();

          setDialog(cpu.getTime(),cpu.getCategory(),cpu.getDate());
          //Toast.makeText(v.getContext(), cpu.getTime()+" "+ cpu.getCategory()+" "+ cpu.getDate()+" is still pending", Toast.LENGTH_SHORT).show();
        }
      });

      edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          PendingUtils cpu = (PendingUtils) v.getTag();
          new ProgressTask().execute(cpu.getTime(),cpu.getDate(),cpu.getCategory());
        }
      });

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

          /*PendingUtils cpu = (PendingUtils) view.getTag();

          Toast.makeText(view.getContext(), cpu.getTime()+" "+ cpu.getDate()+" is still pending", Toast.LENGTH_SHORT).show();*/

        }
      });

    }

    private class ProgressTask extends AsyncTask<String,Object,Object> {

      android.app.FragmentTransaction ft;
      Bundle utils = new Bundle();
      @Override
      protected void onPreExecute(){
        //bar.setVisibility(View.VISIBLE);
        Bar bar = new Bar();
        android.app.FragmentTransaction ft = fm.beginTransaction();
        bar.show(ft,"bar");
      }

      @Override
      protected void onPostExecute(Object o) {
        Fragment fragment = fm.findFragmentByTag("bar");
        if (fragment != null){
          fm.beginTransaction().remove(fragment).commit();
        }
        /*Fragment fragment1 = fm.findFragmentByTag("pending");
        if (fragment1 != null){
          fm.beginTransaction().remove(fragment1).commit();
        }*/

        //context.reset();
      }

      @Override
      protected Object doInBackground(String... cpu) {


        try {
          Thread.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        /*ed = new EditFragment();
         ft = fm.beginTransaction();
        //new ProgressTask().execute(cpu.getTime(),cpu.getDate(),cpu.getCategory());
         utils.putString("time",cpu[0]);
        utils.putString("date",cpu[1]);
        utils.putString("category",cpu[2]);
        ed.setArguments(utils);
        ed.show(ft,"edit_fragment");*/
        slip.reset();

        return null;
      }
    }



    private void setDialog(final String time, final String category, final String date){
      final SQLiteHandler db = new SQLiteHandler(context);
      HashMap<String, String> user = db.getUserDetails();
      final String userid = user.get("uid");

      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
      alertDialogBuilder.setMessage("Are you sure you want to Delete this form?");
      alertDialogBuilder.setCancelable(false);
      alertDialogBuilder.setPositiveButton("yes",
        new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface arg0, int arg1) {
            db.deleteSlip(date,userid,time,category);
            Toast.makeText(context,"Deleted Successfully", Toast.LENGTH_SHORT).show();
          }
        });

      alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          dialog.cancel();
        }
      });

      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();
    }
  }


}
