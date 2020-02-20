package com.linetv.demo.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DramasTable {

    @Id
    private Long  drama_id;

    private String name;
    private int total_views;
    private String created_at;
    private String thumb_url;  //URL
    private String pic_name;   //實際圖片檔名
    private double rating;
    @Keep
    public DramasTable(Long drama_id, String name, int total_views, String created_at,
                       String thumb, double rating) {
        this.drama_id = drama_id;
        this.name = name;
        this.total_views = total_views;
        this.created_at = created_at;
        this.rating = rating;
    }
    public DramasTable() {
    }
    @Generated(hash = 1511819149)
    public DramasTable(Long drama_id, String name, int total_views, String created_at,
            String thumb_url, String pic_name, double rating) {
        this.drama_id = drama_id;
        this.name = name;
        this.total_views = total_views;
        this.created_at = created_at;
        this.thumb_url = thumb_url;
        this.pic_name = pic_name;
        this.rating = rating;
    }
    public Long  getDrama_id() {
        return this.drama_id;
    }
    public void setDrama_id(Long drama_id) {
        this.drama_id = drama_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTotal_views() {
        return this.total_views;
    }
    public void setTotal_views(int total_views) {
        this.total_views = total_views;
    }
    public String getCreated_at() {
        return this.created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public double getRating() {
        return this.rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getThumb_url() {
        return this.thumb_url;
    }
    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
    public String getPic_name() {
        return this.pic_name;
    }
    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }
}
