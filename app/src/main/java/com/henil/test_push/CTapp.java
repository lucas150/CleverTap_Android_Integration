package com.henil.test_push;

import android.app.Application;
import android.app.NotificationManager;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;


import me.leolin.shortcutbadger.ShortcutBadger;

public class CTapp extends Application {

    private CleverTapAPI cleverTapAPI;

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();

        // Initialize CleverTap
        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

        // Create notification channels
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "henil123", "henil123", "henil123", NotificationManager.IMPORTANCE_MAX, true, "hb.mp3");
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "sound", "sound", "sound", NotificationManager.IMPORTANCE_MAX, true, "jsr.mp3");

        // Set badge count
        int badgeCount = 5;
        ShortcutBadger.applyCount(this, badgeCount);

        // Set CleverTap notification handler
        CleverTapAPI.setNotificationHandler(new PushTemplateNotificationHandler());

        //Signed Call initialisation
        SignedCallAndroid.initialize(getApplicationContext(), cleverTapAPI);
    }

}
