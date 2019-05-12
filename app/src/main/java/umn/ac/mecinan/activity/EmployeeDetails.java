package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import umn.ac.mecinan.R;

public class EmployeeDetails extends AppCompatActivity {

    RatingBar rb_employee_details;
    TextView tv_rating, tv_completed_project;
    TextView tv_employeeDetails_username, tv_employeeDetails_field, tv_employeeDetails_fee;
    TextView tv_employeeDetails_phone, tv_employeeDetails_cat, tv_employeeDetails_idEmployee;
    Button btn_employeeDetails_contact;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        rb_employee_details = findViewById(R.id.rb_employee_details);
        tv_rating = findViewById(R.id.tv_rating);
        tv_completed_project = findViewById(R.id.tv_completed_project);
        tv_employeeDetails_username = findViewById(R.id.tv_employeeDetails_username);
        tv_employeeDetails_phone = findViewById(R.id.tv_employeeDetails_phone);
        //tv_employeeDetails_desc = findViewById(R.id.tv_employeeList_desc);
        tv_employeeDetails_cat = findViewById(R.id.tv_employeeDetails_cat);
        tv_employeeDetails_field = findViewById(R.id.tv_employeeDetails_field);
        tv_employeeDetails_fee = findViewById(R.id.tv_employeeDetails_fee);
        tv_employeeDetails_idEmployee = findViewById(R.id.tv_employeeDetails_idEmployee);
        //tv_employeeDetails_completed = findViewById(R.id.tv_employeeDetails_completed);
        btn_employeeDetails_contact = findViewById(R.id.btn_employeeDetails_contact);


        Intent intent = getIntent();

        Float rating = intent.getFloatExtra("rating", 0.0f);
        Integer completed_project = intent.getIntExtra("completed_project", 0);

        if(completed_project > 0 ) {
            rating = rating / completed_project;
        }

        String username = intent.getStringExtra("username");
        String phone = intent.getStringExtra("phone");
        //final String desc = intent.getStringExtra("desc");
        String category = intent.getStringExtra("category");
        String field = intent.getStringExtra("field");
        String fee = intent.getStringExtra("fee");
        final String idEmployee = intent.getStringExtra("id_employee");
        Log.d("id", idEmployee);

        rb_employee_details.setRating(rating);
        tv_rating.setText(String.format("%.1f", rating));
        tv_completed_project.setText(completed_project.toString() + " Completed Project");
        tv_employeeDetails_username.setText(username);
        tv_employeeDetails_phone.setText(phone);
        //tv_employeeDetails_desc.setText(desc);
        tv_employeeDetails_cat.setText(category);
        tv_employeeDetails_field.setText(field);
        tv_employeeDetails_fee.setText(fee);
        tv_employeeDetails_idEmployee.setText(idEmployee);

        /**
         * BUTTON PROPOSE
         */
        btn_employeeDetails_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProposeProjectActivity.class);
                intent.putExtra("idemployee", idEmployee);
                v.getContext().startActivity(intent);
            }
        });


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
