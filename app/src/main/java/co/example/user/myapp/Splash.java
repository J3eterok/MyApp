package co.example.user.myapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv = (ImageView) findViewById(R.id.rot);

        Animation anim = null;

        anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
        iv.startAnimation(anim);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent SecAct = new Intent(Splash.this, MainActivity.class);
                startActivity(SecAct);

            }
        }, 2*1000);
    }
}
