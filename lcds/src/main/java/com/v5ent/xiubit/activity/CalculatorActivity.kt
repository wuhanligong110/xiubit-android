package com.v5ent.xiubit.activity

import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.text.Editable
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import com.toobei.common.entity.ProductDetail
import com.toobei.common.entity.QueryRedPacketData
import com.toobei.common.entity.QueryRedPacketEntity
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CouponApi
import com.toobei.common.utils.TextDecorator
import com.toobei.common.utils.TextFormatUtil
import com.toobei.common.view.timeselector.Utils.TextUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.imp.MyAnimationListener
import com.v5ent.xiubit.imp.MyOnKeyboardActionListener
import com.v5ent.xiubit.imp.MyTextWatcher
import com.v5ent.xiubit.util.C
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_calculator.*
import java.lang.reflect.Method

/**
 * 公司: tophlc
 * 类说明：Activity-佣金计算器界面
 *
 * @date 2015-12-17
 */
class CalculatorActivity : NetBaseActivity() {


    private val keyboardView: KeyboardView? = null
    // 产品详情
    private lateinit var product: ProductDetail
    private var feeRatio: Float = 0f //佣金率
    private var productRatio: Float = 0f //产品年化收益率
    private var liecaiRedPacketMoney: Float = 0f //产品年化收益率
    private var isFlow: String? = null
    private var productData: Int = 0//产品期限

    private var isKeyBoardAnimationIng = false
    private var isKeyBoardOpened = false

    private var mCurrForcusEditText: EditText? = null
    //投资金额,平台奖励金额
    private var defaultEditStrs: Array<Float> = arrayOf(10000f, 0f);
    private var inputEditStrs: Array<Float> = arrayOf(10000f, 0f);

    override fun getContentLayout(): Int {
        return R.layout.activity_calculator
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initIntent()
        initView()
        loadRedPacketInfo()
    }


    private fun initIntent() {
        //产品期限和收益率都取最低，不论是否浮动，皆不可输入
        product = intent.getSerializableExtra("product") as ProductDetail
        feeRatio = (product?.feeRatio?.toFloat() ?: 0f) / 100
        productData = product.deadLineMinValue.toInt() ?: 0
        productRatio = product?.productRateText.split("%")[0].toFloat() / 100

        productDateTv.text = productData.toString()


    }

    private fun initView() {
        setSoftInputMode()
        hideSoftInputView()
        headerLayout.showTitle("收益计算器")
        headerLayout.showRightImageButton(R.drawable.img_question_white) { WebActivityCommon.showThisActivity(ctx, C.URL_SALE_COMMISSION, "销售佣金说明") }

        initEditTexts()
        initKeyBoard()
    }

    var redPacketList: ArrayList<QueryRedPacketData> = arrayListOf()
    private fun loadRedPacketInfo() {
        RetrofitHelper.getInstance().retrofit.create(CouponApi::class.java)
                .availableProductRedPacket(ParamesHelp()
                        .put("productId", product.productId)
                        .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<QueryRedPacketEntity>() {
                    override fun bindViewWithDate(response: QueryRedPacketEntity) {
                        redPacketList.clear()
                        redPacketList.addAll(response.data.datas)
                        calculateResult(defaultEditStrs)
                    }
                })
    }

    /**
     * 1.理财收益=投资金额*产品期限÷360*佣金率+理财红包  ，去尾保留两位小数
     * 2.产品收益=投资金额*产品期限÷360*平台收益率+平台奖励金额，去尾保留两位小数
     * 3.综合收益=计算去尾后的理财收益+计算去尾后的产品收益
     * 4.综合年化=综合收益÷投资金额÷(产品期限/360)，百分比后面去尾保留两位小数
     */
    private fun calculateResult(editStrs: Array<Float>) {
        //红包后台已经做好排序从小到大，计算收益只取最大的红包
        var liecaiRedPacketText = ""
        var available: Boolean = false
        for (it in redPacketList) {

            when (it.amountLimit) { //number	0=不限|1=大于|2=大于等于
                "0" -> available = true
                "1" -> available = editStrs[0] > it.amount.toFloat()
                "2" -> available = editStrs[0] >= it.amount.toFloat()
            }
            if (available) {
                liecaiRedPacketText = "投资${when (it.amountLimit) {
                    "0" -> ">"
                    "1" -> ">"
                    "2" -> "≥"
                    else -> ">"
                }}" + "${TextFormatUtil.subZeroAndDot("${it.amount ?: "0"}")},最高奖励${TextFormatUtil.subZeroAndDot(it.money)}"
                liecaiRedPacketMoney = it.money.toFloat()
                break
            }

        }
        //不符合条件，不计算红包收益
        if (!available) {
            liecaiRedPacketText = "暂无可用红包"
            liecaiRedPacketMoney = 0f
        }

        var liecaiProfit = TextFormatUtil.formatFloat2zero("${editStrs[0] * productData / 360 * feeRatio + liecaiRedPacketMoney}")
        var productProfit = TextFormatUtil.formatFloat2zero("${editStrs[0] * productData / 360 * productRatio + editStrs[1]}")

        var totalProfit = TextFormatUtil.formatFloat2zero((liecaiProfit.toFloat() + productProfit.toFloat()).toString())
        var totalYearProfit = TextFormatUtil.formatFloat2zero(if (editStrs[0] == 0f) "0" else "${totalProfit.toFloat() / editStrs[0] / (productData.toFloat() / 360) * 100}")

        var totalStr = "综合年化: ${totalYearProfit}%   综合收益(元): ${totalProfit}"

        liecaiRedPacketTv.text = liecaiRedPacketText
        liecaiProfitTv.text = "$liecaiProfit"
        productProfitTv.text = "$productProfit"
        TextDecorator.decorate(totalDataTv, totalStr)
                .setTextColor(R.color.text_black_common, "综合年化:", "综合收益(元):")
                .setAbsoluteSize(resources.getDimension(R.dimen.w13).toInt(), "综合年化:", "综合收益(元):")
                .build()


    }

    private fun initEditTexts() {
        investMoneyEt.hint = "${defaultEditStrs.get(0).toInt()}"
        platformAwardEt.hint = "平台奖励金额(选填)"

        val editTexts = ArrayList<EditText>()
        editTexts.add(investMoneyEt)
        editTexts.add(platformAwardEt)
        val cls = EditText::class.java
        var setShowSoftInputOnFocus: Method

        for (i in editTexts.indices) {
            val editText = editTexts[i]
            editText.tag = i

            //利用反射，设置所有的editText都将不会触发软键盘
            try {
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
                setShowSoftInputOnFocus.isAccessible = true
                setShowSoftInputOnFocus.invoke(editText, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            //当editText获取焦点时
            editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    mCurrForcusEditText = editText
                    editText.text.clear() //点击获取焦点时清空上一次文字
                    inputEditStrs[i] = defaultEditStrs[i]
                    calculateResult(inputEditStrs)
                    keyboardView?.setVisibility(View.VISIBLE)
                    if (!isKeyBoardAnimationIng && !isKeyBoardOpened) {
                        isKeyBoardAnimationIng = true
                        bottomV.startAnimation(mLoadAnimation)
                    }
                }
            }
            //编辑监听
            editText.addTextChangedListener(object : MyTextWatcher() {
                override fun afterTextChanged(s: Editable) {
                    if (mCurrForcusEditText == null) return
                    val index = mCurrForcusEditText?.getTag() as Int
                    val string = mCurrForcusEditText?.getText().toString()
                    if (TextUtil.isEmpty(string)) {
                        inputEditStrs[index] = defaultEditStrs[index]
                    } else {
                        inputEditStrs[index] = string.toFloat()
                    }
                    calculateResult(inputEditStrs)

                }
            })
        }

        setupUIListenerAndCloseKeyboard(scrollV)
        investMoneyEt.isFocusable = true
    }

    public override fun setupUIListenerAndCloseKeyboard(view: View) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener(object : View.OnTouchListener {

                var downx: Int = 0
                var downy: Int = 0
                var upX: Int = 0
                var upY: Int = 0
                var downTime: Long = 0
                var upTime: Long = 0

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            downTime = System.currentTimeMillis()

                            downx = event.rawX.toInt()
                            downy = event.rawY.toInt()
                        }
                        MotionEvent.ACTION_UP -> {
                            upTime = System.currentTimeMillis()
                            upX = event.rawX.toInt()
                            upY = event.rawY.toInt()
                            val MoveX = Math.abs(upX - downx)
                            val MoveY = Math.abs(upY - downy)
                            if (upTime - downTime < 200 && MoveX < 20 && MoveY < 20) {
                                //单击事件
                                if (mCurrForcusEditText != null) {
                                    mCurrForcusEditText?.clearFocus()
                                }
                                if (!isKeyBoardAnimationIng && isKeyBoardOpened) {
                                    isKeyBoardAnimationIng = true
                                    bottomV.startAnimation(mHideAnimation)
                                }
                            }
                        }
                    }
                    return false
                }
            })
        }

//        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until (view as ViewGroup).childCount) {
                val innerView = (view as ViewGroup).getChildAt(i)
                setupUIListenerAndCloseKeyboard(innerView)
            }
        }
    }

    private var mLoadAnimation: Animation? = null
    private var mHideAnimation: Animation? = null
    private fun initKeyBoard() {
        // 布局键盘
        val keyboard = Keyboard(this, R.xml.symbols)
        keyboardV.setKeyboard(keyboard)
        keyboardV.setEnabled(true)
        keyboardV.setPreviewEnabled(false)
        mLoadAnimation = AnimationUtils.loadAnimation(ctx, R.anim.popup_from_bottom)
        mHideAnimation = AnimationUtils.loadAnimation(ctx, R.anim.popup_back_bottom)
        mLoadAnimation?.setDuration(300)
        mHideAnimation?.setDuration(300)

        mLoadAnimation?.setAnimationListener(object : MyAnimationListener() {
            override fun onAnimationEnd(animation: Animation) {
                isKeyBoardAnimationIng = false
                isKeyBoardOpened = true
                keyboardV.setVisibility(View.VISIBLE)
            }
        })

        mHideAnimation?.setAnimationListener(object : MyAnimationListener() {
            override fun onAnimationEnd(animation: Animation) {
                isKeyBoardAnimationIng = false
                isKeyBoardOpened = false
                keyboardV.setVisibility(View.GONE)
            }
        })

        keyboardV.setOnKeyboardActionListener(object : MyOnKeyboardActionListener() {
            override fun onKey(primaryCode: Int, keyCodes: IntArray) {
                if (mCurrForcusEditText != null) {
                    val editable = mCurrForcusEditText?.getText()


                    val start = mCurrForcusEditText?.getSelectionStart() ?: 0


                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable!!.length > 0) {
                            if (start > 0) {
                                editable!!.delete(start - 1, start)
                            }
                        }
                    } else if (primaryCode == -2) {
                        if (editable != null && editable!!.length > 0) {
                            if (start > 0) {
                                editable!!.append(".")
                            }
                        }
                    } else {
                        editable!!.insert(start, Character.toString(primaryCode.toChar()))
                    }

                }
            }

        })

    }

}
