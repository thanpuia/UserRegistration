package com.lalthanpuiachhangte.userregistration;

import android.content.Context;

//NOT WORKING
public class mProgressDialog {
    android.app.ProgressDialog progressDialog;
    Context mContext;


    public mProgressDialog(Context context) {

        mContext = context;
    }

    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new android.app.ProgressDialog(mContext);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage("If you want something, ask!");
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
