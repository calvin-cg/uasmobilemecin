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
import android.widget.TextView;

import umn.ac.mecinan.R;

public class EmployeeDetails extends AppCompatActivity {

    TextView tv_employeeDetails_username, tv_employeeDetails_field, tv_employeeDetails_fee, tv_employeeDetails_completed;
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

        final String username = intent.getStringExtra("username");
        final String phone = intent.getStringExtra("phone");
        //final String desc = intent.getStringExtra("desc");
        final String category = intent.getStringExtra("category");
        final String field = intent.getStringExtra("field");
        final String fee = intent.getStringExtra("fee");
        final String idEmployee = intent.getStringExtra("idemployee");
        Log.d("id", idEmployee);
        //final String completed = intent.getStringExtra("completed");

        tv_employeeDetails_username.setText(username);
        tv_employeeDetails_phone.setText(phone);
        //tv_employeeDetails_desc.setText(desc);
        tv_employeeDetails_cat.setText(category);
        tv_employeeDetails_field.setText(field);
        tv_employeeDetails_fee.setText(fee);
        tv_employeeDetails_idEmployee.setText(idEmployee);
        //tv_employeeDetails_completed.setText(completed);

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
