package com.namlh.demogroovy

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.util.Log
import android.widget.TextView
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

        TextView.metaClass.invokeMethod = { String name, args ->
            Log.e("NamLH","Invoke method $name")
            if (name == "hienthivanban"){
                name = "setText"
            }
            def validMethod = TextView.metaClass.getMetaMethod(name,args)
            if (validMethod){
                validMethod.invoke(delegate,args)
            }
        }

        TextView.metaClass.hienthivanban = { args ->
            delegate.text = args
        }
    }
}