package com.lalthanpuiachhangte.userregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    MaterialEditText loginId, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        loginId = findViewById(R.id.loginId);
        password = findViewById(R.id.loginPassword);
    }

    public void loginClick(View view) {

        String username = loginId.getText().toString();
        String passwordd = password.getText().toString();
//    Log.i("TAGG", ""+stand+ " "+asdf);

     String url = "http://10.180.243.20:8080/oauth/token?grant_type=password&username=admin&password=pass";
       // String url = "http://10.180.243.20:8080/oauth/token/";
        //String url = "http://10.180.243.20:8080/";

        Ion.with(this)
                .load("POST",url)
                .basicAuthentication(username,passwordd)

                .asString()

                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        Log.i("TAGG",result+"");
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String access = jsonObject.getString("access_token");

                            gotoPrivate(access);

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                            Toast.makeText(getApplicationContext(),"Invalid password or username!",Toast.LENGTH_SHORT).show();
                        }
                    }


                });
/*
  .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "209ddc08-87fb-4f64-b9c5-dd24bc3b9ac1")*/
/*
        Intent intent = new Intent (this, HomeActivity.class);
        startActivity(intent);*/
    }

    private void gotoPrivate(String access) {

        String url = "http://10.180.243.20:8080/private?access_token="+access;

        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do stuff with the result or error
                        Log.i("TAGG"," "+result);

                     Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
                       startActivity(intent);
                    }
                });


    }

    public void registrationClick(View view) {

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);

    }

    public void forgotPasswordClick(View view) {
        Intent intent = new Intent (this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}


/*
/private?access_token=*/
