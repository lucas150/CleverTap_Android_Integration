package com.henil.test_push;

import android.app.Application;
import android.app.NotificationManager;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;


public class CTapp extends Application {
    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
            super.onCreate();
        CleverTapAPI clevertapDefaultInstance=CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        // Create a notification channel
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "henil123", "henil123", "henil123", NotificationManager.IMPORTANCE_MAX, true);

        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());

    }
}
