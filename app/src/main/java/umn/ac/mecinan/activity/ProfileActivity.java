package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.io.IOException;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;
import umn.ac.mecinan.listener.OnGetUserAvatarDataListener;
import umn.ac.mecinan.listener.OnGetUserDataListener;

public class ProfileActivity extends AppCompatActivity {

    User user = new User();
    final String TAG = "retrieve_profile";

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /**
         * Logout Broadcast Receiver
         */
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("umn.ac.mecinan.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive","Logout in progress");
                finish();
            }
        }, intentFilter);

        /**
         * Retrieving Profile Data from Firebase Storage
         */
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser curr_user = auth.getCurrentUser();

        /**Retrieve Profile**/
        user.retrieveProfile(curr_user, new OnGetUserDataListener() {
            final String TAG = "retrieve_profile";

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(User user) {
                TextView tvName = findViewById(R.id.tvName);
                //TextView tvTagline = findViewById(R.id.tvTagline);
                TextView tvEmail = findViewById(R.id.tvEmail);
                TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
                TextView tvDesc = findViewById(R.id.tvDesc);
                TextView tvField = findViewById(R.id.tvField);
                TextView tvCategory = findViewById(R.id.tvCategory);
                TextView tvFee = findViewById(R.id.tvFee);


                tvName.setText(user.getUsername());
                //tvTagline.setText(user.getTagline());
                tvEmail.setText(user.getEmail());
                tvPhoneNumber.setText(user.getPhoneNumber());
                tvDesc.setText(user.getDesc());
                tvField.setText(user.getField());
                tvCategory.setText(user.getCategory());
                tvFee.setText(user.getFee());


                if(user.getDesc() == null) {
                    tvDesc.setVisibility(View.GONE);
                }
                if(user.getField() == null) {
                    tvField.setVisibility(View.GONE);
                }
                if(user.getCategory() == null) {
                    tvCategory.setVisibility(View.GONE);
                }
                if(user.getFee() == null) {
                    tvFee.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });

        /**Retrieve Avatar**/
        try{
            user.retrieveAvatar(curr_user, new OnGetUserAvatarDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File file) {
                    ImageView avatar = findViewById(R.id.imageView);

                    Glide.with(getApplicationContext())
                            .load(file)
                            .into(avatar);
                }

                @Override
                public void onFailed(Exception exception) {
                    Log.d(TAG, "Exception: " + exception);
                }
            });
        } catch(IOException e) {
            e.printStackTrace();
        }


        /**
         * Button Logout
         */
        Button btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent();
                logoutIntent.setAction("umn.ac.mecinan.ACTION_LOGOUT");
                sendBroadcast(logoutIntent);

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });


        /**
         * Button Edit Profile
         */
        Button btnEditProfile = findViewById(R.id.btn_editprofile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });


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


}
