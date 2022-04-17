package com.mobileapp.rugshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 99;

    EditText userNameEditText;
    EditText eMailAddressEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    EditText phoneEditText;
    Spinner spinner;
    RadioGroup accountTypeGroup;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // Bundle bundle = getIntent().getExtras();
        // int secret_key = bundle.getInt("SECRET_KEY");
        // int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        /*if (secret_key != 99) {
            finish();
        }*/

        //userNameEditText = findViewById(R.id.userNameEditText);
        eMailAddressEditText = findViewById(R.id.userEmailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfirmEditText = findViewById(R.id.passwordAgainEditText);
        //phoneEditText = findViewById(R.id.phoneEditText);
        //spinner = findViewById(R.id.phoneSpinner);
        //accountTypeGroup = findViewById(R.id.accountTypeGroup);
        //accountTypeGroup.check(R.id.buyer);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        eMailAddressEditText.setText(email);
        passwordEditText.setText(password);
        passwordConfirmEditText.setText(password);

        //spinner.setOnItemSelectedListener(this);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        //        R.array.phone_labels, android.R.layout.simple_spinner_item);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);

        Log.i(LOG_TAG, "onCreate");
    }

    public void register(View view) {
        //String userName = userNameEditText.getText().toString();
        String email = eMailAddressEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordConfirm = passwordConfirmEditText.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése.");
            return;
        }

        if(TextUtils.isEmpty(email)) {
            eMailAddressEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(password)) {
            passwordEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(passwordConfirm)) {
            passwordConfirmEditText.setError("Cannot be empty");
            return;
        }

        //String phone = phoneEditText.getText().toString();
        //String phoneType = spinner.getSelectedItem().toString();

        //int accountTypeId = accountTypeGroup.getCheckedRadioButtonId();
        //View radioButton = accountTypeGroup.findViewById(accountTypeId);
        //int id = accountTypeGroup.indexOfChild(radioButton);
        //String accountType =  ((RadioButton)accountTypeGroup.getChildAt(id)).getText().toString();

        //Log.i(LOG_TAG, "Regisztrált: " + userName + ", e-mail: " + email);
        // startShopping();
        // TODO: A regisztrációs funkcionalitást meg kellene valósítani egyszer.

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d(LOG_TAG, "User created successfully");
                    startShopping();
                } else {
                    Log.d(LOG_TAG, "User wasn't created successfully:", task.getException());
                    Toast.makeText(RegisterActivity.this, "User wasn't created successfully:", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void cancel(View view) {
        finish();
    }

    private void startShopping(/* registered used class */) {
        Intent intent = new Intent(this, CarpetListActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Log.i(LOG_TAG, selectedItem);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
