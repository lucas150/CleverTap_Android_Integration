package com.henil.test_push;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize CleverTap
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

        // User profile setup
        HashMap<String, Object> profileUpdate = new HashMap<>();
        profileUpdate.put("Name", "Henil Gandhi");
        profileUpdate.put("Identity", 1111111);
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

        // Send an event to CleverTap
        clevertapDefaultInstance.pushEvent("Product viewed");

        // Create a notification channel
        CleverTapAPI.createNotificationChannel(getApplicationContext(),
                "henil123", "henil123", "henil123", NotificationManager.IMPORTANCE_MAX, true);

        // Enable edge-to-edge support
        EdgeToEdge.enable(this);

        // Set the content view
        setContentView(R.layout.activity_main);

        // Handle system insets to provide proper padding for the system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by ID
        Button buttonPush = findViewById(R.id.buttonPush);
        Button buttonInApp = findViewById(R.id.buttonInApp);
        Button buttonCustom = findViewById(R.id.buttonCustom);
        Button in_app_2 = findViewById(R.id.in_app_2);
        Button in_app_3 = findViewById(R.id.in_app_3);
        Button in_app_4 = findViewById(R.id.in_app_4);

        // Set click listeners for each button
        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Push Notification Triggered");
            }
        });

        buttonInApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("In-app Notification Triggered");
            }
        });

        buttonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Custom Notification Triggered");
            }
        });


        in_app_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("In-app_2 Notification Triggered");
            }
        });

        in_app_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("In-app_3 Notification Triggered");
            }
        });

        in_app_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("In-app_4 Notification Triggered");
            }
        });
    }
}
