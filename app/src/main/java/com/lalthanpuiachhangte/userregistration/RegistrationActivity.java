package com.lalthanpuiachhangte.userregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.entity.User;

public class RegistrationActivity extends AppCompatActivity {

    EditText username, password, email;
    User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.usernameET);
        password = findViewById(R.id.passwordET);
        email = findViewById(R.id.emailET);

        mUser = new User();

    }

    public void loginClick(View view) {

        String mUsername = username.getText().toString();
        String mPassword = password.getText().toString();
        String mEmail = email.getText().toString();

        mUser.setUserName(mUsername);
        mUser.setPassword(mPassword);
        mUser.setEmail(mEmail);
        mUser.setId(4);

        Log.i("TAGG",""+mUser.getUserName());

        String url = "http://10.180.243.20:8080/api/add";
        Ion.with(this)
                .load(url)
                .setJsonPojoBody(mUser)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                 ;
                    }
                });


    }

    public void memberLoginClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
