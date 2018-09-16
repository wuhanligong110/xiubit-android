package com.v5ent.xiubit.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.utils.CacheUtils
import com.v5ent.xiubit.activity.CouponActivity
import com.v5ent.xiubit.entity.*
import com.v5ent.xiubit.network.httpapi.HomePageDialogApi
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import com.v5ent.xiubit.view.dialog.CouponHomeDialog
import com.v5ent.xiubit.view.dialog.DoubleElevenDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.xsl781.utils.Logger


/**
 * 公司: tophlc
 * 类说明:
 *
 * @author hasee-pc
 * @time 2017/10/26
 */
@SuppressLint("StaticFieldLeak")
object HomePageDialogManager {

    private var ctx: Activity? = null
    private var callback: ControlCallBack? = null

    private var doubleElevenData: HasNewDoubleElevenData? = null
    private var jobGradeData: JobGradeVoucherPopupData? = null
    private var redPacketData: HasNewRedPacketData? = null
    private var addFeeData: HasNewAddFeeCouponDatas? = null
    private var addFeeIncomeData: HasNewAddFeeUseRecordData? = null


    // 职级- 》加佣 -》红包-》双十一
    fun show(ctx: Activity, cb: ControlCallBack) {
        try {
            if (!LoginService.getInstance().isTokenExsit) return
            this.ctx = ctx
            this.callback = cb

        showJobGradeDialog()
        }catch (e:Exception){
            e.printStackTrace()
            Logger.e("首页弹窗",e)
        }
    }

    interface ControlCallBack {
        fun allowShow(): Boolean
    }

    private fun isAllowShow(): Boolean {
        return callback != null && callback!!.allowShow()
    }

    /**
     * 职级体验弹窗
     */
    private fun showJobGradeDialog() {

        fun showNext() {
            showAddFeeDialog()
        }

        fun show() {
            CacheUtils.getInstance().remove(C.CacheKey.HOME_DIALOG_JOBGRADE)
            if (!isAllowShow()) {
                showNext()
                jobGradeData = null
                return
            }
//        0:没有 1:有
            if (jobGradeData?.haveNewJobGrade ?: false == "true") {
                CouponHomeDialog(ctx!!, CouponHomeDialog.TYPE_JOBGRADE, "恭喜您获得${jobGradeData?.jobGrade ?: "职级"}体验券")
                        .setOnBtnClickListener(object : CouponHomeDialog.BtnClickListener {
                            override fun onConfirmClick() {
                                var intent = Intent(ctx, CouponActivity::class.java)
                                intent.putExtra("type", 1)
                                ctx?.startActivity(intent)
                            }

                            override fun onCloseClick() {
                                showNext()
                            }
                        }).show()

            } else {
                showNext()
            }
            jobGradeData = null
        }

        if (jobGradeData == null) jobGradeData = CacheUtils.getInstance().getSerializable(C.CacheKey.HOME_DIALOG_JOBGRADE) as JobGradeVoucherPopupData?

        if (jobGradeData != null ) show() else {
            RetrofitHelper.getInstance().retrofit.create(HomePageDialogApi::class.java)
                    .jobGradeVoucherPopup(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<JobGradeVoucherPopupEntity>() {

                        override fun bindViewWithDate(response: JobGradeVoucherPopupEntity) {
                            jobGradeData = response.data
                            show()
                            if (jobGradeData != null) CacheUtils.getInstance().put(C.CacheKey.HOME_DIALOG_JOBGRADE,jobGradeData!!)

                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            showNext()
                        }

                    })
        }

    }

    /**
     * 加佣券
     */
    private fun showAddFeeDialog() {
        fun showNext() {
            showAddFeeIncomeDialog()
        }

        fun show() {
            CacheUtils.getInstance().remove(C.CacheKey.HOME_DIALOG_ADDFEE)
            if (!isAllowShow()) {
                showNext()
                addFeeData = null
                return
            }
            //        0:没有 1:有
            if (addFeeData?.hasNewAddFeeCoupon ?: "0" == "1") {

                CouponHomeDialog(ctx!!, CouponHomeDialog.TYPE_ADDFEE, "恭喜您获得${addFeeData?.addFeeCoupon?.rate}%" +
                        when (addFeeData?.addFeeCoupon?.type) {
                            "1" -> "加佣券"
                            "2" -> "奖励券"
                            else -> "加佣券"
                        })
                        .setOnBtnClickListener(object : CouponHomeDialog.BtnClickListener {
                            override fun onConfirmClick() {
                                var intent = Intent(ctx, CouponActivity::class.java)
                                intent.putExtra("type", 2)
                                ctx?.startActivity(intent)
                            }

                            override fun onCloseClick() {
                                showNext()
                            }
                        }).show()
            } else {
                showNext()
            }

            addFeeData = null
        }


        if (addFeeData == null) addFeeData = CacheUtils.getInstance().getSerializable(C.CacheKey.HOME_DIALOG_ADDFEE) as HasNewAddFeeCouponDatas?
        if (addFeeData != null) show() else {

            RetrofitHelper.getInstance().retrofit.create(HomePageDialogApi::class.java)
                    .hasNewAddFeeCoupon(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<HasNewAddFeeCouponEntity>() {

                        override fun bindViewWithDate(response: HasNewAddFeeCouponEntity) {
                            addFeeData = response.data
                            show()
                            if(addFeeData != null) CacheUtils.getInstance().put(C.CacheKey.HOME_DIALOG_ADDFEE, addFeeData!!)
                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            showNext()
                        }

                    })
        }


    }

    /**
     * 加佣收益
     */
    private fun showAddFeeIncomeDialog() {
        fun showNext() {
            showRedPackDialog()
        }

        fun show() {
            CacheUtils.getInstance().remove(C.CacheKey.HOME_DIALOG_ADDFEE_INCOME)
            if (!isAllowShow()) {
                showNext()
                addFeeIncomeData = null
                return
            }
            //        0:没有 1:有
            if (addFeeIncomeData?.hasNewAddFee ?: "0" == "1") {

                CouponHomeDialog(ctx!!, CouponHomeDialog.TYPE_ADDFEE_INCOME, "恭喜您获得${addFeeIncomeData?.addFeeCouponUseDetail?.feeRate}%" +
                        when (addFeeIncomeData?.addFeeCouponUseDetail?.couponType) {
                            "1" -> "加佣"
                            "2" -> "奖励"
                            else -> "加佣"
                        }+"收益${addFeeIncomeData?.addFeeCouponUseDetail?.feeAmount}元！")
                        .setOnBtnClickListener(object : CouponHomeDialog.BtnClickListener {
                            override fun onConfirmClick() {

                            }

                            override fun onCloseClick() {
                                showNext()
                            }
                        }).show()
            } else {
                showNext()
            }

            addFeeIncomeData = null
        }


        if (addFeeIncomeData == null) addFeeIncomeData = CacheUtils.getInstance().getSerializable(C.CacheKey.HOME_DIALOG_ADDFEE_INCOME) as HasNewAddFeeUseRecordData?
        if (addFeeIncomeData != null) show() else {

            RetrofitHelper.getInstance().retrofit.create(HomePageDialogApi::class.java)
                    .hasNewAddFeeUseRecord(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<HasNewAddFeeUseRecordData>>() {

                        override fun bindViewWithDate(response: BaseResponseEntity<HasNewAddFeeUseRecordData>) {
                            addFeeIncomeData = response.data
                            show()
                            if(addFeeIncomeData != null) CacheUtils.getInstance().put(C.CacheKey.HOME_DIALOG_ADDFEE_INCOME, addFeeIncomeData!!)
                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            showNext()
                        }

                    })
        }


    }

    private fun showRedPackDialog() {
        fun showNext() {
            showDoubleEvenlyDialog()
        }

        //        是否有新的红包 0：没有 1：有
        fun show() {
            CacheUtils.getInstance().remove(C.CacheKey.HOME_DIALOG_REDPACK)
            if (!isAllowShow()) {
                showNext()
                redPacketData = null
                return
            }

            if (redPacketData?.hasNewRedPacket ?: "0" == "1") {

                CouponHomeDialog(ctx!!, CouponHomeDialog.TYPE_REDPACKET, "主人，你有新红包可以使用快快点我查看吧！")
                        .setOnBtnClickListener(object : CouponHomeDialog.BtnClickListener {
                            override fun onConfirmClick() {
                                var intent = Intent(ctx, CouponActivity::class.java)
                                intent.putExtra("type", 0)
                                ctx?.startActivity(intent)
                            }

                            override fun onCloseClick() {
                                showNext()
                            }
                        }).show()
            } else {
                showNext()
            }

            redPacketData = null
        }

        if (redPacketData == null) redPacketData = CacheUtils.getInstance().getSerializable(C.CacheKey.HOME_DIALOG_REDPACK) as HasNewRedPacketData?
        if (redPacketData != null) show() else {

            RetrofitHelper.getInstance().retrofit.create(HomePageDialogApi::class.java)
                    .hasNewRedPacket(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<HasNewRedPacketEntity>() {

                        override fun bindViewWithDate(response: HasNewRedPacketEntity) {
                            redPacketData = response.data
                            show()
                            if (redPacketData != null) CacheUtils.getInstance().put(C.CacheKey.HOME_DIALOG_REDPACK, redPacketData!!)
                        }

                        override fun onNetSystemException(error: String?) {
                            super.onNetSystemException(error)
                            showNext()
                        }

                    })
        }


    }


    private fun showDoubleEvenlyDialog() {


        fun show() {
            CacheUtils.getInstance().remove(C.CacheKey.HOME_DIALOG_DOUBLEEVELE)
            if (!isAllowShow()) {
                doubleElevenData = null
                return
            }
//        0:没有 1:有
            if (doubleElevenData?.hasNewDoubleEleven ?: "0" == "1") {
                DoubleElevenDialog(ctx!!,object:DoubleElevenDialog.BtnClickListener{
                    override fun onConfirmClick() {
                    }

                    override fun onCloseClick() {
                    }
                }).show()

            }else{
            }

            doubleElevenData = null
        }
        if (doubleElevenData == null) doubleElevenData = CacheUtils.getInstance().getSerializable(C.CacheKey.HOME_DIALOG_DOUBLEEVELE) as HasNewDoubleElevenData?
        if (doubleElevenData != null) show() else {

            RetrofitHelper.getInstance().retrofit.create(HomePageDialogApi::class.java)
                    .hasNewDoubleEleven(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<HasNewDoubleElevenEntity>() {

                        override fun bindViewWithDate(response: HasNewDoubleElevenEntity) {
                            doubleElevenData = response.data
                            show()
                            if (doubleElevenData != null) CacheUtils.getInstance().put(C.CacheKey.HOME_DIALOG_DOUBLEEVELE, doubleElevenData!!)
                        }

                    })
        }
    }


}
