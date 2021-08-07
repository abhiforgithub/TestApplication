package com.example.testapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class NetworkRequest {
    private Context context;
//    private ProgressDialog pDialog;
    private long mRequestStartTime;
    private String poorNetwork = "Your data connection is too slow – please try again when you have a better network connection";
    private String timeout = "Response timed out – please try again.";
    private String networkErrorTitle = "Network error";
    private String poorNetworkTitle = "Poor Network Connection";
    private String timeoutTitle = "Network Error";
    int MY_SOCKET_TIMEOUT_MS = 500000;

    public NetworkRequest(Context context) {
        this.context = context;
    }

    public void volleyGetJsonObject(final String url,String msg, final VolleyResponseLinserObject listener) {

               Log.e("Request", url);



//        pDialog = new ProgressDialog(act,R.style.ProgressDialogTheme);
//             pDialog.setMessage(msg);
//        pDialog.setCancelable(false);


        RequestQueue queue = makeRequestQueue(context);
        if (isOnline()) {

//            pDialog.show();
//            ProgressBar progressbar=(ProgressBar)pDialog.findViewById(android.R.id.progress);
//            int colorPrimaryDark = ThemeattrUtils.getThemeAttrColor(act, R.attr.colorPrimaryDark);
//            progressbar.getIndeterminateDrawable().setColorFilter(colorPrimaryDark, android.graphics.PorterDuff.Mode.SRC_IN);
            mRequestStartTime = System.currentTimeMillis();
            JsonObjectGETRequest_Old_Support jsonObjReq = new JsonObjectGETRequest_Old_Support(Request.Method.GET,
                    url,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                                         Log.e("JSONArray Response", response.toString());
                            //                        Log.e("response",response.toString());
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
                            try {
//                                pDialog.dismiss();
                                listener.onResponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //  VolleyLog.d("JSONArray VolleyError", "Error: " + error.getMessage());
//                    pDialog.dismiss();


                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    return headers;
                }
            };

            queue.add(jsonObjReq);

        }
    }

    public void volleyGetJsonObjectPOST(final String url, final JSONObject json, final String msg, final VolleyResponseLinserObject listener) {


//        pDialog = new ProgressDialog(context,R.style.ProgressDialogTheme);
        //    pDialog.setMessage(msg);
        //pDialog.set
//        pDialog.setCancelable(false);

        Log.e("json", json.toString());
        RequestQueue queue = makeRequestQueue(context);
        if (isOnline()) {
//            pDialog.show();
//            ProgressBar progressbar=(ProgressBar)pDialog.findViewById(android.R.id.progress);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                progressbar.getIndeterminateDrawable().setColorFilter(context.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            }
            mRequestStartTime = System.currentTimeMillis();
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url,json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("JSONArray Response", response.toString());
                            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;


                            try {
//                                pDialog.dismiss();
                                listener.onResponse(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    }








                    if (error instanceof TimeoutError) {
                        listener.onError(timeout, timeoutTitle);

                    } else if (error instanceof NoConnectionError) {
                        listener.onError(poorNetwork, poorNetworkTitle);

                    }
                }

            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Operating-System", "Android");

                    return headers;
                }
            };
            queue.add(jsonObjReq);

        }
    }

    private RequestQueue makeRequestQueue(Context act)
    {
        return  Volley.newRequestQueue(act);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(context, "No internet connection!", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
