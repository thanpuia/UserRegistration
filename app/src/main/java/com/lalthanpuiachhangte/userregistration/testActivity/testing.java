package com.lalthanpuiachhangte.userregistration.testActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class testing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        String url =  "http://" + mURL + ":8080/add";

       //JsonObject sampleObject = new JsonObject();

    /*    try{
            sampleObject.addProperty("username", "mmm");
            sampleObject.addProperty("password", "nnn");
            sampleObject.addProperty("active", "true");

            HashMap<String, String> map1 = new HashMap<>();
            map1.put("name","USER");

            ArrayList<JSONObject> list = new ArrayList<>();
            list.add(new JSONObject(map1));
            JSONArray test = new JSONArray(list);

            sampleObject.addProperty("roles", String.valueOf(test));
        }catch (Exception e){ }*/

        String bowlingJson =
             "{'username':'HIGH_dSCORE',"
                    + "'password':'Bowling',"
                    + "'active':'true',"

                    + "'roles':["
                    + "{'name':'USER'},"
                    + "]}";

        JsonObject convertedObject = new Gson().fromJson(bowlingJson, JsonObject.class);

        Ion.with(this)
                .load("POST",url)
                .setJsonPojoBody(convertedObject)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        // do stuff with the result or error

                        Log.i("TAG", result+"");
                        //REMOVE THE PROGRESS BAR
//                                if(result.equals(null)){
//                                    dismissProgressDialog();
//                                    Toasty.error(getApplicationContext(),"Server problem!",Toasty.LENGTH_SHORT).show();
//
//                                }else {
//                                    dismissProgressDialog();
//                                    Toasty.success(getApplicationContext(),"Registration successfully!",Toasty.LENGTH_SHORT).show();
//                                    startActivity(new Intent (getApplicationContext(), HomeActivity.class));
//                                }
                        //GOTO HOME if REGISTRATION SUCCESSFUL

                        //  Animatoo.animateFade(getApplicationContext());
                    }
                });
    }
}
