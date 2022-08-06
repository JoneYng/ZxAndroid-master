package com.zx.lib.share

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64
import android.widget.Toast
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb
import java.io.File
import java.util.regex.Pattern

/**
 * 分享SDK工具类
 */
class ShareSDKUtil {


    companion object {
        /**
         * 分享到新浪微博
         * @param context
         * @param paListener
         * @param text
         * @param imageUrl
         */
        fun doSinaShare(
            context: Activity,
            paListener: UMShareListener?,
            title: String,
            text: String?,
            url: String,
            imageUrl: String
        ) {
            val shareAction = ShareAction(context)
                .setPlatform(SHARE_MEDIA.SINA)
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(title)) {
                shareAction.withText(title + url)
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
            } else {
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
                    .withText(title)
            }
            shareAction.setCallback(paListener)
                .share()
        }
        /**
         * 分享到QQ
         * @param context
         * @param paListener
         * @param title
         * @param titleUrl
         * @param text
         * @param imageUrl
         */
        fun doQQShare(
            context: Activity,
            paListener: UMShareListener?,
            title: String?,
            titleUrl: String?,
            text: String?,
            imageUrl: String
        ) {
            val shareAction = ShareAction(context)
                .setPlatform(SHARE_MEDIA.QQ)
            if (!TextUtils.isEmpty(titleUrl) && !TextUtils.isEmpty(title)) {
                val umWeb = UMWeb(titleUrl)
                umWeb.title = title
                umWeb.description = text
                umWeb.setThumb(setShareImageUrl(context, imageUrl))
                shareAction.withMedia(umWeb)
            } else {
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
                    .withText(title)
            }
            shareAction.setCallback(paListener)
                .share()
        }

        /**
         * 分享到QQ空间
         * @param context
         * @param paListener
         * @param title
         * @param titleUrl
         * @param text
         * @param imageUrl
         */
        fun doQZoneShare(
            context: Activity,
            paListener: UMShareListener?,
            title: String?,
            titleUrl: String?,
            text: String?,
            imageUrl: String
        ) {
            val shareAction = ShareAction(context)
                .setPlatform(SHARE_MEDIA.QZONE)
            if (!TextUtils.isEmpty(titleUrl) && !TextUtils.isEmpty(title)) {
                val umWeb = UMWeb(titleUrl)
                umWeb.title = title
                umWeb.description = text
                umWeb.setThumb(setShareImageUrl(context, imageUrl))
                shareAction.withMedia(umWeb)
            } else {
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
                    .withText(title)
            }
            shareAction.setCallback(paListener)
                .share()
        }

        /**
         * 分享到微信
         * @param context
         * @param paListener
         * @param title
         * @param text
         * @param imageUrl
         * @param url        void
         */
        fun doWeChatShare(
            context: Activity,
            paListener: UMShareListener?,
            title: String?,
            text: String?,
            imageUrl: String,
            url: String?
        ) {
            val shareAction = ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN)
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(title)) {
                val umWeb = UMWeb(url)
                umWeb.title = title
                umWeb.description = text
                umWeb.setThumb(setShareImageUrl(context, imageUrl))
                shareAction.withMedia(umWeb)
            } else {
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
                    .withText(title)
            }
            shareAction.setCallback(paListener)
                .share()
        }

        /**
         * 分享到微信朋友圈
         * @param context
         * @param paListener
         * @param title
         * @param text
         * @param imageUrl
         * @param url        void
         */
        fun doWeChatMShare(
            context: Activity,
            paListener: UMShareListener?,
            title: String?,
            text: String?,
            imageUrl: String,
            url: String?
        ) {
            val shareAction = ShareAction(context)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(title)) {
                val umWeb = UMWeb(url)
                umWeb.title = title
                umWeb.description = text
                umWeb.setThumb(setShareImageUrl(context, imageUrl))
                shareAction.withMedia(umWeb)
            } else {
                shareAction.withMedia(setShareImageUrl(context, imageUrl))
                    .withText(title)
            }
            shareAction.setCallback(paListener)
                .share()
        }

        /**
         * 设置图片 并返回是否是网络图片
         * @param activity
         * @param imageUrl 支持网络连接，路径，base 64
         * @return
         */
        private fun setShareImageUrl(activity: Activity, imageUrl: String): UMImage? {
            if (TextUtils.isEmpty(imageUrl)) {
                return null
            }
            var umImage: UMImage? = null
            if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://")) {
                umImage = UMImage(activity, imageUrl)
            } else if(isBase64(imageUrl)){
                //将字符串转换成Bitmap类型
                var bitmap: Bitmap? = null
                try {
                    val bitmapArray = Base64.decode(
                        imageUrl.split(",").toTypedArray().get(1),
                        Base64.DEFAULT
                    ) //注意解码的时候要把编码的头（"data:image/png;base64,"）去掉，否则将会失效
                    bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                umImage = UMImage(activity,bitmap)
            }else {
                umImage = UMImage(activity, File(imageUrl))
            }
            umImage.setThumb(umImage)
            return umImage
        }

        /**
         * 是否是Base64位图片
         */
         fun  isBase64( str:String):Boolean {
            val base64Pattern = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$"
            return Pattern.matches(base64Pattern, str);
        }


        fun checkWeiXin(context: Context): Boolean {
            val isHave = isHaveInstallApplication(context, ShareConstant.APP_PACKAGE_NAME_WEIXIN)
            if (!isHave) {
                Toast.makeText(context, R.string.activity_share_wx_nointall_string, Toast.LENGTH_SHORT)
                    .show()
            }
            return isHave
        }

        fun checkQQ(context: Context): Boolean {
            val isHave = isHaveInstallApplication(context, ShareConstant.APP_PACKAGE_NAME_QQ)
            if (!isHave) {
                Toast.makeText(context, R.string.activity_share_qq_nointall_string, Toast.LENGTH_SHORT)
                    .show()
            }
            return isHave
        }

        fun checkSina(context: Context): Boolean {
            val isHave = isHaveInstallApplication(context, ShareConstant.APP_PACKAGE_NAME_SINA)
            if (!isHave) {
                Toast.makeText(
                    context,
                    R.string.activity_share_sina_nointall_string,
                    Toast.LENGTH_SHORT
                ).show()
            }
            return isHave
        }

        fun initShareSdk(context: Context?) {
            //微信
            PlatformConfig.setWeixin(ShareConstant.WeixinID, ShareConstant.WeixinSecret)
            //QQ
            PlatformConfig.setQQZone(ShareConstant.QQID, ShareConstant.QQSecret)
            //新浪
            PlatformConfig.setSinaWeibo(
                ShareConstant.SinaWeiboID,
                ShareConstant.SinaWeiboSecret,
                ShareConstant.SinaWeiboRedirectUrl
            )
        }

        /**
         * 手机中是否安装某个应用
         *
         * @param context
         * @param packageName 包名
         * @return
         */
        fun isHaveInstallApplication(context: Context, packageName: String): Boolean {
            val packageManager = context.packageManager
            val pinfo = packageManager.getInstalledPackages(0)
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == packageName) {
                    return true
                }
            }
            return false
        }

        /**
         * 防止连续点击 连续 返回TRUE 否则返回 FALSE
         * @return
         * @author yanshch
         */
        private var lastClickTime: Long = 0
        val isFastDoubleClick: Boolean
            get() {
                val time = System.currentTimeMillis()
                val timeD = time - lastClickTime
                if (timeD in 1..499) {
                    return true
                }
                lastClickTime = time
                return false
            }
    }
}