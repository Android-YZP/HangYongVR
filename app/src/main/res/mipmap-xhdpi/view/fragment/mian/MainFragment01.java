package com.mytv365.view.fragment.mian;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.advertising.one.CircleFlowIndicator;
import com.fhrj.library.view.advertising.one.ViewFlow;
import com.fhrj.library.view.horizontalscrollview.ColumnHorizontalScrollView;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.fhrj.library.view.viewpager.MyViewPager;
import com.mytv365.R;
import com.mytv365.adapter.CarouselApapter;
import com.mytv365.entity.Carousel;
import com.mytv365.entity.TabView;
import com.mytv365.http.HttpServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/***
 * 主页
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class MainFragment01 extends BaseFragment {

    private ProgressActivity progressActivity;
    /*滚动条*/
    private ScrollView scrollView;
    /*下拉刷新*/
    private PullToRefreshScrollView mPullScrollView;
    /*内容界面*/
    private LinearLayout parent;

    private TextView titles;

    /**
     * 轮播图
     */
    private ViewFlow viewFlow;
    /**
     * 绑定轮播图数据
     */
    private BaseMAdapter<Carousel> carouselAdapter;

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
    public static List<Fragment> pagerList = new ArrayList<Fragment>();
    public com.mytv365.view.fragment.teacher.TeacherMainServerFragment thServer;

    @Override
    public int bindLayout() {
        return R.layout.main_fragment01;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {


    }

    @Override
    public void doBusiness(Context mContext) {
        initScrollView();
        initCarousel();
        initViewTab();
        initDataServer();
        getBanner();
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
//		fragmentManager = getChildFragmentManager();
        fragmentManager = getActivity().getSupportFragmentManager();


    }

    /***
     * 绑定Fragment页面
     *
     * @author 张国浩
     * @date 2016年2月25日 下午7:04:43
     */
    private class BindingFragmentAdapter extends FragmentPagerAdapter {

        public BindingFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerList.get(position);
        }

        @Override
        public int getCount() {
            return pagerList.size();
        }

//		@Override
//		public int getCount() {
//			return pagerList.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			return arg0 == arg1;
//		}
//
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//		}
//
//		@Override
//		public Fragment getItem(int position) {
//			return null;
//		}

//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			Fragment fragment = pagerList.get(position);
//			try {
//				if (!fragment.isAdded()) {
//					FragmentTransaction ft = fragmentManager.beginTransaction();
//					ft.add(fragment, fragment.getClass().getName());
//					ft.commit();
//					fragmentManager.executePendingTransactions();
//				}
//				if (fragment.getView().getParent() == null) {
//					container.addView(fragment.getView());
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return fragment.getView();
//		}
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
        private String item;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 选择的Column里面的Tab
     */
    private void selectTab(int tab_postion) {
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

                localTextView_xia.setWidth(localTextView.length() * 100);
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
        int iocs[] = {R.mipmap.ioc_server01, R.mipmap.ioc_server02, R.mipmap.ioc_server03, R.mipmap.ioc_server04};
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
            ImageView ioc = (ImageView) view1.findViewById(R.id.ioc);
            ioc.setVisibility(View.VISIBLE);
            ioc.setImageDrawable(ContextCompat.getDrawable(mContext, iocs[i]));
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

    /**
     * 初始化轮播图
     */
    private void initCarousel() {
        titles = (TextView) findViewById(R.id.tv_title);
        titles.setText("主页");
        viewFlow = (ViewFlow) parent.findViewById(R.id.viewflow);
        carouselAdapter = new BasicDataAdapter<Carousel>(new CarouselApapter(
                mContext));

    }

    /***
     * 广告图
     */
    private void getBanner() {
        HttpServer.bannerMain(mContext, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONArray response = new JSONArray(responseString);

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        Carousel carousel = new Carousel();
                        carousel.setImage(object.getString("image"));
                        carousel.setUrl(object.getString("url"));
                        carousel.setTitle(object.getString("name"));
                        carouselAdapter.addItem(carousel);
                    }
                    // 轮播图
                    viewFlow.setAdapter(carouselAdapter);
                    viewFlow.setmSideBuffer(response.length()); // 实际图片张数，
                    // 我的ImageAdapter实际图片张数为3
                    carouselAdapter.notifyDataSetChanged();


                    CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
                    viewFlow.setFlowIndicator(indic);
                    viewFlow.setTimeSpan(4500);
                    viewFlow.setSelection(1); // 设置初始位置
                    viewFlow.startAutoFlowTimer(); // 启动自动播放
                    mPullScrollView.onRefreshComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /***
     * 初始化下啦刷新
     */
    private void initScrollView() {
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.main_fragment01_content, null);
        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        progressActivity.showContent();
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ScrollView> refreshView) {
                        getBanner();
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
    }


    private void initDataServer() {
        String[] names = {"胜赢", "智赢", "慧赢", "私人定制"};
        String[] types = {"victoryWin", "wiseWin", "brightWin", "topWin"};


        for (int i = 0; i < 4; i++) {
            com.mytv365.view.fragment.teacher.TeacherMainServerFragment thServer = new com.mytv365.view.fragment.teacher.TeacherMainServerFragment();
            TabView classify = new TabView();
            classify.setId(i);
            classify.setTitle(names[i]);
            thServer.setType(types[i]);
            pagerList.add(thServer);
            newsClassify.add(classify);
        }
        mItemWidth = mScreenWidth / 4;// 一个Item宽度为屏幕的1/7
        initTabColumn();
        viewPager.setCanSlideable(true);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setAdapter(new BindingFragmentAdapter(getChildFragmentManager()));
        viewPager.addOnPageChangeListener(new MyOnPageChange());

        selectTab(0);

    }


    /***
     * 设置ViewPager 高度
     * @param height
     */
//	public static void setViewPager(int height){
//		LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager.getLayoutParams(); // 取控件mGrid当前的布局参数
//		linearParams.height = height;// 当控件的高强制设成50象素
//		viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件myGrid
//	}
}


