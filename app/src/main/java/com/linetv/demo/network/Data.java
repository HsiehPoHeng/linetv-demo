package com.linetv.demo.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linetv.demo.DramasSampleApplication;
import com.linetv.demo.data.DataCRUD;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.data.getDramasList;
import com.linetv.demo.ui.dramas.MainActivity;
import com.linetv.demo.util.RecyclerViewAdapter;
import com.linetv.demo.util.Util;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Data {
    private final Retrofit retrofit;
    public LinearLayout parentView;
    private DramasSampleApplication App;
    public final boolean reload;
    public RecyclerView listView;

    public Data(boolean reload, RecyclerView listView, LinearLayout parentView) {
        this.reload = reload;
        this.listView = listView;
        this.parentView = parentView;
        this.retrofit = new ApiClient().getRetrofit();
    }

    public void getData(Activity pthis) {
        App = ((DramasSampleApplication)pthis.getApplicationContext());

        ApiService apiService = retrofit.create(ApiService.class);
        Call<getDramasList> call = apiService.getDramasList();
        call.enqueue(new Callback<getDramasList>() {
            @Override
            public void onResponse(Call<getDramasList> call, Response<getDramasList> response) {
                if(response.isSuccessful()){
                    getDramasList rsObj = response.body();
                    if(rsObj != null && rsObj.getData() != null){
                        DataCRUD crud = new DataCRUD(App);

                        crud.deleteData();
                        crud.insertData(rsObj.getData());

                        if(reload){
                            List<DramasTable> records = crud.selectRecord(false);
                            Util.checkNetWork(pthis,listView,parentView,records);
                            RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(pthis, crud.selectRecord(true));
                            listAdapter.setCallback(new MainActivity.Callback() {
                                @Override
                                public void onEmptyViewRetryClick() {
                                    Util.load(pthis,listView,parentView,true);
                                }
                            });
                            listView.setAdapter(listAdapter);
                            listView.setLayoutManager(new LinearLayoutManager(pthis));
                        }
                    }else{
                        onFailure(call,new SocketTimeoutException());
                        return;
                    }
                }else{
                    onFailure(call,new SocketTimeoutException());
                    return;
                }
            }

            @Override
            public void onFailure(Call<getDramasList> call, Throwable t) {
                System.out.println(t+"");
                if(reload){
                    AlertDialog.Builder alertDialog = null;
                    if (alertDialog == null) {
                        alertDialog = new AlertDialog.Builder(pthis);
                    }
                    alertDialog.setTitle("連線異常");
                    alertDialog.setMessage(t.toString());
                    alertDialog.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }
        });
    }
}
