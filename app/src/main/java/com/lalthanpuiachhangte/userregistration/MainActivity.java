package com.lalthanpuiachhangte.userregistration;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;

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

        //CHECK THE SHARED PREFERENCE
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String shareUserId = sharedPreferences.getString("userId","");
        String sharedPassword = sharedPreferences.getString("password","");

        if(!shareUserId.equals("") && !sharedPassword.equals("")){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            Animatoo.animateSplit(getApplicationContext());  //fire the zoom animation
        }

    }

    public void loginClick(View view) {

        //withPassword(getApplicationContext());
        withoutPassword();

/*
  .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic bXktdHJ1c3RlZC1jbGllbnQ6c2VjcmV0")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "209ddc08-87fb-4f64-b9c5-dd24bc3b9ac1")*/
/*
        Intent intent = new Intent (this, HomeActivity.class);
        startActivity(intent);*/
    }



    public void registrationClick(View view) {

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        Animatoo.animateSplit(this);  //fire the zoom animation


    }

    public void forgotPasswordClick(View view) {
        Intent intent = new Intent (this, ForgotPasswordActivity.class);
        startActivity(intent);
        //startActivity(new Intent(this, ForgotPasswordActivity.class));
        Animatoo.animateSplit(this);  //fire the zoom animation
    }

    public void withoutPassword(){
        startActivity(new Intent(this,HomeActivity.class));
        Animatoo.animateSplit(this);
    }

    public void withPassword(Context context){
        final String mUsername = loginId.getText().toString();
        final String mPassword = password.getText().toString();
        //    Log.i("TAGG", ""+stand+ " "+asdf);

        String url = "http://10.180.243.20:8080/oauth/token?grant_type=password&username=admin&password=pass";
        // String url = "http://10.180.243.20:8080/oauth/token/";
        //String url = "http://10.180.243.20:8080/";


        try{
            Ion.with(this)
                    .load("POST",url)
                    .basicAuthentication(mUsername,mPassword)

                    .asString()

                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            Log.i("TAGG",result+"");
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                String access = jsonObject.getString("access_token");

                                Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_SHORT).show();

                                gotoPrivate(access, mUsername, mPassword);
                            }catch (JSONException err){
                                Log.d("Error", err.toString());
                                Toast.makeText(getApplicationContext(),"Invalid password or username!",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }catch (Exception e){ }
    }

    private void gotoPrivate(String access, final String userId, final String password) {

        String url = "http://10.180.243.20:8080/private?access_token="+access;
        Ion.with(this)
                .load(url)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do stuff with the result or error
                        Log.i("TAGG"," "+result);

                        prefEditor = sharedPreferences.edit();
                        prefEditor.putString("userId",userId);
                        prefEditor.putString("password",password);
                        prefEditor.commit();

                        Intent intent = new Intent (getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        Animatoo.animateSplit(getApplicationContext());  //fire the zoom animation
                    }
                });
    }
}
