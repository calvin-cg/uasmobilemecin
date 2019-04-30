package umn.ac.mecinan;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Retrieve Data
        retrieveUser();
        retrieveTagline();
        try{
            retrieveAvatar();
        } catch(IOException e) {
            e.printStackTrace();
        }

        /**
         * BOTTOM NAVIGATION
         */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        /** Ubah index untuk aktivasi button page sesuai index */
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        Intent iHome = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(iHome);
                        break;
                    case R.id.navigation_browse:
                        Intent iBrowse = new Intent(ProfileActivity.this, SearchActivity.class);
                        startActivity(iBrowse);
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(ProfileActivity.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        Intent iInbox = new Intent(ProfileActivity.this, InboxActivity.class);
                        startActivity(iInbox);
                        break;
                    case R.id.navigation_profile:
                        /*Intent iProfile = new Intent(FAQActivity.this, ProfileActivity.class);
                        startActivity(iProfile);*/
                        Toast.makeText(getApplicationContext(), "Profile Selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
    }

    public void retrieveUser() {
        final String TAG = "retrieve";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            List<User> user = new ArrayList<User>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user.add(ds.getValue(User.class));
                }

                Log.d(TAG, "Users are: " + user);

                Log.d(TAG, "username: " + user.get(0).getEmail());
                TextView username = findViewById(R.id.textView8);
                TextView name = findViewById(R.id.textView15);

                username.setText("Hi, " + user.get(0).getUsername());
                name.setText(user.get(0).getUsername());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void retrieveTagline() {
        final String TAG = "retrieve";
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user");

        userRef.addValueEventListener(new ValueEventListener() {
            List<User> user = new ArrayList<User>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    user.add(ds.getValue(User.class));
                }

                Log.d(TAG, "Taglines are: " + user);

                Log.d(TAG, "Tagline: " + user.get(0).getTagline());
                TextView tagline = findViewById(R.id.textView9);
                tagline.setText(user.get(0).getTagline());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void retrieveAvatar() throws IOException {
        final String TAG = "retrieve_avatar";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference mAvatarRef;
        mAvatarRef = storage.getReference("user_avatar/default.jpg");


        Log.d(TAG, "snap: " + "start");

        final File localFile = File.createTempFile("images", "jpg");
        mAvatarRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        ImageView avatar = findViewById(R.id.imageView);

                        Log.d(TAG, "snap: " + taskSnapshot);
                        Glide.with(getApplicationContext())
                                .load(localFile)
                                .into(avatar);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "snap: " + "failed");
            }
        });
    }
}
