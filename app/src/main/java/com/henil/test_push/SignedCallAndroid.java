package com.henil.test_push;

import android.content.Context;
import android.util.Log;

import com.clevertap.android.signedcall.enums.VoIPCallStatus;
import com.clevertap.android.signedcall.exception.CallException;
import com.clevertap.android.signedcall.init.SignedCallAPI;
import com.clevertap.android.signedcall.interfaces.OutgoingCallResponse;

import org.json.JSONException;
import org.json.JSONObject;
public class SignedCallAndroid {

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
            public void callStatus(VoIPCallStatus callStatus) {
                Log.d("SignedCall", "Call Status: " + callStatus.name());
            }

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
