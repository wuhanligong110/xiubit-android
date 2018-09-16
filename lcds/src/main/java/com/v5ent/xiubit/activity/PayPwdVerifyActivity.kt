package com.v5ent.xiubit.activity

import android.content.Intent
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import com.toobei.common.entity.BaseResponseEntity
import com.toobei.common.entity.CheckResponseData
import com.toobei.common.network.NetworkObserver
import com.toobei.common.network.RetrofitHelper
import com.toobei.common.network.httpapi.CardBindApi
import com.toobei.common.utils.KeyboardUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import com.v5ent.xiubit.util.ParamesHelp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_paypwd_verify.*

/**
 * //验证码校验
 * Created by 11191 on 2018/5/30.
 */
class PayPwdVerifyActivity : NetBaseActivity(){
    companion object {
        val for_bindcard = 1
    }

    var for_what = 0
    override fun getContentLayout(): Int = R.layout.activity_paypwd_verify

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for_what = intent.getIntExtra("for_what", 0)
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("验证交易密码")
        if (for_what == for_bindcard) {
            textTitle.text = "添加银行卡需验证交易密码"
        }

        KeyboardUtil.disableShowSoftInput(gridPwd.inputView)

        val k2 = Keyboard(ctx, com.toobei.common.R.xml.symbols)
        keyboard_view.keyboard = k2
        keyboard_view.isEnabled = true
        keyboard_view.isPreviewEnabled = false
        keyboard_view.setOnKeyboardActionListener(object : KeyboardView.OnKeyboardActionListener {
            var num =0
            override fun swipeUp() {}

            override fun swipeRight() {}

            override fun swipeLeft() {}

            override fun swipeDown() {}

            override fun onText(arg0: CharSequence) {}

            override fun onRelease(arg0: Int) {}

            override fun onPress(arg0: Int) {}

            override fun onKey(primaryCode: Int, arg1: IntArray) {
                num = gridPwd.passWord.length
                val editable = gridPwd.inputView.text
                val start = gridPwd.inputView.selectionStart
                if (num == 0) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable.length > 0) {
                            if (editable.length > 0) {
                                editable.delete(start - 1, start)
                            }
                        }
                    } else if (primaryCode == -2) {
                        if (editable != null && editable.length > 0) {
                            if (start > 0) {
                                editable.append(".")
                            }
                        }
                    } else {
                        editable!!.insert(start, Character.toString(primaryCode.toChar()))
                    }
                } else if (num == 1) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable.length > 0) {
                            if (editable.length > 0) {
                                editable.delete(start - 1, start)
                            }
                        }
                        gridPwd.passwordArr[0] = null
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(1, Character.toString(primaryCode.toChar()))
                    }
                } else if (num == 2) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(1, null)
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(2, Character.toString(primaryCode.toChar()))
                    }
                } else if (num == 3) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(2, null)
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(3, Character.toString(primaryCode.toChar()))
                    }
                } else if (num == 4) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(3, null)
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(4, Character.toString(primaryCode.toChar()))
                    }
                } else if (num == 5) {
                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        gridPwd.setText(4, null)
                    } else if (primaryCode == -2) {
                    } else {
                        gridPwd.setText(5, Character.toString(primaryCode.toChar()))
//                            oldPwd = gridPwd.passWord
                            userVerifyPayPwd(gridPwd.passWord)
                    }
                }

               if (gridPwd.passWord.length >0) {
                   errorTv.text = ""
               }
            }
        })

        //忘记交易密码
        textForget.setOnClickListener {
            startActivity(Intent(ctx,ResetPayPwdIdentity::class.java))
//            finish()
        }

    }

    private fun userVerifyPayPwd(passWord: String?) {
        RetrofitHelper.getInstance().getRetrofit(true).create(CardBindApi::class.java).verifyPayPwd(ParamesHelp()
                .put("pwd",passWord)
                .build())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NetworkObserver<BaseResponseEntity<CheckResponseData>>() {
                    override fun bindViewWithDate(response: BaseResponseEntity<CheckResponseData>) {
                        if ("true" == response.data.rlt){
                            pwdPass()
                        }else{
                            errorTv.text = "密码错误,请重新输入"
                            gridPwd.clearPassword()
                        }

                    }

                })
    }

    //密码通过
    private fun pwdPass() {
        if(for_what == for_bindcard){
            //跳转绑卡流程
            ctx.showActivity(ctx,CardManagerAdd::class.java)
            finish()
        }
    }


}