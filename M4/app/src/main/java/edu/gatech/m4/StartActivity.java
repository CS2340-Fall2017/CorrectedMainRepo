package edu.gatech.m4;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.support.annotation.NonNull;

import android.widget.ArrayAdapter;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;
    @SuppressWarnings("unused")
    private String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        listView = findViewById(R.id.listView);
        EditText inputSearch = findViewById(R.id.inputSearch);

        dbHelper = new DBHelper(this);



        mAuth = FirebaseAuth.getInstance();
        //get current user
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(StartActivity.this, WelcomeActivity.class));
                    finish();
                }
            }
        };


        if (savedInstanceState == null) {
            InputStream inputStream = getResources().openRawResource(R.raw.rat_sightings);
            CSVFile csvFile = new CSVFile(inputStream);
            //Model.getInstance().readCSV(csvFile);
//            scoreList = Model.getInstance().getRatData();
            HashMap<String, String[]> scoreList = csvFile.read();
            try {
                String[] newly_added_data = (String[]) getIntent().getSerializableExtra("String Array");
                scoreList.put(newly_added_data[0], newly_added_data);
            } catch (Exception e) {
                //shouldn't be executed, if it is, just print something useless
                System.out.println("something useless");
            }
            ArrayList<String> uniqueKeys = new ArrayList<>(scoreList.keySet());
            data = uniqueKeys.toArray(new String[uniqueKeys.size()]);

        }

        //log out
        Button logOut = findViewById(R.id.logout_button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
            }
        });
        //add report
        Button addReport = findViewById(R.id.add_report_button);
        addReport.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             startActivity(new Intent(StartActivity.this, AddRatReportActivity.class));
                                             //finish();
                                         }
                                     });

        Cursor cursor = dbHelper.getAllReports();

        ArrayList<String> names = new ArrayList<>();
        //cursor.moveToNext();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext() ) {
            names.add(cursor.getString(1));
        }

        String[] arr = names.toArray(new String[names.size()]);
        adapter = new ArrayAdapter<>(StartActivity.this,
                android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String uniqueID = (String) parent.getItemAtPosition(position);
                Intent intent = new Intent(StartActivity.this, DetailedRatDataDisplayActivity.class);
                //pass the hashmap to detailedRatDataActivity
                intent.putExtra("String", uniqueID);
                startActivity(intent);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //todo
            }

            @Override
            public void onTextChanged(CharSequence cs, int i, int i1, int i2) {
                StartActivity.this.adapter.getFilter().filter(cs.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //todo
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
        Cursor cursor = dbHelper.getAllReports();
        ArrayList<String> names = new ArrayList<>();
        //cursor.moveToNext();
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext() ) {
            names.add(cursor.getString(1));
        }

        String[] arr = names.toArray(new String[names.size()]);
        adapter = new ArrayAdapter<>(StartActivity.this,
                android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }
}
