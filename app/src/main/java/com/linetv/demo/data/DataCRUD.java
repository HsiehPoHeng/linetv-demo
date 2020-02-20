package com.linetv.demo.data;

import android.util.Log;

import com.linetv.demo.DramasSampleApplication;
import com.linetv.demo.data.db.DaoSession;
import com.linetv.demo.data.db.DramasTable;
import com.linetv.demo.data.db.DramasTableDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import static com.linetv.demo.data.getDramasList.tmpDramas;

public class DataCRUD {
    private final DramasSampleApplication app;
    private final DramasTableDao dramasTableDao;
    private final Database db;

    public DataCRUD(DramasSampleApplication mApp) {
        app = mApp;
        DaoSession daoSession =  mApp.getDaoSession();
        dramasTableDao = daoSession.getDramasTableDao();
        db = dramasTableDao.getDatabase();
    }
    public List<DramasTable> selectRecord(boolean save){
        if(save){
            saveLastSearch("");
        }
        QueryBuilder<DramasTable> qb = dramasTableDao.queryBuilder();
        return qb.orderAsc(DramasTableDao.Properties.Drama_id)
                .list();
    }

    public List<DramasTable> selectRecord(String Name){
        saveLastSearch(Name);
        QueryBuilder<DramasTable> qb = dramasTableDao.queryBuilder();
        return qb.where(DramasTableDao.Properties.Name.like("%" + Name + "%"))
                .orderAsc(DramasTableDao.Properties.Drama_id)
                .list();
    }

    //DramasDetailsActivity
    public DramasTable selectRecord(Long Drama_id){
        QueryBuilder<DramasTable> qb = dramasTableDao.queryBuilder();
        return qb.where(DramasTableDao.Properties.Drama_id.eq(Drama_id))
                .orderAsc(DramasTableDao.Properties.Drama_id)
                .distinct()
                .uniqueOrThrow();
    }

    public void deleteData(){
        dramasTableDao.deleteAll();
    }

    public void insertData(List<getDramasList.DramasList> data) {
        if(data==null){
            return;
        } else{
            for(getDramasList.DramasList item:data){
                DramasTable record = new DramasTable();
                record.setDrama_id(item.getDrama_id());
                record.setName(item.getName());
                record.setTotal_views(item.getTotal_views());
                record.setCreated_at(item.getCreated_at());
                record.setRating(item.getRating());
                record.setThumb_url(item.getThumb());
                record.setPic_name(item.getDrama_id() + ".jpg");
                try{
                    dramasTableDao.insert(record);
                }catch (Exception e) {
                    Log.e("ExceptionCRUD", e.toString());
                }
            }
        }
    }

    private void saveLastSearch(String Name){
        getDramasList.DramasList tmp = new getDramasList.DramasList();
        tmp.setName(Name);
        tmpDramas.set(tmp);
    }
}
