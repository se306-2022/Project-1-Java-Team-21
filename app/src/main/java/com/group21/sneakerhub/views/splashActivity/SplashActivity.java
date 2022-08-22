package com.group21.sneakerhub.views.splashActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.group21.sneakerhub.R;
import com.group21.sneakerhub.views.mainActivity.MainActivity;

public class SplashActivity extends AppCompatActivity {

    private class ViewHolder{
        Animation animation;
        TextView textView;
        public ViewHolder(Animation animation, TextView textView) {
            this.animation = animation;
            this.textView = textView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // make it full screen
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get the view holder
        ViewHolder viewHolder = new ViewHolder(AnimationUtils.loadAnimation(this, R.anim.fade_in), findViewById(R.id.title));
        viewHolder.textView.startAnimation(viewHolder.animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(i);
                // close this activity
                finish();
            }
        }, 5000);
    }
}