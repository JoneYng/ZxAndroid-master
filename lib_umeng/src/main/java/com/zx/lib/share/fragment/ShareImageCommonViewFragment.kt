package com.zx.lib.share.fragment


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Base64
import android.view.*
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.zx.lib.share.*
import com.zx.lib.share.ShareConstant.SHARE_IMAGE_URL
import com.zx.lib.share.ShareConstant.SHARE_PLAT_TYPE
import com.zx.lib.share.ShareConstant.SHARE_TEXT
import com.zx.lib.share.ShareConstant.SHARE_TITLE
import com.zx.lib.share.listener.ShareResultCallBack
import kotlinx.android.synthetic.main.select_share_image_alert_dialog.*
import java.io.File

/**
 * 分享图片
 */
class ShareImageCommonViewFragment : DialogFragment(), View.OnClickListener, UMShareListener {

    private var imageUrl: String? = ""
    private var shareText: String? = ""
    private var shareTitle: String? = ""
    private var tempImageUrl: String? = imageUrl
    private var sharePlatType: Int? = 0
    private var mCallback: ShareResultCallBack? = null

    companion object {
        fun newShareImageCommonViewFragmentInstance(
            shareText: String?,
            shareTitle: String?,
            shareImage: String?,
            platType: ShearEnum.SharePlatType?,
        ): ShareImageCommonViewFragment {
            val bundle = Bundle()
            bundle.putString(SHARE_IMAGE_URL, shareImage)
            bundle.putString(SHARE_TEXT, shareText)
            bundle.putString(SHARE_TITLE, shareTitle)
            if (platType != null) {
                bundle.putInt(SHARE_PLAT_TYPE, platType.value)
            }
            val fragment = ShareImageCommonViewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setStyle(STYLE_NORMAL, R.style.ShareDialogNoFullScreen)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //注意此处
        //设置全屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = Color.TRANSPARENT
        }

        val wlp = window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.width = WindowManager.LayoutParams.MATCH_PARENT
        wlp?.height = WindowManager.LayoutParams.MATCH_PARENT
        //设置刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            wlp?.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window?.attributes = wlp
        //设置dialog的 进出 动画
        window?.setWindowAnimations(R.style.select_share_dialog_style)
        return inflater.inflate(R.layout.select_share_image_alert_dialog, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            UmengUtils.initShareSdk()
        } catch (ex: Exception) {
        }
        initView()
        initData()
        //增加显示时动画
        view?.viewTreeObserver?.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                //执行一次就移除
                view?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                view?.post { showStartAnimate() }
            }
        })
        // 查看是否指定了平台
        val platType = ShearEnum.SharePlatType.getPlatType(sharePlatType!!)
        if (platType != null) {
            onShareClick(platType)
        }
    }


    private fun initView() {
        pop_top_layout.setOnClickListener(this)
        pop_layout.setOnClickListener(this)
        share_select_cancle.setOnClickListener(this)
        share_select_qq.setOnClickListener(this)
        share_select_qzome.setOnClickListener(this)
        share_select_sina.setOnClickListener(this)
        share_select_wechat.setOnClickListener(this)
        share_select_wechatm.setOnClickListener(this)


    }

    private fun initData() {
        if (arguments == null) {
            doDismiss()
            return
        }

        imageUrl = arguments?.getString(SHARE_IMAGE_URL, "")

        shareText = arguments?.getString(SHARE_TEXT, "")

        shareTitle = arguments?.getString(SHARE_TITLE, "")

        sharePlatType = arguments?.getInt(SHARE_PLAT_TYPE, 0)

        tempImageUrl = imageUrl

        if (TextUtils.isEmpty(shareText)) {
            shareText = getString(R.string.share_null_text)
        }

        imageUrl.let {
            if (imageUrl!!.startsWith("http://") || imageUrl!!.startsWith("https://")) {
                Glide.with(this).load(imageUrl).into(img_pop)
            } else if(ShareSDKUtil.isBase64(imageUrl!!)){
                //将字符串转换成Bitmap类型
                var bitmap: Bitmap? = null
                try {
                    val bitmapArray = Base64.decode(
                        imageUrl!!.split(",").toTypedArray().get(1),
                        Base64.DEFAULT
                    ) //注意解码的时候要把编码的头（"data:image/png;base64,"）去掉，否则将会失效
                    bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                img_pop.setImageBitmap(bitmap)
            }else {
//                umImage = UMImage(activity, File(imageUrl))
            }
        }
    }

    override fun onClick(v: View?) {
        if (v == null || ShareSDKUtil.isFastDoubleClick) {
            return
        }

        if (v.id == R.id.share_select_qq) {
            if (ShareSDKUtil.checkQQ(requireContext())) {
                onShareClick(ShearEnum.SharePlatType.QQ)
                return
            }
        }

        if (v.id == R.id.share_select_qzome) {
            if (ShareSDKUtil.checkQQ(requireContext())) {
                onShareClick(ShearEnum.SharePlatType.QZONE)
                return
            }

        }

        if (v.id == R.id.share_select_wechat) {
            if (ShareSDKUtil.checkWeiXin(requireContext())) {
                onShareClick(ShearEnum.SharePlatType.WEICHAT)
                return
            }
        }

        if (v.id == R.id.share_select_wechatm) {
            if (ShareSDKUtil.checkWeiXin(requireContext())) {
                onShareClick(ShearEnum.SharePlatType.WEIMENT)
                return
            }
        }

        if (v.id == R.id.share_select_sina) {
            if (ShareSDKUtil.checkSina(requireContext())) {
                onShareClick(ShearEnum.SharePlatType.SINA)
                return
            }
        }
        doDismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(activity).onActivityResult(requestCode, resultCode, data)
    }

    private fun onShareClick(sharePlatType: ShearEnum.SharePlatType?) {
        if (sharePlatType == null) {
            Toast.makeText(context, "平台信息错误", LENGTH_SHORT).show()
            return
        }

        if (!isAdded) {
            return
        }

        mCallback?.onShareClick(sharePlatType)

        tempImageUrl = imageUrl
        if (TextUtils.isEmpty(imageUrl)) {
            tempImageUrl = ShareConstant.URL_SHARE_DEFAULT_IMAGE
        }

        try {
            when (sharePlatType) {
                ShearEnum.SharePlatType.QQ -> {
                    ShareSDKUtil.doQQShare(
                        requireActivity(),
                        this,
                        shareTitle,
                        "",
                        shareText,
                        tempImageUrl!!
                    )
                }
                ShearEnum.SharePlatType.QZONE -> {
                    ShareSDKUtil.doQZoneShare(
                        requireActivity(),
                        this,
                        shareTitle,
                        "",
                        shareText,
                        tempImageUrl!!
                    )
                }
                ShearEnum.SharePlatType.SINA -> {
                    ShareSDKUtil.doSinaShare(
                        requireActivity(),
                        this,
                        ShareConstant.KAOYANBANG_WEIBO_ACCOUNT.toString() + " " + shareTitle,
                        shareText,
                        "",
                        tempImageUrl!!
                    )
                }
                ShearEnum.SharePlatType.WEICHAT -> {
                    ShareSDKUtil.doWeChatShare(
                        requireActivity(),
                        this,
                        shareTitle,
                        shareText,
                        tempImageUrl!!,
                        ""
                    )
                }
                ShearEnum.SharePlatType.WEIMENT -> {
                    ShareSDKUtil.doWeChatMShare(
                        requireActivity(),
                        this,
                        shareTitle,
                        shareText,
                        tempImageUrl!!,
                        ""
                    )
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            Toast.makeText(
                context,
                getString(R.string.activity_share_exception_string),
                LENGTH_SHORT
            ).show()
            doDismiss()
        }
        doDismiss()
    }


    private fun doDismiss() {
        showEndAnimate()
        dismiss()
    }

    override fun dismiss() {
        super.dismiss()
        mCallback?.onShareCancel()
    }

    /**
     * 展示启动动画
     */
    private fun showStartAnimate() {
        val objectAnimator = getAlphaAnimation(view, 400, true)
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                view!!.visibility = View.VISIBLE
            }
        })
        objectAnimator.start()
    }

    private fun showEndAnimate() {
        val objectAnimator = getAlphaAnimation(view, 400, false)
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view!!.visibility = View.INVISIBLE
                dismiss()
            }
        })
        objectAnimator.end()
    }

    /**
     * 获取透明动画
     * @param view
     * @param duration
     * @param isStart
     * @return
     */
    private fun getAlphaAnimation(view: View?, duration: Long, isStart: Boolean): ObjectAnimator {
        val fromValue = if (isStart) 0f else 1.0f
        val toValue = if (isStart) 1.0f else 0f
        return ObjectAnimator.ofFloat(view, "alpha", fromValue, toValue).setDuration(duration)
    }

    public fun setShareResultCallback(shareResultCallBack: ShareResultCallBack?) {
        mCallback = shareResultCallBack
    }

    override fun onStart(p0: SHARE_MEDIA?) {

    }

    override fun onResult(p0: SHARE_MEDIA?) {
        dismiss()
    }

    override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
        dismiss()

    }

    override fun onCancel(p0: SHARE_MEDIA?) {
        dismiss()
    }
}