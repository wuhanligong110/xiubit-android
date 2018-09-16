package com.toobei.tb.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.entity.AccountType;
import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.popupwindow.ListCategoryPopupWindow;
import com.toobei.tb.R;
import com.toobei.tb.entity.PlatFormHeadData;
import com.toobei.tb.entity.PlatFormHeadEntity;

import org.xsl781.utils.PixelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司: tophlc
 * 类说明：机构列表 头部筛选点击控件
 *
 * @date 2016-2-2
 */
public class OrgListClickHeadLayout extends LinearLayout implements View.OnClickListener, ListCategoryPopupWindow.OnItemSelected {
    private final Context context;
    private TextView[] titles = new TextView[3];
    private ImageView[] imgs = new ImageView[3];
    // 排序的参数
    private int safeSort = 0;
    private int annualSort = 0;
    private int dateSort = 0;
    private int curIndex = 0;
    /**
     * 当前的选择的button  安全评级 | 年化收益 | 产品期限
     */

    // 安全评级排序button
    private View safeRankHead;
    private OnHeadTitleClickListener listener;
    private ArrayList<AccountType> safeRankSortList;
    private AccountType saferankSortType;
    private ListCategoryPopupWindow saferankPopupWindowSelectType;


    //年化排序button
    private View annualHead;
    private ArrayList<AccountType> annualSortList;
    private AccountType annualSortType;
    private ListCategoryPopupWindow annualPopupWindowSelectType;

    // 产品期限排序
    private View dateHead;
    private ArrayList<AccountType> dateSortList;
    private AccountType dateSortType;
    private ListCategoryPopupWindow datePopupWindowSelectType;
    private PlatFormHeadEntity headData;


    private List<PlatFormHeadData.DeadlineBean> deadlineBeanList;
    private List<PlatFormHeadData.OrgLevelBean> orgLevelBeanList;
    private List<PlatFormHeadData.ProfitBean> profitBeanList;
    private Activity activity;

    @Override
    public void selected(AccountType category) {


        switch (curIndex) {  // 当前筛选button
            case 0:
                saferankPopupWindowSelectType.setSelected(category);
                titles[0].setText(category.getTypeValue().equals("") ? getResources().getString(R.string.institution_list_tab_rank) : category.getTypeName());
                saferankPopupWindowSelectType.dismiss();
                break;
            case 1:
                annualPopupWindowSelectType.setSelected(category);
                titles[1].setText(category.getTypeValue().equals("") ? getResources().getString(R.string.institution_list_tab_income) : category.getTypeName());
                annualPopupWindowSelectType.dismiss();

                break;
            case 2:

                datePopupWindowSelectType.setSelected(category);
                titles[2].setText(category.getTypeValue().equals("") ? getResources().getString(R.string.institution_list_tab_date) : category.getTypeName());
                datePopupWindowSelectType.dismiss();
                break;
        }
        this.listener.headTitleClicked(curIndex + "", saferankPopupWindowSelectType.getSelected().getTypeValue(), annualPopupWindowSelectType.getSelected().getTypeValue(), datePopupWindowSelectType.getSelected().getTypeValue());
    }

    public interface OnHeadTitleClickListener {
        /**
         * 功能：
         *
         * @param index 从1开始
         */
        void headTitleClicked(String index, String safeSort, String annualSort, String dateSort);
    }

    public OrgListClickHeadLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public OrgListClickHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {


        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View selectHeader = mInflater.inflate(R.layout.layout_orginfo_click_head, null, false);
        safeRankHead = selectHeader.findViewById(R.id.title1_ll);
        safeRankHead.setOnClickListener(this);

        annualHead = selectHeader.findViewById(R.id.title2_ll);
        annualHead.setOnClickListener(this);

        dateHead = selectHeader.findViewById(R.id.title3_ll);
        dateHead.setOnClickListener(this);
        titles[0] = (TextView) selectHeader.findViewById(R.id.title_text1);
        titles[1] = (TextView) selectHeader.findViewById(R.id.title_text2);
        titles[2] = (TextView) selectHeader.findViewById(R.id.title_text3);
        imgs[0] = (ImageView) selectHeader.findViewById(R.id.title_img1);
        imgs[1] = (ImageView) selectHeader.findViewById(R.id.title_img2);
        imgs[2] = (ImageView) selectHeader.findViewById(R.id.title_img3);

        addView(selectHeader);
    }

    /**
     * 安全评级选择button
     */
    private void initSafeRankCategoryPopupWindow() {
        safeRankSortList = new ArrayList<AccountType>();
        saferankSortType = new AccountType("", "全部");
        safeRankSortList.add(saferankSortType);

        for (int k = 0; k < orgLevelBeanList.size(); k++) {
            String key = orgLevelBeanList.get(k).getKey();
            String value = orgLevelBeanList.get(k).getValue();
            safeRankSortList.add(new AccountType(value, key));
        }
        //	accountTypes.add(new AccountType("0", "按投资额"));
//        safeRankSortList.add(new AccountType("1", "AAA"));
//        safeRankSortList.add(new AccountType("2", "AA以上"));
//        safeRankSortList.add(new AccountType("3", "A以上"));
//        safeRankSortList.add(new AccountType("4", "BBB以上"));
//        safeRankSortList.add(new AccountType("5", "BB以上"));
//        safeRankSortList.add(new AccountType("6", "B以上"));
        saferankPopupWindowSelectType = new ListCategoryPopupWindow(RelativeLayout.LayoutParams.MATCH_PARENT, safeRankSortList.size() * (PixelUtil.dip2px(context, 44) + 1), safeRankSortList, saferankSortType, LayoutInflater.from(context).inflate(R.layout.image_select_list_dir, null));
        saferankPopupWindowSelectType.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        saferankPopupWindowSelectType.setOnItemSelected(this);
    }


    /**
     * 年化收益选择button
     */
    private void initAnnualCategoryPopupWindow() {
        annualSortList = new ArrayList<AccountType>();
        annualSortType = new AccountType("", "全部");
        annualSortList.add(annualSortType);

        for (int k = 0; k < profitBeanList.size(); k++) {
            String key = profitBeanList.get(k).getKey();
            String value = profitBeanList.get(k).getValue();
            annualSortList.add(new AccountType(value, key));
        }


        //	accountTypes.add(new AccountType("0", "按投资额"));
//        annualSortList.add(new AccountType("1", "20%以上"));
//        annualSortList.add(new AccountType("2", "16%~20%"));
//        annualSortList.add(new AccountType("3", "12%~16%"));
//        annualSortList.add(new AccountType("4", "8%~12%"));
//        annualSortList.add(new AccountType("5", "8%以下"));

        annualPopupWindowSelectType = new ListCategoryPopupWindow(RelativeLayout.LayoutParams.MATCH_PARENT, annualSortList.size() * (PixelUtil.dip2px(context, 44) + 1), annualSortList, annualSortType, LayoutInflater.from(context).inflate(R.layout.image_select_list_dir, null));

        annualPopupWindowSelectType.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
//				// 设置背景颜色变暗
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        annualPopupWindowSelectType.setOnItemSelected(this);
    }


    /**
     * 产品期限选择button
     */
    private void initDateCategoryPopupWindow() {
        dateSortList = new ArrayList<AccountType>();
        dateSortType = new AccountType("", "全部");
        dateSortList.add(dateSortType);
        //	accountTypes.add(new AccountType("0", "按投资额"));


        for (int k = 0; k < deadlineBeanList.size(); k++) {
            String key = deadlineBeanList.get(k).getKey();
            String value = deadlineBeanList.get(k).getValue();
            dateSortList.add(new AccountType(value, key));
        }
//        dateSortList.add(new AccountType("1", "30天以内"));
//        dateSortList.add(new AccountType("2", "1~3个月"));
//        dateSortList.add(new AccountType("3", "3~6个月"));
//        dateSortList.add(new AccountType("4", "6~12个月"));
//        dateSortList.add(new AccountType("5", "12个月以上"));

        datePopupWindowSelectType = new ListCategoryPopupWindow(RelativeLayout.LayoutParams.MATCH_PARENT, dateSortList.size() * (PixelUtil.dip2px(context, 44) + 1), dateSortList, dateSortType, LayoutInflater.from(context).inflate(R.layout.image_select_list_dir, null));
        datePopupWindowSelectType.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#44444444")));
        datePopupWindowSelectType.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1.0f;
                activity.getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        datePopupWindowSelectType.setOnItemSelected(this);
    }

    private void changeView(boolean isInitView, int index) {
        for (int i = 0; i < titles.length; i++) {
            int color = ContextCompat.getColor(getContext(),R.color.text_black_common);
            titles[i].setTextColor(color);
        }
        for (int i = 0; i < titles.length; i++) {
            imgs[i].setImageResource(R.drawable.img_orginfo_click_head_btn_nomal);
        }
        if (!isInitView && curIndex == index) {
//            isDown = !isDown;
        } else {
            //  isDown = true;
            curIndex = index;
        }
        titles[curIndex].setTextColor(ContextCompat.getColor(getContext(),R.color.text_red_common));

        imgs[curIndex].setImageResource(R.drawable.img_orginfo_click_head_btn_selected);

        if (!isInitView && listener != null) {
            //  listener.headTitleClicked(index + 1);
        }
    }

    @Override
    public void onClick(View v) {
        if (activity == null) {
            return;
        }
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = .8f;
        activity.getWindow().setAttributes(lp);


        switch (v.getId()) {
            case R.id.title1_ll:  // 安全等级
                changeView(false, 0);
                if (saferankPopupWindowSelectType == null) {
                    ToastUtil.showCustomToast(" saferankPopupWindowSelectType == null");
                    return;
                }
                saferankPopupWindowSelectType.setAnimationStyle(R.style.anim_popup_category);
                saferankPopupWindowSelectType.showAsDropDown(safeRankHead, 0, 0);


                break;
            case R.id.title2_ll: // 年化
                changeView(false, 1);
                if (annualPopupWindowSelectType == null) {
                    ToastUtil.showCustomToast(" annualPopupWindowSelectType == null");
                    return;
                }
                annualPopupWindowSelectType.setAnimationStyle(R.style.anim_popup_category);
                annualPopupWindowSelectType.showAsDropDown(annualHead, 0, 0);
                break;
            case R.id.title3_ll: //产品期限

                changeView(false, 2);
                if (datePopupWindowSelectType == null) {
                    ToastUtil.showCustomToast(" datePopupWindowSelectType == null");
                    return;
                }
                datePopupWindowSelectType.setAnimationStyle(R.style.anim_popup_category);
                datePopupWindowSelectType.showAsDropDown(dateHead, 0, 0);
                break;

            default:
                break;
        }
    }

    /**
     * 功能：
     *
     * @param //curIndex 从1开始计算
     *                   (1:注册时间，2:直接收益，3:间接收益)
     * @param //isDown
     */
    public void initHeadView(int selectIndex) {
        this.curIndex = selectIndex - 1;

        changeView(true, curIndex);
    }

    public void setListener(OnHeadTitleClickListener listener) {
        this.listener = listener;
    }


    public void setHeadData(PlatFormHeadEntity data, Activity activity) {

        this.headData = data;
        this.activity = activity;
        deadlineBeanList = data.getData().getDeadline();
        orgLevelBeanList = data.getData().getOrgLevel();
        profitBeanList = data.getData().getProfit();


        // 初始化顶部筛选点击button
        initSafeRankCategoryPopupWindow();
        initAnnualCategoryPopupWindow();
        initDateCategoryPopupWindow();

    }
}
