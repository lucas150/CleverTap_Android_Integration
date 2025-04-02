package com.henil.test_push;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InboxMessageListener;
import com.clevertap.android.sdk.inbox.CTInboxMessage;
import com.clevertap.android.sdk.inbox.CTInboxMessageContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class CustomInbox extends AppCompatActivity implements InboxMessageListener {


    // CleverTap instance for managing the app inbox
    private CleverTapAPI cleverTapDefaultInstance;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<InboxItem> inboxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_inbox);

        // Apply window insets for a full-screen experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize CleverTap API instance
        cleverTapDefaultInstance = CleverTapAPI.getDefaultInstance(this);
        cleverTapDefaultInstance.setCTInboxMessageListener(this);
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable back navigation
            getSupportActionBar().setTitle("Custom App Inbox"); // Set the title of the toolbar
        }

        // Handle back navigation click
        toolbar.setNavigationOnClickListener(v -> finish());

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set up a vertical list

        // Initialize the CleverTap Inbox if instance is available
        if (cleverTapDefaultInstance != null) {
            cleverTapDefaultInstance.initializeInbox();
            Log.d("CleverTap", "Inbox is Initialized");
            loadInboxMessages(); // Load inbox messages
        } else {
            Log.d("CleverTap", "CleverTap instance is null!");
        }

        // Enable swipe-to-delete functionality
        attachSwipeToDelete();
    }

    // Load inbox messages from CleverTap
    private void loadInboxMessages() {
        Log.d("CleverTap", "Fetching inbox messages...");
        inboxList = new ArrayList<>();

        if (cleverTapDefaultInstance != null) {
            List<CTInboxMessage> messages = cleverTapDefaultInstance.getAllInboxMessages();

            for (CTInboxMessage message : messages) {
                JSONObject payload = message.getData();
                Log.d("InboxPayload", "Inbox Message Payload: " + payload.toString());

                boolean hasDefaultTag = false;

                try {
                    if (payload.has("tags")) {
                        JSONArray tagsArray = payload.getJSONArray("tags");

                        for (int i = 0; i < tagsArray.length(); i++) {
                            if ("default".equalsIgnoreCase(tagsArray.getString(i))) {
                                hasDefaultTag = true;
                                break; // Stop checking once found
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e("InboxActivity", "Error parsing tags: " + e.getMessage());
                }

                // Only process messages that contain the "default" tag
                if (hasDefaultTag) {
                    String messageId = message.getMessageId();
                    String title = message.getInboxMessageContents().get(0).getTitle();
                    String messageText = message.getInboxMessageContents().get(0).getMessage();
                    String imageUrl = message.getInboxMessageContents().get(0).getMedia();
                    String buttonText = "Click Here";
                    String bodyLink = "";
                    String buttonLink = "";

                    try {
                        if (message.getInboxMessageContents() != null && !message.getInboxMessageContents().isEmpty()) {
                            CTInboxMessageContent content = message.getInboxMessageContents().get(0);

                            // Extract button text and action URL if available
                            if (content.getLinks() != null && content.getLinks().length() > 0) {
                                JSONObject firstLink = content.getLinks().getJSONObject(0);
                                if (firstLink.has("text") && !firstLink.getString("text").isEmpty()) {
                                    buttonText = firstLink.getString("text");
                                }
                            }

                            if (content.getActionUrl() != null && !content.getActionUrl().isEmpty()) {
                                bodyLink = content.getActionUrl();
                            }

                            // Extract button link if available
                            if (content.getLinks() != null && content.getLinks().length() > 0) {
                                JSONObject firstLink = content.getLinks().getJSONObject(0);
                                if (firstLink.has("url")) {
                                    JSONObject urlObject = firstLink.getJSONObject("url");
                                    if (urlObject.has("android")) {
                                        JSONObject androidObject = urlObject.getJSONObject("android");
                                        if (androidObject.has("text")) {
                                            buttonLink = androidObject.getString("text");
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e("InboxActivity", "Error parsing links: " + e.getMessage());
                    }

                    // Add message details to the inbox list
                    inboxList.add(new InboxItem(messageId, title, messageText, imageUrl, buttonText, bodyLink, buttonLink));
                    Log.d("FilteredInbox", "Added message with 'default' tag: " + title);
                }
            }
        }

        // Set up RecyclerView adapter with the filtered messages
        adapter = new Adapter(this, inboxList);
        recyclerView.setAdapter(adapter);
    }


    // Attach swipe-to-delete functionality to RecyclerView
    private void attachSwipeToDelete() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private final Drawable deleteIcon = ContextCompat.getDrawable(CustomInbox.this, R.drawable.ic_back_arrow);
            private final ColorDrawable background = new ColorDrawable(Color.RED);

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (position < 0 || position >= inboxList.size()) {
                    Log.e("InboxActivity", "Invalid position for swipe delete: " + position);
                    return;
                }

                String messageId = inboxList.get(position).getMessageId();

                // Delete the message from CleverTap inbox
                if (cleverTapDefaultInstance != null) {
                    cleverTapDefaultInstance.deleteInboxMessage(messageId);
                    Log.d("CleverTap", "Deleted message: " + messageId);
                }

                // Remove the message from the list and update UI
                inboxList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(CustomInbox.this, "Message Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;

                if (dX > 0) { // Swiping to the right
                    background.setBounds(itemView.getLeft(), itemView.getTop(), (int) dX, itemView.getBottom());
                    deleteIcon.setBounds(itemView.getLeft() + iconMargin, itemView.getTop() + iconMargin,
                            itemView.getLeft() + iconMargin + deleteIcon.getIntrinsicWidth(),
                            itemView.getBottom() - iconMargin);
                } else { // Swiping to the left
                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                    deleteIcon.setBounds(itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth(), itemView.getTop() + iconMargin,
                            itemView.getRight() - iconMargin, itemView.getBottom() - iconMargin);
                }

                // Draw background and delete icon
                background.draw(c);
                deleteIcon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };



        // Attach swipe gesture to RecyclerView
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onInboxItemClicked(CTInboxMessage ctInboxMessage, int i, int i1) {
        Log.i("CleverTap", "InboxItemClicked at page-index " + i + " with button-index " + i1);

    }
}