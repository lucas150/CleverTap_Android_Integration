//
//
//package com.henil.test_push;
//
//import android.location.Location;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.clevertap.android.sdk.CTInboxListener;
//import com.clevertap.android.sdk.CTInboxStyleConfig;
//import com.clevertap.android.sdk.CleverTapAPI;
//
//import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
//import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
//import java.util.ArrayList;
//
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//
//public class MainActivity extends AppCompatActivity implements CTInboxListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//
//        // Enable edge-to-edge support after setting the content view
//        EdgeToEdge.enable(this);
//
//        // Handle system insets to provide proper padding for system bars
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Initialize CleverTap
//        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
//        if (clevertapDefaultInstance != null) {
//            clevertapDefaultInstance.setCTNotificationInboxListener(this);
//            clevertapDefaultInstance.initializeInbox();
//
//            // User profile setup
//            HashMap<String, Object> profileUpdate = new HashMap<>();
//            profileUpdate.put("Name", "Henil Gandhi");
//            profileUpdate.put("Identity", 300);
//            profileUpdate.put("Email", "henil@clevertap.com");
//            profileUpdate.put("Phone", "+919769660756");
//            profileUpdate.put("Gender", "M");
//            profileUpdate.put("DOB", new Date());
//
//            profileUpdate.put("MSG-email", true);
//            profileUpdate.put("MSG-push", true);
//            profileUpdate.put("MSG-sms", true);
//            profileUpdate.put("MSG-whatsapp", true);
//
//            // Define a list of items
//            ArrayList<String> stuff = new ArrayList<>();
//            stuff.add("bag");
//            stuff.add("shoes");
//            profileUpdate.put("MyStuff", stuff);
//
//            // Update user profile in CleverTap
//            clevertapDefaultInstance.onUserLogin(profileUpdate);
//
//            // Profile push with updated list
//            ArrayList<String> otherStuffList = new ArrayList<>();
//            otherStuffList.add("Jeans");
//            otherStuffList.add("Perfume");
//            profileUpdate.put("MyStuff", otherStuffList);
//            clevertapDefaultInstance.pushProfile(profileUpdate);
//
//            // Send an event to CleverTap
//            clevertapDefaultInstance.pushEvent("Product viewed");
//
//            // Find buttons by ID
//            Button buttonPush = findViewById(R.id.buttonPush);
//            Button buttonInApp = findViewById(R.id.buttonInApp);
//            Button buttonCustom = findViewById(R.id.buttonCustom);
//            Button in_app_2 = findViewById(R.id.in_app_2);
//            Button in_app_3 = findViewById(R.id.in_app_3);
//            Button in_app_4 = findViewById(R.id.in_app_4);
//            Button inbox = findViewById(R.id.inbox);
//            Button buttonProfileUpdate = findViewById(R.id.buttonProfileUpdate);
//            Button buttonProfileUpdate2 = findViewById(R.id.buttonProfileUpdate2);
//
//
//            // Set click listeners for each button
//            buttonPush.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Push Notification Triggered"));
//            buttonInApp.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app Notification Triggered"));
//            buttonCustom.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("Custom Notification Triggered"));
//            in_app_2.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_2 Notification Triggered"));
//            in_app_3.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_3 Notification Triggered"));
//            in_app_4.setOnClickListener(v -> clevertapDefaultInstance.pushEvent("In-app_4 Notification Triggered"));
//            inbox.setOnClickListener(v -> {
//                clevertapDefaultInstance.pushEvent("inbox"); // Track inbox open event
//                clevertapDefaultInstance.showAppInbox();
//            });
//
//            buttonProfileUpdate.setOnClickListener(v -> {
//                HashMap<String, Object> updatedProfile = new HashMap<>();
//                updatedProfile.put("Name", "B");
//                updatedProfile.put("Identity", 2);
//                updatedProfile.put("Email", "new@clevertap.com");
////                updatedProfile.put("Phone", "+919876543210");
//                updatedProfile.put("Gender", "M");
//                updatedProfile.put("DOB", new Date());
//
//                ArrayList<String> updatedStuff = new ArrayList<>();
//                updatedStuff.add("Watch");
//                updatedStuff.add("Sunglasses");
//                updatedProfile.put("MyStuff", updatedStuff);
//
//                clevertapDefaultInstance.onUserLogin(updatedProfile);
//            });
//
//
//            buttonProfileUpdate2.setOnClickListener(v -> {
//                HashMap<String, Object> updatedProfile = new HashMap<>();
//                updatedProfile.put("Name", "C");
//                updatedProfile.put("Identity", 3);
////                updatedProfile.put("Email", "new@clevertap.com");
//                updatedProfile.put("Phone", "+91123456754");
//                updatedProfile.put("Gender", "M");
//                updatedProfile.put("DOB", new Date());
//
//                ArrayList<String> updatedStuff = new ArrayList<>();
//                updatedStuff.add("Watch");
//                updatedStuff.add("Sunglasses");
//                updatedProfile.put("MyStuff", updatedStuff);
//
//                clevertapDefaultInstance.onUserLogin(updatedProfile);
//            });
//
//            // Handle location if available
//            Location location = clevertapDefaultInstance.getLocation();
//            if (location != null) {
//                clevertapDefaultInstance.setLocation(location);
//            }
//        }
//    }
//
//    @Override
//    public void inboxDidInitialize() {
//        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(this);
//        if (clevertapDefaultInstance != null) {
//            ArrayList<String> tabs = new ArrayList<>();
//            tabs.add("Promotions");
//            tabs.add("Offers");
//
//            CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
//            styleConfig.setTabs(tabs);
//            styleConfig.setNavBarTitle("MY INBOX");
//
//            clevertapDefaultInstance.showAppInbox(styleConfig);
//        }
//    }
//
//    @Override
//    public void inboxMessagesDidUpdate() {
//        // Handle inbox message updates if needed
//    }
//}
//
//

package com.henil.test_push;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;

import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

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
            profileUpdate.put("Name", "Vaibhav");
            profileUpdate.put("Identity", 38885);
            profileUpdate.put("Email", "he1yuj@clevertap.com");
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
            /**
             * Data types
             * The value of a property can be of type Date (java.util.Date), an Integer, a
             * Long, a Double,
             * a Float, a Character, a String, or a Boolean.
             *
             * Date object
             * When a property value is of type Date, the date and time are both recorded to
             * the second.
             * This can be later used for targeting scenarios.
             * For e.g. if you are recording the time of the flight as an event property,
             * you can send a message to the user just before their flight takes off.
             */

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

            buttonProfileUpdate.setOnClickListener(v -> {
                HashMap<String, Object> updatedProfile = new HashMap<>();
                updatedProfile.put("Name", "Vaibhav");
                updatedProfile.put("Identity", 4865);
                updatedProfile.put("Prefered Language", "English");
                updatedProfile.put("Email", "naadk@clevertap.com");
                updatedProfile.put("Phone", "+91987654289");
                updatedProfile.put("Gender", "M");
                updatedProfile.put("DOB", new Date());

                ArrayList<String> updatedStuff = new ArrayList<>();
                updatedStuff.add("Watch");
                updatedStuff.add("Sunglasses");
                updatedProfile.put("MyStuff", updatedStuff);

                clevertapDefaultInstance.onUserLogin(updatedProfile);
            });

            buttonProfileUpdate2.setOnClickListener(v -> {
                HashMap<String, Object> updatedProfile = new HashMap<>();
                updatedProfile.put("Name", "Vaibhav2");
                updatedProfile.put("Identity", 4865);
                updatedProfile.put("Email", "naadk@clevertap.com");
                updatedProfile.put("Phone", "+91987654289");
                updatedProfile.put("Prefered Language", "Hindi");
                updatedProfile.put("Gender", "M");
                updatedProfile.put("DOB", new Date());

                ArrayList<String> updatedStuff = new ArrayList<>();
                updatedStuff.add("Watch");
                updatedStuff.add("Sunglasses");
                updatedProfile.put("MyStuff", updatedStuff);

                clevertapDefaultInstance.pushProfile(updatedProfile);
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
                // Example: Extracting Native Display content
                String title = unit.getContents().get(0).getTitle();
                String message = unit.getContents().get(0).getMessage();
                String imageUrl = unit.getContents().get(0).getMedia();

                // Display in your custom UI

                ImageView imageView = findViewById(R.id.native_image);
                Glide.with(this).load(imageUrl).into(imageView);
            }
        }
    }
}
