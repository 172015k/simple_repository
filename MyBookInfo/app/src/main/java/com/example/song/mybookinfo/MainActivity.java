package com.example.song.mybookinfo;

import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable {

    Toolbar toolbar;
    Fragment fragment1;
    Fragment fragment2;
    SQLiteDatabase db;
    String dbName;
    String tableName;
    boolean isDbCreated = false;
    boolean isTableCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        tableName = "bookTB";
        dbName = "bookDB";
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        Bundle bundle = new Bundle();
        bundle.putSerializable("context",this);
        fragment1.setArguments(bundle);
        fragment2.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment1).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("입력"));
        tabs.addTab(tabs.newTab().setText("조회"));


        if(!isDbCreated){
            createDatabase(dbName);
        }


        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity","선택된 탭:" + position);

                Fragment selected = null;
                if(position == 0){
                    selected = fragment2;
                }else if(position == 1){
                    selected = fragment1;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void createTable(String tbName){
        if(isDbCreated){
            try{
                db.execSQL("CREATE TABLE IF NOT EXISTS " + tbName + "("
                        + " _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + " title text, "
                        + " author text, "
                        + " content text)");
                isTableCreated = true;
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }else{

        }
    }

    private void createDatabase(String dbName){
        try{
            db = openOrCreateDatabase(dbName,MODE_PRIVATE,null);
            isDbCreated = true;

            createTable(tableName);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
