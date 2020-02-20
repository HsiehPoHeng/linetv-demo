package com.linetv.demo.network;

import com.linetv.demo.data.getDramasList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("interview/dramas-sample.json")
    Call<getDramasList> getDramasList();
}
