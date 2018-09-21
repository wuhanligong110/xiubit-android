package com.v5ent.xiubit.activity

import android.os.Bundle
import android.view.View
import com.toobei.common.entity.BankCardInfo
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.utils.PhotoUtil
import com.toobei.common.utils.SystemFunctionUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_bankcard_authent.*

/**
 * Created by yangLin on 2018/5/14.
 */
class BankCardAuthentActivity : NetBaseActivity() {
    private var hasCard = false

    override fun getContentLayout(): Int = R.layout.activity_bankcard_authent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasCard = intent.getBooleanExtra("hasCard", false)
        initView()
        loadCardData()
    }

    private fun initView() {
        headerLayout.showTitle("银行卡")
        if (hasCard) {
            addBankCardLl.visibility = View.GONE
            cardInfoLl.visibility = View.VISIBLE
            callPhone.setOnClickListener {
                SystemFunctionUtil.CallServicePhone(ctx, "0755-86725461")
            }

            loadCardData()
        }else{
            cardInfoLl.visibility = View.GONE
            addBankCardLl.visibility = View.VISIBLE
            addBankCardLl.setOnClickListener {
                skipActivity(ctx, CardManagerAdd::class.java)
            }
        }
    }


    private fun loadCardData() {
        RetrofitHelper.getInstance().retrofit.create(CardBindApi::class.java).getUserBindCard(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BankCardInfo>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BankCardInfo>) {
                        bankNameTv.text = response.data.bankName
                        val bankCard = response.data.bankCard
                        bankId.text = "**** **** **** ${bankCard.substring(bankCard.length - 4, bankCard.length)}"
                        PhotoUtil.loadImageByGlide(ctx, response.data.image, bgIv,R.drawable.bank_card_bg)
                        PhotoUtil.loadImageByGlide(ctx, response.data.ico, logoIv,R.drawable.bank_card_logo)
                    }

                })
    }
}