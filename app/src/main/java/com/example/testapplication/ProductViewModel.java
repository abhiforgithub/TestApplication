package com.example.testapplication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    Context context;

    private MutableLiveData<List<ProductModel>> productsList;
    private ArrayList<ProductModel> localList;

    public ProductViewModel( Application application) {
        super(application);
        context = application.getApplicationContext();
        localList = new ArrayList<>();
    }

    LiveData<List<ProductModel>> getProductsList() {
        if (productsList == null) {
            productsList = new MutableLiveData<>();
            loadProducts();
        }
        return productsList;
    }

    private void loadProducts() {
        String url = "https://run.mocky.io/v3/bd26945d-228e-45b4-94a3-04c74f085e40";
        String msg="Loading...";
        new NetworkRequest(context.getApplicationContext()).volleyGetJsonObject(url, msg, new VolleyResponseLinserObject() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                localList.clear();
                JSONArray jsonArray = response.getJSONArray("products");
                for (int i =0;i< jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String price = jsonObject.getString("price");
                    String imageUrl = jsonObject.getString("imageUrl");
                    String description = jsonObject.getString("description");
                    localList.add(new ProductModel(title,price,imageUrl,description));

                    productsList.setValue(localList);
                }

            }

            @Override
            public void onError(String message, String title) {

            }
        });
    }




}
