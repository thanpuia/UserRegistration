package com.lalthanpuiachhangte.userregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.lalthanpuiachhangte.userregistration.adapter.ImageAdapter;

import es.dmoral.toasty.Toasty;

import static com.lalthanpuiachhangte.userregistration.MainActivity.mURL;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;
    String token;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        setContentView(R.layout.activity_home);
        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

      //  ViewPager mViewPager = findViewById(R.id.viewPage);
      //  ImageAdapter adapter = new ImageAdapter(this);
      //  mViewPager.setAdapter(adapter);

    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
       // Animatoo.animateFade(this); //fire the slide left animation
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        final String shareUserName = sharedPreferences.getString("userId","");

        Log.i("TAG", "username: "+ shareUserName);

        String url =  "http://" + mURL + ":8080/view/"+ shareUserName+"?access_token="+token;
        //noinspection SimplifiableIfStatement
        if (id == R.id.profileEdit) {

            //Send the user name to the server;
            try{
                Ion.with(this)
                        .load("GET",url)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                // do stuff with the result or error

                                Log.i("TAG","Result: "+ result);
                                Log.i("TAG","Token: "+ token);
                                Log.i("TAG","shareUsername: "+ shareUserName);


//                                if(result.equals("Exist")){
//                                    Toasty.error(getApplicationContext(), "User Name already exist!", Toasty.LENGTH_SHORT).show();
//                                }else {
//                                    Toasty.success(getApplicationContext(), "Registration successful", Toasty.LENGTH_SHORT).show();
//
//                                }

                                Log.i("TAG", result+"");
                               // dismissProgressDialog();
                                Intent intent = new Intent(getApplicationContext(), EditProfile.class);

                                intent.putExtra("result", result);
                                intent.putExtra("token", token);
                                startActivity(intent);

                            }
                        });
            }catch (Exception e) {
                Toasty.error(getApplicationContext(), "Server Error, please try again after sometime", Toasty.LENGTH_SHORT).show();
              //  dismissProgressDialog();
            }




            return true;



        }else if (id == R.id.logout){
            prefEditor = sharedPreferences.edit();

            //SAVE THE USER CREDENTIALS
            //prefEditor.putString("username",username);
            prefEditor.putString("userId","");
            prefEditor.putString("password","");
            prefEditor.commit();
            Toast.makeText(getApplicationContext(),"Log out successfully!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            // Animatoo.animateWindmill(v.getContext()); //fire the slide left animation
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
