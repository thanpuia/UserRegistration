package com.lalthanpuiachhangte.userregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;
import android.widget.ToggleButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    //public static String mURL = "10.180.243.21";
    public static String mURL = "10.180.243.25";
    MaterialEditText loginId, password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;

    ProgressDialog progressDialog;
    ToggleButton toggleButton;
    public static Boolean MODE;

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
        toggleButton = findViewById(R.id.toggleButton);

        //*******FOR DEVELOP MENT ONLY

        //false = debug ; true = operation mode

        toggleButton.setChecked(false);
        MODE = false;

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(toggleButton.getText().equals("Op Mode")){
                    MODE = true;
                  //  startActivity(new Intent(getApplicationContext(), testing.class));
                }else if(toggleButton.getText().equals("Debug Mode")){
                    MODE = false;
                  //  startActivity(new Intent(getApplicationContext(), testing.class));

                }

                Toasty.success(getApplication(),"State: "+toggleButton.getText(),Toasty.LENGTH_SHORT).show();
            }
        });


        //*******DEVELOPMENT ONLY ENDS
        //INITIATING THE PROGRESS DIALOG CLASS

        //CHECK THE SHARED PREFERENCE
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String shareUserId = sharedPreferences.getString("userId","");
        String sharedPassword = sharedPreferences.getString("password","");

        if(!shareUserId.equals("") && !sharedPassword.equals("")){
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
           // Animatoo.animateSplit(getApplicationContext());  //fire the zoom animation
        }
    }

    public void loginClick(View view) {

        if(MODE)
            withPassword(getApplicationContext());
        else
            withoutPassword();
    }

    public void registrationClick(View view) {


        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
       // Animatoo.animateSplit(this);  //fire the zoom animation


    }


    public void forgotPasswordClick(View view) {
        Intent intent = new Intent (this, ForgotPasswordActivity.class);
        startActivity(intent);
        //startActivity(new Intent(this, ForgotPasswordActivity.class));
       // Animatoo.animateSplit(this);  //fire the zoom animation
    }


    public void withoutPassword(){
        startActivity(new Intent(this,HomeActivity.class));
     //   Animatoo.animateSplit(this);
    }


    public void withPassword(Context context){
        final String mUsername = loginId.getText().toString();
        final String mPassword = password.getText().toString();

        //CHECK IF BOTH ARE FILLED
        if (mUsername.matches("") || (mPassword.matches(""))){
            Toasty.error(getApplicationContext(),"Enter both fields!",Toast.LENGTH_SHORT).show();
        }else {

            //CHECK THE EMAIL PATTERn
            //if(!isEmailValid(mUsername)){

                //IF BOTH FIELDS AND EMAIL PATTERN IS CORRECT SENT TO THE USER SERVER
                //SHOW THE PROGRESS BAR
                showProgressDialog();
                String url = "http://" + mURL + ":8080/oauth/token?grant_type=password&username="+ mUsername+"&password="+mPassword;
                try{
                    Ion.with(this)
                            .load("POST",url)
                            .basicAuthentication("my-trusted-client","secret")
                            .asString()
                            .setCallback(new FutureCallback<String>() {
                                @Override
                                public void onCompleted(Exception e, String result) {
                                    dismissProgressDialog();

                                    try {
                                        Log.i("TAGG",result+"");

                                        JSONObject jsonObject = new JSONObject(result);
                                        String access = jsonObject.getString("access_token");

                                        Toast.makeText(getApplicationContext(),"Login successful!",Toast.LENGTH_SHORT).show();
                                        gotoPrivate(access, mUsername, mPassword);

                                    }catch (JSONException err){
                                        Log.d("Error", err.toString());
                                        Toasty.error(getApplicationContext(),"Invalid password or username or server down!",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }catch (Exception e){
                    Toasty.error(getApplicationContext(),"Server is not responding, try again after sometime",Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                }
           // }else
                //Toasty.error(getApplicationContext(),"Email not correct",Toast.LENGTH_SHORT).show();
        }
    }


    private void gotoPrivate(String access, final String userId, final String password) {

        String url = "http://" + mURL + ":8080/private?access_token="+access;
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
                       // Animatoo.animateSplit(getApplicationContext());  //fire the zoom animation

                        //EXIT THE PROGRESS BAR
                        dismissProgressDialog();
                    }
                });
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(MainActivity.this);
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

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
//        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
//        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
        return true;//testting

    }

}
