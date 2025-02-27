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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import android.location.Location;



public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener {
    private FusedLocationProviderClient fusedLocationClient;
    private CoordinatorLayout coordinatorLayout;
    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize CleverTap
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocation();  // Fetch and update location
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
        }

        clevertapDefaultInstance.setDisplayUnitListener(this);
        // Initialize fusedLocationClient before any usage

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

            // User profile setup
            HashMap<String, Object> profileUpdate = new HashMap<>();
            profileUpdate.put("Name", "Vaibhav");
                profileUpdate.put("Identity", 6769769);
            profileUpdate.put("Email", "newuser2@gmail.com");
            profileUpdate.put("Phone", "+9197660712536");
            profileUpdate.put("Gender", "M");
            profileUpdate.put("Prefered Language", "French");
            profileUpdate.put("DOB", new Date());

            profileUpdate.put("MSG-email", true);
            profileUpdate.put("MSG-push", true);
            profileUpdate.put("MSG-sms", true);
            profileUpdate.put("MSG-whatsapp", true);

            // Define a list of items
            ArrayList<String> stuff = new ArrayList<>();
            stuff.add("bag");
            stuff.add("shoes");
            profileUpdate.put("MyStuff", stuff);

            // Update user profile in CleverTap
           clevertapDefaultInstance.onUserLogin(profileUpdate);

            // Profile push with updated list
            ArrayList<String> otherStuffList = new ArrayList<>();
            otherStuffList.add("Jeans");
            otherStuffList.add("Perfume");
            profileUpdate.put("MyStuff", otherStuffList);
            //clevertapDefaultInstance.pushProfile(profileUpdate);

            // Send an event to CleverTap
            clevertapDefaultInstance.pushEvent("Product viewed");

            // event with properties
            HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
            prodViewedAction.put("Product Name", "Casio Chronograph Watch");
            prodViewedAction.put("Category", "Mens Accessories");
            prodViewedAction.put("Price", 59.99);
            prodViewedAction.put("EndDate", "$D_1738125118");

            clevertapDefaultInstance.pushEvent("Product2", prodViewedAction);
            clevertapDefaultInstance.pushEvent("ImageURL");


            // Find buttons by ID
            Button buttonPush = findViewById(R.id.buttonPush);
            Button buttonInApp = findViewById(R.id.buttonInApp);
            Button buttonCustom = findViewById(R.id.buttonCustom);
            Button in_app_2 = findViewById(R.id.in_app_2);
            Button in_app_3 = findViewById(R.id.in_app_3);
            Button in_app_4 = findViewById(R.id.in_app_4);
            Button inbox = findViewById(R.id.inbox);
            Button buttonProfileUpdate = findViewById(R.id.buttonProfileUpdate);
            Button buttonProfileUpdate2 = findViewById(R.id.buttonProfileUpdate2);
            Button nativeDisplay = findViewById(R.id.nativedisplay);
            Button cache = findViewById(R.id.cache);

            // Set click listeners for each button
            buttonPush.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Push Notification Triggered"));
            buttonInApp.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app Notification Triggered"));
            buttonCustom.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Custom Notification Triggered"));
            in_app_2.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_2 Notification Triggered"));
            in_app_3.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_3 Notification Triggered"));
            in_app_4.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_4 Notification Triggered"));
            inbox.setOnClickListener(v -> {
                clevertapDefaultInstance.pushEvent("inbox"); // Track inbox open event
                clevertapDefaultInstance.showAppInbox();
            });
            nativeDisplay.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Native Display"));
            cache.setOnClickListener(v -> {
                Log.d("CleverTap", "CT_ID Before App Data clear   ➡️  "+clevertapDefaultInstance.getCleverTapID());
                clearAppData();
                Log.d("CleverTap", "CT_ID After App Data clear   ➡️  "+clevertapDefaultInstance.getCleverTapID());

            });





            CheckBox checkEmail = findViewById(R.id.checkEmail);
            CheckBox checkSMS = findViewById(R.id.checkSMS);
            CheckBox checkWhatsApp = findViewById(R.id.checkWhatsApp);

            long temp2 = 1978594685790783689L;
//            clevertapDefaultInstance.pushEvent(temp2"");
            buttonProfileUpdate.setOnClickListener(v ->
            {
                HashMap<String, Object> updatedProfile = new HashMap<>();
                updatedProfile.put("Name", "Vaibhav");
                updatedProfile.put("Identity", 7697674);
                updatedProfile.put("Prefered Language", "English");
//                updatedProfile.put("Email", "newuser@gmail.com");
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

                clevertapDefaultInstance.onUserLogin(updatedProfile);
            });

            buttonProfileUpdate2.setOnClickListener(v -> {
                HashMap<String, Object> updatedProfile = new HashMap<>();
                updatedProfile.put("name", "Vhav2");
                updatedProfile.put("identity", 97674);
                updatedProfile.put("email", "hakjl@clevertap.com");
                updatedProfile.put("Phone", "+91987654289");
                updatedProfile.put("Prefered Language", "Hindi");
                updatedProfile.put("Gender", "M");
                updatedProfile.put("DOB", new Date());

                ArrayList<String> updatedStuff = new ArrayList<>();
                updatedStuff.add("Watch");
                updatedStuff.add("Sunglasses");
                updatedProfile.put("MyStuff", updatedStuff);

                clevertapDefaultInstance.onUserLogin(updatedProfile);
            });

            // Handle location if available
            Location location = clevertapDefaultInstance.getLocation();
            if (location != null) {
                clevertapDefaultInstance.setLocation(location);
            }
        }
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
        if (clevertapDefaultInstance != null) {
            ArrayList<String> tabs = new ArrayList<>();
            tabs.add("Promotions");
            tabs.add("Offers");

            CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
            styleConfig.setTabs(tabs);
            styleConfig.setNavBarTitle("MY INBOX");

            clevertapDefaultInstance.showAppInbox(styleConfig);
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
                    String updateMessage = unit.getCustomExtras().get("update_alert");

                    // Load Image into ImageView
                    ImageView imageView = findViewById(R.id.native_image);
                    Glide.with(this).load(imageUrl).into(imageView);

                    // ✅ Show Snackbar if an update message exists
                    if (updateMessage != null && !updateMessage.isEmpty()) {
                        showSnackbar(updateMessage);
                    }
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
}
