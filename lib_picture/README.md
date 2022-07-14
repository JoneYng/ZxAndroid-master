# lib_picture 图片选择库
## 多张图片选择
``` 
//LayoutManager 布局管理器
val manager = FullyGridLayoutManager(
    this,
    4, GridLayoutManager.VERTICAL, false
)
mRecyclerView.layoutManager = manager
//item动画
val itemAnimator: RecyclerView.ItemAnimator? = mRecyclerView.itemAnimator
if (itemAnimator != null) {
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
        false
}
//添加间距
mRecyclerView.addItemDecoration(
    GridSpacingItemDecoration(
        4,
        DensityUtil.dip2px(this, 8f), false
    )
)
mAdapter = GridImageAdapter(this, mData)
//最大选择数量
mAdapter.selectMax = maxSelectNum + maxSelectVideoNum
mRecyclerView.adapter = mAdapter
//添加点击事件
mAdapter.setOnItemClickListener(object :GridImageAdapter.OnItemClickListener{
    override fun onItemClick(v: View?, position: Int) {
        //预览图片
        PictureUtils.picturePreviewLocalMedia(this@TestActivity,position,mAdapter.data)
    }

    override fun openPicture() {
        //添加图片
        PictureUtils.choosePic(this@TestActivity,PictureConfig.CHOOSE_REQUEST,maxSelectNum,mAdapter.data)
    }
})
//回调结果
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
```


## 单张图片选择
 ```
//当前已选择图片
private var mSelectMedias:  ArrayList<LocalMedia?>? = null
val mPhotoSelectDialog = PhotoSelectDialog.newInstance()
            mPhotoSelectDialog.showNow(requireFragmentManager(), "MainMeFragment")
            //设置当前选中图片
            mPhotoSelectDialog.setSelectMedias(mSelectMedias)
            mPhotoSelectDialog.setOnClickListener(object :
                PhotoSelectDialog.OnPhotoClickListener{
                override fun onTakePhotoClick(path: ArrayList<LocalMedia?>?) {
                    mSelectMedias=path
                }
                override fun onSelectPhotoClick(list: ArrayList<LocalMedia?>?) {
                    mSelectMedias=list
                }
            })
```
