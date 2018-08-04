package com.example.song.myimage2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int currentPosition;
    float clickImageX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = findViewById(R.id.imageView);
        final int[] images = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4};
        Button prevBtn = findViewById(R.id.prevBtn);
        Button nextBtn = findViewById(R.id.nextBtn);

        final float imageX = imageView.getScaleX();


        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPosition == 0){
                    currentPosition = images.length-1;
                }else{
                    currentPosition--;
                }
                imageView.setImageResource(images[currentPosition]);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                    if(currentPosition == images.length-1){
                        currentPosition =0;
                    }else{
                        currentPosition++;
                    }
                    imageView.setImageResource(images[currentPosition]);
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event){
                clickImageX = event.getX();
                if(clickImageX <= imageX){
                    if(currentPosition == 0){
                        currentPosition = images.length-1;
                    }else{
                        currentPosition--;
                    }
                }else {
                    if(currentPosition == images.length-1){
                        currentPosition = 0;
                    }else{
                        currentPosition++;
                    }

                }
                imageView.setImageResource(images[currentPosition]);
                return true;
            }
        });




    }
}
