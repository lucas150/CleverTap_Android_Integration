//package com.henil.test_push;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Application;
//import android.app.NotificationManager;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.clevertap.android.sdk.ActivityLifecycleCallback;
//import com.clevertap.android.sdk.CleverTapAPI;
//import com.clevertap.android.sdk.Constants;
//import com.clevertap.android.sdk.inapp.CTLocalInApp;
//import com.clevertap.android.sdk.interfaces.NotificationHandler;
//import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
//import com.clevertap.android.signedcall.exception.InitException;
//import com.clevertap.android.signedcall.init.SignedCallAPI;
//import com.clevertap.android.signedcall.init.SignedCallInitConfiguration;
//import com.clevertap.android.signedcall.interfaces.SignedCallInitResponse;
//
//
//import java.util.Arrays;
//
//import me.leolin.shortcutbadger.BuildConfig;
//import me.leolin.shortcutbadger.ShortcutBadger;
//
//public class CTapp extends Application {
//
//    private CleverTapAPI cleverTapAPI;  // Define as a class-level variable
//
//    @Override
//    public void onCreate() {
//        ActivityLifecycleCallback.register(this);
//        super.onCreate();
//
//        // Initialize CleverTap
//        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
//        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
//
//        // Create notification channels
//        CleverTapAPI.createNotificationChannel(getApplicationContext(),
//                "henil123", "henil123", "henil123", NotificationManager.IMPORTANCE_MAX, true, "hb.mp3");
//        CleverTapAPI.createNotificationChannel(getApplicationContext(),
//                "sound", "sound", "sound", NotificationManager.IMPORTANCE_MAX, true, "jsr.mp3");
//
//        // Set badge count
//        int badgeCount = 5;
//        ShortcutBadger.applyCount(this, badgeCount);
//
//        // Set CleverTap notification handler
//        CleverTapAPI.setNotificationHandler((NotificationHandler) new PushTemplateNotificationHandler());
//
//        // Initialize Signed Call API
//        initializeSignedCall();
//    }
//
//    private void initializeSignedCall() {
//        boolean allowPersistSocketConnection = true;  // Set based on your requirement
//        JSONObject initOptions = new JSONObject();
//        try {
//            initOptions.put("accountId", "61a52046f56a14cb19a1e9cc");
//            initOptions.put("apiKey", "9dcced09dae16c5e3606c22346d92361b77efdb360425913850bea4f22d812dd");
//            initOptions.put("cuid", "ct_henil123");
//            initOptions.put("appId", BuildConfig.APPLICATION_ID);  // Ensure correct import
//            initOptions.put("name", "ct_henil123");
//            initOptions.put("ringtone", "");
//        } catch (JSONException e) {
//            Log.e("SignedCall", "JSON Error: ", e);
//        }
//        JSONObject jsonObjectConfig = CTLocalInApp.builder()
//                .setInAppType(CTLocalInApp.InAppType.HALF_INTERSTITIAL)
//                .setTitleText("Get Notified")
//                .setMessageText("Please enable notifications on your device to use Push Notifications.")
//                .followDeviceOrientation(true)
//                .setPositiveBtnText("Allow")
//                .setNegativeBtnText("Cancel")
//                .setBackgroundColor(Constants.WHITE)
//                .setBtnBorderColor(Constants.BLUE)
//                .setTitleTextColor(Constants.BLUE)
//                .setMessageTextColor(Constants.BLACK)
//                .setBtnTextColor(Constants.WHITE)
//                .setImageUrl("https://icons.iconarchive.com/icons/treetog/junior/64/camera-icon.png")
//                .setBtnBackgroundColor(Constants.BLUE)
//                .build();
//
//        // 🛠 Fix: Ensure SCInitMode is used correctly
//        SignedCallInitConfiguration initConfiguration =
//                new SignedCallInitConfiguration.Builder(initOptions, allowPersistSocketConnection)
//                        .build();
//
//        SignedCallAPI.getInstance().init(getApplicationContext(), initConfiguration, cleverTapAPI, signedCallInitListener);
//    }
//
//    // Moved SignedCallInitResponse outside initializeSignedCall()
//    private final SignedCallInitResponse signedCallInitListener = new SignedCallInitResponse() {
//        @Override
//        public void onSuccess() {
//            Log.d("SignedCall", "SDK Initialized Successfully");
//        }
//
//        @Override
//        public void onFailure(@NonNull InitException initException) {
//            Log.e("SignedCall", "Error Code: " + initException.getErrorCode()
//                    + "\nMessage: " + initException.getMessage()
//                    + "\nExplanation: " + initException.getExplanation());
//
//            if (initException.getErrorCode() == InitException.SdkNotInitializedException.getErrorCode()) {
//                Log.e("SignedCall", "SDK Not Initialized!");
//            }
//        }
//    };
//}



package com.henil.test_push;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.inapp.CTLocalInApp;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.signedcall.exception.InitException;
import com.clevertap.android.signedcall.init.SignedCallAPI;
import com.clevertap.android.signedcall.init.SignedCallInitConfiguration;
import com.clevertap.android.signedcall.interfaces.SignedCallInitResponse;


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

        // Initialize Signed Call API
        initializeSignedCall();
    }

    private void initializeSignedCall() {
        boolean allowPersistSocketConnection = true;  // Set based on your requirement
        JSONObject initOptions = new JSONObject();
        try {
            initOptions.put("accountId", "61a52046f56a14cb19a1e9cc");
            initOptions.put("apiKey", "9dcced09dae16c5e3606c22346d92361b77efdb360425913850bea4f22d812dd");
            initOptions.put("cuid", "ct_henil123");
            initOptions.put("appId", BuildConfig.APPLICATION_ID);
            initOptions.put("name", "ct_henil123");
            initOptions.put("ringtone", "");
        } catch (JSONException e) {
            Log.e("SignedCall", "JSON Error: ", e);
        }

        // Fix: Manually define color values instead of using `Constants`
        JSONObject jsonObjectConfig = CTLocalInApp.builder()
                .setInAppType(CTLocalInApp.InAppType.HALF_INTERSTITIAL)
                .setTitleText("Get Notified")
                .setMessageText("Please enable notifications on your device to use Push Notifications.")
                .followDeviceOrientation(true)
                .setPositiveBtnText("Allow")
                .setNegativeBtnText("Cancel")
                .setBackgroundColor("#FFFFFF")  // WHITE
                .setBtnBorderColor("#4285F4")  // BLUE
                .setTitleTextColor("#4285F4")  // BLUE
                .setMessageTextColor("#000000") // BLACK
                .setBtnTextColor("#FFFFFF")  // WHITE
                .setImageUrl("https://icons.iconarchive.com/icons/treetog/junior/64/camera-icon.png")
                .setBtnBackgroundColor("#4285F4")  // BLUE
                .build();

        // ✅ Fix: Set Init Mode Correctly
        SignedCallInitConfiguration initConfiguration =
                new SignedCallInitConfiguration.Builder(initOptions, allowPersistSocketConnection)
                        .promptPushPrimer(jsonObjectConfig)
                        .setNotificationPermissionRequired(false)
                        .build();

        SignedCallAPI.getInstance().init(getApplicationContext(), initConfiguration, cleverTapAPI, signedCallInitListener);
    }

    // Moved SignedCallInitResponse outside initializeSignedCall()
    private final SignedCallInitResponse signedCallInitListener = new SignedCallInitResponse() {
        @Override
        public void onSuccess() {
            Log.d("SignedCall", "SDK Initialized Successfully");
        }

        @Override
        public void onFailure(@NonNull InitException initException) {
            Log.e("SignedCall", "Error Code: " + initException.getErrorCode()
                    + "\nMessage: " + initException.getMessage()
                    + "\nExplanation: " + initException.getExplanation());

            if (initException.getErrorCode() == InitException.SdkNotInitializedException.getErrorCode()) {
                Log.e("SignedCall", "SDK Not Initialized!");
            }
        }
    };


}
