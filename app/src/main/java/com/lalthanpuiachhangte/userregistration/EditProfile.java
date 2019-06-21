package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class EditProfile extends AppCompatActivity {
    EditText username, password, email, mobile;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.usernameETEdit);
        password = findViewById(R.id.passwordETEdit);
        email = findViewById(R.id.emailETEdit);
        mobile = findViewById(R.id.phoneETEdit);
    }

    public void loginClickEdit(View view) {

//        String url = "http://" + mURL + ":8080";
//
//        try{
//            Ion.with(this)
//                    .load(url)
//                    .setJsonPojoBody()
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//                            // do stuff with the result or error
//
//                            //REMOVE THE PROGRESS BAR
//                            dismissProgressDialog();
//
//                        }
//                    });
//        }catch (Exception e){}

    }
    public void showProgressDialog(){
        progressDialog = new ProgressDialog(EditProfile.this);
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
