package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.entity.User;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class RegistrationActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText username, password, email, mobile;
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
        mobile = findViewById(R.id.phoneET);


        mUser = new User();

    }

    public void loginClick(View view) {

        String mUsername = username.getText().toString();
        String mPassword = password.getText().toString();
        String mEmail = email.getText().toString();
        String mMobile = mobile.getText().toString();

        mUser.setUserName(mUsername);
        mUser.setPassword(mPassword);
        mUser.setEmail(mEmail);
        mUser.setPhone(mMobile);

        //SHOW THE DIALOG BAR
        showProgressDialog();
        //mUser.setId(4);

        Log.i("TAGG",""+mUser.getUserName());

       // String url = "http://10.180.243.21:8080/api/add";

        String url = "http://" + mURL + ":8080/api/add";

        try{
            Ion.with(this)
                    .load(url)
                    .setJsonPojoBody(mUser)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error

                            //REMOVE THE PROGRESS BAR
                           dismissProgressDialog();

                            //GOTO HOME if REGISTRATION SUCCESSFUL
                            startActivity(new Intent (getApplicationContext(), HomeActivity.class));
                          //  Animatoo.animateFade(getApplicationContext());
                        }
                    });
        }catch (Exception e){}

    }

    public void memberLoginClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
     //   Animatoo.animateFade(this); //fire the slide left animation

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
      //  Animatoo.animateFade(this); //fire the slide left animation
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(RegistrationActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        progressDialog.setMessage("loading...");
        progressDialog.show();


    }

    public void dismissProgressDialog(){

        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
