package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.entity.AccountbookEditEntity
import com.toobei.common.entity.AccountbookInvestingDetailEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.AccountbookApi
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.event.AccountBookEditEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_account_book_edit.*
import org.greenrobot.eventbus.EventBus

class AccountBookEditActivity : NetBaseActivity() {
    private var bookId : String? = null
    override fun getContentLayout(): Int {
        return R.layout.activity_account_book_edit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        bookId = intent.getStringExtra("id")
        super.onCreate(savedInstanceState)
        initView()
        if(!bookId.isNullOrBlank()) getNetData()
    }

    private fun initView() {
        headerLayout.showTitle("记一笔")

        setupUIListenerAndCloseKeyboard(scrollView)

        completeBtn.setOnClickListener {
            var investDirectionText = investDirectionEt.editableText.toString()
            var investAmtText = investAmtEt.editableText.toString()
            var waitProfitText = waitProfitEt.editableText.toString()
            var remarkText = remarkEt.editableText.toString()

            if (investDirectionText.isNullOrBlank()) {
                ToastUtil.showCustomToast("请输入投资去向")
                return@setOnClickListener
            }

            if (investAmtText.isNullOrBlank()) {
                ToastUtil.showCustomToast("请输入投资本金")
                return@setOnClickListener
            }

//            investAmt	本金	number
//            investDirection	去向	string
//            profit	收益	number
//            remark	备注	string
//            status	状态	boolean	true:在投 false:删除
//            token	登录令牌	string
            RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                    .accountbookEdit(ParamesHelp()
                            .put("id",bookId)
                            .put("investAmt", investAmtText)
                            .put("investDirection", investDirectionText)
                            .put("profit", waitProfitText)
                            .put("remark", remarkText)
                            .put("status", "true")
                            .build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<AccountbookEditEntity>() {

                        override fun bindViewWithDate(response: AccountbookEditEntity) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onNext(response: AccountbookEditEntity) {
                            noMsgToast = true
                            if (response.code == "0"){
                                ToastUtil.showCustomToast("添加成功")
                                EventBus.getDefault().post(AccountBookEditEvent())
                                finish()
                            }else{
                                ToastUtil.showCustomToast("添加失败，请重试")
                            }


                        }

                    })
        }
    }


    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                .accountbookInvestingDetail(ParamesHelp().put("id", bookId).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<AccountbookInvestingDetailEntity>() {
                    override fun bindViewWithDate(response: AccountbookInvestingDetailEntity) {

                        var title = response.data.investDirection?:title
                        headerLayout.showTitle("${title ?: "记一笔"}")
                        investAmtEt.setText(response.data.investAmt ?: "")
                        waitProfitEt.setText(response.data.profit ?: "")
                        investDirectionEt.setText(response.data.investDirection ?: "")
                        remarkEt.setText(response.data.remark ?: "")
                    }
                })
    }




}
