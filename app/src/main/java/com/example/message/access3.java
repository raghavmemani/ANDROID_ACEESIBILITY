package com.example.message;

//import static android.content.ContentValues.TAG;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;




public class access3 extends AccessibilityService {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AccessibilityServiceInfo info= new AccessibilityServiceInfo();
    private static final String TAG="MY_SERVICE";




    @Override
    public void onAccessibilityEvent (AccessibilityEvent event){
        Log.e(TAG, "DONE");


        String packname = event.getPackageName().toString();
        PackageManager pkg = this.getPackageManager();
        if (!pkg.toString().equals("System UI") || !pkg.toString().equals("System launcher")) {
            Log.d(TAG, "YOUR RESULTS:" + pkg);


            try {
                ApplicationInfo applicationInfo = pkg.getApplicationInfo(packname, 0);
                CharSequence chg = pkg.getApplicationLabel(applicationInfo);
                Map<String, Object> user = new HashMap<>();

//            if (!user.toString().equals("System UI") || !user.toString().equals("System launcher") || !user.toString().equals("System launcher")) {


                user.put("first", chg);


                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                Log.d(TAG, (String) chg);
                //Log.d(TAG,"DONE1"+applicationInfo);
                System.out.println(chg);


            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
        }
    }

    @Override
    public void onServiceConnected () {

        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED |
                AccessibilityEvent.TYPE_VIEW_FOCUSED;


        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN;


        info.notificationTimeout = 500;

        this.setServiceInfo(info);
        Log.e(TAG, "CONNECTED");


    }

    @Override
    public void onInterrupt () {
        Log.e(TAG, "Something Went Wrong");

    }
}



