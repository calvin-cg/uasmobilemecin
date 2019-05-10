package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import umn.ac.mecinan.R;

public class EmployeeDetails extends AppCompatActivity {

    TextView tv_employeeDetails_username, tv_employeeDetails_field, tv_employeeDetails_rate, tv_employeeDetails_completed;
    Button btn_employeeDetails_contact;

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
        tv_employeeDetails_rate.setText(rate);
        tv_employeeDetails_completed.setText(completed);

    }
}
