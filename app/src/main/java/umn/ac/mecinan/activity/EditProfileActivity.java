package umn.ac.mecinan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final String TAG = "edit_profile";

    private String string_field;
    private String string_category;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser curr_user = auth.getCurrentUser();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = db.getReference("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        Spinner spinnerField = findViewById(R.id.spinnerField);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.field_select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerField.setAdapter(adapter);
        spinnerField.setOnItemSelectedListener(this);

        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_it, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter1);
        spinnerCategory.setOnItemSelectedListener(this);

        final String TAG = "edit_profile";

        final FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

        if (users == null) {
            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String FirebaseUsername = users.getUid();
            String FirebaseEmail = users.getEmail();


            Log.d(TAG, "firebase: " + FirebaseUsername);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference("user");

        /** Get User Data **/
        userRef.addValueEventListener(new ValueEventListener() {
            User dataUser;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange");

                for(DataSnapshot ds: dataSnapshot.getChildren()){

                    if(curr_user.getEmail().equals(ds.getValue(User.class).getEmail())) {
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getUsername());
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getTagline());
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getEmail());
                        Log.d(TAG, "ds: " + ds.getValue(User.class).getPhoneNumber());
                        dataUser = ds.getValue(User.class);

                        EditText et_username = findViewById(R.id.editText_ef_username);
                        EditText et_desc = findViewById(R.id.editText_ef_decs);
                        EditText et_phone = findViewById(R.id.editText_ef_phone);
                        EditText et_fee = findViewById(R.id.editText_ef_fee);

                        if(dataUser.getUsername() != null) {
                            et_username.setText(dataUser.getUsername());
                        }
                        if(dataUser.getDesc() != null) {
                            et_desc.setText(dataUser.getDesc());
                        }
                        if(dataUser.getPhoneNumber() != null) {
                            et_phone.setText(dataUser.getPhoneNumber());
                        }
                        if(dataUser.getFee() != null) {
                            et_fee.setText(dataUser.getFee());
                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read user value.", error.toException());
            }
        });

        Button btnEdit = findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            TextView tv_ef_username = findViewById(R.id.ef_username);
            TextView tv_ef_desc = findViewById(R.id.ef_desc);
            TextView tv_ef_fee = findViewById(R.id.ef_fee);
            TextView tv_ef_phone = findViewById(R.id.ef_phone);

            @Override
            public void onClick(View v) {
                Boolean isEmpty = false;
                EditText inputusername = findViewById(R.id.editText_ef_username);
                EditText inputdesc = findViewById(R.id.editText_ef_decs);
                EditText inputfee = findViewById(R.id.editText_ef_fee);
                EditText inputphone = findViewById(R.id.editText_ef_phone);

                String username = inputusername.getText().toString().trim();
                String desc = inputdesc.getText().toString().trim();
                String fee = inputfee.getText().toString().trim();
                String phone = inputphone.getText().toString().trim();

                /** Username Edit Field Validation **/
                if(TextUtils.isEmpty(username)) {
                    tv_ef_username.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    tv_ef_username.setTextColor(getResources().getColor(R.color.black));
                }

                /** Describe Edit Field Validation **/
                if(TextUtils.isEmpty(desc)) {
                    tv_ef_desc.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    tv_ef_desc.setTextColor(getResources().getColor(R.color.black));
                }

                /** Phone Edit Field Validation **/
                if(TextUtils.isEmpty(fee)) {
                    tv_ef_phone.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    tv_ef_phone.setTextColor(getResources().getColor(R.color.black));
                }

                /** Fee Edit Field Validation **/
                if(TextUtils.isEmpty(fee)) {
                    tv_ef_fee.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    tv_ef_fee.setTextColor(getResources().getColor(R.color.black));
                }

                if(isEmpty) {
                    Toast.makeText(getApplicationContext(), "Fill the required field", Toast.LENGTH_SHORT).show();
                }

                if(!isEmpty){
                    String email = users.getEmail();
                    String field = string_field;
                    String category = string_category;

                    /**Write New Data User **/
                    User user = new User(
                                    email,
                                    username,
                                    desc,
                                    phone,
                                    field,
                                    category,
                                    fee
                            );
                    Map<String, Object> postValues = user.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("username", username);
                    childUpdates.put("desc", desc);
                    childUpdates.put("phoneNumber", phone);
                    childUpdates.put("field", field);
                    childUpdates.put("category", category);
                    childUpdates.put("fee", fee);

                    userRef.child(users.getUid()).updateChildren(childUpdates);


                    startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                    finish();
                }


                Log.d(TAG, "field: " + string_field);
                Log.d(TAG, "category: " + string_category);

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinnerField) {
            string_field = parent.getItemAtPosition(position).toString();
        }

        if(parent.getId() == R.id.spinnerCategory) {
            string_category = parent.getItemAtPosition(position).toString();
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "nothing selected");
    }


}
