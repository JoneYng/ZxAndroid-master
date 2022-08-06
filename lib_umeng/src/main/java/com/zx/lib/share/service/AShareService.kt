package com.zx.lib.share.service

import android.app.Activity
import androidx.fragment.app.FragmentManager
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.bean.SHARE_MEDIA
import com.zx.lib.share.ShearEnum
import com.zx.lib.share.fragment.ShareCommonViewFragment
import com.zx.lib.share.fragment.ShareImageCommonViewFragment
import com.zx.lib.share.listener.ShareResultCallBack


/**
 * @description:
 * @author: zhouxiang
 * @created: 2022/07/01 11:36
 * @version: V1.0
 */
object AShareService {
    /**
     * 分享弹窗
     * @param shareText 分享内容
     * @param shareTitle 分享标题
     * @param shareUrl 分享链接
     * @param shareImage 分享图片
     * @param shareType 分享类型（业务）
     * @param platType 平台类型（QQ、微信、新浪）
     * @param viewTitle 弹窗标题
     * @param shareResultCallBack 分享回调
     */
    fun showShareView(
        fragmentManager: FragmentManager?, shareText: String?, shareTitle: String?,
        shareUrl: String?, shareImage: String?, shareType: ShearEnum.ShareContentTypeEnum,
        platType: ShearEnum.SharePlatType?, viewTitle: String?,
        shareResultCallBack: ShareResultCallBack?
    ) {
        if (fragmentManager == null || fragmentManager.isDestroyed) {
            return
        }
        val shareCommonViewFragment: ShareCommonViewFragment =
            ShareCommonViewFragment.newShareCommonFragmentInstance(
                shareText, shareTitle, shareUrl,
                shareImage, shareType, platType, viewTitle
            )
        shareCommonViewFragment.setShareResultCallback(shareResultCallBack)
        shareCommonViewFragment.show(fragmentManager, "ShareCommonViewFragment")
    }

    /**
     * 分享弹窗
     * @param shareText 分享内容
     * @param shareTitle 分享标题
     * @param shareImage 分享图片
     * @param platType 平台类型（QQ、微信、新浪）
     * @param shareResultCallBack 分享回调
     */
    fun showShareImageView(
        fragmentManager: FragmentManager?, shareText: String?,shareTitle: String?, shareImage: String?,
        platType: ShearEnum.SharePlatType?,
        shareResultCallBack: ShareResultCallBack?
    ) {
        if (fragmentManager == null || fragmentManager.isDestroyed) {
            return
        }
        val shareImageCommonViewFragment: ShareImageCommonViewFragment =
            ShareImageCommonViewFragment.newShareImageCommonViewFragmentInstance(
                shareText, shareTitle, shareImage,  platType)
        shareImageCommonViewFragment.setShareResultCallback(shareResultCallBack)
        shareImageCommonViewFragment.show(fragmentManager, "ShareImageCommonViewFragment")
    }

    /**
     * 微信登录
     */
    fun onWeChatClick(context:Activity,authListener: UMAuthListener) {
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.WEIXIN, authListener)
    }
    /**
     *QQ登录
     */
    fun onQQClick(context:Activity,authListener: UMAuthListener) {
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, authListener)
    }
    /**
     *微博登录
     */
    fun onWeiboClick(context:Activity,authListener: UMAuthListener) {
        UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.SINA, authListener)
    }
}