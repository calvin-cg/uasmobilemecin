package umn.ac.mecinan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputUsername;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;

    //private static final String TAG = "SigunUp";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnSignIn = (Button) findViewById(R.id.btn_signup_login);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        inputEmail = (EditText) findViewById(R.id.editText_signup_email);
        inputPassword = (EditText) findViewById(R.id.editText_signup_password);
        inputUsername = (EditText) findViewById(R.id.editText_signup_username);

        /*btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });*/

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Enter Email address, Password, and Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Enter Email address, Password, and Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(TextUtils.isEmpty(username)){
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(getApplicationContext(), "Your email address not valid!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                btnSignUp.setVisibility(View.VISIBLE);

                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + " " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignupActivity.this, "Sign Up Success!", Toast.LENGTH_SHORT).show();

                                //email verify
                                final FirebaseUser user = auth.getCurrentUser();

                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(SignupActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                //btnSignUp.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Sign Up failed." + " " + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });


                //store to db
                //passwordnya jangan lupa di enkripsi atau ilangin aja
                User user = new User(
                        username,
                        password,
                        email
                );

                mDatabase.child("user").child(username).setValue(user);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }
}
