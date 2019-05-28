package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;
import umn.ac.mecinan.listener.OnGetUserAvatarDataListener;
import umn.ac.mecinan.listener.OnGetUserDataListener;

public class ProfileActivity extends AppCompatActivity {

    User user = new User();
    final String TAG = "retrieve_profile";

    /**
     * DECLARATION - CHOOSE IMAGE
     */
    private Uri file_path;
    private final int PICK_IMAGE_REQUEST = 71;

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
                final RatingBar rb_employee_profile = findViewById(R.id.rb_employee_profile);
                TextView tv_rating = findViewById(R.id.tv_rating);
                TextView tvName = findViewById(R.id.tvName);
                TextView tv_completed_project = findViewById(R.id.tv_completed_project);
                TextView tvName2 = findViewById(R.id.tvName2);
                TextView tvEmail = findViewById(R.id.tvEmail);
                TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
                TextView tvDesc = findViewById(R.id.tvDesc);
                TextView tvField = findViewById(R.id.tvField);
                TextView tvCategory = findViewById(R.id.tvCategory);
                TextView tvFee = findViewById(R.id.tvFee);
                Integer completed_project = user.getTotalProjectCompleted();
                Float rating;

                if(completed_project > 0) {
                    rating = user.getRatingEmployee() / completed_project;
                } else {
                    rating = user.getRatingEmployee();
                }

                if(completed_project > 0) {
                    tv_rating.setText(String.format("%.1f", rating));
                }

                rb_employee_profile.setRating(rating);
                tv_completed_project.setText(completed_project.toString() + " Completed Project");
                tvName.setText("Hi, " + user.getUsername() + "!");
                tvName2.setText(user.getUsername());
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
            user.retrieveAvatar(curr_user.getUid(), new OnGetUserAvatarDataListener() {
                @Override
                public void onStart() {

                }

                @Override
                public void onSuccess(File file) {
                    ImageView avatar = findViewById(R.id.imageView);

                    file_path = android.net.Uri.parse(file.toURI().toString());
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file_path);
                        avatar.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                if(file_path == null) {
                    Toast.makeText(getApplicationContext(), "Please Wait until Profile Photo is Loaded", Toast.LENGTH_SHORT).show();
                } else {
                    TextView tvName = findViewById(R.id.tvName);
                    TextView tvName2 = findViewById(R.id.tvName2);
                    TextView tvEmail = findViewById(R.id.tvEmail);
                    TextView tvPhoneNumber = findViewById(R.id.tvPhoneNumber);
                    TextView tvDesc = findViewById(R.id.tvDesc);
                    TextView tvField = findViewById(R.id.tvField);
                    TextView tvCategory = findViewById(R.id.tvCategory);
                    TextView tvFee = findViewById(R.id.tvFee);

                    Bundle extras = new Bundle();
                    String avatar_path = file_path.toString();
                    String name = tvName.getText().toString();
                    String username = tvName2.getText().toString();
                    String email = tvEmail.getText().toString();
                    String phone_number = tvPhoneNumber.getText().toString();
                    String desc = tvDesc.getText().toString();
                    String field = tvField.getText().toString();
                    String category = tvCategory.getText().toString();
                    String fee = tvFee.getText().toString();

                    /** Storing to bundle */
                    extras.putString("USER_AVATAR", avatar_path);
                    extras.putString("USER_NAME", name);
                    extras.putString("USER_USERNAME", username);
                    extras.putString("USER_EMAIL", email);
                    extras.putString("USER_PHONE_NUMBER", phone_number);
                    extras.putString("USER_DESC", desc);
                    extras.putString("USER_FIELD", field);
                    extras.putString("USER_CATEGORY", category);
                    extras.putString("USER_FEE", fee);

                    Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                    intent.putExtras(extras);

                    startActivity(intent);
                }
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
