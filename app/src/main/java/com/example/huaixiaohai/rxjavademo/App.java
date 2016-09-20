package com.example.huaixiaohai.rxjavademo;

import android.app.Application;

import com.example.huaixiaohai.rxjavademo.service.GankService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huaixiaohai on 16/9/20.
 */
public class App extends Application {

    private static App instance;
    private static Retrofit retrofit;
    private static GankService service;

    public static App getInstance() {
        if(instance == null) {
            instance = new App();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public GankService getGankService() {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://gank.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        if(service == null) {
            service = retrofit.create(GankService.class);
        }
        return service;
    }
}
