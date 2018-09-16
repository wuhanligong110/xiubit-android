package com.v5ent.xiubit.view.dialog

import android.app.Activity
import android.app.Dialog
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.toobei.common.TopApp
import com.toobei.common.utils.TextDecorator
import com.toobei.common.view.FrameAnimDrawable
import com.toobei.common.view.RCRelativeLayout
import com.v5ent.xiubit.R
import com.v5ent.xiubit.entity.SignInData
import org.xsl781.utils.Logger

/**
 * 公司: tophlc
 * 类说明: 签到成功弹窗
 *
 * @author hasee-pc
 * @time 2017/5/5
 */

class SignInSuccessDialog(private val ctx: Activity,private var data: SignInData) : Dialog(ctx, com.v5ent.xiubit.R.style.Dialog_Fullscreen) {
    private var mBtnClickListener: BtnClickListener? = null
    lateinit var rootView : View
    lateinit var goldCoinAnimalIv: ImageView
    lateinit var mainView: RCRelativeLayout
    lateinit var contentTv: TextView
    private var soundPool: SoundPool = SoundPool(10, AudioManager.STREAM_SYSTEM, 5) //金币声

    init {
        soundPool.load(ctx, R.raw.sign_gold_out_vioce, 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        全屏显示dialog
        window!!.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        rootView = LayoutInflater.from(ctx).inflate(R.layout.dialog_sign_in_success, null)
        setContentView(rootView)
        ButterKnife.bind(this)

        setCancelable(false)
        initView()
    }

    private fun initView() {
        val fps = 15
        val frameAnimDrawable = FrameAnimDrawable(fps,getRes(R.array.frame_sign_gold_down_resArrayId), context.resources,false)
        goldCoinAnimalIv = rootView.findViewById<ImageView>(R.id.goldCoinAnimalIv)
        mainView = rootView.findViewById<RCRelativeLayout>(R.id.mainView)
        contentTv = rootView.findViewById<TextView>(R.id.contentTv)

        goldCoinAnimalIv.setImageDrawable(frameAnimDrawable)
        frameAnimDrawable.start()

        try {
            goldCoinAnimalIv.postDelayed({  soundPool.play(1, 1f, 1f, 0, 0, 1f) }, (1000 / fps * 10).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
            Logger.e(e)
        }
        goldCoinAnimalIv.postDelayed({ mainView!!.visibility = View.VISIBLE }, (1000 / fps * 18).toLong())
        setContentText(data)

    }

    private fun setContentText(data: SignInData): SignInSuccessDialog {
        this.data = data
        val str1 = "恭喜您！\n获得奖励金${data.bonus}元"
        var str2 = ""
        if (data.times != null && data.times!="0") { str2= "${data.bonus}元*${data.times}"}

        TextDecorator.decorate(contentTv, "$str1${if (str2.isNullOrBlank()) "" else "+$str2"}")
                .setTextColor(R.color.text_red_common, data.bonus?:"",str2)
                .build()
        return this
    }

    fun setOnBtnClickListener(listener: BtnClickListener): SignInSuccessDialog {
        mBtnClickListener = listener
        return this
    }

    interface BtnClickListener {

        fun onConfirmClick()

        fun onCloseClick()

    }


    @OnClick(R.id.btn, R.id.closedIv)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.btn -> if (mBtnClickListener != null) mBtnClickListener!!.onConfirmClick()
            R.id.closedIv -> if (mBtnClickListener != null) mBtnClickListener!!.onCloseClick()
        }
        dismiss()

    }

    override fun dismiss() {
        if (ctx.isFinishing) return
        super.dismiss()
    }

    companion object {

        fun getRes(resArrayId: Int): IntArray {
            val typedArray = TopApp.getInstance().resources.obtainTypedArray(resArrayId)
            val len = typedArray.length()
            val resId = IntArray(len)
            for (i in 0 until len) {
                resId[i] = typedArray.getResourceId(i, -1)
            }
            typedArray.recycle()
            return resId
        }
    }
}
