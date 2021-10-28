package com.example.getbin.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BinInterface {

    @GET
    Call<CardInfo> getCardInfo(@Url String url);
}
