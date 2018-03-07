package com.example.christina.avayadl;

import android.os.Bundle;

import com.example.christina.avayadl.studyManagement.RSActivity;
import com.example.christina.avayadl.studyManagement.RSActivityManager;

public class AVAOnboardingActivity extends RSActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RSActivityManager.get().queueActivity(this, "MEDLFull", true);
        RSActivityManager.get().queueActivity(this, "MEDLSpot", true);
//        RSActivityManager.get().queueActivity(this, "YADLSpot", true);
    }


}
