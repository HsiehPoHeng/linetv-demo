package com.linetv.demo.data;

import com.f2prateek.rx.preferences2.Preference;
import com.linetv.demo.DramasSampleApplication;
import com.linetv.demo.util.GsonPreferenceAdapter;

import java.util.List;

public class getDramasList {

    private static final String PREFIX = getDramasList.class.getSimpleName();
    public static Preference<DramasList> tmpDramas = DramasSampleApplication.getRxPreferences().getObject(PREFIX+".tmpDramas", null,new GsonPreferenceAdapter(DramasList.class));

    private List<DramasList> data;

    public List<DramasList> getData() {
        return data;
    }

    public void setData(List<DramasList> data) {
        this.data = data;
    }

    public static class DramasList{
        /**
         * drama_id : 1
         * name : 致我們單純的小美好
         * total_views : 23562274
         * created_at : 2017-11-23T02:04:39.000Z
         * thumb : https://i.pinimg.com/originals/61/d4/be/61d4be8bfc29ab2b6d5cab02f72e8e3b.jpg
         * rating : 4.4526
         */

        private Long  drama_id;
        private String name;
        private int total_views;
        private String created_at;
        private String thumb;
        private double rating;

        public Long  getDrama_id() {
            return drama_id;
        }

        public void setDrama_id(Long drama_id) {
            this.drama_id = drama_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getTotal_views() {
            return total_views;
        }

        public void setTotal_views(int total_views) {
            this.total_views = total_views;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }
    }
}
