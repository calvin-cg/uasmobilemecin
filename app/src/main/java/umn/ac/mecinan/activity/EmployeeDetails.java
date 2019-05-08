package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import umn.ac.mecinan.R;

public class EmployeeDetails extends AppCompatActivity {

    TextView tv_employeeDetails_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        Intent intent = getIntent();

        final String username = intent.getStringExtra("username");

        tv_employeeDetails_username.setText(username);

    }
}
