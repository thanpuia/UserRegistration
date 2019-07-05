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

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class EditProfile extends AppCompatActivity {
    EditText username, password, email, phone, newPasswordet;
    ProgressDialog progressDialog;

    Intent intent;
    String result;
    int mSerialNumber;
    String token;
    String mUsername, mPassword, mEmail, mPhoneno;
    String newUsername,oldPassword, newPassword, newEmail, newPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.usernameETEdit);
        password = findViewById(R.id.passwordETEdit);
        email = findViewById(R.id.emailETEdit);
        phone = findViewById(R.id.phoneETEdit);
        newPasswordet = findViewById(R.id.newpasswordETEdit);


        intent =getIntent();
        token = intent.getStringExtra("token");
        result = intent.getStringExtra("result");
       // result = "{'username':'jadfe','password':'passe'}";

        try {

            JSONObject obj = new JSONObject(result);
            Log.d("My App", obj.toString());

            mUsername = (String) obj.get("username");
            mEmail = (String) obj.get("email");
            mPhoneno = (String) obj.get("phoneno");

            username.setText(mUsername);
            email.setText(mEmail);
            phone.setText(mPhoneno);

            Log.i("TAG",mUsername);
            //String mPassword = (String) obj.get("password");
            mSerialNumber = (int) obj.get("id");
            Log.i("TAG","Serial NO:"+mSerialNumber);

            //password.setText(mPassword);

        } catch (Throwable t) {
            Log.e("My App", "Could not parse malformed JSON: \"" + result + "\"");
        }
    }

    public void loginClickEdit(View view) {

        newUsername = username.getText().toString();
        oldPassword = password.getText().toString();
        newPhone = phone.getText().toString();
        newEmail = email.getText().toString();

        newPassword = newPasswordet.getText().toString();

        if(newPassword.matches(""))
            withoutPassword(mSerialNumber, newUsername, newEmail, newPhone );
        else
            withPassword(mSerialNumber,newUsername,newEmail,newPhone,newPassword,oldPassword);


    }

    public void withoutPassword(int sr, String un,  String em,String ph){

        Log.i("TAG","New String N " + newUsername);
        Log.i("TAG","Serial" + mSerialNumber);

        showProgressDialog();
        //Convert all the require data to JSONOBJECT

        JsonObject myJsonObject = myJsonObjected(sr,un, em, ph);

        Log.i("TAG","SerialNo:"+myJsonObject.get("id")+"  Name:"+myJsonObject.get("username"));

        String url =  "http://" + mURL + ":8080/update?access_token="+token;
        //upload to server
        try{
            Ion.with(this)
                    .load("POST",url)
                    .setJsonPojoBody(myJsonObject)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {

                            Log.i("TAG",result);
                            //TODO: result return hi enge
                            if(result.equals("user updated successfully...")) {
                                Toasty.success(getApplicationContext(), "Update Successfull!", Toasty.LENGTH_SHORT).show();
                            }else
                                Toasty.error(getApplicationContext(), "Server problem!", Toasty.LENGTH_SHORT).show();


                            dismissProgressDialog();

                        }
                    });
        }catch (Exception e) {
            Toasty.error(getApplicationContext(), "Server Error, please try again after sometime", Toasty.LENGTH_SHORT).show();
            dismissProgressDialog();
        }

    }

    public void withPassword(int sr, String un, String em, String ph, String newpass, String oldpass){
        showProgressDialog();
        //Convert all the require data to JSONOBJECT

        JsonObject myJsonObject = myJsonObjected(sr,un, em,ph, oldpass);

        Log.i("TAG","SerialNo:"+myJsonObject.get("id")+"  Name:"+myJsonObject.get("username"));

        String url =  "http://" + mURL + ":8080/changePass/"+ newpass +"?access_token="+token;
        //upload to server
        try{
            Ion.with(this)
                    .load("POST",url)
                    .setJsonPojoBody(myJsonObject)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {

                            Log.i("TAG",result);
                            //TODO: result return hi enge
                            if(result.equals("Password updated successfully")) {
                                Toasty.success(getApplicationContext(), "Update Successfull!", Toasty.LENGTH_SHORT).show();
                            }else
                                Toasty.error(getApplicationContext(), "Server problem!", Toasty.LENGTH_SHORT).show();


                            dismissProgressDialog();

                        }
                    });
        }catch (Exception e) {
            Toasty.error(getApplicationContext(), "Server Error, please try again after sometime", Toasty.LENGTH_SHORT).show();
            dismissProgressDialog();
        }


    }

    public JsonObject myJsonObjected(int sn, String un, String em, String ph){

        JSONObject mJSONObject = new JSONObject();

        //TODO: all the key are yet to be change

        try {
            mJSONObject.put("id",sn);
            mJSONObject.put("username", un);
            mJSONObject.put("email", em);
            mJSONObject.put("phoneno", ph);
           // mJSONObject.put("password",pw);

        }catch (Exception e){}
        try {
            Log.i("TAG","TEST: "+mJSONObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonObj = gson.toJson(mJSONObject);

        Log.i("TAG","TST: -->>"+ jsonObj);
        JsonObject convertedJson = new Gson().fromJson(jsonObj, JsonObject.class);


        Log.i("TAG","777test:"+convertedJson.get("username"));
        Log.i("TAG","666test:"+convertedJson);

        JsonObject newObj = (JsonObject) convertedJson.get("nameValuePairs");

        Log.i("TAG","999test:"+newObj.get("username"));
        return newObj;
    }

    public JsonObject myJsonObjected(int sn, String un, String em, String ph, String newpass){

        JSONObject mJSONObject = new JSONObject();

        //TODO: all the key are yet to be change

        try {
            mJSONObject.put("id",sn);
            mJSONObject.put("username", un);
            mJSONObject.put("email", em);
            mJSONObject.put("phoneno", ph);
            mJSONObject.put("password", newpass);
            // mJSONObject.put("password",pw);

        }catch (Exception e){}
        try {
            Log.i("TAG","TEST: "+mJSONObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsonObj = gson.toJson(mJSONObject);

        Log.i("TAG","TST: -->>"+ jsonObj);
        JsonObject convertedJson = new Gson().fromJson(jsonObj, JsonObject.class);


        Log.i("TAG","777test:"+convertedJson.get("username"));
        Log.i("TAG","666test:"+convertedJson);

        JsonObject newObj = (JsonObject) convertedJson.get("nameValuePairs");

        Log.i("TAG","999test:"+newObj.get("username"));
        return newObj;
    }


   /* public JsonObject userObjectToJsonObject(){

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
    }*/

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


    public void changePassword(View view) {


    }
}
