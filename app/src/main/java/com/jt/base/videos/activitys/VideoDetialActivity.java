package com.jt.base.videos.activitys;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.http.responsebean.GetRoomBean;
import com.jt.base.utils.UIUtils;
import com.jt.base.videoDetails.adapters.RvAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import org.xutils.common.util.LogUtil;

import java.util.List;

public class VideoDetialActivity extends AppCompatActivity {
    private static final String ACTION = "com.jt.base.SENDBROADCAST";
    private static final int HTTP_SUCCESS = 0;
    private static final String COUNT = "3";//每次获取到的数据
    private RecyclerViewPager mRvVideoDetaillist;
    private VrPanoramaView panoWidgetView;
    private VrPanoramaView.Options panoOptions;
    private SwipyRefreshLayout mSwipyRefresh;
    private int mPager = 1;
    private List<GetRoomBean.ResultBean> mRoomLists;
    private RvAdapter mRvAdapter;
    private GetRoomBean mRoomListBean;
    private LinearLayout mIvDetialErrorBg;
    private ImageView mIvTwoDBg;
    private ViewPager mViewpager;
    private DrawerLayout mDlLayout;
    private Handler handler = new Handler();
    private int mCurrentPosition = 0;//判断这个界面的第一屏应该展示哪一个界面,默认第一页
    boolean isScroll = true;//是不是手指滑动过来的


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detials);
        initView();
        initRecyclerViewPager();
        mRvVideoDetaillist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        LogUtil.i("1");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        LogUtil.i("2");
                        break;
                    case MotionEvent.ACTION_UP:
                        LogUtil.i("3");
                        break;
                }
                return false;
            }
        });
    }




    private void initView() {
        mSwipyRefresh = (SwipyRefreshLayout) findViewById(R.id.sf_detail_SwipeRefreshLayout);
        mRvVideoDetaillist = (RecyclerViewPager) findViewById(R.id.rv_video_detail_list);
        mIvDetialErrorBg = (LinearLayout) findViewById(R.id.iv_detial_bg);
    }

    private void initRecyclerViewPager() {
        //初始化竖直的viewPager
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvVideoDetaillist.setLayoutManager(layout);
    }

}
