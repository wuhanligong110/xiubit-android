package com.v5ent.xiubit.activity

import android.os.Bundle
import android.view.View
import com.toobei.common.entity.BankCardInfo
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_name_authent.*

/**
 * Created by yangLin on 2018/5/14.
 */
class NameAuthentActivity : NetBaseActivity() {
    var hasAuthent = false
    override fun getContentLayout(): Int = R.layout.activity_name_authent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasAuthent =  intent.getBooleanExtra("hasAuthent",false)
        initView()
    }


    private fun initView() {
        headerLayout.showTitle("实名认证")

        if (hasAuthent){
            hasAuthentLl.visibility = View.VISIBLE
            hasNoAuthentLl.visibility = View.GONE
            loadUserData()
        }else{
            hasAuthentLl.visibility = View.GONE
            hasNoAuthentLl.visibility = View.VISIBLE
            goNameAuthentTv.setOnClickListener {
                skipActivity(this, CardManagerAdd::class.java)
            }
        }
    }

    private fun loadUserData() {
        RetrofitHelper.getInstance().retrofit.create(CardBindApi::class.java).getUserBindCard(ParamesHelp().build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BankCardInfo>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<BankCardInfo>) {
                        nameTv.text = "*${response.data.getUserName().substring(1,response.data.getUserName().length)}"
                        val idCard = response.data.getIdCard()
                        identityTv.text = "**** **** **** ${idCard.substring(idCard.length-4,idCard.length)}"
                    }

                })
    }

}