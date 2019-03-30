package com.example.a67527.aieverywhere;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private int[] res = {R.id.menu,R.id.icon1,R.id.icon2,R.id.icon3,R.id.icon4};
    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    ImageView imageView_menu;
    ImageView imageView_icon1;
    ImageView imageView_icon2;
    ImageView imageView_icon3;
    ImageView imageView_icon4;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        imageView_menu=findViewById(R.id.menu);
        imageView_icon1=findViewById(R.id.icon1);
        imageView_icon2=findViewById(R.id.icon2);
        imageView_icon3=findViewById(R.id.icon3);
        imageView_icon4=findViewById(R.id.icon4);

    }
    public void click(View view){
        switch (view.getId()){
            case R.id.menu:
                start_action();
                break;
                default:
                    break;
        }
    }
    private void start_action() {
        ObjectAnimator animator1= ObjectAnimator.ofFloat(imageView_icon1,"translationX",0F,300F);
        ObjectAnimator animator2= ObjectAnimator.ofFloat(imageView_icon2,"translationX",0F,210F);
        ObjectAnimator animator3= ObjectAnimator.ofFloat(imageView_icon2,"translationY",0F,100F);
        ObjectAnimator animator4= ObjectAnimator.ofFloat(imageView_icon3,"translationX",0F,110F);
        ObjectAnimator animator5= ObjectAnimator.ofFloat(imageView_icon3,"translationY",0F,225F);
        ObjectAnimator animator6= ObjectAnimator.ofFloat(imageView_icon4,"translationY",0F,300F);
        AnimatorSet set = new AnimatorSet();
        set.play(animator1);
        set.play(animator2).with(animator3).with(animator1);
        set.play(animator4).with(animator5);
        set.play(animator6);
        set.setDuration(700);
        set.start();
    }

    public void onclick1(View view){
        Intent intent =new Intent(Menu.this,progressbar.class);
        intent.putExtra("activity","one");
        startActivity(intent);
    }

    public void onclick2(View view){
        Intent intent =new Intent(Menu.this,progressbar.class);
        intent.putExtra("activity","two");
        startActivity(intent);
    }

    public void onclick3(View view){
        Intent intent =new Intent(Menu.this,progressbar.class);
        intent.putExtra("activity","three");
        startActivity(intent);
    }

    public void onclick4(View view){
        Intent intent =new Intent(Menu.this,progressbar.class);
        intent.putExtra("activity","four");
        startActivity(intent);
    }
}
