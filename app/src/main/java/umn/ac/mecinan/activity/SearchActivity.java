package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import umn.ac.mecinan.adapter.EmployeeAdapter;
import umn.ac.mecinan.R;
import umn.ac.mecinan.adapter.ProjectsViewAdapter;
import umn.ac.mecinan.listener.EmployeeAdapterListener;
import umn.ac.mecinan.listener.OnGetEmployeeListener;
import umn.ac.mecinan.listener.OnGetProjectDataListener;
import umn.ac.mecinan.listener.OnGetUserInProjectListener;
import umn.ac.mecinan.model.ButtonProject;
import umn.ac.mecinan.model.Project;
import umn.ac.mecinan.model.User;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, EmployeeAdapterListener {

    /**
     * DECLARATION - SPINNER FIELD
     */
    private Spinner browseSpinnerField;

    /**
     * DECLARATION - BOTTOM NAVIGATION
     */
    BottomNavigationView bottomNavigationView;

    /**
     * DECLARATION - SEARCH VIEW
     */
    List<User> listEmployee = new ArrayList<>();
    EmployeeAdapter employeeAdapter;
    String filterName;

    //EmployeeAdapterListener employeeAdapterListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
         * SEARCH
         */
        SearchView searchName = findViewById(R.id.searchViewName);
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                filterName = newText;

                RecyclerView recyclerView = findViewById(R.id.employeeRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                if(employeeAdapter == null) {
                    employeeAdapter = new EmployeeAdapter(getApplication(), listEmployee, employeeAdapterListener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(employeeAdapter);
                } else {
                    employeeAdapter.getFilter().filter(filterName);
                }

                return false;
            }
        });


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
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        final String selectedField = parent.getItemAtPosition(position).toString();

        /**
         * RECYCLER VIEW - EMPLOYEE
         */
        User employee = new User();
        employee.retrieveEmployee(new OnGetEmployeeListener() {
            final String TAG = "retrieve_employee";

            TextView tvEmpty = findViewById(R.id.tvEmptyEmployee);
            RecyclerView recyclerView = findViewById(R.id.employeeRecyclerView);
            ProgressBar pbar = findViewById(R.id.pBar);

            @Override
            public void onStart() {
                tvEmpty.setText(getString(R.string.loading_employee));
                pbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onDataChange(User user) {
                Log.d(TAG, "position: " + position);

                if (position == 4 || user.getField().equals(selectedField)){
                    listEmployee.add(user);
                }

                tvEmpty.setVisibility(View.GONE);
                pbar.setVisibility(View.GONE);
            }

            @Override
            public void onSuccess() {
                List<User> listEmployeeUpdate = new ArrayList<>(listEmployee);

                employeeAdapter = new EmployeeAdapter(getApplication(), listEmployeeUpdate, employeeAdapterListener);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(employeeAdapter);

                if(filterName != null) {
                    employeeAdapter.getFilter().filter(filterName);
                }
                listEmployee.clear();

                pbar.setVisibility(View.GONE);

                Log.d("Test","Masuk sini");
                if(employeeAdapter.getItemCount() <= 0) {
                    tvEmpty.setText(getString(R.string.empty_employee));
                    tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                Log.d(TAG, "dbError: " + databaseError);
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*
     * Method: employeeSelected
     * Desc: Called when an employee is clicked in Search Activity
     * Params: @User employee
     * Result: Intent to EmployeeDetails Activity
    */
    public void employeeSelected(User employee) {

        // Make intent
        Intent intent = new Intent(SearchActivity.this, EmployeeDetails.class);

        // Put intent extra details to be sent
        intent.putExtra("username", employee.getUsername());

        // Start activity
        startActivity(intent);

    } // End of employeeSelected() method

} // End of SearchActivity class
