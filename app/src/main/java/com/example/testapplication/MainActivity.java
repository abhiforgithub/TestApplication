package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.testapplication.databinding.ActivityMainBinding;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private LoginViewModel loginViewModel;
    EditText editTextUsername,editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editTextUsername = binding.editTextUsername;
        editTextPassword = binding.editTextPassword;

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);





        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(binding.editTextUsername.getText().toString())) {
                    binding.editTextUsername.setError("Enter User Name");
                    binding.editTextUsername.requestFocus();
                }
                else if (TextUtils.isEmpty(binding.editTextPassword.getText().toString())) {
                    binding.editTextPassword.setError("Enter a Password");
                    binding.editTextPassword.requestFocus();
                }
                else {

//                    String password = Base64.decode(binding.editTextPassword.getText().toString(), Base64.DEFAULT).toString();
                    loginViewModel.setUserDetails(binding.editTextUsername.toString(), binding.editTextPassword.getText().toString(), getApplicationContext());
                    String username = editTextUsername.getText().toString();
                    String password = editTextPassword.getText().toString();
                    Log.e("uuu", "onClick: "+username +","+password);
                    loginViewModel.getUserData(username,password).observe(MainActivity.this, userDataList -> {
                        if (userDataList.equals("false")){
                            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                            startActivity(intent);
                        }
                        // update UI

                    });
                }




            }
        });

    }
}