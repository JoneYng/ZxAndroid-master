package com.zx.lib.share.listener;


import com.zx.lib.share.ShearEnum;

public interface ShareResultCallBack {

    void onShareClick(ShearEnum.SharePlatType type);

    void onShareCancel();
}
