package com.zx.lib.picture

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.config.PictureSelectionConfig.selectorStyle
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
import com.luck.picture.lib.interfaces.OnMediaEditInterceptListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.luck.picture.lib.style.BottomNavBarStyle
import com.luck.picture.lib.style.PictureSelectorStyle
import com.luck.picture.lib.style.SelectMainStyle
import com.luck.picture.lib.style.TitleBarStyle
import com.luck.picture.lib.utils.DateUtils
import com.luck.picture.lib.utils.StyleUtils
import com.yalantis.ucrop.UCrop
import com.zx.lib.picture.manager.GlideEngine
import java.io.File


/**
 * @description:
 * @author: zhouxiang
 * @created: 2022/06/22 18:05
 * @version: V1.0
 */
//@readme: https://github.com/LuckSiege/PictureSelector/blob/version_component/README_CN.md
object PictureUtils {
    var IMAGE_CACHE_DIR = "IMAGE"
    /**
     * 配置图片选择
     */
    fun getStyle(context: Context): PictureSelectorStyle {
        val selectorStyle = PictureSelectorStyle()
        val whiteTitleBarStyle = TitleBarStyle()
        whiteTitleBarStyle.titleBackgroundColor =
            ContextCompat.getColor(context, R.color.ps_color_white)
        whiteTitleBarStyle.titleDrawableRightResource = R.mipmap.ic_orange_arrow_down
        whiteTitleBarStyle.titleLeftBackResource = R.drawable.ps_ic_black_back
        whiteTitleBarStyle.titleTextColor =
            ContextCompat.getColor(context, R.color.ps_color_black)
        whiteTitleBarStyle.titleCancelTextColor =
            ContextCompat.getColor(context, R.color.ps_color_53575e)
        whiteTitleBarStyle.isDisplayTitleBarLine = true

        val whiteBottomNavBarStyle = BottomNavBarStyle()
        whiteBottomNavBarStyle.bottomNarBarBackgroundColor = Color.parseColor("#EEEEEE")
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(context, R.color.ps_color_53575e)

        whiteBottomNavBarStyle.bottomPreviewNormalTextColor =
            ContextCompat.getColor(context, R.color.ps_color_9b)
        whiteBottomNavBarStyle.bottomPreviewSelectTextColor =
            ContextCompat.getColor(context, R.color.ps_color_fa632d)
        whiteBottomNavBarStyle.isCompleteCountTips = false
        whiteBottomNavBarStyle.bottomEditorTextColor =
            ContextCompat.getColor(context, R.color.ps_color_53575e)
        whiteBottomNavBarStyle.bottomOriginalTextColor =
            ContextCompat.getColor(context, R.color.ps_color_53575e)

        val selectMainStyle = SelectMainStyle()
        selectMainStyle.statusBarColor =
            ContextCompat.getColor(context, R.color.ps_color_white)
        selectMainStyle.isDarkStatusBarBlack = true
        selectMainStyle.selectNormalTextColor =
            ContextCompat.getColor(context, R.color.ps_color_9b)
        selectMainStyle.selectTextColor =
            ContextCompat.getColor(context, R.color.ps_color_fa632d)
        selectMainStyle.previewSelectBackground = R.drawable.ps_checkbox_selector
        selectMainStyle.selectBackground = R.drawable.ps_checkbox_selector
//        selectMainStyle.selectText = "%1$d/%2$d 完成"
        selectMainStyle.mainListBackgroundColor =
            ContextCompat.getColor(context, R.color.ps_color_white)

        selectorStyle.titleBarStyle = whiteTitleBarStyle
        selectorStyle.bottomBarStyle = whiteBottomNavBarStyle
        selectorStyle.selectMainStyle = selectMainStyle

        return selectorStyle
    }

    fun picturePreview(context: Activity, position: Int, url: List<String>) {
        PictureSelector.create(context)
            .openPreview()
            .setSelectorUIStyle(getStyle(context))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                override fun onPreviewDelete(position: Int) {}
                override fun onLongPressDownload(media: LocalMedia): Boolean {
                    return false
                }
            }).startActivityPreview(position, true, getLocalMedia(url))
    }

    fun picturePreview(context: FragmentActivity, position: Int, url: List<String>) {
        PictureSelector.create(context)
            .openPreview()
            .setSelectorUIStyle(getStyle(context))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                override fun onPreviewDelete(position: Int) {}
                override fun onLongPressDownload(media: LocalMedia): Boolean {
                    return false
                }
            }).startActivityPreview(position, true, getLocalMedia(url))
    }

    fun picturePreview(context: Fragment, position: Int, url: List<String>) {
        PictureSelector.create(context)
            .openPreview()
            .setSelectorUIStyle(getStyle(context.requireContext()))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                override fun onPreviewDelete(position: Int) {}
                override fun onLongPressDownload(media: LocalMedia): Boolean {
                    return false
                }
            }).startActivityPreview(position, true, getLocalMedia(url))
    }

    fun picturePreviewLocalMedia(context: Activity, position: Int, medias: List<LocalMedia>) {
        val mediasList = ArrayList<LocalMedia>()
        mediasList.addAll(medias)
        PictureSelector.create(context)
            .openPreview()
            .setSelectorUIStyle(getStyle(context))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                override fun onPreviewDelete(position: Int) {}
                override fun onLongPressDownload(media: LocalMedia): Boolean {
                    return false
                }
            }).startActivityPreview(position, true, mediasList)
    }
    /**
     * 选择照片
     * @param
     */
    fun choosePic(activity: Activity, requestCode: Int, max: Int, selectMedias: List<LocalMedia>?) {
        PictureSelector.create(activity)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(getStyle(activity))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(max)//图片最大选择数量
            .isMaxSelectEnabledMask(true)//达到最大选择数是否开启禁选蒙层
            .isOriginalControl(true)//是否开启原图功能
            .setEditMediaInterceptListener(getCustomEditMediaEvent(activity))//编辑
            .setSelectedData(selectMedias)
            .forResult(requestCode)
    }

    /**
     * 选择一张照片 直接返回
     */
    fun chooseSinglePic(activity: Activity, requestCode: Int, selectMedias: List<LocalMedia>?) {
        PictureSelector.create(activity)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(getStyle(activity))
            .setImageEngine(GlideEngine.createGlideEngine())
            .setMaxSelectNum(1)//图片最大选择数量
            .isMaxSelectEnabledMask(true)//达到最大选择数是否开启禁选蒙层
            .isOriginalControl(true)//是否开启原图功能
            .setEditMediaInterceptListener(getCustomEditMediaEvent(activity))//编辑
            .setSelectedData(selectMedias)
            .isDirectReturnSingle(true)
            .forResult(requestCode)
    }

    /**
     * 选择一张照片 直接返回
     */
    fun chooseSinglePic(activity: Activity, selectMedias: List<LocalMedia?>?,callBack:OnResultCallbackListener<LocalMedia?>) {
        PictureSelector.create(activity)
            .openGallery(SelectMimeType.ofImage())
            .setSelectorUIStyle(getStyle(activity))
            .setImageEngine(GlideEngine.createGlideEngine()) .setMaxSelectNum(1)//图片最大选择数量
            .isMaxSelectEnabledMask(true)//达到最大选择数是否开启禁选蒙层
            .isOriginalControl(true)//是否开启原图功能
            .setEditMediaInterceptListener(getCustomEditMediaEvent(activity))//编辑
            .setSelectedData(selectMedias)
            .isDirectReturnSingle(true)
            .forResult(callBack)
    }

    fun getLocalMedia(list: List<String>): ArrayList<LocalMedia> {
        val list1 = ArrayList<LocalMedia>()
        if (list.isNotEmpty()) {
            for (info in list) {
                val localMedia = LocalMedia()
                localMedia.path = info
                list1.add(localMedia)
            }
        }
        return list1
    }

    /**
     * 自定义编辑事件
     *
     * @return
     */
    private fun getCustomEditMediaEvent(context:Context): OnMediaEditInterceptListener {
        return MeOnMediaEditInterceptListener(
            getSandboxPath(context),
            buildOptions(context)
        )
    }

    /**
     * 创建自定义输出目录
     *
     * @return
     */
    private fun getSandboxPath(context:Context): String {
        val externalFilesDir: File? = context.getExternalFilesDir("")
        val customFile = File(externalFilesDir?.absolutePath, IMAGE_CACHE_DIR)
        if (!customFile.exists()) {
            customFile.mkdirs()
        }
        return customFile.absolutePath + File.separator
    }

    /**
     * 配制UCrop，可根据需求自我扩展
     *
     * @return
     */
    private fun buildOptions(context:Context): UCrop.Options {
        val options = UCrop.Options()
//        options.setHideBottomControls(!cb_hide.isChecked())
//        options.setFreeStyleCropEnabled(cb_styleCrop.isChecked())
//        options.setShowCropFrame(cb_showCropFrame.isChecked())
//        options.setShowCropGrid(cb_showCropGrid.isChecked())
//        options.setCircleDimmedLayer(cb_crop_circular.isChecked())
//        options.withAspectRatio(aspect_ratio_x.toFloat(), aspect_ratio_y.toFloat())
        options.setCropOutputPathDir(getSandboxPath(context))
        options.isCropDragSmoothToCenter(false)
//        options.setSkipCropMimeType(getNotSupportCrop())
//        options.isForbidCropGifWebp(cb_not_gif.isChecked())
        options.isForbidSkipMultipleCrop(false)
        options.setMaxScaleMultiplier(100f)
        if (selectorStyle != null && selectorStyle.selectMainStyle.statusBarColor != 0) {
            val mainStyle: SelectMainStyle = selectorStyle.selectMainStyle
            val isDarkStatusBarBlack = mainStyle.isDarkStatusBarBlack
            val statusBarColor = mainStyle.statusBarColor
            options.isDarkStatusBarBlack(isDarkStatusBarBlack)
            if (StyleUtils.checkStyleValidity(statusBarColor)) {
                options.setStatusBarColor(statusBarColor)
                options.setToolbarColor(statusBarColor)
            } else {
                options.setStatusBarColor(
                    ContextCompat.getColor(
                        context,
                        R.color.ps_color_grey
                    )
                )
                options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            }
            val titleBarStyle: TitleBarStyle = selectorStyle.titleBarStyle
            if (StyleUtils.checkStyleValidity(titleBarStyle.titleTextColor)) {
                options.setToolbarWidgetColor(titleBarStyle.titleTextColor)
            } else {
                options.setToolbarWidgetColor(
                    ContextCompat.getColor(
                        context,
                        R.color.ps_color_white
                    )
                )
            }
        } else {
            options.setStatusBarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            options.setToolbarColor(ContextCompat.getColor(context, R.color.ps_color_grey))
            options.setToolbarWidgetColor(
                ContextCompat.getColor(
                    context,
                    R.color.ps_color_white
                )
            )
        }
        return options
    }

    /**
     * 自定义编辑
     */
    private class MeOnMediaEditInterceptListener(
        private val outputCropPath: String,
        private val options: UCrop.Options
    ) :
        OnMediaEditInterceptListener {
        override fun onStartMediaEdit(
            fragment: Fragment,
            currentLocalMedia: LocalMedia,
            requestCode: Int
        ) {
            val currentEditPath = currentLocalMedia.availablePath
            val inputUri =
                if (PictureMimeType.isContent(currentEditPath)) Uri.parse(currentEditPath) else Uri.fromFile(
                    File(currentEditPath)
                )
            val destinationUri = Uri.fromFile(
                File(outputCropPath, DateUtils.getCreateFileName("CROP_") + ".jpeg")
            )
            val uCrop = UCrop.of<Any>(inputUri, destinationUri)
            options.setHideBottomControls(false)
            uCrop.withOptions(options)
            uCrop.startEdit(fragment.requireActivity(), fragment, requestCode)
        }
    }

}