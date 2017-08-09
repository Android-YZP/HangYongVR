package com.hy.vrfrog.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.VideoTypeBean;
import com.hy.vrfrog.main.adapter.HomeAdapter;
import com.hy.vrfrog.main.home.activitys.SearchActivity;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    private static final String[] CHANNELS = new String[]{"CUPCAKE", "DONUT", "ECLAIR", "GINGERBREAD", "HONEYCOMB", "ICE_CREAM_SANDWICH", "JELLY_BEAN", "KITKAT", "LOLLIPOP", "M", "NOUGAT"};
    private static final int HTTP_SUCCESS = 0;
    private ViewPager mViewPager;
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private List<VideoTypeBean.ResultBean> mTitle;
    private MagicIndicator mMagicIndicator;
    private ImageView mIvSearch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search_topic);
        mMagicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    private void initData() {
        HttpVideoType();
    }

    private void initListener() {
        mIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
                getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_right_out);
            }
        });
    }

    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitle == null ? 0 : mTitle.size() +3;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);

                if (index == 0){
                    simplePagerTitleView.setText("推荐");
                }else if (index == 1){
                    simplePagerTitleView.setText("个人直播");
                }else if (index == 2){
                    simplePagerTitleView.setText("企业直播");
                }else {
                    simplePagerTitleView.setText(mTitle.get(index - 3).getName());
                }
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }


    /**
     * 请求侧边栏列表
     */
    private void HttpVideoType() {
        LogUtil.e("请求侧边栏列表");
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.VideoType);
        requestParams.addHeader("token", HttpURL.Token);
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                LogUtil.i(result);
                VideoTypeBean videoTypeBean = new Gson().fromJson(result, VideoTypeBean.class);
                if (videoTypeBean.getCode() == HTTP_SUCCESS) {//获取数据成功

                    mTitle = videoTypeBean.getResult();
                    initMagicIndicator();
                    mViewPager.setAdapter(new HomeAdapter(getActivity().getSupportFragmentManager(), mTitle));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }
}
