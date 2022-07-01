package com.zx.lib.share.activity;

import android.content.Intent;
import android.os.Bundle;

import com.umeng.message.UmengNotifyClickActivity;


/**
 * 离线消息通过系统通道下发时，点击通知栏会起动该Activity，注意：需要后台发送时指定该Activity的全名称
 */
public class PushNotifyActivity extends UmengNotifyClickActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);
        //此方法必须调用，否则无法统计打开数

    }

}
