package com.example.a67527.aieverywhere;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TDR_demonstration extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdr_demonstration);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    public void onclick_return(View view){
        Intent intent=new Intent(TDR_demonstration.this,Menu.class);
        startActivity(intent);
    }
    public void onclick_camera(View view){
        Intent intent = new Intent(TDR_demonstration.this,cameraphoto.class);
        startActivity(intent);
    }
}
