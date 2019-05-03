package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import umn.ac.mecinan.adapter.EmployeeAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    /**
     * DECLARATION - SPINNER FIELD
     */
    private Spinner browseSpinnerField;
    private TextView testestes;

    /**
     * DECLARATION - DATABASE EMPLOYEE
     */
    RecyclerView recyclerView;
    EmployeeAdapter employeeAdapter;
    List<User> employeeList;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /**
         * DECLARATION - SPINNER FIELD
         */
        browseSpinnerField = findViewById(R.id.browseSpinnerField);
        ArrayAdapter fieldAdapter = ArrayAdapter.createFromResource(this, R.array.field_view, android.R.layout.simple_spinner_item);
        fieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        browseSpinnerField.setAdapter(fieldAdapter);
        browseSpinnerField.setSelection(4);
        browseSpinnerField.setOnItemSelectedListener(this);

        /**
         * RECYCLER VIEW - EMPLOYEE
         */
        employeeList = new ArrayList<>();
        recyclerView = findViewById(R.id.employeeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeList.add(
                new User(
                        "abed",
                        "abed@abed.com",
                        "abed is my name",
                        "081806542abed"
                )
        );

        employeeList.add(
                new User(
                        "nego",
                        "nego@nego.com",
                        "nego is my name",
                        "08180654nego"
                )
        );

        employeeList.add(
                new User(
                        "vito",
                        "vito@vito.com",
                        "vito is my name",
                        "08180654vito"
                )
        );

        employeeAdapter = new EmployeeAdapter(this, employeeList);
        recyclerView.setAdapter(employeeAdapter);

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
                        Intent iHome = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(iHome);
                        break;
                    case R.id.navigation_browse:
                        /*Intent iBrowse = new Intent(FAQActivity.this, SearchActivity.class);
                        startActivity(iBrowse);*/
                        Toast.makeText(getApplicationContext(), "Search Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_faq:
                        Intent iFAQ = new Intent(SearchActivity.this, FAQActivity.class);
                        startActivity(iFAQ);
                        break;
                    case R.id.navigation_inbox:
                        Intent iInbox = new Intent(SearchActivity.this, InboxActivity.class);
                        startActivity(iInbox);
                        break;
                    case R.id.navigation_profile:
                        Intent iProfile = new Intent(SearchActivity.this, ProfileActivity.class);
                        startActivity(iProfile);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedField = parent.getItemAtPosition(position).toString();
        testestes = findViewById(R.id.testestes);
        testestes.setText(selectedField);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
