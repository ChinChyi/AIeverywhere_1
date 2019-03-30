package com.example.a67527.aieverywhere;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class TPR extends AppCompatActivity {
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            Intent intent =new Intent(TPR.this,TDR_demonstration.class);
            startActivity(intent);
            finish();
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdr);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        handler.sendEmptyMessageDelayed(1,1000);
    }
    public void onclick_return(View view){
        Intent intent  =new Intent(TPR.this,Menu.class);
        startActivity(intent);
    }
}
