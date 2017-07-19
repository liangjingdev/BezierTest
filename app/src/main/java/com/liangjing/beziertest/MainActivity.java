package com.liangjing.beziertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
    }


    public void secondBezierTest(View view) {
        startActivity(new Intent(this, SecondBezierActivity.class));
    }

    public void thirdBezierTest(View view) {
        startActivity(new Intent(this, ThirdBezierActivity.class));
    }

    public void drawBezierTest(View view) {
        startActivity(new Intent(this, DrawPadActivity.class));
    }

    public void pathMorphingBezierTest(View view) {
        startActivity(new Intent(this, PathMorphingActivity.class));
    }

    public void waveBezierTest(View view) {
        startActivity(new Intent(this, WaveActivity.class));
    }

    public void pathBezierTest(View view) {
        startActivity(new Intent(this, PathBezierActivity.class));
    }
}
