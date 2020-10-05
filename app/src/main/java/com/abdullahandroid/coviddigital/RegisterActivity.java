package com.abdullahandroid.coviddigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mFirstName;
    private EditText mMiddleName;
    private EditText mLastName;
    private EditText mEmailAddress;
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mSubmitButton;
    private User user;
    private String userId;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private FirebaseFirestore mStore;
    private static final String USER = "user";
    private static final String TAG = "RegisterUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFirstName = (EditText) findViewById(R.id.UserFirstName);
        mMiddleName = (EditText) findViewById(R.id.UserMiddleName);
        mLastName = (EditText) findViewById(R.id.UserLastName);
        mEmailAddress = (EditText) findViewById(R.id.UserEmailAddress);
        mPhoneNumber = (EditText) findViewById(R.id.UserPhoneNumber);
        mPassword = (EditText) findViewById(R.id.UserPassword);
        mConfirmPassword = (EditText) findViewById(R.id.UserConfirmPassword);
        mSubmitButton = (Button) findViewById(R.id.SubmitButton);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mStore = FirebaseFirestore.getInstance();


        // Keyboard sign in action
        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.register_form_finished || id == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });
    }

    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {
        mFirstName.setError(null);
        mEmailAddress.setError(null);

        String firstname = mFirstName.getText().toString();
        String lastname = mLastName.getText().toString();
        String middlename = mMiddleName.getText().toString();
        String phone = mPhoneNumber.getText().toString();
        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(firstname)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }

        if (TextUtils.isEmpty(lastname)) {
            mLastName.setError(getString(R.string.error_field_required));
            focusView = mLastName;
            cancel = true;
        }

        if (TextUtils.isEmpty(phone)) {
            mPhoneNumber.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumber;
            cancel = true;
        }

        if (TextUtils.isEmpty(firstname)) {
            mFirstName.setError(getString(R.string.error_field_required));
            focusView = mFirstName;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailAddress.setError(getString(R.string.error_field_required));
            focusView = mEmailAddress;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailAddress .setError(getString(R.string.error_invalid_email));
            focusView = mEmailAddress;
            cancel = true;
        }

        // Check for a valid Password
        if(TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mConfirmPassword.setError(getString(R.string.error_password_not_matched));
            focusView = mConfirmPassword;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and check focus.
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here

            user = new User(firstname,middlename,lastname,email,password,phone);
            createFireBaseUser();
        }
    }

    private void createFireBaseUser() {
        String email = mEmailAddress.getText().toString();
        String password = mPassword.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUser onComplete:" + task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d(TAG, "createUser Failed." + task.getException());
                    showAlertDialog("Registration attempt failed.");
                } else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateFireBaseUser(user);
                }

            }
        });
    }

    private void updateFireBaseUser(FirebaseUser currentUser) {

        String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String Password) {
        String confirmPassword = mConfirmPassword.getText().toString();
        return confirmPassword.equals(Password);
    }

    public void backToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        finish();
        startActivity(intent);
    }

    private void showAlertDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}