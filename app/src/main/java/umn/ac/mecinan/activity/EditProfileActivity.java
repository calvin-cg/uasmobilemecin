package umn.ac.mecinan.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import umn.ac.mecinan.R;
import umn.ac.mecinan.model.User;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    final String TAG = "edit_profile";

    private Uri file_path;
    private Uri new_avatar_path;
    private final int PICK_IMAGE_REQUEST = 71;

    private String string_field;
    private String string_category;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser curr_user = auth.getCurrentUser();

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = db.getReference("user");

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference("user_avatar/");

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


        //Log.d("field :", string_field);

        if(string_field == "Information & Technology"){
            Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_it, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter1);
            spinnerCategory.setOnItemSelectedListener(this);
        }

        if(string_field == "Art & Design"){
            Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_ad, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter1);
            spinnerCategory.setOnItemSelectedListener(this);
        }

        if(string_field == "Business"){
            Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_bu, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter1);
            spinnerCategory.setOnItemSelectedListener(this);
        }

        if(string_field == "Public Relation") {
            Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.cat_pr, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(adapter1);
            spinnerCategory.setOnItemSelectedListener(this);
        }

        if (curr_user == null) {
            Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            String FirebaseUsername = curr_user.getUid();
            String FirebaseEmail = curr_user.getEmail();

            Log.d(TAG, "firebase: " + FirebaseUsername);
        }


        /** Get User Data from Bundle **/
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String avatar_path = extras.getString("USER_AVATAR");
        String name = extras.getString("USER_NAME");
        String username = extras.getString("USER_USERNAME");
        String email = extras.getString("USER_EMAIL");
        String phone_number = extras.getString("USER_PHONE_NUMBER");
        String desc = extras.getString("USER_DESC");
        String field = extras.getString("USER_FIELD");
        String category = extras.getString("USER_CATEGORY");
        String fee = extras.getString("USER_FEE");

        ImageView iv_avatar = findViewById(R.id.iv_avatar);
        EditText et_username = findViewById(R.id.editText_ef_username);
        EditText et_desc = findViewById(R.id.editText_ef_decs);
        EditText et_phone = findViewById(R.id.editText_ef_phone);
        EditText et_fee = findViewById(R.id.editText_ef_fee);

        if(avatar_path != null) {
            file_path = android.net.Uri.parse(avatar_path);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file_path);
                iv_avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(username != null) {
            et_username.setText(username);
        }

        if(desc != null) {
            et_desc.setText(desc);
        }

        if(phone_number != null) {
            et_phone.setText(phone_number);
        }

        if(fee != null) {
            et_fee.setText(fee);
        }


        /** Listener for Edit Profile Photo */
        Button btn_edit_avatar = findViewById(R.id.btn_edit_avatar);
        btn_edit_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("avatar_change", "avatar clicked");
                chooseImage();
            }
        });


        /** Listener for Confirm Edit */
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
                    String email = curr_user.getEmail();
                    String field = string_field;
                    String category = string_category;
                    String id = curr_user.getUid();


                    /**Write New Data User **/
                    User user = new User(
                                    email,
                                    username,
                                    desc,
                                    phone,
                                    field,
                                    category,
                                    fee,
                                    id
                            );
                    Map<String, Object> postValues = user.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("username", username);
                    childUpdates.put("desc", desc);
                    childUpdates.put("phoneNumber", phone);
                    childUpdates.put("field", field);
                    childUpdates.put("category", category);
                    childUpdates.put("fee", fee);

                    userRef.child(curr_user.getUid()).updateChildren(childUpdates);


                    /**
                     * Changing profile photo
                     * If there is a changed
                     **/
                    if(new_avatar_path != file_path && new_avatar_path != null) {
                        final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setTitle("Uploading Profile Photo...");
                        progressDialog.show();

                        storageReference = storageReference.child(curr_user.getUid());
                        Log.d("avatar_change", "storageReference: " + storageReference);

                        storageReference.putFile(new_avatar_path)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplication(), "Uploaded", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplication(), "Upload Failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                                    }
                                });
                    } else {
                        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                        finish();
                    }
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

    public void chooseImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Application");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView avatar = findViewById(R.id.iv_avatar);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            new_avatar_path = data.getData();
            Log.d("avatar_change", "path: " + new_avatar_path);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), new_avatar_path);
                avatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
