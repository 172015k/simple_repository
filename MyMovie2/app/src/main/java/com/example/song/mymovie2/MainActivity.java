package com.example.song.mymovie2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.song.mymovie2.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tvActor, tvDirec, tvPlot, tvReleased, tvGenre = null;
    EditText etTitle = null;
    ImageView ivPoster = null;
    Button btnSearch = null;
    String etUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvActor = (TextView) findViewById(R.id.tvActor);
        tvDirec = (TextView) findViewById(R.id.tvDirec);
        tvPlot = (TextView) findViewById(R.id.tvPlot);
        tvReleased = (TextView) findViewById(R.id.tvRelease);
        tvGenre = (TextView) findViewById(R.id.tvGener);
        etTitle = (EditText) findViewById(R.id.etTitle);
        ivPoster = (ImageView) findViewById(R.id.ivPoster);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        etUrl = "http://www.omdbapi.com/?apikey=a237fc2f&t=";

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etTitle.getText() != null && !etTitle.getText().toString().isEmpty()){
                    //get방식에서 ? 뒤에 붙는게 title 이걸 입력받는걸로 대체해주자.
                    MySearchTask st = new MySearchTask();
                    String title = etTitle.getText().toString();
                    etUrl= etUrl.concat(title);
                    Log.i("제목",etUrl);
                    st.execute(etUrl);
                } else{
                    Toast.makeText(getApplicationContext(),"타이틀을 입력하시오.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    class MySearchTask extends AsyncTask<String, Void, JSONObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected JSONObject doInBackground(String... values) {
            BufferedReader br = null;
            Log.i("확인","여기까지 전달됨");
            try {
                URL url = new URL(values[0]);
                Log.i("url정보",url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");//접속 방식은 GET
                conn.setDoInput(true);

                StringBuilder sb = new StringBuilder();

                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    //만약 접속이 제대로 성공했으면
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line=null;
                    Log.i("접속성공","성공");
                    while((line = br.readLine())!=null){
                        sb.append(line);
                    }
                }
                conn.disconnect();
                return new JSONObject(sb.toString());
                //중간과정 보여줄려면 update로... 그렇지 않으면 바로 화면에 출력해주면됨
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }

                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            //출력결과 받아와서 찍자.
            try {
                //감독
                //개봉일
                //장르
                //배우
                //줄거리
                //Log.i("감독",result.getString("Director"));

                etTitle.setText(result.getString("Title"));
                tvDirec.setText(result.getString("Director"));
                tvReleased.setText(result.getString("Released"));
                tvGenre.setText(result.getString("Genre"));
                tvActor.setText(result.getString("Actors"));
                tvPlot.setText(result.getString("Plot"));

                MyImageTask mit = new MyImageTask();
                String imgUrl = result.getString("Poster");
                mit.execute(imgUrl);

            }catch(Exception e){
                e.printStackTrace();
            }

        }



    }

    class MyImageTask extends AsyncTask<String,Void,Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected Bitmap doInBackground(String... values) {
            Bitmap myBitmap = null;
            try{

                URL url1 = new URL(values[0]);
                HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
                conn1.setConnectTimeout(10000);
                conn1.setRequestMethod("GET");
                conn1.setDoInput(true);

                StringBuilder sb1 = new StringBuilder();
                int responseCode = conn1.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    myBitmap = BitmapFactory.decodeStream(conn1.getInputStream());


                }else{
                    sb1.append("Connection failed");
                }
                conn1.disconnect();
                return myBitmap;
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap result) {
            ivPoster.setImageBitmap(result);
        }

    }
}
