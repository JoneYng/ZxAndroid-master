package com.zx.lib.share.service

import androidx.fragment.app.FragmentManager
import com.zx.lib.share.ShearEnum
import com.zx.lib.share.fragment.ShareCommonViewFragment
import com.zx.lib.share.listener.ShareResultCallBack


/**
 * @description:
 * @author: zhouxiang
 * @created: 2022/07/01 11:36
 * @version: V1.0
 */
object AShareService {
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
}