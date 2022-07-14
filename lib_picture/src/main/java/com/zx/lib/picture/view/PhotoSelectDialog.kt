package com.zx.lib.picture.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener
import com.zx.lib.picture.PictureUtils
import com.zx.lib.picture.R


/**
 * @author：zhou
 * @date：2022-06-30 17:58
 * @describe：PhotoSelectDialog
 * 图片选择
 */
class PhotoSelectDialog : BottomSheetDialogFragment(), View.OnClickListener {
    private var mOnClickListener: OnPhotoClickListener? = null
    private var mSelectMedias:  List<LocalMedia?>? = null//当前选中的图片


    fun setSelectMedias(selectMedias: List<LocalMedia?>?) {
        mSelectMedias = selectMedias
    }

    fun setOnClickListener(onPhotoClickListener: OnPhotoClickListener) {
        mOnClickListener = onPhotoClickListener
    }
    interface OnPhotoClickListener {
        fun onTakePhotoClick(path: ArrayList<LocalMedia?>?)

        fun onSelectPhotoClick(list: ArrayList<LocalMedia?>?)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            resources.displayMetrics.widthPixels,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
//        dialog!!.window!!.setLayout(resources.displayMetrics.widthPixels - DisplayUtil.dip2px(16f) * 2, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.findViewById<View>(R.id.design_bottom_sheet)
            .setBackgroundResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_select, container, false)
        val btnSelectPhoto = view.findViewById<View>(R.id.btn_select_photo) as TextView
        val btnTakePhoto = view.findViewById<View>(R.id.btn_take_photo) as TextView
        val btnCancel = view.findViewById<View>(R.id.btn_cancel) as TextView

        btnSelectPhoto.setOnClickListener(this)
        btnTakePhoto.setOnClickListener(this)
        btnCancel.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View) {
        val i = v.id
        when (i) {
            R.id.btn_take_photo -> {
                //单独拍照
                PictureSelector.create(this)
                    .openCamera(SelectMimeType.ofImage())
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: ArrayList<LocalMedia?>?) {
                            if (mOnClickListener != null) {
                                mOnClickListener!!.onTakePhotoClick(result)
                            }
                            dismissAllowingStateLoss()
                        }
                        override fun onCancel() {

                        }
                    })
            }
            R.id.btn_select_photo -> {
                PictureUtils.chooseSinglePic(
                    requireActivity(),
                    mSelectMedias,
                    object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: ArrayList<LocalMedia?>?) {
                            if (mOnClickListener != null) {
                                mOnClickListener!!.onSelectPhotoClick(result)
                            }
                            dismissAllowingStateLoss()
                        }
                        override fun onCancel() {

                        }
                    })
            }
            R.id.btn_cancel -> {
                dismiss()
            }
        }
    }

    companion object {
        val TAG = PhotoSelectDialog::class.java.simpleName
        fun newInstance(): PhotoSelectDialog {
            return PhotoSelectDialog()
        }
    }
}
