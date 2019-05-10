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

import org.w3c.dom.Text;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.Propose;
import umn.ac.mecinan.model.User;

public class ProposeProjectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final String TAG = "propose_project";

    Button btn_cancel, btn_propose;
    private String field;
    private String category;

    private DatabaseReference mDatabase;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser curr_user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_project);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_propose = findViewById(R.id.btn_propose);

        Spinner spinnePurposerField = findViewById(R.id.spinner_propose_Field);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.field_select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnePurposerField.setAdapter(adapter);
        spinnePurposerField.setOnItemSelectedListener(this);

        Spinner spinnerPurposeCategory = findViewById(R.id.spinner_propose_Category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_it, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPurposeCategory.setAdapter(adapter1);
        spinnerPurposeCategory.setOnItemSelectedListener(this);

        btn_cancel.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProposeProjectActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        btn_propose.setOnClickListener(new View.OnClickListener() {
            TextView textname = findViewById(R.id.tv_propose_Name);
            TextView textduration = findViewById(R.id.tv_propose_Duration);
            TextView textprice = findViewById(R.id.tv_propose_Price);
            TextView textdesc = findViewById(R.id.tv_propose_Desc);
            TextView texttitle = findViewById(R.id.tv_propose_Title);

            @Override
            public void onClick(View v) {
                Boolean isEmpty = false;
                EditText inputname = findViewById(R.id.et_propose_Name);
                EditText inputduration = findViewById(R.id.et_propose_Duration);
                EditText inputprice = findViewById(R.id.et_propose_Price);
                EditText inputdesc = findViewById(R.id.et_propose_Desc);
                EditText inputtitle = findViewById(R.id.et_propose_Title);

                String name = inputname.getText().toString().trim();
                String duration = inputduration.getText().toString().trim();
                String price = inputprice.getText().toString().trim();
                String desc = inputdesc.getText().toString().trim();
                String title = inputtitle.getText().toString().trim();

                /** Name Propose Field Validation **/
                if(TextUtils.isEmpty(name)) {
                    textname.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    textname.setTextColor(getResources().getColor(R.color.black));
                }

                /** Ttile Propose Field Validation **/
                if(TextUtils.isEmpty(title)) {
                    texttitle.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    texttitle.setTextColor(getResources().getColor(R.color.black));
                }

                /** Duration Propose Field Validation **/
                if(TextUtils.isEmpty(duration)) {
                    textduration.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    textduration.setTextColor(getResources().getColor(R.color.black));
                }

                /** Fee Propose Field Validation **/
                if(TextUtils.isEmpty(price)) {
                    textprice.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    textprice.setTextColor(getResources().getColor(R.color.black));
                }

                /** Desc Propose Field Validation **/
                if(TextUtils.isEmpty(desc)) {
                    textdesc.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    textdesc.setTextColor(getResources().getColor(R.color.black));
                }

                if(isEmpty) {
                    Toast.makeText(getApplicationContext(), "Fill the required field", Toast.LENGTH_SHORT).show();
                }

                if(!isEmpty) {
                    String idClient = curr_user.getUid();
                    int status = 1;

                    Intent intent = getIntent();
                    String idEmployee = intent.getStringExtra("idemployee");

                    Propose propose = new Propose(
                            name,
                            idClient,
                            idEmployee,
                            field,
                            category,
                            title,
                            duration,
                            price,
                            desc,
                            status
                    );
                    mDatabase.child("propose_project").push().setValue(propose);

                    Intent i = new Intent(ProposeProjectActivity.this, SearchActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner_propose_Field) {
            field = parent.getItemAtPosition(position).toString();
            Log.d(TAG, field);
        }

        if(parent.getId() == R.id.spinner_propose_Category) {
            category = parent.getItemAtPosition(position).toString();
            Log.d(TAG, category);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "nothing selected");
    }
}
