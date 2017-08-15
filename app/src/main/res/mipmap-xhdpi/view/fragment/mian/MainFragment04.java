package com.mytv365.view.fragment.mian;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.config.SysEnv;
import com.fhrj.library.third.universalimageloader.core.ImageLoader;
import com.fhrj.library.tools.ToolImage;
import com.fhrj.library.tools.ToolUnit;
import com.fhrj.library.view.imageview.RoundImageView;
import com.fhrj.library.view.listview.ListViewScoll;
import com.mytv365.R;
import com.mytv365.adapter.listview.ListViewSetUpAdapter;
import com.mytv365.common.Constant;
import com.mytv365.entity.Up;
import com.mytv365.view.activity.user.MessageActivity;
import com.mytv365.view.activity.user.MyServiceActivity;
import com.mytv365.view.activity.user.TradingActivity;

/***
 * 个人中心
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class MainFragment04 extends BaseFragment {

    /*图片操*/
    private ImageLoader imageLoader;
    /* 设置列表*/
    private ListViewScoll mListView;
    /* 头部 */
    private View header;
    /* 头部背景图*/
    private ImageView mImageView;
    /* 标题*/
    private TextView title;

    private String[] function;
    /* 设置数据*/
    private BaseMAdapter<Up> adapter;
    /*头部扩展*/
    private LinearLayout extend;
    /*头像*/
    private RoundImageView tou;
    /* 用户名*/
    private static TextView name;
    /*个人资料保存*/
    public static final int INFORESULT = 1;

    public static final int REQUESRCOSE = 0;

    @Override
    public int bindLayout() {
        return R.layout.main_fragment04;
    }

    @Override
    public void initParams(Bundle params) {
    }

    /**
     * @param view
     */
    @Override
    public void initView(View view) {
        imageLoader = ToolImage.getImageLoader();
        mListView = (ListViewScoll) findViewById(R.id.layout_listview);
        // 分割线高度
        mListView.setDividerHeight(0);

        // 分割线颜色
        mListView.setDivider(new ColorDrawable(ContextCompat.getColor(mContext, R.color.theme_bg)));
        header = LayoutInflater.from(mContext).inflate(
                R.layout.listview_header, null);
        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
        tou = (RoundImageView) header.findViewById(R.id.tou);
        tou.setType(RoundImageView.TYPE_ROUND);
        name = (TextView) header.findViewById(R.id.name);

    }

    @Override
    public void doBusiness(final Context mContext) {

        initListView();
        // 点击事件
        click();

    }

    /***
     * 点击事件
     */
    public void click() {
        name.setOnClickListener(new MyOnClick(1));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (Constant.userinfo != null) {
                    switch (i) {
                        case 1:
                            getOperation().forward(com.mytv365.view.activity.user.UserInfoActivity.class);
                            break;
                        case 2:
                            getOperation().forward(com.mytv365.view.activity.user.AccountSafeActivity.class);
                            break;
                        case 3:
                            getOperation().forward(TradingActivity.class);
                            break;
                        case 4:
                            getOperation().forward(MyServiceActivity.class);
                            break;
                        case 5:
                            // TODO: 2016/8/31 我的消息
                            getOperation().forward(MessageActivity.class);
                            break;
                    }
                } else {
                    getOperation().forward(com.mytv365.view.activity.user.LoginActivity.class);
                }
            }
        });
    }

    /***
     * 初始化设置信息
     */
    public void initListView() {
        tou.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ioc_user));
        adapter = new BasicDataAdapter<Up>(new ListViewSetUpAdapter(mContext));
        mListView.setZoomRatio(ListViewScoll.ZOOM_X2);
        mImageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                R.mipmap.menu_icon_user_bg));
        mImageView.setBackgroundColor(ContextCompat.getColor(mContext,
                R.color.theme_select));
        ViewGroup.LayoutParams params = mImageView.getLayoutParams();
        params.height = SysEnv.SCREEN_HEIGHT / 10 * 3;
        params.width = SysEnv.SCREEN_WIDTH;
        mImageView.setLayoutParams(params);
        // mListView.setDivider(getResources().getDrawable(R.color.theme_bg));
        // mListView.setDividerHeight(20);

        mListView.setParallaxImageView(mImageView);
        header.setPadding(0, 0, 0, ToolUnit.spTopx(10));
        mListView.addHeaderView(header);
        // 名称
        function = getResources().getStringArray(R.array.my_up);
        // 图标
        int[] ioc = new int[]{R.mipmap.my_ioc_geren,
                R.mipmap.my_ioc_anquan, R.mipmap.my_ioc_jiaoyi,
                R.mipmap.my_ioc_jilv, R.mipmap.my_ioc_notification};
        // 是否显示右边图标
        boolean[] right = new boolean[]{true, true, true, true, true};
        // 是否显示左边图标
        boolean[] left = new boolean[]{true, true, true, true, true};
        for (int i = 0; i < function.length; i++) {
            Up up = new Up(function[i], left[i], right[i], ioc[i]);
            adapter.addItem(up);
        }

        mListView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.userinfo == null) {
            name.setText("立即登录");
        } else {
            name.setText(Constant.userinfo.getNickname());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        name.setText(Constant.userinfo.getNickname());
        super.onActivityResult(requestCode, resultCode, data);
    }

    /****
     * 点击事件
     *
     * @author ZhangGuoHao
     * @date 2016年6月8日 下午1:45:09
     */
    public class MyOnClick implements View.OnClickListener {
        int item;

        public MyOnClick() {
        }

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1:
                    getOperation().forward(com.mytv365.view.activity.user.UserInfoActivity.class);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }
}
