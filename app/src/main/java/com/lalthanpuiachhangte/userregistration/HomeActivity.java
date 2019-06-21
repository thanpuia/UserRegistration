package com.lalthanpuiachhangte.userregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.lalthanpuiachhangte.userregistration.adapter.ImageAdapter;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        // Attaching the layout to the toolbar object
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ViewPager mViewPager = findViewById(R.id.viewPage);
        ImageAdapter adapter = new ImageAdapter(this);
        mViewPager.setAdapter(adapter);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profileEdit) {

            startActivity(new Intent(this,EditProfile.class));

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
