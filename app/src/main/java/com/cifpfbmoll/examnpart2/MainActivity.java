package com.cifpfbmoll.examnpart2;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean firstAnimation = true;
    private Context myContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myContext = this.getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.helloAction:
                Intent intent = new Intent(MainActivity.this, HelloWorld.class);
                finish();
                startActivity(intent);
                return true;
            case R.id.sunAction:
                Toast toast = Toast.makeText(this, "You're Already Here", Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void anochecer(View v){

        AnimatorSet animatorSet = new AnimatorSet();
        View skyBackground = findViewById(R.id.sky);
        ImageView sun = findViewById(R.id.sun);
        ObjectAnimator sunAnimator = ObjectAnimator.ofFloat(sun,"translationY",800f).setDuration(3500);
        ObjectAnimator originSun = ObjectAnimator.ofFloat(sun,"translationY",0f).setDuration(0);


        int colorStart = getResources().getColor(R.color.blue);
        int color2 = getResources().getColor(R.color.sky2);
        int color3 = getResources().getColor(R.color.orange);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorStart, color2, color3);
        colorAnimation.setDuration(3000); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                skyBackground.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        int colorEnd = getResources().getColor(R.color.dark);
        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), color3, colorEnd);
        colorAnimation2.setDuration(1500);
        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                skyBackground.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });
        if(!firstAnimation){
            animatorSet.play(originSun).before(sunAnimator);
        }
        animatorSet.play(sunAnimator).with(colorAnimation);
        animatorSet.play(colorAnimation2).after(sunAnimator);
        animatorSet.start();
        firstAnimation = false;
    }

}