package com.linetv.demo.ui.dramas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.linetv.demo.R;
import com.linetv.demo.util.Util;

public class CommunicateActivity extends Activity {

    private CommunicateActivity pthis;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        pthis = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Util.isNetworkAvailable(pthis)){
            if(alertDialog == null) {
                alertDialog = new AlertDialog.Builder(pthis);
            }
            alertDialog.setTitle("網路狀態");
            alertDialog.setMessage("目前無網路，使用離線功能");
            alertDialog.setPositiveButton("繼續", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    init();
                    dialog.cancel();
                }
            });
            alertDialog.setNegativeButton("離開", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    dialog.dismiss();
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }else{
            init();
        }
    }
    private void init(){
        Intent intent = new Intent(pthis, MainActivity.class);
        pthis.startActivity(intent);
        pthis.finish();
    }
}
