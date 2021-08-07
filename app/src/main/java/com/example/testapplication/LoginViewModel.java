package com.example.testapplication;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginViewModel extends AndroidViewModel {

    String editTextUsername,password;
    @SuppressLint("StaticFieldLeak")
    Context context;


    private MutableLiveData<String> userDataList;

    public LoginViewModel(Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void setUserDetails(String editTextUsername, String password, Context context) {
        this.editTextUsername = editTextUsername;
        this.password = password;
        this.context = context;
    }

    LiveData<String> getUserData(String username, String password) {
        if (userDataList == null) {
            userDataList = new MutableLiveData<>();
            registerUserinServer(context,username,password);
//            registerUser(context);
        }
        return userDataList;
    }



    private void registerUserinServer(Context context, String username, String password) {
        String url = "http://54.251.37.65/TESTAPI/api/Users/UserAuthentication";
        JSONObject post_order = new JSONObject();

        String msg="Loading...";


        try {

            post_order.accumulate("UserID", username);
            post_order.accumulate("Password",password);




        } catch (JSONException e) {
            e.printStackTrace();
        }
        new NetworkRequest(context.getApplicationContext()).volleyGetJsonObjectPOST(url, post_order, msg, new VolleyResponseLinserObject() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                String Status = response.getString("Status");
                userDataList.setValue(Status);
            }

            @Override
            public void onError(String message, String title) {

            }
        });

    }


    private void registerUser(Context context) {
        String url = "http://54.251.37.65/TESTAPI/api/Users/UserAuthentication";

        StringRequest postRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", "onResponse: "+response );
                        try {
                            final JSONObject jsonObject = new JSONObject(response);
                            String Status = jsonObject.getString("Status");
                            userDataList.setValue(Status);
                            // Process your json here as required
                        } catch (JSONException e) {
                            // Handle json exception as needed
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("UserID", editTextUsername);
                params.put("Password",password);
                // Pass more params as needed in your rest API
                // Example you may want to pass user input from EditText as a parameter
                // editText.getText().toString().trim()
                return params;
            }
            @Override
            public String getBodyContentType() {
                // This is where you specify the content type
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        // This adds the request to the request queue
        VolleySingleton.getInstance(context)
                .addToRequestQue(postRequest);
    }







}
