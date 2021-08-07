package com.example.testapplication;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton mySingleTon;
    private RequestQueue requestQueue;
    private Context context;
    private VolleySingleton(Context context){
        this.context=context;
        this.requestQueue=getRequestQueue();

    }
    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized VolleySingleton getInstance(Context context){
        if (mySingleTon==null){
            mySingleTon=new VolleySingleton(context);
        }
        return mySingleTon;
    }
    public<T> void addToRequestQue(Request<T> request){
        getRequestQueue().add(request);

    }
}
