package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.adapter.MainProjectsPageAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public class MainActivity extends AppCompatActivity {

    /**
    * DECLARATION - TAB LAYOUT
    * */
    TabLayout tabLayout;
    TabItem tabOnGoingProjects;
    TabItem tabPastProjects;
    ViewPager tabViewPager;
    MainProjectsPageAdapter projectsPageAdapter;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();

/*        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("project");
        String key = ref.push().getKey();

        Project project = new Project(
            key,
            "Project w/ Status 4 (punya Devi)",
            "C35GovM3FAcHq1M0j9LesmeWinz1",
            "lmUOraYXRRYuqWzAJrjrU4DCEr63",
            "IT",
            "Website",
            "Project dengan status 4 (punya Devi)",
            5000,
            4,
            5
        );

        Log.d("manual_input", "id: " + project.getIdProject());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, project);
        ref.updateChildren(childUpdates);*/


        Log.d("current_user", "curruser UID: " + currentUser.getUid());
        Log.d("current_user", "curruser photo: " + currentUser.getPhotoUrl());

        currentUser.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult getTokenResult) {
                String idToken = getTokenResult.getToken();

                Log.d("current_user", "curruser token: " + idToken);
            }
        });

        /*
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("project");
        String key = ref.push().getKey();

        Project project = new Project(
            key,
            "Project w/ Status 4 (client confirm)",
            "C35GovM3FAcHq1M0j9LesmeWinz1",
            "lmUOraYXRRYuqWzAJrjrU4DCEr63",
            "IT",
            "Website",
            "Project dengan status 4 (for employee)",
            5000,
            0,
            5
        );

        Log.d("manual_input", "id: " + project.getIdProject());
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, project);
        ref.updateChildren(childUpdates);
        */

        Log.d("EZRA", "Start Main");

        /**
         * TAB LAYOUT
         */
        tabLayout = findViewById(R.id.tabLayout);
        tabOnGoingProjects = findViewById(R.id.tabLeft);
        tabPastProjects = findViewById(R.id.tabRight);
        tabViewPager = findViewById(R.id.viewPager);

        projectsPageAdapter = new MainProjectsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        tabViewPager.setAdapter(projectsPageAdapter);
        tabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /**
         * BOTTOM NAVIGATION
         */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        Menu bottomMenu = bottomNavigationView.getMenu();
        /** Ubah index untuk aktivasi button page sesuai index */
        MenuItem bottomMenuItem = bottomMenu.getItem(0);
        bottomMenuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        /*Intent iHome = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(iHome);*/
                        Toast.makeText(getApplicationContext(), "Home Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_browse:
                        Intent iBrowse = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(iBrowse);
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(MainActivity.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        Intent iInbox = new Intent(MainActivity.this, InboxActivity.class);
                        startActivity(iInbox);
                        break;
                    case R.id.navigation_profile:
                        Intent iProfile = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(iProfile);
                        break;
                }
                return false;
            }
        });

        Log.d("EZRA", "End Main");
    }
}
