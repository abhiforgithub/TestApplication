package com.example.testapplication;

import org.json.JSONException;
import org.json.JSONObject;


public interface VolleyResponseLinserObject {
    void onResponse(JSONObject response) throws JSONException;

    void onError(String message, String title);
}
