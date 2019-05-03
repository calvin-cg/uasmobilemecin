package umn.ac.mecinan.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;


import umn.ac.mecinan.R;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;

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

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        EditText inputusername = findViewById(R.id.editText_ef_username);
        EditText inputdesc = findViewById(R.id.editText_ef_decs);
        EditText inputfee = findViewById(R.id.editText_ef_fee);

        String username = inputusername.getText().toString().trim();
        String desc = inputdesc.getText().toString().trim();
        String fee = inputfee.getText().toString().trim();

        if(TextUtils.isEmpty(username) && TextUtils.isEmpty(desc) && TextUtils.isEmpty(fee)){
            Toast.makeText(getApplicationContext(), "Enter Username, Describe, and Fee!", Toast.LENGTH_SHORT).show();
            return;
        }

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
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
