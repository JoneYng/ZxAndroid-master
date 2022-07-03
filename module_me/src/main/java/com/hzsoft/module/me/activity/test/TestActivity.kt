package com.hzsoft.module.me.activity.test

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hzsoft.lib.base.utils.ToastUtil
import com.hzsoft.lib.base.view.BaseActivity
import com.zx.lib.picture.manager.FullyGridLayoutManager
import com.zx.lib.picture.PictureUtils
import com.zx.lib.picture.adapter.GridImageAdapter
import com.hzsoft.module.me.R
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.utils.DensityUtil
import com.luck.picture.lib.utils.MediaUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zx.lib.share.ShearEnum
import com.zx.lib.share.listener.ShareResultCallBack
import com.zx.lib.share.service.AShareService

class TestActivity : BaseActivity(),View.OnClickListener{
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, TestActivity::class.java).apply {
            })
        }
    }
    private lateinit var editText: EditText
    private lateinit var  mRecyclerView:RecyclerView
    private lateinit var mAdapter: GridImageAdapter
    private val mData: List<LocalMedia> = ArrayList()
    private val maxSelectNum = 9
    private val maxSelectVideoNum = 1
    override fun getTootBarTitle(): String = "测试一下"

    //开启返回按钮
    override fun enableToolBarLeft(): Boolean = true
    override fun onBindLayout(): Int {
        return  R.layout.activity_test
    }

    override fun initView() {
        editText = findViewById(R.id.editText)
        mRecyclerView = findViewById(R.id.recycler)


//        List<LocalMedia> list = new ArrayList<>();
//        list.add(LocalMedia.generateLocalMedia("https://fdfs.test-kepu.weiyilewen.com/group1/M00/00/01/wKhkY2Iv936EMKWzAAAAAHuLNY8762.mp4", PictureMimeType.ofMP4()));
//        list.add(LocalMedia.generateLocalMedia("http:/125.124.10.5:81/dfs2/group1/M00/0E/31/CtosLGI0V5aACBl9AFZJdmcSWKg004.mp4", PictureMimeType.ofMP4()));
//        list.add(LocalMedia.generateLocalMedia("https://wx1.sinaimg.cn/mw2000/0073ozWdly1h0afogn4vij30u05keb29.jpg", PictureMimeType.ofJPEG()));
//        list.add(LocalMedia.generateLocalMedia("https://wx3.sinaimg.cn/mw2000/0073ozWdly1h0afohdkygj30u05791kx.jpg", PictureMimeType.ofJPEG()));
//        list.add(LocalMedia.generateLocalMedia("https://wx2.sinaimg.cn/mw2000/0073ozWdly1h0afoi70m2j30u05fq1kx.jpg", PictureMimeType.ofJPEG()));
//        list.add(LocalMedia.generateLocalMedia("https://wx2.sinaimg.cn/mw2000/0073ozWdly1h0afoipj8xj30kw3kmwru.jpg", PictureMimeType.ofJPEG()));
//        list.add(LocalMedia.generateLocalMedia("https://wx4.sinaimg.cn/mw2000/0073ozWdly1h0afoj5q8ij30u04gqkb1.jpg", PictureMimeType.ofJPEG()));
//        list.add(LocalMedia.generateLocalMedia("https://ww1.sinaimg.cn/bmiddle/bcd10523ly1g96mg4sfhag20c806wu0x.gif", PictureMimeType.ofGIF()));
//        mData.addAll(list);
        val manager = FullyGridLayoutManager(
            this,
            4, GridLayoutManager.VERTICAL, false
        )
        mRecyclerView.layoutManager = manager
        val itemAnimator: RecyclerView.ItemAnimator? = mRecyclerView.itemAnimator
        if (itemAnimator != null) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
        }
        mRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                4,
                DensityUtil.dip2px(this, 8f), false
            )
        )
        mAdapter = GridImageAdapter(this, mData)
        mAdapter.selectMax = maxSelectNum + maxSelectVideoNum
        mRecyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : GridImageAdapter.OnItemClickListener{
            override fun onItemClick(v: View?, position: Int) {
                PictureUtils.picturePreviewLocalMedia(this@TestActivity,position,mAdapter.data)
            }

            override fun openPicture() {
                PictureUtils.choosePic(this@TestActivity,PictureConfig.CHOOSE_REQUEST,maxSelectNum,mAdapter.data)
            }
        })
    }

    override fun initData() {
    }

   override fun initListener() {
        findViewById<Button>(R.id.button_3).setOnClickListener(this::onClick)
        findViewById<Button>(R.id.button_4).setOnClickListener(this::onClick)
        findViewById<Button>(R.id.button_5).setOnClickListener(this::onClick)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_3 -> {
                val trim = editText.text.toString().trim()
                if (trim.isBlank()) {
                    ToastUtil.showToastCenter("输入内容不能为空")
                    return
                }
                SaveStateTestActivity.start(this, trim)
            }
            R.id.button_4 -> {
                RoomTestActivity.start(this)
            }
            R.id.button_5 -> {
               var rxPermission= RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { permission ->
                        if (permission) {
                           AShareService.showShareView(supportFragmentManager,
                                "这是分享内容",
                                "这是分享标题",
                                "https://github.com/JoneYng",
                                "",
                                ShearEnum.ShareContentTypeEnum.OTHER,
                                null,
                                "",
                                object : ShareResultCallBack {
                                    override fun onShareClick(type: ShearEnum.SharePlatType) {
                                    }

                                    override fun onShareCancel() {
                                    }
                                })
                        }
                    }

            }
        }
    }

    /**
     * 处理选择结果
     * @param result
     */
    private fun analyticalSelectResults(result: ArrayList<LocalMedia>) {
        for (media in result) {
            if (media.width == 0 || media.height == 0) {
                if (PictureMimeType.isHasImage(media.mimeType)) {
                    val imageExtraInfo = MediaUtils.getImageSize(this, media.path)
                    media.width = imageExtraInfo.width
                    media.height = imageExtraInfo.height
                } else if (PictureMimeType.isHasVideo(media.mimeType)) {
                    val videoExtraInfo = MediaUtils.getVideoSize(this, media.path)
                    media.width = videoExtraInfo.width
                    media.height = videoExtraInfo.height
                }
            }
//        Log.i(TAG, "文件名: " + media.getFileName());
//        Log.i(TAG, "是否压缩:" + media.isCompressed());
//        Log.i(TAG, "压缩:" + media.getCompressPath());
//        Log.i(TAG, "初始路径:" + media.getPath());
//        Log.i(TAG, "绝对路径:" + media.getRealPath());
//        Log.i(TAG, "是否裁剪:" + media.isCut());
//        Log.i(TAG, "裁剪路径:" + media.getCutPath());
//        Log.i(TAG, "是否开启原图:" + media.isOriginal());
//        Log.i(TAG, "原图路径:" + media.getOriginalPath());
//        Log.i(TAG, "沙盒路径:" + media.getSandboxPath());
//        Log.i(TAG, "水印路径:" + media.getWatermarkPath());
//        Log.i(TAG, "视频缩略图:" + media.getVideoThumbnailPath());
//        Log.i(TAG, "原始宽高: " + media.getWidth() + "x" + media.getHeight());
//        Log.i(TAG, "裁剪宽高: " + media.getCropImageWidth() + "x" + media.getCropImageHeight());
//        Log.i(TAG, "文件大小: " + PictureFileUtils.formatAccurateUnitFileSize(media.getSize()));
        }
        runOnUiThread {
            val isMaxSize = result.size == mAdapter.selectMax
            val oldSize: Int = mAdapter.data.size
            mAdapter.notifyItemRangeRemoved(0, if (isMaxSize) oldSize + 1 else oldSize)
            mAdapter.data.clear()
            mAdapter.data.addAll(result)
            mAdapter.notifyItemRangeInserted(0, result.size)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST || requestCode == PictureConfig.REQUEST_CAMERA) {
                val result = PictureSelector.obtainSelectorList(data)
                analyticalSelectResults(result)
            }
        } else if (resultCode == RESULT_CANCELED) {

        }
    }
}