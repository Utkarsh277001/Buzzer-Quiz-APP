package com.example.buzzer.AppCotroller;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppControl extends Application {

    private static AppControl instance;
    private RequestQueue reqQue;

    public static synchronized AppControl getInstance(){
        return instance;
    }
    public RequestQueue getRequestQueue(){
        if(reqQue==null){
            reqQue= Volley.newRequestQueue(getApplicationContext());
        }
        return reqQue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
        @Override
        public void onCreate() {
            super.onCreate();
            instance=this;
        }


}
