package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import umn.ac.mecinan.R;

public class EmployeeDetails extends AppCompatActivity {

    TextView tv_employeeDetails_username, tv_employeeDetails_field, tv_employeeDetails_rate, tv_employeeDetails_completed;
    Button btn_employeeDetails_contact;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        tv_employeeDetails_username = findViewById(R.id.tv_employeeDetails_username);
        tv_employeeDetails_field = findViewById(R.id.tv_employeeDetails_field);
        tv_employeeDetails_rate = findViewById(R.id.tv_employeeDetails_rate);
        tv_employeeDetails_completed = findViewById(R.id.tv_employeeDetails_completed);
        btn_employeeDetails_contact = findViewById(R.id.btn_employeeDetails_contact);

        Intent intent = getIntent();

        final String username = intent.getStringExtra("username");
        final String field = intent.getStringExtra("field");
        final String rate = intent.getStringExtra("rate");
        final String completed = intent.getStringExtra("completed");

        tv_employeeDetails_username.setText(username);
        tv_employeeDetails_field.setText(field);
        tv_employeeDetails_rate.setText("Rate: " + rate);
        tv_employeeDetails_completed.setText(completed);

        /**
         * BOTTOM NAVIGATION
         */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        /** Ubah index untuk aktivasi button page sesuai index */
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigation_home:
                        Intent iHome = new Intent(EmployeeDetails.this, MainActivity.class);
                        startActivity(iHome);
                        break;
                    case R.id.navigation_browse:
                        Intent iBrowse = new Intent(EmployeeDetails.this, SearchActivity.class);
                        startActivity(iBrowse);
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(EmployeeDetails.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        Intent iInbox = new Intent(EmployeeDetails.this, InboxActivity.class);
                        startActivity(iInbox);
                        break;
                    case R.id.navigation_profile:
                        Intent iProfile = new Intent(EmployeeDetails.this, ProfileActivity.class);
                        startActivity(iProfile);
                        break;
                }
                return false;
            }
        });

    }
}
