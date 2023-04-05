package com.example.message;

//import static android.content.ContentValues.TAG;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.google.android.material.tabs.TabLayout;

public class access extends AccessibilityService {
    AccessibilityServiceInfo info= new AccessibilityServiceInfo();
    private static final String TAG="MY_SERVICE";
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG,"DONE");


        String packname=  event.getPackageName().toString();
        PackageManager pkg=this.getPackageManager();
        Log.d(TAG,"YOUR RESULTS:"+pkg);
        try {
            ApplicationInfo applicationInfo= pkg.getApplicationInfo(packname,0);
             CharSequence chg= pkg.getApplicationLabel(applicationInfo);
            Log.d(TAG, (String) chg);
            //Log.d(TAG,"DONE1"+applicationInfo);
            System.out.println(chg);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    @Override
    public void onServiceConnected() {

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED;


        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;




        info.notificationTimeout = 500;

        this.setServiceInfo(info);
        Log.e(TAG,"CONNECTED");


    }

    @Override
    public void onInterrupt() {
        Log.e(TAG,"Something Went Wrong");

    }


}
