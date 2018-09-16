package com.v5ent.xiubit.activity

import android.os.Bundle
import com.toobei.common.utils.ToastUtil
import com.v5ent.xiubit.NetBaseActivity
import com.v5ent.xiubit.R
import kotlinx.android.synthetic.main.activity_hotfix_hot.*

/**
 * Created by yangLin on 2018/5/3.
 */
class HotfixTestActivity : NetBaseActivity(){
    private var text1 = "001"
    override fun getContentLayout(): Int = R.layout.activity_hotfix_hot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        headerLayout.showTitle("热修复测试页")

        if (getText1() == text1){
            test1.text = "yes"
        }

        if (TestClass().params == 123 && TestClass().addNum(1,2) == 3){
            test2.text = "yes"
        }


        test3.text = getString(R.string.fix_Success)
        test3.setTextColor(resources.getColor(R.color.fix_color))

        var arr = intArrayOf(1,2)
        cashTestBtn.setOnClickListener {
            var i = arr[0] + arr[1]
            ToastUtil.showCustomToast("成功结果：$i")
        }

    }

    private fun getText1():String {
        return text1
    }


    class TestClass {
        var params = 123

        fun addNum(i1:Int,i2:Int):Int = i1+i2
    }

}