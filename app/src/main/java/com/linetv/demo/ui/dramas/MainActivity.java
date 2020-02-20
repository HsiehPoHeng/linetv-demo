package com.linetv.demo.ui.dramas;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.linetv.demo.R;
import com.linetv.demo.data.DataCRUD;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.ui.BaseActivity;
import com.linetv.demo.util.RecyclerViewAdapter;
import com.linetv.demo.util.Util;

import java.util.List;

import static com.linetv.demo.data.getDramasList.tmpDramas;

public class MainActivity extends BaseActivity {

    private MainActivity pthis;
    private RecyclerView listView;
    private SearchView searchView;
    private DataCRUD crud;
    public RecyclerViewAdapter listAdapter;
    private TextView txtNotFound;
    private LinearLayout net_status;
    private LinearLayout parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pthis = this;
        init();
        Util.load(pthis,listView,parentView,false);
    }

    private void init(){
        parentView = findViewById(R.id.parentView);
        listView = findViewById(R.id.listView);
        txtNotFound = findViewById(R.id.txtNotFound);
        net_status = findViewById(R.id.net_status);
        searchView = findViewById(R.id.searchView);
        crud = new DataCRUD(App);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(tmpDramas.get() != null){
            //第二次之後
            searchView.setQueryHint("搜尋紀錄:"+tmpDramas.get().getName());
            searchView.setQuery(tmpDramas.get().getName(),false);
            if(!checkNotFound()){
                listAdapter = new RecyclerViewAdapter(pthis,crud.selectRecord(tmpDramas.get().getName()));
                listAdapter.setCallback(new Callback() {
                    @Override
                    public void onEmptyViewRetryClick() {
                        Util.load(pthis,listView,parentView,true);
                    }
                });
            }
        }else{
            //初始
            searchView.setQueryHint("劇名相關字");
            if(!checkNotFound()){
                listAdapter = new RecyclerViewAdapter(pthis,crud.selectRecord(false));
                listAdapter.setCallback(new Callback() {
                    @Override
                    public void onEmptyViewRetryClick() {
                        Util.load(pthis,listView,parentView,true);
                    }
                });
            }
        }

        listView.setAdapter(listAdapter);
        listView.setLayoutManager(new LinearLayoutManager(pthis));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return setAdaper();
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return setAdaper();
            }
            boolean setAdaper(){
                RecyclerViewAdapter listAdapter;
                if("".equals(String.valueOf(searchView.getQuery()))){
                    listAdapter = new RecyclerViewAdapter(pthis,crud.selectRecord(true));
                }else {
                    listAdapter = new RecyclerViewAdapter(pthis,crud.selectRecord(String.valueOf(searchView.getQuery())));
                }
                listAdapter.setCallback(new Callback() {
                    @Override
                    public void onEmptyViewRetryClick() {
                        Util.load(pthis,listView,parentView,true);
                    }
                });
                listView.setAdapter(listAdapter);
                listView.setLayoutManager(new LinearLayoutManager(pthis));
                return false;
            }
        });
    }

    private boolean checkNotFound() {
        List<DramasTable> records = crud.selectRecord(false);
        Util.checkNetWork(pthis,listView,parentView,records);
        if (records.size() > 0) {
            txtNotFound.setVisibility(View.GONE);
            return false;
        } else {
            txtNotFound.setVisibility(View.VISIBLE);
            return true;
        }
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }
}
