package com.wimea.hp.wimea_ict;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Bar extends DialogFragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
    setCancelable(false);
    return inflater.inflate(R.layout.loading,container);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(PendingFragment.STYLE_NORMAL, R.style.loadingbar);
    //test = getArguments().getString("test");
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    //getDialog().getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
  }
}
