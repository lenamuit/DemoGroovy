package com.namlh.demogroovy

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import com.squareup.okhttp.OkHttpClient
import retrofit.RestAdapter
import retrofit.client.OkClient

class BaseActivity extends ActionBarActivity{

    TinhteService tinhteService

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)
        RestAdapter restAdapter = new RestAdapter.Builder()
                                .setClient(new OkClient(new OkHttpClient()))
                                .setEndpoint("http://tinhte-api.herokuapp.com")
                                .build()

        tinhteService = restAdapter.create(TinhteService.class)


    }
}