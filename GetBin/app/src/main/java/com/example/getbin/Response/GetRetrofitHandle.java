package com.example.getbin.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class GetRetrofitHandle {

  public static final String BASE_URL = "https://lookup.binlist.net/";

    private static Retrofit retrofit=null;

    public static Retrofit GetRetrofitHandle()
    {
        if (retrofit==null)
        {

            OkHttpClient.Builder builder= new OkHttpClient.Builder();
            HttpLoggingInterceptor interceptor= new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            // builder.addInterceptor(new ResponseInterceptor());
            builder.addInterceptor(interceptor);
            Gson gson=new GsonBuilder().serializeNulls().create();
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();

        }
        return retrofit;

    }


}

