package com.example.swipe101_manchester;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView; 
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 videoPager;
    private List<Video> videoList = new ArrayList<>();
    private Button startButton;
    private TextView videoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //set to home layout

        //scale and pad video
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        startButton = findViewById(R.id.startButton);
        videoTextView = findViewById(R.id.videoTextView); //create TextView
        videoPager = findViewById(R.id.videoPager);

        //set up the click listener for the button
        startButton.setOnClickListener(v -> {
            readData(); //readData method call
            videoPager.setVisibility(View.VISIBLE); //view page after click
        });
    }

    private void readData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference videoRef = database.getReference("videos");
    //firebase database
        HashMap<String, Video> videoMap = new HashMap<>();

        videoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                videoList.clear(); //clear the list
                videoMap.clear(); //clear the map for fresh data

                //check if the snapshot exists
                if (dataSnapshot.exists()) {
                    StringBuilder allValues = new StringBuilder();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Video video = snapshot.getValue(Video.class);
                        //map values
                        String key = snapshot.getKey();
                        videoMap.put(key, video); //add it to the map
                        videoList.add(video); //add to the list for the adapter

                        allValues.append(video.getTitle()).append('\n'); // Append video title or any property
                    }

                    //text of the TextView to show all video titles
                    videoTextView.setText(allValues.toString());

                    // Debugging log
                    Log.d("MainActivity", "Number of videos fetched: " + videoList.size());

                    //create the adapter after data is fetched
                    VideoAdapter adapter = new VideoAdapter(videoList);
                    videoPager.setAdapter(adapter);
                    videoPager.setVisibility(View.VISIBLE); // Make the ViewPager2 visible
                } else {
                    Log.d("MainActivity", "No videos available.");
                    videoTextView.setText("No videos available.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("MainActivity", "Error fetching videos: " + databaseError.getMessage());
            }
        });
    }
}