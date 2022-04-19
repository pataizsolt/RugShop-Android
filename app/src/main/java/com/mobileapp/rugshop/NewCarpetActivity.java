package com.mobileapp.rugshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobileapp.rugshop.model.Carpet;

public class NewCarpetActivity extends AppCompatActivity {
    private static final String LOG_TAG = NewCarpetActivity.class.getName();
    private FirebaseUser user;

    private FirebaseFirestore mFirestore;
    private CollectionReference mCarpets;

    EditText carpetNameEditText;
    EditText colorEditText;
    EditText typeEditText;
    EditText widthEditText;
    EditText lengthEditText;
    EditText priceEditText;
    EditText stockEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }
        setContentView(R.layout.activity_new_carpet);

        carpetNameEditText = findViewById(R.id.carpetName);
        colorEditText= findViewById(R.id.color);
        typeEditText= findViewById(R.id.type);
        widthEditText= findViewById(R.id.width);
        lengthEditText= findViewById(R.id.length);
        priceEditText= findViewById(R.id.price);
        stockEditText= findViewById(R.id.stock);

        mFirestore = FirebaseFirestore.getInstance();
        mCarpets = mFirestore.collection("Carpets");

    }
    public void cancel(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);



        Intent intent = new Intent(this, CarpetListActivity.class);
        startActivity(intent);
    }

    public void request(View view) {
        String carpetName = carpetNameEditText.getText().toString();
        String color = colorEditText.getText().toString();
        String type = typeEditText.getText().toString();
        String width = widthEditText.getText().toString();
        String length = lengthEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String stock = stockEditText.getText().toString();

        if(TextUtils.isEmpty(carpetName)) {
            carpetNameEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(color)) {
            colorEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(type)) {
            typeEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(width)) {
            widthEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(length)) {
            lengthEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(price)) {
            priceEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(stock)) {
            stockEditText.setError("Cannot be empty");
            return;
        }
        Integer widthInt = Integer.parseInt(width);
        Integer lengthInt = Integer.parseInt(length);
        Integer priceInt = Integer.parseInt(price);
        Integer stockInt = Integer.parseInt(stock);

        mCarpets.add(new Carpet(carpetName,color,type,widthInt,lengthInt,priceInt,stockInt,2131230821));
        Log.i(LOG_TAG, "New carpet added ");

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Returning to the shop...", Toast.LENGTH_LONG).show();
    }
}