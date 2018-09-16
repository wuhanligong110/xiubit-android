package com.v5ent.xiubit.activity;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jungly.gridpasswordview.Util;
import com.toobei.common.view.timeselector.Utils.TextUtil;
import com.v5ent.xiubit.MyApp;
import com.v5ent.xiubit.MyNetworkBaseActivity;
import com.v5ent.xiubit.R;
import com.v5ent.xiubit.entity.FeeCalBaseData;
import com.v5ent.xiubit.entity.FeeCalBaseDataEntity;
import com.v5ent.xiubit.imp.MyAnimationListener;
import com.v5ent.xiubit.imp.MyOnKeyboardActionListener;
import com.v5ent.xiubit.imp.MyTextWatcher;
import com.v5ent.xiubit.util.C;
import com.v5ent.xiubit.view.MyTextView;
import com.v5ent.xiubit.view.dialog.CfgLevelCalculateSelectDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/5/23
 */

public class CfgLevelCalculateActivity extends MyNetworkBaseActivity<FeeCalBaseDataEntity> {

    @BindView(R.id.levelText)
    TextView mLevelText;
    @BindView(R.id.levelSelectTv)
    TextView mLevelSelectTv;
    @BindView(R.id.yearProfitTv)
    TextView mYearProfitTv;
    @BindView(R.id.inputEt1)
    EditText mInputEt1;
    @BindView(R.id.outIncomeTv1)
    MyTextView mOutIncomeTv1;
    @BindView(R.id.inputEt2)
    EditText mInputEt2;
    @BindView(R.id.outIncomeTv2)
    MyTextView mOutIncomeTv2;
    @BindView(R.id.inputEt3)
    EditText mInputEt3;
    @BindView(R.id.outIncomeTv3)
    MyTextView mOutIncomeTv3;
    @BindView(R.id.inputEt4)
    EditText mInputEt4;
    @BindView(R.id.outIncomeTv4)
    MyTextView mOutIncomeTv4;
    @BindView(R.id.allResultTv)
    MyTextView mAllResultTv;
    @BindView(R.id.keyboardView)
    KeyboardView mKeyboardView;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.allResultLl)
    LinearLayout mAllResultLl;
    @BindView(R.id.bottomLl)
    LinearLayout mBottomLl;
    @BindView(R.id.rootView)
    RelativeLayout mRootView;
    @BindView(R.id.levelExplainTv1)
    TextView mLevelExplainTv1;
    @BindView(R.id.hideLl)
    LinearLayout mHideLl;
    @BindView(R.id.cardLl)
    LinearLayout mCardLl;
    @BindView(R.id.shawdowRl)
    RelativeLayout mShawdowRl;
    private Animation mLoadAnimation;
    private Animation mHideAnimation;
    private EditText mCurrForcusEditText; //当前获取焦点的EditText
    private boolean isKeyBoardAnimationIng;  //判断键盘是否正在进行动画，如果正在，则不开启新的动画
    private boolean isKeyBoardOpened; //当前键盘是否已经弹出
    private int myLevel; //当前职级
    private List<String> mCfgLevelNames = new ArrayList<>();
    private int myProfitLevel; //当前年化佣金率等级，默认1.5% 也就是 1
    private Double[][] levelProfits =
            {{1.0, 2.6, 0.5, 0.5}, //总监
                    {1.0, 2.2, 0.3, 0.3},  //经理
                    {1.0, 1.6, 0.0, 0.0},  //顾问
                    {1.0, 1.6, 0.0, 0.0}}; //见习
    private int[] inputStrs = {0, 0, 0, 0};
    private int[] defaultNum = {0, 0, 0, 0};
    private long[] levelResults = new long[4];
    private List<TextView> mResultTexts;
    private List<FeeCalBaseData.CrmCfpLevelListBean> mCrmCfpLevelList;
    private List<String> mFeeTypeList;

    @Override
    protected int onGetContentViewLayout() {
        return R.layout.activity_level_cfg_calculate;
    }

    @Override
    protected void onInitParamBeforeLoadData() {
        super.onInitParamBeforeLoadData();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        headerLayout.showTitle(getResources().getString(R.string.calculate_title));
        headerLayout.showLeftBackButton();
        headerLayout.showRightTextButton(R.string.calculate_introduce, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivityCommon.showThisActivity(ctx, C.URL_RANK_DESC, "");
            }
        });
        initEditTexts();
        initKeyBoard();
        initOutResultTexts();
    }

    private void initOutResultTexts() {
        mResultTexts = new ArrayList<>();
        mResultTexts.add(mOutIncomeTv1);
        mResultTexts.add(mOutIncomeTv2);
        mResultTexts.add(mOutIncomeTv3);
        mResultTexts.add(mOutIncomeTv4);
    }

    private void initKeyBoard() {
        // 布局键盘
        final Keyboard keyboard = new Keyboard(this, R.xml.symbols);
        mKeyboardView.setKeyboard(keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mLoadAnimation = AnimationUtils.loadAnimation(ctx, R.anim.popup_from_bottom);
        mHideAnimation = AnimationUtils.loadAnimation(ctx, R.anim.popup_back_bottom);
        mLoadAnimation.setDuration(300);
        mHideAnimation.setDuration(300);

        mLoadAnimation.setAnimationListener(new MyAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                isKeyBoardAnimationIng = false;
                isKeyBoardOpened = true;
            }
        });

        mHideAnimation.setAnimationListener(new MyAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                isKeyBoardAnimationIng = false;
                isKeyBoardOpened = false;
                mKeyboardView.setVisibility(View.GONE);
            }
        });

        mKeyboardView.setOnKeyboardActionListener(new MyOnKeyboardActionListener() {
            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                if (mCurrForcusEditText != null) {
                    Editable editable = mCurrForcusEditText.getText();


                    int start = mCurrForcusEditText.getSelectionStart();


                    if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                        if (editable != null && editable.length() > 0) {
                            if (start > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                    } else if (primaryCode == -2) {
                        if (editable != null && editable.length() > 0) {
                            if (start > 0) {
                                editable.append(".");
                            }
                        }
                    } else {
                        editable.insert(start, Character.toString((char) primaryCode));
                    }

                }
            }

        });

    }

    private void initEditTexts() {
        List<EditText> editTexts = new ArrayList<>();
        editTexts.add(mInputEt1);
        editTexts.add(mInputEt2);
        editTexts.add(mInputEt3);
        editTexts.add(mInputEt4);
        Class<EditText> cls = EditText.class;
        Method setShowSoftInputOnFocus;

        for (int i = 0; i < editTexts.size(); i++) {
            final EditText editText = editTexts.get(i);
            editText.setTag(i);

            //利用反射，设置所有的editText都将不会触发软键盘
            try {
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(editText, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //当editText获取焦点时
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mCurrForcusEditText = editText;
                        editText.getText().clear(); //点击获取焦点时清空上一次文字
                        refreshResultText();
                        mKeyboardView.setVisibility(View.VISIBLE);
                        if (!isKeyBoardAnimationIng && !isKeyBoardOpened) {
                            isKeyBoardAnimationIng = true;
                            mBottomLl.startAnimation(mLoadAnimation);
                        }
                    }

                }
            });
            //编辑监听
            editText.addTextChangedListener(new MyTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    if (mCurrForcusEditText == null) return;
                    int index = (int) mCurrForcusEditText.getTag();
                    String string = mCurrForcusEditText.getText().toString();
                    if (TextUtil.isEmpty(string)) {
                        inputStrs[index] = defaultNum[index];
                    } else {
                        inputStrs[index] = Integer.parseInt(string);
                    }
                    setOutResultText(index);

                }
            });
        }

        setupUIListenerAndCloseKeyboard(mScrollView, this);

    }

    private void setOutResultText(int index) {
        if (index < 4) {
            levelResults[index] = getLevelCalculateResult(index);
            mResultTexts.get(index).setText("" + levelResults[index]);
        }
        mAllResultTv.setText("" + getAllResult());

    }

    private void refreshResultText() {
        for (int i = 0; i < 4; i++) {
            levelResults[i] = getLevelCalculateResult(i);
            mResultTexts.get(i).setText("" + levelResults[i]);
        }
        mAllResultTv.setText("" + getAllResult());
    }

    public void setupUIListenerAndCloseKeyboard(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {

                public int downx,downy,upX,upY;
                public long downTime,upTime;

                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                        downTime = System.currentTimeMillis();

                        downx = (int) event.getRawX();
                        downy = (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            upTime = System.currentTimeMillis();
                            upX = (int) event.getRawX();
                            upY = (int) event.getRawY();
                            int MoveX = Math.abs(upX - downx);
                            int MoveY = Math.abs(upY - downy);
                            if ((upTime - downTime) < 200 && MoveX < 20 && MoveY < 20) {
                                //单击事件
                                if (mCurrForcusEditText != null) {
                                    mCurrForcusEditText.clearFocus();
                                }
                                if (!isKeyBoardAnimationIng && isKeyBoardOpened) {
                                    isKeyBoardAnimationIng = true;
                                    mBottomLl.startAnimation(mHideAnimation);
                                }
                            }
                            break;
                    }
                    return false;
                }
            });
        }

//        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUIListenerAndCloseKeyboard(innerView, activity);
            }
        }

    }




    @Override
    protected FeeCalBaseDataEntity onLoadDataInBack() throws Exception {
        return MyApp.getInstance().getHttpService().getFeeCalBaseData();
    }

    @Override
    protected void onRefreshDataView(FeeCalBaseDataEntity data) {
        FeeCalBaseData feeCalBaseData = data.getData();
        mCrmCfpLevelList = feeCalBaseData.getCrmCfpLevelList();
        mFeeTypeList = feeCalBaseData.getFeeTypeList();
        Collections.sort(mCrmCfpLevelList, new Comparator<FeeCalBaseData.CrmCfpLevelListBean>() {
            @Override
            public int compare(FeeCalBaseData.CrmCfpLevelListBean lhs, FeeCalBaseData.CrmCfpLevelListBean rhs) {
                return rhs.getLevelWeight() - lhs.getLevelWeight();  //根据职级权重，按照从大到小排列
            }
        });
        Collections.sort(mFeeTypeList, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return (int) ((Float.parseFloat(rhs)- Float.parseFloat(lhs)) * 100);
            }
        });
        mLevelSelectTv.setText(mCrmCfpLevelList.get(myLevel).getLevelName().trim());
        String str = mFeeTypeList.get(myProfitLevel);
//        if (str != null && str.contains(".")) {
//            str = new DecimalFormat("##0.0").format(Double.parseDouble(str.trim()));
//        }
        mYearProfitTv.setText(str + "%");
        for (FeeCalBaseData.CrmCfpLevelListBean bean : mCrmCfpLevelList) {
            mCfgLevelNames.add(bean.getLevelName());
        }
        refreshUi();
        refreshResultText();
    }

    /**
     * 根据各个职级返回对应收入结果
     *
     * @param level
     * @return
     */
    private long getLevelCalculateResult(int level) {
        return (long) (inputStrs[level] * 10000 * Double.parseDouble(mFeeTypeList.get(myProfitLevel)) / 100 * levelProfits[myLevel][level]);
    }

    /**
     * 总收入计算
     *
     * @return
     */
    private long getAllResult() {  //各级收入相加总和
        long allResult = 0;
        for (long levelResult : levelResults) {
            allResult += levelResult;
        }
        return allResult;
    }


    @OnClick({R.id.levelSelectTv, R.id.yearProfitTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.levelSelectTv:
                new CfgLevelCalculateSelectDialog(this, mCfgLevelNames, getResources().getString(R.string.calculate_select_level_title), CfgLevelCalculateSelectDialog.TYPE_CFGLEVEL,new CfgLevelCalculateSelectDialog.ItemClickListener() {
                @Override
                public void onItemClick(int itemIndex) {
                    mLevelSelectTv.setText(mCfgLevelNames.get(itemIndex));
                    myLevel = itemIndex;
                    refreshUi();
                    refreshResultText();
                }
            }).show();
                break;
            case R.id.yearProfitTv:
                new CfgLevelCalculateSelectDialog(this, mFeeTypeList, getResources().getString(R.string.calculate_select_Fee_title),CfgLevelCalculateSelectDialog.TYPE_YEARPROFIT,new CfgLevelCalculateSelectDialog.ItemClickListener() {
                    @Override
                    public void onItemClick(int itemIndex) {
                        String str = mFeeTypeList.get(itemIndex);
//                        if (str != null && str.contains(".")) {
//                            str = new DecimalFormat("##0.0").format(Double.parseDouble(str.trim())) + "%";
//                        }
                        mYearProfitTv.setText(str + "%");
                        myProfitLevel = itemIndex;
                        refreshResultText();
                    }
                }).show();
                break;
        }
    }

    private void refreshUi() {
        mLevelExplainTv1.setText(mCfgLevelNames.get(myLevel) + "自己出单");
        if (myLevel > 1) {  //顾问见习，没有二三级推荐理财师
            mHideLl.setVisibility(View.GONE);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mShawdowRl.getLayoutParams();
            lp.height = Util.dp2px(this, 160);
            mShawdowRl.setLayoutParams(lp);
        } else {
            mHideLl.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mShawdowRl.getLayoutParams();
            lp.height = Util.dp2px(this, 250);
            mShawdowRl.setLayoutParams(lp);
        }
    }






}
