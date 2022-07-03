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
