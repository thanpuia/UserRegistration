package com.lalthanpuiachhangte.userregistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.lalthanpuiachhangte.userregistration.adapter.ImageAdapter;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_home);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        ViewPager mViewPager = findViewById(R.id.viewPage);
        ImageAdapter adapter = new ImageAdapter(this);
        mViewPager.setAdapter(adapter);

        Button bt = findViewById(R.id.btmo);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefEditor = sharedPreferences.edit();

                //SAVE THE USER CREDENTIALS
                //prefEditor.putString("username",username);
                prefEditor.putString("userId","");
                prefEditor.putString("password","");
                prefEditor.commit();
                Toast.makeText(v.getContext(),"Log out successfully!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                Animatoo.animateWindmill(v.getContext()); //fire the slide left animation

            }
        });
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateSpin(this); //fire the slide left animation
    }
}
