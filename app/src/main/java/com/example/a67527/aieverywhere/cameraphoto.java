package com.example.a67527.aieverywhere;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.test.TouchUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsSpinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.widget.Toast.LENGTH_SHORT;

public class cameraphoto extends AppCompatActivity {
    private SurfaceView cammera_preview;
    private Camera camera = null;
    private View view0, view1, view2, view3, view4, view5, view6;
    private ViewPager viewPager;
    private List<View> viewList;
    private int cameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
    //预览
    private SurfaceHolder.Callback cammera_previewHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopPreview();
        }
    };

    private void startPreview() {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(cammera_preview.getHolder());
            camera.setDisplayOrientation(0);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview() {
        if(camera!=null){
            camera.stopPreview();
            camera=null;
        }

    }


    //捕捉图片
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String path = getApplicationContext().getExternalCacheDir()+"/AI-Picture";//通过Context.getExternalCacheDir()方法可以获取到 SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
            File picture_File = new File(path);
            picture_File.mkdirs();
            String fillName = System.currentTimeMillis()+".jpg";//System.currentTimeMillis() 获取当前时间
            File destFill = new File(picture_File.getAbsolutePath(),fillName);
            Log.i("cameraphoto","onPictureTaken"+destFill.getAbsolutePath());
            destFill.deleteOnExit();
            try {
                destFill.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("TAG", "picture_file:" + picture_File);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(destFill);
                fileOutputStream.write(data);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast toast;
                toast = Toast.makeText(cameraphoto.this, "拍照成功"+path, LENGTH_SHORT);
                toast.show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    //动态申请权限

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        if (Build.VERSION.SDK_INT > 22) {
            if (ContextCompat.checkSelfPermission(cameraphoto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {//GRANTED已经获得权限 DENIED未获得
                ActivityCompat.requestPermissions(cameraphoto.this, new String[]{Manifest.permission.CAMERA}, 1001);
            } else {
                Toast toast;
                toast = Toast.makeText(cameraphoto.this, "相机权限已开启", LENGTH_SHORT);
                toast.show();
            }
        }
        onRequestPermissionsResult();


        cammera_preview = findViewById(R.id.camerapreview);
        cammera_preview.getHolder().addCallback(cammera_previewHolderCallback);//预览画面通过sufaceHoder与Camera绑定，添加回调事件

        //滑动选中
        viewPager = findViewById(R.id.viewpager);
        LayoutInflater inflater = getLayoutInflater();
        view0 = inflater.inflate(R.layout.layout0, null);
        view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);
        view4 = inflater.inflate(R.layout.layout4, null);
        view5 = inflater.inflate(R.layout.layout5, null);
        view6 = inflater.inflate(R.layout.layout6, null);


        viewList = new ArrayList<View>();
        viewList.add(view0);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);
        viewList.add(view6);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {

                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

                return view == object;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView(viewList.get(position));

            }

            @Override
            public float getPageWidth(int position) {
                return (float) 0.2;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.page_margin));
    }

    private void onRequestPermissionsResult() {
    }


    public void onclick_camera2(View view) {
        camera.autoFocus(new Camera.AutoFocusCallback() {
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    camera.takePicture(null, null, mPicture);
                    camera.cancelAutoFocus();
                } else {
                    camera.takePicture(null, null, mPicture);
                    camera.cancelAutoFocus();
                }
            }
        });

    }
    public void onclick_c(View view) {
        Intent intent = new Intent(cameraphoto.this, Menu.class);
        startActivity(intent);
        stopPreview();
    }

    //翻转镜头
    protected void reseal(){
        int count = Camera.getNumberOfCameras();
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < count; i++) {
            Camera.getCameraInfo(i,cameraInfo);
            if(cameraID==0) {
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK);
                {
                    camera.stopPreview();
                    camera.release();
                    camera = null;
                    camera = Camera.open(1);
                    try{
                        camera.setPreviewDisplay(cammera_preview.getHolder());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    camera.startPreview();
                    cameraID=1;
                    break;
                }
            }
            else {
                if(cameraInfo.facing==Camera.CameraInfo.CAMERA_FACING_FRONT){
                    camera.stopPreview();
                    camera.release();
                    camera=null;
                    camera = Camera.open(0);
                    try{
                        camera.setPreviewDisplay(cammera_preview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    camera.startPreview();
                    cameraID=0;
                    break;
                }
            }
        }
    }

    public void onclick_reversal(View view) {
        reseal();
    }
}
