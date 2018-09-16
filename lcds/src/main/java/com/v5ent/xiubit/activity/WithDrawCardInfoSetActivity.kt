package com.v5ent.xiubit.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.Gson
import com.toobei.common.entity.*
import com.toobei.common.event.CardBindSuccessEvent
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.network.httpapi.WithDrawApi
import com.toobei.common.utils.KeyboardUtil
import com.toobei.common.utils.ToastUtil
import com.toobei.common.utils.UpdateViewCallBack
import com.toobei.common.utils.getJson
import com.toobei.common.view.dialog.PromptDialogCommon
import com.toobei.common.view.dialog.PromptDialogMsg
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.event.WithDrawSuccessEvent
import com.v5ent.xiubit.service.LoginService
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_with_drawcard_set.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.xsl781.utils.SimpleTextWatcher

/**
 * Created by 11191 on 2018/5/11.
 */
class WithDrawCardInfoSetActivity : NetBaseActivity() {
    override fun getContentLayout(): Int = R.layout.activity_with_drawcard_set
    private var bankCardInfo: BankCardInfo? = null
    private var money: String = "0.00"
    //选取的省份城市数据
    private var selectPrivice = ""
    private var selectCity = ""

    private var cityBeans: CityJsonBean? = null
    private var bankLists: ArrayList<BankBean>? = null

    private var cityPick: OptionsPickerView<String>? = null
    private var bankPick: OptionsPickerView<String>? = null

    companion object {
        val FOR_BINDCARD = 1
        val FOR_WITHDROW = 2
    }

    private var for_what = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bankCardInfo = intent.getSerializableExtra("bankCardInfo") as BankCardInfo
        for_what = intent.getIntExtra("for_what", 0)
        money = intent.getStringExtra("money") ?: "0.00"
        initView()
        loadDate()
    }

    private fun initView() {
        headerLayout.showTitle("填写银行卡信息")
        phoneInputEt.setText(LoginService.getInstance().curPhone)
        if (bankCardInfo?.bankName?.isNullOrBlank() ?: true) {
            bankNameTv.text = "请选择银行"
            bankNameTv.setTextColor(resources.getColor(R.color.text_blue_common))
        } else {
            bankNameTv.text = bankCardInfo?.bankName
            bankNameTv.setTextColor(resources.getColor(R.color.text_black_common))
        }


        bankNameTv.setOnClickListener {
            queryBanks(UpdateViewCallBack { e, s ->
                KeyboardUtil.hideKeyboard(ctx)
                bankPick?.show()
            })
        }

        selectCityTv.setOnClickListener {
            queryCitys(UpdateViewCallBack { e, s ->
                KeyboardUtil.hideKeyboard(ctx)
                cityPick?.show()
            })
        }

        completeBtn.setOnClickListener {
            when (for_what) {
                FOR_WITHDROW -> withdrawMoney()
                FOR_BINDCARD -> {
                    val phoneNum = phoneInputEt.text.toString()
                    if (bankNameTv.text.length == 0 || "请选择银行".equals(bankNameTv.text)) {
                        ToastUtil.showCustomToast("请先选择银行")
                        return@setOnClickListener
                    }
                    if (TextUtil.isEmpty(selectCity)) {
                        ToastUtil.showCustomToast("请选择地区和城市")
                        return@setOnClickListener
                    }
                    if (TextUtil.isEmpty(phoneNum) || phoneNum.length != 11) {
                        ToastUtil.showCustomToast("手机号码长度为11位，请检查")
                        return@setOnClickListener
                    }

                    bankCardInfo?.city = selectCity
                    bankCardInfo?.mobile = phoneNum
                    var intent = Intent(ctx, BindCardPhoneVcodeActivity::class.java)
                    intent.putExtra("bankCardInfo", bankCardInfo)
                    intent.putExtra("vcodePhoneNum", phoneNum)
                    startActivity(intent)
                }

            }
        }

        phoneNumInputLl.visibility = if (for_what == FOR_WITHDROW) View.GONE else View.VISIBLE

        remindInfoIv.setOnClickListener {
            PromptDialogMsg(ctx, "预留手机号说明", resources.getString(R.string.user_phone_explain), "我知道了").show()
        }

        phoneInputEt.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence?, i: Int, i2: Int, i3: Int) {
                super.onTextChanged(charSequence, i, i2, i3)
                phoneNumRemindTv.visibility = View.INVISIBLE
                completeBtn.isEnabled = (checkPhone() && selectCity != null)
            }

        })


    }


    private fun loadDate() {
        queryBanks(null)
        queryCitys(null)



    }

    private fun queryBanks(callBack: UpdateViewCallBack<String>?) {
        if (bankPick != null) callBack?.updateView(null, null)
        else {
            RetrofitHelper.getInstance().retrofit.create(CardBindApi::class.java).queryAllbank(ParamesHelp().build())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : NetworkObserver<BaseResponseEntity<PageListBase<BankBean>>>() {
                        override fun bindViewWithDate(response: BaseResponseEntity<PageListBase<BankBean>>) {
                            bankLists = response.data.datas as ArrayList<BankBean>?
                            //银行选择器
                            bankPick = OptionsPickerBuilder(ctx, OnOptionsSelectListener { options1, options2, options3, v ->
                                bankNameTv.text = bankLists?.get(options1)?.bankName
                                bankCardInfo?.bankName =  bankLists?.get(options1)?.bankName
                                if (for_what == FOR_WITHDROW){
                                    completeBtn.isEnabled = !(TextUtil.isEmpty(selectCity))
                                }else{
                                    completeBtn.isEnabled = !(TextUtil.isEmpty(selectCity)) && checkPhone()
                                }

                            }).setContentTextSize(22)//滚轮文字大小
                                    .build<String>()

                            var bankNames = arrayListOf<String>()
                            bankLists?.forEach {
                                bankNames.add(it.bankName)
                            }
                            bankPick?.setPicker(bankNames)
                            callBack?.updateView(null, null)
                        }
                    })
        }
    }


    private fun queryCitys(callBack: UpdateViewCallBack<String>?) {
        if (cityPick != null) callBack?.updateView(null, null)
        else {
            try {
                selectCityTv.post(Runnable {
                    val cityJson = getJson(ctx, "city.json")
                    cityBeans = Gson().fromJson(cityJson, CityJsonBean::class.java)
                    parssBean()
                    callBack?.updateView(null, null)
                })

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun parssBean() {
        if (cityBeans == null) return
        var proviceNameist = arrayListOf<String>()
        var cityNameList = ArrayList<List<String>>()
        cityBeans?.provices?.forEachIndexed({ index, provicesBean ->
            proviceNameist.add(provicesBean.text)
            var cityTempList = arrayListOf<String>()  //本省下的城市列表
            provicesBean.children.forEach({
                cityTempList.add(it.text)
            })
            cityNameList.add(cityTempList)
        })

        cityPick = OptionsPickerBuilder(ctx, OnOptionsSelectListener { options1, options2, options3, v ->
            selectPrivice = proviceNameist[options1]
            selectCity = cityNameList[options1][options2]
            selectCityTv.text = selectPrivice + " " + selectCity
//            selectCityTv.setTextColor(resources.getColor(R.color.text_black_common))
            if (for_what == FOR_WITHDROW){
                completeBtn.isEnabled = !(TextUtil.isEmpty(bankCardInfo?.bankName))
            }else{
                completeBtn.isEnabled = !(TextUtil.isEmpty(bankCardInfo?.bankName)) && checkPhone()
            }

        }).setContentTextSize(22)//滚轮文字大小
                .build<String>()

        cityPick?.setPicker(proviceNameist, cityNameList)

    }

    private fun withdrawMoney() {
        completeBtn.isEnabled = !(selectCity == null)
        RetrofitHelper.getInstance().retrofit.create(WithDrawApi::class.java).userWithdrawRequest(
                ParamesHelp()
                        .put("bankCard", bankCardInfo?.bankCard ?: "")
                        .put("bankName", bankCardInfo?.bankName ?: "")
                        .put("amount", money)
                        .put("city", selectPrivice)
                        .put("kaihuhang", selectCity)   //必填，不要问为什么，不填城市不会被录入系统--！
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<BaseEntity>>() {
                    override fun onNext(response: BaseResponseEntity<BaseEntity>) {
                        if (response.code == "0") {
                            EventBus.getDefault().post(WithDrawSuccessEvent())
                            var intent = Intent(ctx, WithdrawSuccessActivity::class.java)
                            intent.putExtra("bankCardInfo", bankCardInfo)
                            intent.putExtra("withdrawMonty", money)
                            startActivity(intent)
                            finish()
                        } else {
                            var errorMsg = "因${response.errorsMsgStr},提现失败，请稍后重试。"
                            val errorMsgDialog = PromptDialogCommon(ctx, "提现失败", "因${response.errorsMsgStr},提现失败，请稍后重试。", true)
                            errorMsgDialog.show()
                            headerLayout.postDelayed(Runnable { if (errorMsgDialog.isShowing) errorMsgDialog.dismiss() }, 3000)
                        }

                    }

                    override fun bindViewWithDate(response: BaseResponseEntity<BaseEntity>) {}

                })
    }


    private fun checkPhone(): Boolean {
        val phone = phoneInputEt.getText().toString();
        return phone.length == 11
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun cardBindSuccess(event: CardBindSuccessEvent) {
        finish()
    }
}