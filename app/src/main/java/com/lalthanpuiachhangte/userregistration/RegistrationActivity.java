package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.Random;

import es.dmoral.toasty.Toasty;

import static com.lalthanpuiachhangte.userregistration.MainActivity.MODE;
import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class RegistrationActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText username, password, email, mobile;
    User mUser;

    Random rand ;
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
        rand = new Random();

    }

    public void newRegistrationClick(View view) {

        String mUsername = username.getText().toString();
        String mPassword = password.getText().toString();
        String mEmail = email.getText().toString();
        String mMobile = mobile.getText().toString();
        int mUserId;
        try{
             mUserId =  Integer.parseInt(String.valueOf(mobile.getText()));

        }catch (Exception e){
          //put random number

             mUserId = rand.nextInt();
        }


        mUser.setUsername(mUsername);
        mUser.setPassword(mPassword);
        mUser.setEmail(mEmail);
        mUser.setPhoneno(mMobile);

        //SHOW THE DIALOG BAR
        //mUser.setId(4);


        Log.i("TAGG",""+mUser.getUsername());
        if(MODE){
            if(mUsername.matches("") || mEmail.matches("") || mPassword.matches("") || mUser.equals(null)){
                Toasty.error(getApplicationContext(),"Enter all field",Toasty.LENGTH_SHORT).show();
            }else{
                registerToServer();
            }

        }else{
            Toasty.info(getApplicationContext(),"Registration successfully! (debug mode)",Toasty.LENGTH_SHORT).show();
        }


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


    public void registerToServer(){
        showProgressDialog();

        //String url =  "http://" + mURL + ":8080/secure/rest/admin/add";
        String url =  "http://" + mURL + ":8080/register";
        try{
            Ion.with(this)
                    .load(url)
                    .setJsonPojoBody(mUser)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            // do stuff with the result or error

                            try{
                                //REMOVE THE PROGRESS BAR
                                if(result.equals(null)){
                                    dismissProgressDialog();
                                    Toasty.error(getApplicationContext(),"Server problem!",Toasty.LENGTH_SHORT).show();

                                }else {
                                    dismissProgressDialog();
                                    Toasty.success(getApplicationContext(),"Registration successfully!",Toasty.LENGTH_SHORT).show();
                                    startActivity(new Intent (getApplicationContext(), HomeActivity.class));
                                }
                            }catch (Exception df) {
                                Toasty.error(getApplicationContext(),"Server Error or Server down!",Toasty.LENGTH_SHORT).show();
                                dismissProgressDialog();

                            }


                            //GOTO HOME if REGISTRATION SUCCESSFUL

                            //  Animatoo.animateFade(getApplicationContext());
                        }
                    })
            .wait(9000);
        }catch (Exception e){}
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
