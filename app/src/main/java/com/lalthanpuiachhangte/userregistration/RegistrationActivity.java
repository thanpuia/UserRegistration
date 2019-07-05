package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.entity.User;

import es.dmoral.toasty.Toasty;

import static com.lalthanpuiachhangte.userregistration.MainActivity.isEmailValid;
import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class RegistrationActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    EditText username, password, email, mobile, address;
    Spinner countryCodeSpinner;
    User mUser;
    String mUsername, mPassword,mEmail,tempMobile,mAddress,countryCode, mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try { this.getSupportActionBar().hide(); } catch (NullPointerException e){}

        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.usernameET);
        password = findViewById(R.id.passwordET);
        email = findViewById(R.id.emailET);
        mobile = findViewById(R.id.phoneET);
        address = findViewById(R.id.addressET);

        countryCodeSpinner = findViewById(R.id.countryCodeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.countrycode,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryCodeSpinner.setAdapter(adapter);

        mUser = new User();

    }

    public void newRegistrationClick(View view) {

        mUsername = username.getText().toString();
        mPassword = password.getText().toString();
        mEmail = email.getText().toString();
        tempMobile = mobile.getText().toString();
        mAddress = address.getText().toString();
        countryCode = countryCodeSpinner.getSelectedItem().toString();

        mPhone = countryCode + tempMobile;
        //FILL ALL THE FIELDS
        if(mUsername.matches("") || mPassword.matches("")|| mEmail.matches("")|| mPhone.matches("")|| mAddress.matches("")){
                Toasty.error(getApplicationContext(),"Enter all field",Toasty.LENGTH_SHORT).show();
        }else {
                //CHECK EMAIL PATTERN
                if(isEmailValid(mEmail)){
                    //CHECK THE MOBILE PHONE LENGTH
                    if(tempMobile.length() == 10){
                        registerToServer();
                    }else{
                        Toasty.error(getApplicationContext(),"Phone Number should equal 10 digit",Toasty.LENGTH_SHORT).show();
                    }
                }else{
                    Toasty.error(getApplicationContext(),"incorrect email type",Toasty.LENGTH_SHORT).show();
                }
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

        String url =  "http://" + mURL + ":8080/add";

        JsonObject mUser = userObjectToJsonObject();

        try{
            Ion.with(this)
                    .load("POST",url)
                    .setJsonPojoBody(mUser)
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            // do stuff with the result or error
                            if(result.equals("user added successfully...")){
                                Toasty.success(getApplicationContext(), "Registration successful", Toasty.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toasty.error(getApplicationContext(), "User Name already exist!", Toasty.LENGTH_SHORT).show();
                            }

                            Log.i("TAG", result+"");
                        dismissProgressDialog();

                        }
                    });
        }catch (Exception e) {
          Toasty.error(getApplicationContext(), "Server Error, please try again after sometime", Toasty.LENGTH_SHORT).show();
          dismissProgressDialog();
      }
    }

    public JsonObject userObjectToJsonObject(){

        User mUser = new User();


        mUser.setUsername(mUsername);
        mUser.setPassword(mPassword);
        mUser.setPhoneno(mPhone);
        mUser.setEmail(mEmail);

        //CONVERT THE USER OBJECT TO NORMAL JsonObject
        GsonBuilder gsonMapBuilder = new GsonBuilder();
        Gson gsonObject = gsonMapBuilder.create();
        String jsonObj = gsonObject.toJson(mUser);

        JsonObject convertedObject = new Gson().fromJson(jsonObj, JsonObject.class);

        return convertedObject ;
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


/*  public JSONObject jsonObject(){
        JSONObject sampleObject = new JSONObject();

        try{
            sampleObject.put("username", username.getText().toString());
            sampleObject.put("password", password.getText().toString());
            sampleObject.put("active", "true");

            // Role role = new Role();
            //  role.setName("USER");


            HashMap<String, String> map1 = new HashMap<>();

            map1.put("name","USER");

            ArrayList<JsonObject> list = new ArrayList<>();

            JsonObject convertedObject = new Gson().fromJson(String.valueOf(map1), JsonObject.class);


            list.add(convertedObject);

//            for(HashMap<String, String> data : list) {
//                JSONObject obj = new JSONObject(data);
//                jsonObj.add(obj);
//            }

            JSONArray test = new JSONArray(list);
            // JSONArray roles = new JSONArray();
            //  roles.put(role);

            //new JSONObject(map1);
//            GsonBuilder gsonMapBuilder = new GsonBuilder();
//            Gson gsonObject = gsonMapBuilder.create();
//            String JSONObject = gsonObject.toJson(map1);


            sampleObject.put("roles", test);
            Log.i("TAG", sampleObject+"");
            return sampleObject;

        }catch (Exception e){ return  null;}
}
*/