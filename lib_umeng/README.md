# lib_umeng 基于友盟的分享、推送基础库
``` 
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
AShareService.showShareView(supportFragmentManager,
                                "这是分享内容",
                                "这是分享标题",
                                "https://github.com/JoneYng",
                                "",
                                ShearEnum.ShareContentTypeEnum.OTHER,
                                null,
                                "",
                                object : ShareResultCallBack {//分享回调
                                    override fun onShareClick(type: ShearEnum.SharePlatType) {
                                    }

                                    override fun onShareCancel() {
                                    }
                                })
```
#三方登录
```
fun onWeChatClick(context:Activity,authListener: UMAuthListener) {
    UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.WEIXIN, authListener)
}

fun onQQClick(context:Activity,authListener: UMAuthListener) {
    UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.QQ, authListener)
}

fun onWeiboClick(context:Activity,authListener: UMAuthListener) {
    UMShareAPI.get(context).getPlatformInfo(context, SHARE_MEDIA.SINA, authListener)
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

/**
 * 三方登录回调
 */
private val authListener: UMAuthListener = object : UMAuthListener {
    private var uid: String? = ""
    private var openIdWx: String? = ""
    private var thirdLoginType: Int = 1
    private var thirdLoginAvatar: String? = ""
    private var thirdLoginNickName: String? = ""
    
    override fun onStart(paramSHARE_MEDIA: SHARE_MEDIA?) {
    }

    override fun onComplete(paramSHARE_MEDIA: SHARE_MEDIA?, paramInt: Int, paramMap: MutableMap<String, String>?) {
        paramMap?.let {
            when (paramSHARE_MEDIA) {
                SHARE_MEDIA.WEIXIN -> {
                    uid = paramMap["unionid"]
                    openIdWx = paramMap["openid"]
                    thirdLoginType = ParamsLoginHandler.THIRD_TYPE_WX
                    thirdLoginAvatar = paramMap["profile_image_url"]
                    thirdLoginNickName = paramMap["name"]
                }

                SHARE_MEDIA.QQ -> {
                    uid = paramMap["uid"]
                    thirdLoginType = ParamsLoginHandler.THIRD_TYPE_QQ
                    thirdLoginAvatar = paramMap["profile_image_url"]
                    thirdLoginNickName = paramMap["name"]

                }

                SHARE_MEDIA.SINA -> {
                    uid = paramMap["uid"]
                    thirdLoginType = ParamsLoginHandler.THIRD_TYPE_WB
                    thirdLoginAvatar = paramMap["profile_image_url"]
                    thirdLoginNickName = paramMap["name"]
                }

                else -> {
                }
            }

        }
    }

    override fun onCancel(SHARE_MEDIA: SHARE_MEDIA?, paramInt: Int) {
    }

    override fun onError(paramSHARE_MEDIA: SHARE_MEDIA?, paramInt: Int, paramThrowable: Throwable?) {
    }
}
```
