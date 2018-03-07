package com.example.christina.avayadl;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.christina.avayadl.studyManagement.RSActivity;
import com.example.christina.avayadl.studyManagement.RSActivityManager;
import com.example.christina.avayadl.studyManagement.RSApplication;
import com.example.christina.avayadl.studyManagement.RSResultsProcessorManager;

import org.researchstack.skin.ui.MainActivity;
import org.researchsuite.rsrp.CSVBackend.RSRPCSVBackend;
import org.researchsuite.rsrp.Core.RSRPResultsProcessor;
import org.spongycastle.jcajce.provider.asymmetric.RSA;

public class AVAMainActivity extends RSActivity implements View.OnClickListener {

    public Button deleteFullButton;
    public Button deleteSpotButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteFullButton = (Button) findViewById(R.id.delete_full);
        deleteFullButton.setOnClickListener(this);
//        deleteFull.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteFull();
//
//            }
//        });
//        deleteSpot = (Button) findViewById(R.id.delete_spot);
//        deleteSpot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               RSRPCSVBackend backend = RSResultsProcessorManager.getBackend();
//               backend.removeFileForType("yadl_spot");
//
//              //  String s = (RSApplication) this.getApplication();
//            }
//        });

        if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()){
            RSActivityManager.get().queueActivity(this, "MEDLFull", true);
//            RSActivityManager.get().queueActivity(this, "YADLSpot", true);
        }

    }

//    private void deleteFull() {
//        Log.d("Button test","get here");
//        deleteFull.setText("clicked");
//        String temp = "temp";
//        RSRPCSVBackend backend = RSResultsProcessorManager.getBackend();
//        backend.removeFileForType("yadl_full");
//
//    }



    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 2:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                }else{

                }
                break;

            case 3:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

                }else{

                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.delete_full:
            {
                Log.d("button","here");
                break;
            }


        }
    }
}
