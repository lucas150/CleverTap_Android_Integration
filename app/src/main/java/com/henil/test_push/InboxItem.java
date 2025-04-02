package com.henil.test_push;


import android.content.Context;
import android.content.SharedPreferences;

public class InboxItem {
    private final String messageId;
    private final String title;
    private final String message;
    private final String imageUrl;
    private final String buttonText;
    private final String bodyLink;
    private final String buttonLink;

    private static final String PREFS_NAME = "InboxPrefs"; // SharedPreferences name

    public InboxItem(String messageId, String title, String message, String imageUrl, String buttonText, String bodyLink, String buttonLink) {
        this.messageId = messageId;
        this.title = (title != null && !title.trim().isEmpty()) ? title : "No Title";
        this.message = (message != null && !message.trim().isEmpty()) ? message : "No Message";
        this.imageUrl = (imageUrl != null && !imageUrl.trim().isEmpty()) ? imageUrl : "";
        this.buttonText = (buttonText != null && !buttonText.trim().isEmpty()) ? buttonText : "Click Here";
        this.bodyLink = (bodyLink != null && !bodyLink.trim().isEmpty()) ? bodyLink : "";
        this.buttonLink = (buttonLink != null && !buttonLink.trim().isEmpty()) ? buttonLink : "";
    }

    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getImageUrl() { return imageUrl; }
    public String getButtonText() { return buttonText; }
    public String getBodyLink() { return bodyLink; }
    public String getButtonLink() { return buttonLink; }
    public String getMessageId() { return messageId; }

    // ✅ Check if the message was already viewed
    public boolean isViewed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(messageId, false);
    }

    // ✅ Mark the message as viewed
    public void setViewed(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(messageId, true).apply();
    }

    // ✅ Remove message from SharedPreferences
    public void removeMessage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(messageId).apply();
    }
}









