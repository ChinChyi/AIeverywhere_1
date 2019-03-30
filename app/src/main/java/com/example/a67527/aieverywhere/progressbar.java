package com.example.a67527.aieverywhere;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class progressbar extends AppCompatActivity {
    public static final String TAG = "progressBar";
    private int start_x;
    private int end_x;
    private int status = 0;
    private ProgressBar bar;
    private int data;
    private int[] location = new int[2];
    private int caculate_time = 0;
    private ObjectAnimator animator;
    private ImageView imageView;
    private int dation;
    private int width;
    private boolean orientationChanged = false;
    String[] mess = {"Tips:目前有2300万玩家停留在此关卡。", "Tips:学而时习之，温故而知新", "Tips:人有三个宝，手、眼和大脑", "Tips:经常练习，有助于记忆哦", "Tips:不要忘记每天练习哦。"};
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what <= 100) {
                animator = ObjectAnimator.ofFloat(imageView, "translationX", (float) start_x, (float) end_x);
                animator.setDuration(0);
                animator.start();
                bar.setProgress(status);
                Log.i(TAG, "status: " + status);
//                Log.v(TAG, String.valueOf(dation*670/100));
                Log.i(TAG, "start_x:" + start_x + "，end_x:" + end_x);
//                Log.v(TAG, String.valueOf(end_x-start_x));
                if (status == 100) {
                    Intent intent = getIntent();
                    String activity = intent.getStringExtra("activity");
                    switch (activity) {
                        case "first":
                            break;
                        case "second":
                            break;
                        case "three": {
                            Intent intent1 = new Intent(progressbar.this, TPR.class);
                            startActivity(intent1);
                        }
                        break;
                        case "four":
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_bar);
        imageView = findViewById(R.id.circle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        bar = findViewById(R.id.progress_length);
        imageView.getLocationOnScreen(location);
        TextView textView = findViewById(R.id.textview);
        Random random = new Random();
        int i = random.nextInt(5);
        textView.setText(mess[i]);
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!orientationChanged) { // 竖屏切横屏，会多测量一次。
                    orientationChanged = true;
                    return;
                }
                width = bar.getWidth(); // image前进总路程，应该是bar的宽度
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                Log.i(TAG, "动画开始,imageView宽度：" + width);

                new Thread() {
                    public void run() {
                        while (status < 100) {
                            status = doWork();
                            mHandler.sendEmptyMessage(status);

                            try {
                                Thread.sleep(100); // 暂停要放在通知主线程之后，因为计算进度和更新UI同属一个周期
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        };
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);


    }

    private int doWork() {
        dation = (int) (Math.random() * 5); // dation是前进百分比
        Log.i(TAG, "dation:" + dation);
        data += dation;
        if (data > 100) {
            dation = dation - (data - 100);
            data = 100;
        }
        if (caculate_time == 0) {
            start_x = 0;
            end_x = (width * dation / bar.getMax());
        } else {
            start_x = end_x;
            end_x = end_x + (width * dation / bar.getMax()); // image的终止位置，应该是当前位置 + 刻度条长度 * 前进百分比
        }

        caculate_time += 1;

        return data;
    }
}

