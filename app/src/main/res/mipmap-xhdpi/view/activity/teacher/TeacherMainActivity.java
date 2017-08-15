package com.mytv365.view.activity.teacher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.third.universalimageloader.core.ImageLoader;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolImage;
import com.fhrj.library.tools.ToolUnit;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.gridview.HeaderGridView;
import com.fhrj.library.view.horizontalscrollview.ColumnHorizontalScrollView;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshScrollView;
import com.fhrj.library.view.viewpager.MyViewPager;
import com.mytv365.R;
import com.mytv365.adapter.gridview.VideoListAdapter;
import com.mytv365.entity.TabView;
import com.mytv365.entity.TeacherService;
import com.mytv365.entity.Videos;
import com.mytv365.http.HTTPTeacherServer;
import com.mytv365.view.fragment.teacher.TeacherServerFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 老师主页
 * Created by zhangguohao on 16/8/8.
 */
public class TeacherMainActivity extends BaseActivity {
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


    private ProgressActivity progressActivity;
    /*滚动条*/
    private ScrollView scrollView;
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
    private MyViewPager viewPager;
    private FragmentManager fragmentManager;
    /*页面集合*/
    private List<Fragment> pagerList = new ArrayList<Fragment>();
    /* 老师服务*/
    private TeacherServerFragment thServer = new TeacherServerFragment();


    /* 精彩回顾  */
    private HeaderGridView courseGridView;
    /* 绑定精彩回顾*/
    private BaseMAdapter<Videos> courseAdapter;
    /*精彩回顾头部*/
    private View courseHeader;
    /*图标*/
    private ImageView courseIoc;
    /*名称*/
    private TextView courseName;
    /* 更多*/
    private TableRow courseMore;
    /* 数据绑定*/
    private BaseMAdapter<Videos> adapter;

    /*上拉加载 */
    private static boolean pull = false;
    /*下拉刷新 */
    private static boolean down = false;
    /*记录当前页面 */
    private static int page = 1;
    private boolean hasMoreData = true;
    /*是否是第一次访问*/
    private boolean isNoe = true;

    /* 老师ID*/
    private String teacherId;

    @Override
    public int bindLayout() {
        return R.layout.activity_teacher_main;
    }

    @Override
    public void initParms(Bundle parms) {
        teacherId = parms.getString("teacherId");
    }

    @Override
    public void initView(View view) {
        setTintManager(R.color.touming);
        initTitle("主页");
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

    @Override
    public void doBusiness(Context mContext) {
        this.mContext = mContext;
        initView();
        headerRefresh();
    }

    /****
     * 初始化控件
     */
    private void initView() {
        initInformatio();
        initScrollView();
        initViewCourse();
        initViewTab();
//        getData();
        initOnClick();
    }

    /***
     * 老师信息
     */
    private void initInformatio() {
        imageLoader = ToolImage.getImageLoader();
        parent = (LinearLayout) LayoutInflater.from(mContext).inflate(
                R.layout.activity_teacher_main_content, null);
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
        mPullScrollView = (PullToRefreshScrollView) findViewById(R.id.scrollView);
        scrollView = mPullScrollView.getRefreshableView();
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.addView(parent);

        mPullScrollView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                        page = 1;
                        down = true;
                        pull = false;
                        hasMoreData = true;
                        getData();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                        ToolAlert.toastShort("2222");
                        if (hasMoreData) {
                            down = false;
                            pull = true;
                            page = page + 1;
                            getData();
                        }
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
//        scrollView.setOnTouchListener(new TouchListenerImpl());


    }

    public void headerRefresh() {
//        mPullScrollView.onRefreshComplete();
        mPullScrollView.setMode(PullToRefreshBase.Mode.BOTH);
//        mPullScrollView.setShowViewWhileRefreshing(false);
        mPullScrollView.setRefreshing(false);
    }

    /***
     * 初始化课程相关控件
     */
    private void initViewCourse() {
        courseGridView = (HeaderGridView) parent.findViewById(R.id.my_live);
        courseGridView.setFocusable(false);
        courseHeader = LayoutInflater.from(mContext).inflate(
                R.layout.public_title_list_grid, null);
        courseIoc = (ImageView) courseHeader.findViewById(R.id.ioc);
        courseName = (TextView) courseHeader.findViewById(R.id.name);
        courseMore = (TableRow) courseHeader.findViewById(R.id.more);
        courseName.setText("课程");
        courseIoc.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.ioc_l));
        courseIoc.setVisibility(View.VISIBLE);
        courseGridView.addHeaderView(courseHeader);
        courseMore.setVisibility(View.GONE);
        adapter = new BasicDataAdapter<Videos>(new VideoListAdapter(mContext));
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
    private class BindingFragment extends PagerAdapter {

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
                switch (position) {
                    case 0:
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    default:
                        break;
                }
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
     * 老师相关数据
     */
    private void getData() {
//        ToolAlert.toastShort("111");
        HTTPTeacherServer.teacherIndex(mContext, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                if (isNoe) {
                    progressActivity.showLoading();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressActivity.showContent();
                mPullScrollView.onRefreshComplete();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
//                    hiddeTitleBar();
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getString("resultType").equals("success")) {
                        JSONObject object = jsonObject.getJSONObject("resultData");
                        //老师资料
                        name.setText(object.getString("name"));
                        position.setText(object.getString("position"));
                        description.setText(object.getString("description"));
                        imageLoader.displayImage(object.getString("photoLocation"), ioc);

                        //老师服务
                        JSONArray roomPhoneList = object.getJSONArray("roomPhoneList");
                        newsClassify = new ArrayList<TabView>();
                        pagerList = new ArrayList<Fragment>();
                        for (int i = 0; i < roomPhoneList.length(); i++) {
                            JSONObject object1 = roomPhoneList.getJSONObject(i);
                            TabView classify = new TabView();
                            classify = new TabView();
                            classify.setId(i);
                            classify.setTitle(object1.getString("type"));
                            newsClassify.add(classify);
                            thServer = new TeacherServerFragment();
                            TeacherService teacherService = new TeacherService();
                            teacherService.setItem(i);
                            teacherService.setId(object1.getString("id"));
                            teacherService.setType(object1.getString("type"));
                            teacherService.setDescription(object1.getString("description"));
                            teacherService.setRoomPrice(object1.getString("roomPrice"));
                            teacherService.setUserNumber(object1.has("userNumber") == false ? "0" : object1.getString("userNumber"));
                            teacherService.setAllowBuy(object1.getBoolean("allowBuy"));
                            teacherService.setTeacherId(object1.getString("teacherId"));
                            teacherService.setPhone(object1.has("mobilePhone") == false ? "0" : object1.getString("mobilePhone"));
                            teacherService.setTeacherName(object1.getString("teacherName"));
                            thServer.setTeacherService(teacherService);

                            pagerList.add(thServer);

                        }
                        mItemWidth = mScreenWidth / roomPhoneList.length();// 一个Item宽度为屏幕的1/7
                        initTabColumn();
                        viewPager.setCanSlideable(true);
                        viewPager.setOffscreenPageLimit(1);
                        viewPager.setAdapter(new BindingFragment());
                        viewPager
                                .addOnPageChangeListener(new MyOnPageChange());

                        selectTab(0);
                        //课程列表
                        JSONArray coursePhoneList = object.getJSONArray("coursePhoneList");


                        if (coursePhoneList.length() > 0) {
                            if (pull) {
                                hasMoreData = true;
                            } else if (down) {
                                adapter.clear();
                                adapter = new BasicDataAdapter<Videos>(new VideoListAdapter(mContext));
                            }
                            isNoe = false;
                            for (int i = 0; i < coursePhoneList.length(); i++) {
                                JSONObject object1 = coursePhoneList.getJSONObject(i);
                                Videos videos = new Videos();
                                videos.setId(object1.getString("id"));
                                videos.setTitle(object1.getString("title"));
                                videos.setDescription(object1.getString("description"));
                                videos.setIsCharged(object1.getString("isCharged"));
                                videos.setPrice(object1.getString("price"));
                                videos.setType(object1.getString("type"));
                                videos.setIoc(object1.getString("coverpageLocation"));
                                videos.setTeacherId(object1.getString("teacherId"));
                                videos.setTeacherName(object1.getString("teacherName"));
                                videos.setPreviewAddress(object1.getString("previewAddress"));
                                videos.setAccessingPortal(object1.getString("accessingPortal"));
                                videos.setPhone(object1.has("mobilePhone") == false ? "021-5590-0526" : object.getString("mobilePhone"));
                                adapter.addItem(videos);
                            }
                            adapter.notifyDataSetChanged();
                            if (down) {
                                courseGridView.setAdapter(adapter);
                                Tool.setGridViewHeightBasedOnChildren(courseGridView, ToolUnit.dipToPx(mContext, ToolUnit.dipTopx(20)));
                            } else {
                                Tool.setGridViewHeightBasedOnChildren(courseGridView, ToolUnit.dipToPx(mContext, 0));
                            }

                        }

                        progressActivity.showContent();
                        mPullScrollView.onRefreshComplete();
                    } else {
                        ToolAlert.toastShort(jsonObject.getString("resultMessage"));
                    }
                } catch (Exception e) {
                    progressActivity.showContent();
                    mPullScrollView.onRefreshComplete();
                    e.printStackTrace();
                }
            }
        }, teacherId, page + "", "4");
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


    private class TouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY = view.getScrollY();
                    int height = view.getHeight();
                    int scrollViewMeasuredHeight = scrollView.getChildAt(0).getMeasuredHeight();
                    System.out.println("滑动到了顶端 view.getScrollY()=" + scrollY);
                    if (scrollY > top.getHeight()) {
                        showTitleBar();
                    } else if (scrollY < top.getHeight()) {
                        hiddeTitleBar();
                    } else if (scrollY == 0) {
                        hiddeTitleBar();
                    }
//                    if(scrollY==0){
//                        System.out.println("滑动到了顶端 view.getScrollY()="+scrollY);
//                    }
//                    if((scrollY+height)==scrollViewMeasuredHeight){
//                        System.out.println("滑动到了底部 scrollY="+scrollY);
//                        System.out.println("滑动到了底部 height="+height);
//                        System.out.println("滑动到了底部 scrollViewMeasuredHeight="+scrollViewMeasuredHeight);
//                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    }

    ;
}

