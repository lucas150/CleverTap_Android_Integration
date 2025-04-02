package com.henil.test_push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;

import com.clevertap.android.sdk.InAppNotificationButtonListener;
import com.clevertap.android.sdk.InboxMessageListener;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;

import java.io.File;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.clevertap.android.sdk.inbox.CTInboxMessage;
import com.clevertap.android.sdk.inbox.CTInboxMessageContent;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;
import com.clevertap.android.sdk.pushnotification.amp.CTPushAmpListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import android.location.Location;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener, InAppNotificationButtonListener, InboxMessageListener, CTPushNotificationListener {
    private FusedLocationProviderClient fusedLocationClient;
    private CoordinatorLayout coordinatorLayout;
    private static final int LOCATION_PERMISSION_REQUEST = 1001;
    private static final int RECORD_AUDIO_PERMISSION_CODE = 101;

    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize CleverTap
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        clevertapDefaultInstance.suspendInAppNotifications();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();  // Fetch and update location
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }

        clevertapDefaultInstance.setDisplayUnitListener(this);
        // Initialize fusedLocationClient before any usage
//        cleverTapInstance.setInAppNotificationButtonListener(this);

        setContentView(R.layout.activity_main);

        // Enable edge-to-edge support after setting the content view
        EdgeToEdge.enable(this);

        // Handle system insets to provide proper padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            clevertapDefaultInstance.initializeInbox();
            clevertapDefaultInstance.setInAppNotificationButtonListener(this);
            clevertapDefaultInstance.setCTInboxMessageListener(this);
            clevertapDefaultInstance.setCTPushNotificationListener(this);


        }
            // Send an event to CleverTap
//            clevertapDefaultInstance.pushEvent("Product viewed");

            // event with properties
            HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
            prodViewedAction.put("Product Name", "Casio Chronograph Watch");
            prodViewedAction.put("Category", "Mens Accessories");
            prodViewedAction.put("Price", 59.99);
            prodViewedAction.put("EndDate", "$D_1738125118");

            clevertapDefaultInstance.pushEvent("Product2", prodViewedAction);
//            clevertapDefaultInstance.pushEvent("ImageURL");


            // Find buttons by ID
            Button buttonPush = findViewById(R.id.buttonPush);
            Button buttonInApp = findViewById(R.id.buttonInApp);
            Button buttonCustom = findViewById(R.id.buttonCustom);
            Button in_app_2 = findViewById(R.id.in_app_2);
            Button in_app_3 = findViewById(R.id.in_app_3);
            Button in_app_4 = findViewById(R.id.in_app_4);
            Button buttonProfileUpdate = findViewById(R.id.buttonProfileUpdate);
            Button CustomInbox = findViewById(R.id.custominbox);
            Button nativeDisplay = findViewById(R.id.nativedisplay);
            Button cache = findViewById(R.id.cache);



        HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
        chargeDetails.put("Amount", 300);
        chargeDetails.put("Payment Mode", "Credit card");
        chargeDetails.put("Charged ID", 24052013);
        chargeDetails.put("Category", "Cash");

        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("Product category", "books");
        item1.put("L3 Category", "Books");
        item1.put("Book name", "The Millionaire next door");
        item1.put("Quantity", 1);

        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("Product category", "Shoes");
        item2.put("L3 Category", "Shoes");
        item2.put("Puma", "Shoes");
        item2.put("Quantity", 1);

        HashMap<String, Object> item3 = new HashMap<String, Object>();
        item3.put("Product category", "Watches");
        item3.put("L3 Category", "Watches");
        item3.put("Titan", "Chuck it, let's do it");
        item3.put("Quantity", 5);

        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        items.add(item1);
        items.add(item2);
        items.add(item3);


        // Set click listeners for each button
            buttonPush.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Push Notification Triggered"));
            buttonInApp.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app Notification Triggered"));
            buttonCustom.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Custom Notification Triggered"));
            in_app_2.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_2 Notification Triggered"));
            in_app_3.setOnClickListener(v -> clevertapDefaultInstance.pushChargedEvent(chargeDetails, items));

        in_app_4.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_4 Notification Triggered"));

            nativeDisplay.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Native Display"));
            cache.setOnClickListener(v -> {
                Log.d("CleverTap", "CT_ID Before App Data clear   ➡️  "+clevertapDefaultInstance.getCleverTapID());
                clearAppData();
                Log.d("CleverTap", "CT_ID After App Data clear   ➡️  "+clevertapDefaultInstance.getCleverTapID());

            });
            CustomInbox.setOnClickListener(v->{
                Intent customIntent = new Intent(MainActivity.this,CustomInbox.class);
                startActivity(customIntent);
            });





            CheckBox checkEmail = findViewById(R.id.checkEmail);
            CheckBox checkSMS = findViewById(R.id.checkSMS);
            CheckBox checkWhatsApp = findViewById(R.id.checkWhatsApp);
         //   clevertapDefaultInstance.getCTPushAmpListener();

            long temp2 = 1978594685790783689L;
//            clevertapDefaultInstance.pushEvent(temp2"");
            buttonProfileUpdate.setOnClickListener(v ->
            {
                HashMap<String, Object> updatedProfile = new HashMap<>();
                updatedProfile.put("Name", "Vaibhav");
                updatedProfile.put("Identity", 94);
                updatedProfile.put("Prefered Language", "English");
                updatedProfile.put("Email", "test12345@gmail.com");
//                updatedProfile.put("Phone", "+919876542089");
                updatedProfile.put("Gender", "M");
                updatedProfile.put("DOB", new Date());

                // Array list example
                ArrayList<String> updatedStuff = new ArrayList<>();
                updatedStuff.add("Watch");
                updatedStuff.add("Sunglasses");
                updatedProfile.put("MyStuff", updatedStuff);
                updatedProfile.put("MSG-sms", false);
                // Add DND Preferences
                updatedProfile.put("MSG-dndEmail", true);
//                updatedProfile.put("MSG-dndPhone", checkSMS.isChecked());
                updatedProfile.put("MSG-dndWhatsApp", checkWhatsApp.isChecked());

//                clevertapDefaultInstance.onUserLogin(updatedProfile,"123345");
            });



        Location location = new Location("");
        location.setLatitude(19.110000);
        location.setLongitude(72.850000);
        if (location != null) {
                clevertapDefaultInstance.setLocation(location);
            }

        Button btnCall = findViewById(R.id.call);
        btnCall.setOnClickListener(v->{
            SignedCallAndroid.makeSignedCall(getApplicationContext(), "johndoe", "Test CleverTap Call");
        });
        Button view2 = findViewById(R.id.view2);
        view2.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void clearAppData() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            activityManager.clearApplicationUserData(); // Clears all app data
        }
    }


    @Override
    public void inboxDidInitialize() {
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(this);
        Button inbox = findViewById(R.id.inbox);
        inbox.setOnClickListener(v -> {
            clevertapDefaultInstance.pushEvent("inbox");
//            clevertapDefaultInstance.pushEvent("Custom Event");
        if (clevertapDefaultInstance != null) {
            ArrayList<String> tabs = new ArrayList<>();
            tabs.add("Promotions");
            tabs.add("Default");

            CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
            styleConfig.setTabs(tabs);
            styleConfig.setNavBarTitle("MY INBOX");

            clevertapDefaultInstance.showAppInbox(styleConfig);
//            clevertapDefaultInstance.showAppInbox();
        }
        });
    }

    @Override
    public void onInboxItemClicked(CTInboxMessage message, int contentPageIndex, int buttonIndex){
        Log.i("CleverTap", "InboxItemClicked at page-index " + contentPageIndex + " with button-index " + buttonIndex);

        //The contentPageIndex corresponds to the page index of the content, which ranges from 0 to the total number of pages for carousel templates. For non-carousel templates, the value is always 0, as they only have one page of content.
        CTInboxMessageContent messageContentObject = message.getInboxMessageContents().get(contentPageIndex);

        //The buttonIndex corresponds to the CTA button clicked (0, 1, or 2). A value of -1 indicates the app inbox body/message clicked.
        if (buttonIndex != -1) {
            //button is clicked
            try {
                JSONObject buttonObject = (JSONObject) messageContentObject.getLinks().get(buttonIndex);
                String buttonType = buttonObject.getString("type");
                Log.i("CleverTap", "type of button clicked: " + buttonType);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        } else {
            //item is clicked
            Log.i("CleverTap", "type/template of App Inbox item:" + message);
        }
    }

    @Override
    public void inboxMessagesDidUpdate() {
        // Handle inbox message updates if needed
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> displayUnits) {
        if (displayUnits != null && !displayUnits.isEmpty()) {
            for (CleverTapDisplayUnit unit : displayUnits) {
                if (unit.getContents() != null && !unit.getContents().isEmpty()) {
                    String title = unit.getContents().get(0).getTitle();
                    String message = unit.getContents().get(0).getMessage();
                    String imageUrl = unit.getContents().get(0).getMedia();

                    // ✅ Corrected: Extract the actual update message
//                    String updateMessage = unit.getCustomExtras().get("update_alert");

                    // Load Image into ImageView
                    ImageView imageView = findViewById(R.id.native_image);
                    Glide.with(this).load(imageUrl).into(imageView);

                    // ✅ Show Snackbar if an update message exists
//                    if (updateMessage != null && !updateMessage.isEmpty()) {
//                        showSnackbar(updateMessage);
//                    }
                }
            }
        }
    }


    private void showSnackbar(String message) {
        View rootView = findViewById(R.id.coordinatorLayout);

        if (rootView == null) {
            // ✅ Use root view as a fallback
            rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        }

        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                updateLocationInCleverTap(location);
            } else {
                Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLocationInCleverTap(Location location) {
        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.setLocation(location);  // Correct way to update location
            Toast.makeText(this, "Location Updated!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onInAppButtonClick(HashMap<String, String> hashMap) {
        clevertapDefaultInstance.pushEvent("Test");
        Log.d("CleverTap", "Payload" + hashMap.toString());

    }

    @Override
    public void onNotificationClickedPayloadReceived(HashMap<String, Object> hashMap) {
        Log.d("CleverTap", "onNotificationClickedPayloadReceived: ");
    }
}









