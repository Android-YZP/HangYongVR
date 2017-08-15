package com.mytv365.view.activity.teacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.third.universalimageloader.core.ImageLoader;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolImage;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.horizontalscrollview.ColumnHorizontalScrollView;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.fhrj.library.view.viewpager.MyViewPager;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.entity.Stock;
import com.mytv365.entity.TabView;
import com.mytv365.entity.TeacherView;
import com.mytv365.http.UserServer;
import com.mytv365.view.fragment.teacher.StockFragment;
import com.mytv365.view.fragment.teacher.TeacherViewFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangguohao on 16/8/22.
 */
public class TeacherServerActivity extends BaseActivity {
    private Context mContext;
    /*图片操作*/
    private ImageLoader imageLoader;

    private LinearLayout top;
    /*老师名称*/
    private TextView name;
    /*老师职位*/
    private TextView position;
    /*老师简介*/
    private TextView description;
    /*头像*/
    private ImageView ioc;
    /*返回*/
    private ImageView back;
    private List<Stock> stocks;
    private List<TeacherView> teacherViews;


    private ProgressActivity progressActivity;
    /*滚动条*/
    private static ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;


    /*自定义HorizontalScrollView*/
    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private RelativeLayout rl_column;
    private LinearLayout mRadioGroup_content;
    // private ViewPager mViewPager;
    /*左阴影部分*/
    public ImageView shade_left;
    /*右阴影部分*/
    public ImageView shade_right;
    /*新闻分类列表*/
    private ArrayList<TabView> newsClassify = new ArrayList<TabView>();
    /*当前选中的栏*/
    private int columnSelectIndex = 0;
    /*Item宽度*/
    private int mItemWidth = 0;
    /*屏幕宽度 */
    private int mScreenWidth = 0;
    private LinearLayout tab;
    /*滑动*/
    private static MyViewPager viewPager;
    private FragmentManager fragmentManager;
    /*页面集合*/
    private List<Fragment> pagerList = new ArrayList<Fragment>();
    /* 老师观点*/
    private TeacherViewFragment teacherViewFragment = new TeacherViewFragment();
    /* 股票池*/
    private StockFragment stockFragment = new StockFragment();
    /*加载提示*/
    private AlertDialog dialog;

    /*服务ID*/
    private String serviceId;

    @Override
    public int bindLayout() {
        return R.layout.activity_teacher_server;
    }

    @Override
    public void initParms(Bundle parms) {
        serviceId = parms.getString("id");
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle("主页");
    }

    @Override
    public void doBusiness(Context mContext) {
        this.mContext = mContext;
        stocks = new ArrayList<>();
        teacherViews = new ArrayList<>();
        initInformatio();
        initScrollView();
        initViewTab();
        initOnClick();
        initData();
        getData();
    }

    /***
     * 初始化标题
     *
     * @param title
     */
    private void initTitle(String title) {
        initBackTitleBar(title, Gravity.CENTER);
        showTitleBar();
    }

    /***
     * 老师信息
     */
    private void initInformatio() {
        imageLoader = ToolImage.getImageLoader();
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_teacher_server_content, null);
        top = (LinearLayout) parent.findViewById(R.id.top);
        name = (TextView) parent.findViewById(R.id.name);
        back = (ImageView) parent.findViewById(R.id.back);
        position = (TextView) parent.findViewById(R.id.position);
        description = (TextView) parent.findViewById(R.id.description);
        ioc = (ImageView) parent.findViewById(R.id.ioc);
    }

    /***
     * 初始化下啦刷新
     */
    private void initScrollView() {
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        String label = DateUtils.formatDateTime(mContext,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // foundPageIndex = 1;
                        mPullScrollView.onRefreshComplete();
//                        hiddeTitleBar();
                    }


                });
        /***
         * 状态发生改变时候调用
         */
        mPullScrollView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ScrollView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ScrollView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
            }
        });

        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);
//        scrollView.setOnTouchListener(new TouchListenerImpl());
    }


    /**
     * 初始化TAB相关控件
     */
    private void initViewTab() {
        tab = (LinearLayout) findViewById(R.id.tab);
//        tab.setVisibility(View.GONE);
        mScreenWidth = SysEnv.SCREEN_WIDTH;
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) parent
                .findViewById(R.id.mColumnHorizontalScrollView);
        mRadioGroup_content = (LinearLayout) parent
                .findViewById(R.id.mRadioGroup_content);
        shade_left = (ImageView) parent.findViewById(R.id.shade_left);
        shade_right = (ImageView) parent.findViewById(R.id.shade_right);
        viewPager = (MyViewPager) parent.findViewById(R.id.viewpager);
        fragmentManager = getSupportFragmentManager();


    }


    /***
     * 绑定Fragment页面
     *
     * @author 张国浩
     * @version 1.0
     * @TODO QQ:5069506
     * @date 2016年2月25日 下午7:04:43
     */
    private class BindingFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = pagerList.get(position);
            try {
                if (!fragment.isAdded()) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.add(fragment, fragment.getClass().getName());
                    ft.commit();
                    fragmentManager.executePendingTransactions();
                }
                if (fragment.getView().getParent() == null) {
                    container.addView(fragment.getView());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fragment.getView();
        }
    }

    /**
     * 主页滑动事件
     *
     * @author 张国浩
     * @version 1.0
     * @TODO QQ:5069506
     * @date 2016年2月25日 下午6:41:42
     */
    private class MyOnPageChange implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            try {
                selectTab(position);
//                int height=0;
//                switch (position) {
//                    case 0:
//                        height=stockFragment.getListViewHeight();
//                        break;
//                    case 1:
//                        height=teacherViewFragment.getListViewHeight();
//                        break;
//                }
//                setViewPager(height);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 选择的Column里面的Tab
     */
    public void selectTab(int tab_postion) {
        columnSelectIndex = tab_postion;
        for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
            View checkView = mRadioGroup_content.getChildAt(tab_postion);
            int k = checkView.getMeasuredWidth();
            int l = checkView.getLeft();
            int i2 = l + k / 2 - mScreenWidth / 2;
            mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
        }
        // 判断是否选中
        for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
            View checkView = mRadioGroup_content.getChildAt(j);
            TextView localTextView_xia = (TextView) checkView
                    .findViewById(R.id.name_xia);
            TextView localTextView = (TextView) checkView
                    .findViewById(R.id.title_name);
//			((FragmentNews)(pagerList.get(j))).getData();
            boolean ischeck;
            if (j == tab_postion) {
                ischeck = true;
                localTextView_xia.setVisibility(View.VISIBLE);

                localTextView_xia.setWidth(localTextView.length() * 40);
                localTextView.setTextColor(ContextCompat.getColor(mContext,
                        R.color.theme_select));

            } else {
                localTextView_xia.setVisibility(View.INVISIBLE);
                localTextView.setTextColor(getResources().getColor(
                        R.color.theme_default));
                ischeck = false;
            }
            checkView.setSelected(ischeck);
        }
    }

    /**
     * 初始化Column栏目项
     */
    private void initTabColumn() {
        mRadioGroup_content.removeAllViews();
        int count = newsClassify.size();

        mColumnHorizontalScrollView.setParam((Activity) mContext, mScreenWidth,
                mRadioGroup_content, shade_left, shade_right, rl_column);
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    mItemWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
            // params.leftMargin = 10;
            // params.rightMargin = 10;

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view1 = inflater.inflate(R.layout.public_title, null);
            view1.setLayoutParams(lp);
            TextView localTextView = (TextView) view1
                    .findViewById(R.id.title_name);
            localTextView.setPadding(5, 0, 5, 0);
            localTextView.setText(newsClassify.get(i).getTitle());
            if (columnSelectIndex == i) {
                view1.setSelected(true);
            }
            view1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
                        View localView = mRadioGroup_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);

                        } else {
                            localView.setSelected(true);
                            viewPager.setCurrentItem(i);
                        }
                    }
                }
            });
            mRadioGroup_content.addView(view1, i, params);
        }

    }

    /***
     * 点击事件
     */
    public void initOnClick() {
        back.setOnClickListener(new MyOnClick(1));
    }

    /***
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener {
        private int item;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:
                    finish();
                    break;
            }
        }
    }


    private void initData() {


        String names[] = {"股票池", "老师观点"};
        pagerList.add(stockFragment);
        pagerList.add(teacherViewFragment);
        for (int i = 0; i < names.length; i++) {
            TabView classify = new TabView();
            classify = new TabView();
            classify.setId(i);
            classify.setTitle(names[i]);
            newsClassify.add(classify);
        }
        mItemWidth = mScreenWidth / 2;// 一个Item宽度为屏幕的1/7
        initTabColumn();
        viewPager.setCanSlideable(true);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new BindingFragmentAdapter());
        viewPager
                .addOnPageChangeListener(new MyOnPageChange());

        selectTab(0);
    }

    /***
     * 设置ViewPager 高度
     *
     * @param height
     */
    public void setViewPager(int height) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
        linearParams.height = height;// 当控件的高强制设成50象素
        viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
    }


    private void getData() {
        UserServer.myTeacherServer(mContext, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);
                dialog.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.hide();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog.hide();
                try {
                    Log.i("=====", responseString);
                    JSONObject object = new JSONObject(responseString);
                    if (object.getString("type").equals("1")) {
                        JSONObject obj = object.getJSONObject("obj");
                        JSONObject entity = obj.getJSONObject("modelMap");
                        name.setText(entity.getString("name"));
                        position.setText(entity.getString("position"));
                        description.setText(entity.getString("description"));
                        imageLoader.displayImage(entity.getString("photo"), ioc);
                        JSONArray listInformationEntity = obj.getJSONArray("listInformationEntity");
                        JSONArray listViewPointEntity = obj.getJSONArray("listViewPointEntity");
                        for (int i = 0; i < listInformationEntity.length(); i++) {
                            JSONObject object1 = listInformationEntity.getJSONObject(i);
                            Stock stock = new Stock();
                            stock.setId(object1.getInt("id"));
                            stock.setLastModifiedTime(object1.getInt("lastModifiedTime"));
                            stock.setCreateTime(object1.getInt("createTime"));
                            stock.setStockCode(object1.getString("stockCode"));
                            stock.setStockName(object1.getString("stockName"));
                            stock.setRecommendedPerson(object1.getString("recommendedPerson"));
                            stock.setRecommendation(object1.getString("recommendation"));
                            stock.setRoomId(object1.getInt("roomId"));
                            stock.setType(object1.getString("type"));
                            stock.setPriceHighest(object1.getDouble("priceHighest"));
                            stock.setPriceLowest(object1.getDouble("priceLowest"));
                            stock.setHoldingStatus(object1.getString("holdingStatus"));
                            stock.setReleaseTime(object1.getInt("releaseTime"));
                            stock.setStopGain(object1.getDouble("stopGain"));
                            stock.setTeacherId(object1.getInt("teacherId"));
                            stock.setHoldingPercent(object1.getInt("holdingPercent"));
                            stock.setStopLoss(object1.getDouble("stopLoss"));
                            stocks.add(stock);
                        }
                        for (int i = 0; i < listViewPointEntity.length(); i++) {
                            JSONObject object2 = listViewPointEntity.getJSONObject(i);
                            TeacherView view = new TeacherView();
                            view.setId(object2.getInt("id"));
                            view.setContent(object2.getString("content"));
                            view.setLastModifiedTime(object2.getInt("lastModifiedTime"));
                            view.setCreateTime(object2.getInt("createTime"));
                            view.setIsCharged(object2.getInt("isCharged"));
                            view.setTitle(object2.getString("title"));
                            view.setPrice(object2.getInt("price"));
                            view.setRecommendedBy(object2.getString("recommendedBy"));
                            view.setReleaseTime(object2.getInt("releaseTime"));
                            view.setTeacherId(object2.getInt("teacherId"));
                            teacherViews.add(view);
                        }
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));
//                        stocks.add(new Stock(1111, 111, 1111, "333", "dsdsds", "dsdsds", "dsdsds", 2312, "dsdsds", 12312, 122, "dsdsds", 123123, 2131.123, 2321, 12, 32));

                        stockFragment.setData(stocks);
//                        teacherViews.add(new TeacherView(111, 111, 111, 111, 111, "sdasda", "sdasda", "sdasda", 22, 333));
//                        teacherViews.add(new TeacherView(111, 111, 111, 111, 111, "sdasda", "sdasda", "sdasda", 22, 333));
                        teacherViewFragment.setData(teacherViews);

                    } else {
                        ToolAlert.toastShort(object.getString("msg"));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("===", responseString);

            }
        }, serviceId, Constant.userinfo.getToken(), Constant.userinfo.getUserid());
    }
}
