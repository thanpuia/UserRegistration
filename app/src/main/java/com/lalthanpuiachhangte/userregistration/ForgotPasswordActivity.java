package com.lalthanpuiachhangte.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_forgot_password);
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Animatoo.animateFade(this); //fire the slide left animation
    }
}
