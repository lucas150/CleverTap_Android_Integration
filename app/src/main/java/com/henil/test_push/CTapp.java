package com.henil.test_push;

import android.app.Application;
import android.app.NotificationManager;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.signedcall.fcm.SignedCallNotificationHandler;
import com.google.firebase.analytics.FirebaseAnalytics;


import java.util.Objects;

import me.leolin.shortcutbadger.ShortcutBadger;

public class CTapp extends Application {


    private CleverTapAPI cleverTapAPI;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();

        // Initialize CleverTap
        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Create notification channels
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "henil123", "henil123", "henil123", NotificationManager.IMPORTANCE_MAX, true, "hb.mp3");
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "sound", "sound", "sound", NotificationManager.IMPORTANCE_MAX, true, "jsr.mp3");
        mFirebaseAnalytics.setUserProperty("ct_objectId", Objects.requireNonNull(CleverTapAPI.getDefaultInstance(this)).getCleverTapID());

        // Set badge count
        int badgeCount = 5;
        ShortcutBadger.applyCount(this, badgeCount);

        // Set CleverTap notification handler
        CleverTapAPI.setNotificationHandler(new PushTemplateNotificationHandler());
        CleverTapAPI.setSignedCallNotificationHandler(new SignedCallNotificationHandler());
        //Signed Call initialisation
        SignedCallAndroid.initialize(getApplicationContext(), cleverTapAPI);


    }

}
