package com.henil.test_push;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Enable edge-to-edge support after setting the content view
        EdgeToEdge.enable(this);

        // Handle system insets to provide proper padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize CleverTap
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.setCTNotificationInboxListener(this);
            clevertapDefaultInstance.initializeInbox();

            // User profile setup
            HashMap<String, Object> profileUpdate = new HashMap<>();
            profileUpdate.put("Name", "Henil Gandhi");
            profileUpdate.put("Identity", 300);
            profileUpdate.put("Email", "henil@clevertap.com");
            profileUpdate.put("Phone", "+919769660756");
            profileUpdate.put("Gender", "M");
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
            clevertapDefaultInstance.pushProfile(profileUpdate);

            // Send an event to CleverTap
            clevertapDefaultInstance.pushEvent("Product viewed");

            // Find buttons by ID
            Button buttonPush = findViewById(R.id.buttonPush);
            Button buttonInApp = findViewById(R.id.buttonInApp);
            Button buttonCustom = findViewById(R.id.buttonCustom);
            Button in_app_2 = findViewById(R.id.in_app_2);
            Button in_app_3 = findViewById(R.id.in_app_3);
            Button in_app_4 = findViewById(R.id.in_app_4);
            Button inbox = findViewById(R.id.inbox);

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

            // Handle location if available
            Location location = clevertapDefaultInstance.getLocation();
            if (location != null) {
                clevertapDefaultInstance.setLocation(location);
            }
        }
    }

    @Override
    public void inboxDidInitialize() {
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(this);
        if (clevertapDefaultInstance != null) {
            Button inbox = findViewById(R.id.inbox);
            inbox.setOnClickListener(v -> {
                clevertapDefaultInstance.pushEvent("Inbox Opened"); // Track inbox open event

                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("Promotions");
                tabs.add("Offers");  // We support up to 2 tabs only, additional tabs will be ignored

                CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
                styleConfig.setFirstTabTitle("First Tab");
                styleConfig.setTabs(tabs);  // Do not use if you don't want to use tabs
                styleConfig.setTabBackgroundColor("#FF0000");
                styleConfig.setSelectedTabIndicatorColor("#0000FF");
                styleConfig.setSelectedTabColor("#0000FF");
                styleConfig.setUnselectedTabColor("#FFFFFF");
                styleConfig.setBackButtonColor("#FF0000");
                styleConfig.setNavBarTitleColor("#FF0000");
                styleConfig.setNavBarTitle("MY INBOX");
                styleConfig.setNavBarColor("#FFFFFF");
                styleConfig.setInboxBackgroundColor("#ADD8E6");

                clevertapDefaultInstance.showAppInbox(styleConfig);
            });
        }
    }

    @Override
    public void inboxMessagesDidUpdate() {
        // Handle inbox message updates if needed
    }
}
