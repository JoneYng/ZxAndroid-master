package com.zx.lib.share

import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.meituan.android.walle.WalleChannelReader
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import com.umeng.message.PushAgent
import com.umeng.message.UTrack.ICallBack
import com.umeng.message.UmengMessageHandler
import com.umeng.message.UmengNotificationClickHandler
import com.umeng.message.api.UPushRegisterCallback
import com.umeng.message.entity.UMessage
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareConfig
import com.zx.lib.share.ShareConstant.MeiZuAppId
import com.zx.lib.share.ShareConstant.MeiZuAppKey
import com.zx.lib.share.ShareConstant.OppoAppKey
import com.zx.lib.share.ShareConstant.OppoAppSecret
import com.zx.lib.share.ShareConstant.UMAppKey
import com.zx.lib.share.ShareConstant.UMSecret
import com.zx.lib.share.ShareConstant.XiaomiId
import com.zx.lib.share.ShareConstant.XiaomiKey
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.mezu.MeizuRegister
import org.android.agoo.oppo.OppoRegister
import org.android.agoo.vivo.VivoRegister
import org.android.agoo.xiaomi.MiPushRegistar


/**
 * @description: 友盟初始化工具类
 * @author: zhouxiang
 * @created: 2021/07/16 14:43
 * @version: V1.0
 */
object UmengUtils {

    // preInit预初始化函数耗时极少，不会影响App首次冷启动用户体验
    fun preInit(context: Context) {
        val channel: String = getChannel(context)
        //解决推送消息显示乱码的问题
        PushAgent.setup(
            context,
           UMAppKey,
            UMSecret
        )
        UMConfigure.preInit(context,UMAppKey, channel)
    }
    //初始化
    fun init(context: Application,) {
        //配置了微信、QQ/Qzone、新浪的三方appkey，如果使用其他平台，在这里增加对应平台key配置
        initShareSdk()
        uMConfigInit(context)
    }

    /**
     * 初始化common库
     * 参数1:上下文，不能为空
     * 参数2:友盟 app key  58512226c62dca4c18000ba3
     * 参数3:友盟 channel
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret
     */
    private fun uMConfigInit(context: Context) {
        val channel = getChannel(context)
        UMConfigure.init(
            context,
            UMAppKey,
            channel,
            UMConfigure.DEVICE_TYPE_PHONE,
            UMSecret
        )
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.MANUAL)
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
//        UMConfigure.setLogEnabled(context.isDebug())
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        // UMConfigure.setEncryptEnabled(true);
        // 每次登陆都授权，防止切换第三方账号UID返回一致
        val config = UMShareConfig()
        config.isNeedAuthOnGetUserInfo(true)
        UMShareAPI.get(context).setShareConfig(config)
    }

    /**
     * 初始化友盟推送
     * @param alias 注册别名
     */
    private fun uMPushAgentInit(context: Application,alias:String) {
        // 有盟推送
        val mPushAgent = PushAgent.getInstance(context)
        // 注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : UPushRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回device token
                Log.e("uMPushAgentinit", deviceToken)
                //友盟 别名绑定
                uMAddAlias(context,alias)
            }

            override fun onFailure(s: String, s1: String) {
                Log.e("uMPushAgentinit","xm失败")
            }
        })
        // 友盟厂商通道初始化
        initChannel(context)
        mPushAgent.notificationClickHandler = object : UmengNotificationClickHandler() {
            override fun handleMessage(context: Context, uMessage: UMessage) {
                notificationClick(context, uMessage)
            }

            override fun dealWithCustomAction(context: Context, msg: UMessage) {
            }
        }
        val msgHandler: UmengMessageHandler = object : UmengMessageHandler() {
            //处理透传消息
            override fun dealWithCustomMessage(context: Context, msg: UMessage) {
                super.dealWithCustomMessage(context, msg)
                Log.i("mPushAgent", "custom receiver:" + msg.raw.toString())
            }
        }
        mPushAgent.messageHandler = msgHandler
    }

    /**
     * 友盟厂商通道初始化
     */
    fun initChannel(context: Application) {
        MiPushRegistar.register(context, XiaomiId, XiaomiKey)
        HuaWeiRegister.register(context) // appid定义在Manifest中
        MeizuRegister.register(context, MeiZuAppId, MeiZuAppKey)
        OppoRegister.register(context, OppoAppKey, OppoAppSecret)
        VivoRegister.register(context) // appid定义在Manifest中
    }

    /**
     * 友盟 别名绑定
     */
    fun uMAddAlias(context: Context,alias:String) {
        PushAgent.getInstance(context)
            .addAlias(
                alias, "ALIAS_TYPE.APP",
                ICallBack { isSuccess, message -> Log.e("deviceToken",
                    "addAlias=====$isSuccess") })
    }

    /**
     *友盟 删除别名
     */
    fun deleteAlias(context: Context,alias:String){
        PushAgent.getInstance(context).deleteAlias(
            alias, "ALIAS_TYPE.APP",
            ICallBack { isSuccess, message -> Log.e("Push", "deleteAlias=====$isSuccess") })
    }

    /**
     * 友盟推送通知栏点击
     */
    fun notificationClick(context: Context, uMessage: UMessage) {
        try {
            Log.e("Push", "uMessage=====" + uMessage.extra.toString())
            var linkType = "" //跳转类型
            val extra = uMessage.extra
            if (extra != null) {
                linkType = (if (extra.containsKey("linkType")) extra["linkType"] else "") as String
            }
            //两种：1.打开浏览器（默认会打开自带浏览器）；2.打开课程详情
            if ("go_app" == uMessage.after_open) {

            } else if ("go_url" == uMessage.after_open) {

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getChannel(context: Context): String{
        var channel: String? = WalleChannelReader.getChannel(context)
        if (TextUtils.isEmpty(channel)) {
            channel = "service"
        }
        return channel!!
    }

    fun initShareSdk() {
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
}