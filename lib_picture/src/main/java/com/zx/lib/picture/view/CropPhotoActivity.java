package com.zx.lib.picture.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.luck.picture.lib.widget.TitleBar;
import com.zx.lib.picture.R;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;


/**
 * 头像裁剪ClipImageActivity
 */
public class CropPhotoActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "CropPhotoActivity";
    private ClipViewLayout clipViewLayout1;
    private ImageView back;
    private TextView btnCancel;
    private TextView btnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_image);
        initView();
    }

    /**
     * 初始化组件
     */
    public void initView() {
        clipViewLayout1 = findViewById(R.id.clipViewLayout1);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk = findViewById(R.id.bt_ok);
        back = findViewById(R.id.img_clip_image_back);

        //设置点击事件监听器
        back.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "image uri: " + getIntent().getData());
        clipViewLayout1.setVisibility(View.VISIBLE);
        //设置图片资源
        clipViewLayout1.setImageSrc(getIntent().getData());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_cancel) {
            finish();
        } else if (id == R.id.bt_ok) {
            generateUriAndReturn();
        }else if (id == R.id.img_clip_image_back) {
            finish();
        }
    }


    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        zoomedCropBitmap = clipViewLayout1.clip();
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
