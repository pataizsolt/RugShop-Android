package com.mobileapp.rugshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;
import com.mobileapp.rugshop.adapter.CarpetAdapter;
import com.mobileapp.rugshop.model.Carpet;

import java.util.ArrayList;

public class CarpetListActivity extends AppCompatActivity {
    private static final String LOG_TAG = CarpetListActivity.class.getName();
    private FirebaseUser user;

    private RecyclerView mRecyclerView;
    private ArrayList<Carpet> mCarpetData;
    private CarpetAdapter mAdapter;
    private Integer itemLimit = 5;




    private FirebaseFirestore mFirestore;
    private CollectionReference mCarpets;

    private boolean viewRow = true;


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


        setContentView(R.layout.activity_item_list);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));

        mCarpetData = new ArrayList<>();

        mAdapter = new CarpetAdapter(this, mCarpetData);
        mRecyclerView.setAdapter(mAdapter);

        mFirestore = FirebaseFirestore.getInstance();
        mCarpets = mFirestore.collection("Carpets");
        queryData();








    }
    private void queryData() {
//        TypedArray itemsImageResources = getResources().obtainTypedArray(R.array.carpets);
//        for (int i = 0; i < itemsImageResources.length(); i++) {
//            Log.d(LOG_TAG,itemsImageResources.getResourceId(i, 0)+" carpet"+i);
//        }
        mCarpetData.clear();
        mCarpets.orderBy("soldCounter", Query.Direction.DESCENDING).limit(itemLimit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Carpet item = document.toObject(Carpet.class);
                item.setId(document.getId());
                mCarpetData.add(item);
            }

            if (mCarpetData.size() == 0) {
                mCarpets.add(new Carpet("nev","SZINES","FASZASZONYEG",234,2344,54,3,2131230921));
                mCarpets.add(new Carpet("A KURVA ISTEN MEGBASSZA HOGY MUKODIK AZ ANYJA IS","asd","d",233333333,2344,54,3,2131230921));
                queryData();

            }

            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        });
    }

    public void deleteCarpet(Carpet carpet){
        DocumentReference ref = mCarpets.document(carpet._getId());
        ref.delete().addOnSuccessListener(success ->{
            Log.d(LOG_TAG, "Carpet deleted");
        }).addOnFailureListener(failure ->{
            Log.d(LOG_TAG, "Failed to delete carpet");
            Toast.makeText(this,"Carpet "+ carpet._getId() +" cannot be deleted", Toast.LENGTH_LONG).show();
        });


    }
    public void updateCarpet(Carpet carpet){
        mCarpets.document(carpet._getId()).update("stock", carpet.getStock()-1).addOnFailureListener(failure ->
        {
            Toast.makeText(this,carpet.getName() +" bought!", Toast.LENGTH_LONG).show();
        });
        if(carpet.getStock()-1<=0){
            //mCarpets.document(carpet._getId()).delete();
            deleteCarpet(carpet);
        }
        queryData();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.rug_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG_TAG, s);
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.viewswitch:
                Log.d(LOG_TAG, "Viewswitch clicked!");
                if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_NO){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            case R.id.newCarpet:
                Log.d(LOG_TAG, "New carpet clicked!");
                newCarpet();
                return true;
            case R.id.log_out:
                Log.d(LOG_TAG, "Logging out!");
                FirebaseAuth.getInstance().signOut();
                finish();
                login();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void newCarpet() {
        Intent intent = new Intent(this, NewCarpetActivity.class);
        startActivity(intent);
    }
    private void login(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}