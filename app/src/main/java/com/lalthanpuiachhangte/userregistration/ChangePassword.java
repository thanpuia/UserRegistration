package com.lalthanpuiachhangte.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import es.dmoral.toasty.Toasty;

public class ChangePassword extends AppCompatActivity {

    EditText oldpassword, newpassword, newpasswordconfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        oldpassword = findViewById(R.id.oldPassword);
        newpassword = findViewById(R.id.newPassword);
        newpasswordconfirm = findViewById(R.id.newPasswordConfirm);

    }

    public void changePasswordClick(View view) {

        String oldPasswordStr = oldpassword.getText().toString();
        String newPasswordStr = newpassword.getText().toString();
        String newPasswordconfirmStr = newpasswordconfirm.toString();

        if(newPasswordconfirmStr.equals(newPasswordStr)){
            //TODO: CHANGE PASSWORD INITIATE


        }else{
            Toasty.error(this,"Password are not matching",Toasty.LENGTH_SHORT).show();
        }
    }
}
