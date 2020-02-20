package com.linetv.demo.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.linetv.demo.R;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.network.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Util {
    private static String imagePath;

    public static void checkNetWork(Context context, RecyclerView listView,LinearLayout parentView, List<DramasTable> records){
        if(Util.isNetworkAvailable(context) || records.size() >0){
            parentView.findViewById(R.id.net_status).setVisibility(View.GONE);
            parentView.findViewById(R.id.txtNotFound).setVisibility(View.GONE);
        }else{
            parentView.findViewById(R.id.net_status).setVisibility(View.VISIBLE);
        }
        parentView.findViewById(R.id.net_status).findViewById(R.id.buttonRetry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MainActivity.net_status:Onclick()");
                Util.load(context,listView,parentView,true);
            }
        });
    }

    public static void load(Context context, RecyclerView listView,LinearLayout parentView,boolean reload){
        Data data = new Data(reload,listView,parentView);
        data.getData((Activity) context);
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null &&
                networkInfo.isConnected();
    }

    public static void savePic(Context mc,Bitmap bm,String filename) {
        File fileDir = mc.getFilesDir();
        imagePath=fileDir+"/";
        try{
            File imageFile=new File(imagePath + filename);
            FileOutputStream imageFout = mc.openFileOutput(imageFile.getName(), Context.MODE_PRIVATE);
            bm.compress(Bitmap.CompressFormat.JPEG,100,imageFout);
            imageFout.close();
        }catch(IOException e){
            Log.e("panel","IOEception",e);
        }
    }

    public static Bitmap getPic(Context mc,String filename) {
        File fileDir = mc.getFilesDir();
        imagePath=fileDir+"/";
        String filePath = imagePath + filename;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        return bitmap;
    }

}
