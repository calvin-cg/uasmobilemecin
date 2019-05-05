package umn.ac.mecinan.activity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


import umn.ac.mecinan.R;

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

        Button btnEdit = findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            TextView tv_ef_username = findViewById(R.id.ef_username);
            TextView tv_ef_desc = findViewById(R.id.ef_desc);
            TextView tv_ef_fee = findViewById(R.id.ef_fee);

            @Override
            public void onClick(View v) {
                Boolean isEmpty = false;
                EditText inputusername = findViewById(R.id.editText_ef_username);
                EditText inputdesc = findViewById(R.id.editText_ef_decs);
                EditText inputfee = findViewById(R.id.editText_ef_fee);

                String username = inputusername.getText().toString().trim();
                String desc = inputdesc.getText().toString().trim();
                String fee = inputfee.getText().toString().trim();

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

                Log.d(TAG, "field: " + string_field);
                Log.d(TAG, "category: " + string_category);
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String FirebaseUsername = user.getUid();
            String FirebaseEmail = user.getEmail();
        }

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
