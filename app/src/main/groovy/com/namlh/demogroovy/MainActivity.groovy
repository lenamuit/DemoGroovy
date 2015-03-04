package com.namlh.demogroovy

import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.util.Log
import android.widget.TextView
import com.arasthel.swissknife.SwissKnife
import com.arasthel.swissknife.annotations.InjectView
import com.arasthel.swissknife.annotations.OnClick
import rx.android.observables.AndroidObservable

class MainActivity extends BaseActivity {

    @InjectView(R.id.hello_world)
    TextView tvHelloWorld;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState)

        contentView = R.layout.main_activity
        SwissKnife.inject(this)
        SwissKnife.restoreState(this,savedInstanceState)

        AndroidObservable.bindActivity(this,tinhteService.getList())
            .flatMap({rx.Observable.from(it.getPosts())})
            .subscribe({
                Log.i("getdata",it.title)
        })


    }

    @OnClick(R.id.hello_world)
    public void onClick() {
        tvHelloWorld.text = "clicked"
    }
}