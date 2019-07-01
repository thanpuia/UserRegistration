package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.entity.User;

import org.json.JSONObject;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class EditProfile extends AppCompatActivity {
    EditText username, password, email, mobile;
    ProgressDialog progressDialog;

    Intent intent;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.usernameETEdit);
        password = findViewById(R.id.passwordETEdit);
        email = findViewById(R.id.emailETEdit);
        mobile = findViewById(R.id.phoneETEdit);

        intent =getIntent();

        result = intent.getStringExtra("result");

    }

    public void loginClickEdit(View view) {

        try {

            JSONObject obj = new JSONObject(result);
            Log.d("My App", obj.toString());

            String mUsername = (String) obj.get("username");
            String mPassword = (String) obj.get("password");

            username.setText(mUsername);
            password.setText(mPassword);


        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }



//        String url = "http://" + mURL + ":8080";

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

    public JsonObject userObjectToJsonObject(){

        User mUser = new User();

        String mUsername = username.getText().toString();
        String mPassword = password.getText().toString();

        mUser.setUsername(mUsername);
        mUser.setPassword(mPassword);

        //CONVERT THE USER OBJECT TO NORMAL JsonObject
        GsonBuilder gsonMapBuilder = new GsonBuilder();
        Gson gsonObject = gsonMapBuilder.create();
        String jsonObj = gsonObject.toJson(mUser);

        JsonObject convertedObject = new Gson().fromJson(jsonObj, JsonObject.class);

        return convertedObject ;
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
