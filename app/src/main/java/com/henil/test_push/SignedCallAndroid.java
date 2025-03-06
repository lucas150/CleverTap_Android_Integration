package com.henil.test_push;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.signedcall.enums.VoIPCallStatus;
import com.clevertap.android.signedcall.exception.CallException;
import com.clevertap.android.signedcall.exception.InitException;
import com.clevertap.android.signedcall.init.SignedCallAPI;
import com.clevertap.android.signedcall.init.SignedCallInitConfiguration;
import com.clevertap.android.signedcall.interfaces.OutgoingCallResponse;
import com.clevertap.android.signedcall.interfaces.SignedCallInitResponse;

import org.json.JSONException;
import org.json.JSONObject;
public class SignedCallAndroid {

    private static boolean isInitialized = false;

    // ✅ Initialize Signed Call SDK
    public static void initialize(Context context, CleverTapAPI cleverTapAPI) {
        if (isInitialized) {
            Log.d("SignedCall", "SignedCallAPI is already initialized.");
            return;
        }

        boolean allowPersistSocketConnection = true;

        JSONObject initOptions = new JSONObject();
        try {
            initOptions.put("accountId", "6568502216033fcfba4cfc76");
            initOptions.put("apiKey", "uXFsHxBseCIUgDqU5kKLpgLR6KO7ZUmY5ObZqs1p5ox4vlxxTKxfHUiewEhCZgTN");
            initOptions.put("cuid", "ct_henil");
            initOptions.put("appId", BuildConfig.APPLICATION_ID);
            initOptions.put("name", "ct_henil");
            initOptions.put("ringtone", "");
        } catch (JSONException e) {
            Log.e("SignedCall", "JSON Error: ", e);
        }

        SignedCallInitConfiguration initConfiguration =
                new SignedCallInitConfiguration.Builder(initOptions, allowPersistSocketConnection)
                        .setNotificationPermissionRequired(true)
                        .build();

        SignedCallAPI.getInstance().init(context, initConfiguration, cleverTapAPI, new SignedCallInitResponse() {
            @Override
            public void onSuccess() {
                Log.d("SignedCall", "SDK Initialized Successfully");
                isInitialized = true;
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
        });

        // ✅ Check if SDK is initialized
        if (SignedCallAPI.getInstance().isInitialized(context)) {
            Log.d("SignedCall", "SDK is initialized");
            isInitialized = true;
        } else {
            Log.e("SignedCall", "SDK is NOT initialized");
        }
    }

    public static void makeSignedCall(Context context, String receiverID, String callLabel) {
        if (!SignedCallAPI.getInstance().isInitialized(context)) {
            Log.e("SignedCall", "SignedCallAPI is not initialized. Call not initiated.");
            return;
        }


        JSONObject callOptions = new JSONObject();
        try {
            callOptions.put("receiver_image", "https://gratisography.com/wp-content/uploads/2024/11/gratisography-augmented-reality-800x525.jpg"); // Optional receiver image
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OutgoingCallResponse outgoingCallResponseListener = new OutgoingCallResponse() {
//            public void callStatus(VoIPCallStatus callStatus) {
//                Log.d("SignedCall", "Call Status: " + callStatus.name());
//            }

            @Override
            public void onSuccess() {
                Log.d("SignedCall", "Call Initiated Successfully");
            }

            @Override
            public void onFailure(CallException callException) {
                Log.e("SignedCall", "Call Failed: " + callException.getMessage());
            }
        };

        Log.d("SignedCall", "Attempting to make a call to: " + receiverID);
        SignedCallAPI.getInstance().call(context, receiverID, callLabel, callOptions, outgoingCallResponseListener);
    }
}
