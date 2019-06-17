package com.lalthanpuiachhangte.userregistration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {
    EditText username, password, email, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        username = findViewById(R.id.usernameETEdit);
        password = findViewById(R.id.passwordETEdit);
        email = findViewById(R.id.emailETEdit);
        mobile = findViewById(R.id.phoneETEdit);
    }

    public void loginClickEdit(View view) {
    }
}
