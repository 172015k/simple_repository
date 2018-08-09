package com.example.song.mybookinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;


public class Fragment2 extends Fragment {
    private static final String TAG = "Status";
    String dbName;
    String tableName;
    boolean isDbCreated = false;
    boolean isTableCreated = false;
    EditText edTitle, edAuthor, edContent;

    SQLiteDatabase db;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_fragment2,container,false);
        edAuthor = (EditText)rootView.findViewById(R.id.edAuthor);
        edTitle = (EditText)rootView.findViewById(R.id.edTitle);
        edContent = (EditText)rootView.findViewById(R.id.edContent);
        Button btnAdd = (Button)rootView.findViewById(R.id.btnSave);



        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle bundle = getArguments();
                MainActivity context = (MainActivity) bundle.getSerializable("context");
                insertRecordParam(edTitle.getText().toString(),edAuthor.getText().toString()
                        ,edContent.getText().toString());

                edTitle.setText("");
                edContent.setText("");
                edAuthor.setText("");


            }
        });







        return rootView;
    }

    private int insertRecordParam(String title, String author, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("author",author);
        contentValues.put("content",content);

        return (int)db.insert(tableName,null,contentValues);
    }




}
