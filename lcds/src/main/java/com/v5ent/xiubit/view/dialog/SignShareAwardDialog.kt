package com.v5ent.xiubit.view.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import butterknife.OnClick
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.utils.TextDecorator
import com.toobei.common.view.RCRelativeLayout
import com.v5ent.xiubit.R
import com.v5ent.xiubit.activity.AwardMoneyActivity
import com.v5ent.xiubit.entity.SignSharePrizeData
import com.v5ent.xiubit.network.httpapi.SignInApi
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_sign_share_award.*

/**
 * 公司: tophlc
 * 类说明: 签到分享成功后弹窗
 *
 * @author hasee-pc
 * @time 2017/5/5
 */

class SignShareAwardDialog(private val ctx: Activity) : Dialog(ctx, com.v5ent.xiubit.R.style.customDialog) {
    private var mBtnClickListener: BtnClickListener? = null
    lateinit var rootView: View
    lateinit var btn: TextView
    lateinit var mainView: RCRelativeLayout
    lateinit var contentTv: TextView
    lateinit var awardBoxIv: ImageView
    lateinit var lookAwardMoneyEntry: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //全屏显示dialog
        rootView = LayoutInflater.from(ctx).inflate(R.layout.dialog_sign_share_award, null)
        setContentView(rootView)
        ButterKnife.bind(this)
        setCancelable(false)
        initView()
    }

    private fun initView() {
        mainView = rootView.findViewById<RCRelativeLayout>(R.id.mainView)
        contentTv = rootView.findViewById<TextView>(R.id.contentTv)
        awardBoxIv = rootView.findViewById<ImageView>(R.id.awardBoxIv)
        lookAwardMoneyEntry = rootView.findViewById<TextView>(R.id.lookAwardMoneyEntry)
        btn = rootView.findViewById<TextView>(R.id.btn)

        lookAwardMoneyEntry.setOnClickListener {
            ctx.startActivity(Intent(ctx, AwardMoneyActivity::class.java))
        }

    }

    fun setOnBtnClickListener(listener: BtnClickListener): SignShareAwardDialog {
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
            R.id.btn -> {
                if (mBtnClickListener != null) mBtnClickListener!!.onConfirmClick()
                getAward()
            }
            R.id.closedIv -> {
                if (mBtnClickListener != null) mBtnClickListener!!.onCloseClick()
                dismiss()

            }
        }


    }

    private fun getAward() {
        RetrofitHelper.getInstance().retrofit.create(SignInApi::class.java)
                .signSharePrize(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<SignSharePrizeData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<SignSharePrizeData>) {
                        refreshView(response.data)
                    }


                })
    }

    private fun refreshView(data: SignSharePrizeData) {
        btn.visibility = View.GONE
        tv2.visibility = View.GONE
        //	奖励类型 0：没有奖励 1：翻倍 2：红包
        when (data.prizeType) {
            "0" -> {
                awardBoxIv.setImageResource(R.drawable.img_share_awardbox_empty)
                contentTv.text = "打开礼包姿势不对，奖品溜了"
                lookAwardMoneyEntry.isClickable = false
                lookAwardMoneyEntry.text = "您已获得签到${data.bouns?:0}元奖励金"
            }
            "1" -> {
                awardBoxIv.setImageResource(R.drawable.img_gold_coin_init)
                TextDecorator.decorate(contentTv,"恭喜您！\n已获得奖励金翻倍${data.bouns?:0}元")
                        .setTextColor(R.color.text_red_common,"${data.bouns?:0}").build()
                lookAwardMoneyEntry.isClickable = false
                lookAwardMoneyEntry.text = "天天签到  天天领现金"
            }
            "2"->{
                awardBoxIv.setImageResource(R.drawable.img_share_award_redpacket)
                TextDecorator.decorate(contentTv,"恭喜您！\n获得${data.redpacketResponse.redpacketMoney?:0}元${data.redpacketResponse.name?:""}!")
                        .setTextColor(R.color.text_red_common,"${data.redpacketResponse.redpacketMoney?:0}").build()
                tv2.visibility = View.VISIBLE
                lookAwardMoneyEntry.isClickable = false
                lookAwardMoneyEntry.text = "天天签到  天天领现金"
            }
        }
    }

    override fun dismiss() {
        if (ctx == null  || ctx.isFinishing) return
        super.dismiss()
    }

    override fun show() {
        if (ctx == null  ||ctx.isFinishing) return
        super.show()
    }

}
