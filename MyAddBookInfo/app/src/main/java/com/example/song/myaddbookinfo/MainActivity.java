package com.example.song.myaddbookinfo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Status";

    String dbName;
    String tableName;
    boolean isDbCreated = false;
    boolean isTableCreated = false;
    EditText edTitle, edAuthor, edContent;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edAuthor = (EditText)findViewById(R.id.edAuthor);
        edTitle = (EditText)findViewById(R.id.edTitle);
        edContent = (EditText)findViewById(R.id.edContent);

        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        dbName = "bookDB";
        tableName = "bookTB";

        if(!isDbCreated) {
            //db가 없는 경우 bookDB를 만든다.
            createDatabase(dbName);

        }


        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int result = insertRecordParam(edTitle.getText().toString(),
                        edAuthor.getText().toString(),edContent.getText().toString());
                if(result>0){
                    Toast.makeText(getApplicationContext(),"책정보가 저장되었습니다.",Toast.LENGTH_SHORT).show();
                    edTitle.setText("");
                    edAuthor.setText("");
                    edContent.setText("");
                }
            }
        });


    }

    private int insertRecordParam(String title, String author, String content){
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("author",author);
        contentValues.put("content",content);

        return (int)db.insert(tableName,null,contentValues);
    }



    private void createDatabase(String dbName){
        try{
            db = openOrCreateDatabase(dbName,MODE_PRIVATE,null);
            isDbCreated = true;
            Log.d(TAG,"database is created.");
            createTable(tableName);
        }catch(Exception ex){
            ex.printStackTrace();
        }
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
                Log.d(TAG,"Err Table Create");
            }
        }else{
            Log.d(TAG,"Err Not Exists Database");
        }
    }


}
