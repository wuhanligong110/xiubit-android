package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.toobei.common.entity.AccountbookEditEntity
import com.toobei.common.entity.AccountbookInvestingDetailEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.httpapi.AccountbookApi
import com.toobei.common.utils.ToastUtil
import com.toobei.common.view.dialog.PromptDialogCommon
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.event.AccountBookEditEvent
import com.v5ent.xiubit.util.ParamesHelp
import com.toobei.common.network.RetrofitHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_account_book_detial.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AccountBookDetialActivity : NetBaseActivity() {

    private lateinit var bookId: String
    private lateinit var title: String
    override fun getContentLayout(): Int {
        return R.layout.activity_account_book_detial
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        bookId = intent.getStringExtra("bookId")
        title = intent.getStringExtra("title")
        super.onCreate(savedInstanceState)
        initView()
        getNetData()
    }

    private fun initView() {
        headerLayout.showTitle("${title ?: ""}")
        headerLayout.addRightImageButton(R.drawable.ic_book_delete, View.OnClickListener {
            deleteBook()
        })
        headerLayout.addRightImageButton(R.drawable.ic_book_edit, View.OnClickListener {
            var intent = Intent(ctx, AccountBookEditActivity::class.java)
            intent.putExtra("id", bookId)
            startActivity(intent)
        })

        refreshViewSrl.setOnRefreshListener {
            getNetData()
        }

    }

    private fun deleteBook() {
        PromptDialogCommon(ctx, "是否删除该项目", "", "确定", "删除")
                .setBtnPositiveClickListener(PromptDialogCommon.PositiveClicklistener {
                    RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                            .accountbookEdit(ParamesHelp()
                                    .put("id", bookId)
                                    .put("status", "false")
                                    .build())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : NetworkObserver<AccountbookEditEntity>() {

                                override fun onNext(response: AccountbookEditEntity) {
                                    if (response.code == "0" ) {
                                        ToastUtil.showCustomToast("删除成功")
                                        EventBus.getDefault().post(AccountBookEditEvent())
                                        finish()
                                    }
                                    noMsgToast = true
                                    super.onNext(response)
                                }
                                override fun bindViewWithDate(response: AccountbookEditEntity) {

                                }
                            })
                }).show()


    }

    private fun getNetData() {
        RetrofitHelper.getInstance().retrofit.create(AccountbookApi::class.java)
                .accountbookInvestingDetail(ParamesHelp().put("id", bookId).build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<AccountbookInvestingDetailEntity>() {
                    override fun bindViewWithDate(response: AccountbookInvestingDetailEntity) {
                        title = response.data.investDirection ?: title
                        headerLayout.showTitle("${title ?: ""}")
                        waitBaseMoneyTv.text = response.data.investAmt ?: "--"
                        waitProfitTv.text = response.data.profit ?: "--"
                        investDirTv.text = response.data.investDirection ?: ""
                        creatTv.text = response.data.createTime ?: ""
                        remarkTv.text = response.data.remark ?: ""
                    }
                })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)  // 发送红包后更新
    fun refreshEvent(event: AccountBookEditEvent) {
        getNetData()
    }


}
