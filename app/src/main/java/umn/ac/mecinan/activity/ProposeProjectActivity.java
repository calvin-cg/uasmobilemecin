package umn.ac.mecinan.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.Mail;
import umn.ac.mecinan.model.Project;
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

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propose_project);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_propose = findViewById(R.id.btn_propose);

        ImageView imageCalander = findViewById(R.id.imageCalender);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        imageCalander.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setDate(v);
                showDate(year, month+1, day);
            }
        });
        showDate(year, month+1, day);

        Spinner spinnePurposerField = findViewById(R.id.spinner_propose_Field);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.field_select, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnePurposerField.setAdapter(adapter);
        spinnePurposerField.setOnItemSelectedListener(this);

        btn_cancel.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProposeProjectActivity.this, SearchActivity.class);
                finish();
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
                EditText inputprice = findViewById(R.id.et_propose_Price);
                EditText inputdesc = findViewById(R.id.et_propose_Desc);
                EditText inputtitle = findViewById(R.id.et_propose_Title);
                EditText inputDuration = findViewById(R.id.et_propose_Duration);

                String name = inputname.getText().toString().trim();
                int price = Integer.parseInt(inputprice.getText().toString().trim());
                String desc = inputdesc.getText().toString().trim();
                String title = inputtitle.getText().toString().trim();
                String duration = inputDuration.getText().toString().trim();

                Log.d("duration", duration);
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

                /** Ttile Propose Field Validation **/
                if(TextUtils.isEmpty(duration)) {
                    textduration.setTextColor(getResources().getColor(R.color.brink_pink));
                    isEmpty = true;
                } else {
                    textduration.setTextColor(getResources().getColor(R.color.black));
                }

                /** Fee Propose Field Validation **/
                if(inputprice.getText().toString().trim().length() < 0) {
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
                    int status = 0;
                    float rating = 5;

                    Intent intent = getIntent();
                    String idEmployee = intent.getStringExtra("idemployee");
                    String idProject = mDatabase.push().getKey();
                    //String idProject = null;

                    Project project = new Project(
                            idProject,
                            title,
                            idEmployee,
                            idClient,
                            field,
                            category,
                            desc,
                            price,
                            status,
                            rating,
                            duration
                    );

                    mDatabase.child("project").child(idProject).setValue(project);


                    /**
                     * Set variable for mail
                     */
                    Mail mail = new Mail();
                    mail.setMailType(1);
                    mail.setMailIsRead(false);
                    mail.setMailCategory("Work");
                    mail.setMailTitle("Project Proposal");
                    mail.setMailContent(curr_user.getEmail() + " Has proposed a new project to You. Please check and consider to accept or reject it.");
                    mail.setMailReceivedDate(null);
                    mail.setProjectName(title);
                    mail.setMailRecipient(idEmployee);
                    mail.setMailSender(curr_user.getUid());
                    mail.setIdProject(idProject);
                    mail.setProjectName(title);
                    mail.setProjectField(field);
                    mail.setProjectCategory(category);
                    mail.sendMail(mail);

                    finish();
                }

            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        EditText inputduration = findViewById(R.id.et_propose_Duration);
        inputduration.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spinner_propose_Field) {
            field = parent.getItemAtPosition(position).toString();

            if(position == 0){
                spinnerCatIT();
            }
            if(position == 1){
                spinnerCatAD();
            }
            if(position == 2){
                spinnerCatBU();
            }
            if(position == 3){
                spinnerCatPR();
            }
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

    public void spinnerCatIT(){
        Spinner spinnerProposeCategory = findViewById(R.id.spinner_propose_Category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_it, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProposeCategory.setAdapter(adapter1);
        spinnerProposeCategory.setOnItemSelectedListener(this);
    }

    public void spinnerCatAD(){
        Spinner spinnerProposeCategory = findViewById(R.id.spinner_propose_Category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_ad, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProposeCategory.setAdapter(adapter1);
        spinnerProposeCategory.setOnItemSelectedListener(this);
    }

    public void spinnerCatBU(){
        Spinner spinnerProposeCategory = findViewById(R.id.spinner_propose_Category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_bu, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProposeCategory.setAdapter(adapter1);
        spinnerProposeCategory.setOnItemSelectedListener(this);
    }

    public void spinnerCatPR(){
        Spinner spinnerProposeCategory = findViewById(R.id.spinner_propose_Category);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_pr, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProposeCategory.setAdapter(adapter1);
        spinnerProposeCategory.setOnItemSelectedListener(this);
    }
}
