package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.testapplication.databinding.ActivityMainBinding;
import com.example.testapplication.databinding.ActivityProductListBinding;

import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ActivityProductListBinding productListBinding;
    private ProductViewModel productViewModel;
    RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productListBinding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(productListBinding.getRoot());
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productsRecyclerView = productListBinding.productsRecyclerView;

        ProductsListAdapter productsListAdapter;


        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        productsListAdapter = new ProductsListAdapter(this);




        productViewModel.getProductsList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                productsListAdapter.setList(productModels);
                productsRecyclerView.setAdapter(productsListAdapter);
            }
        });







    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }
}