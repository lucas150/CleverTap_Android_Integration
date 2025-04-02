package com.henil.test_push;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity implements DisplayUnitListener {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondactivity);
        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        clevertapDefaultInstance.setDisplayUnitListener(this);
        clevertapDefaultInstance.pushEvent("Native Display");
//        clevertapDefaultInstance.resumeInAppNotifications();

        ImageView nativedisplay1 = findViewById(R.id.native1);
        ImageView nativedisplay2 = findViewById(R.id.native2);

    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> displayUnits) {
        if (displayUnits != null && !displayUnits.isEmpty()) {
            for (CleverTapDisplayUnit unit : displayUnits) {
                if (unit.getContents() != null && !unit.getContents().isEmpty()) {
                    String title = unit.getContents().get(0).getTitle();
                    String message = unit.getContents().get(0).getMessage();
                    String imageUrl = unit.getContents().get(0).getMedia();

                    ImageView imageView = findViewById(R.id.native1);
                    Glide.with(this).load(imageUrl).into(imageView);

                }
            }
        }
    }

}
