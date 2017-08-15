package com.mytv365.view.fragment.mian;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.ToolResource;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshGridView;
import com.mytv365.R;
import com.mytv365.adapter.gridview.VideoListAdapter;
import com.mytv365.entity.Videos;
import com.mytv365.http.HttpServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * 课件
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class MainFragment03 extends BaseFragment {
    private TextView title;

    private ProgressActivity progressActivity;
    /*滚动条*/
    private GridView gridView;
    /*下拉刷新*/
    private PullToRefreshGridView mPullGridView;
    /*数据绑定*/
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

    @Override
    public int bindLayout() {
        return R.layout.main_fragment03;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        title = (TextView) findViewById(R.id.tv_title);
        progressActivity = (ProgressActivity) view.findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        mPullGridView = (PullToRefreshGridView) view.findViewById(R.id.gridview_th);

    }

    @Override
    public void doBusiness(final Context mContext) {
        title.setText(mContext.getResources().getString(ToolResource.getStringId("fragment03")));
        adapter = new BasicDataAdapter<Videos>(new VideoListAdapter(mContext));
        mPullGridView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {

                    @Override
                    public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                        page = 1;
                        down = true;
                        pull = false;
                        hasMoreData = true;
                        initVideosList();
                    }

                    @Override
                    public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                    }
                });

        mPullGridView
                .setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
                    @Override
                    public void onLastItemVisible() {
                        if (hasMoreData) {
                            down = false;
                            pull = true;
                            page = page + 1;
                            initVideosList();
                        }

                    }
                });

        gridView = mPullGridView.getRefreshableView();
        gridView.setVerticalScrollBarEnabled(false);
        progressActivity.showContent();
        gridView.setHorizontalSpacing(20);
        gridView.setVerticalSpacing(20);
        gridView.setAdapter(adapter);
        initVideosList();

        headerRefresh();
    }

    public void headerRefresh() {
        mPullGridView.onRefreshComplete();
        mPullGridView.setShowViewWhileRefreshing(true);
        mPullGridView.setCurrentModeRefresh();
        mPullGridView.setRefreshing(true);
    }

    /***
     * 课程数据
     */
    public void initVideosList() {
        HttpServer.videosList(mContext, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                if (isNoe) {
                    progressActivity.showLoading();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                progressActivity.showContent();

                mPullGridView.onRefreshComplete();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressActivity.showContent();
                mPullGridView.onRefreshComplete();
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getString("resultType").equals("success")) {
                        if (pull) {
                            hasMoreData = true;
                        } else if (down) {
                            adapter.clear();
                            adapter = new BasicDataAdapter<Videos>(new VideoListAdapter(getActivity()));
                        }
                        isNoe = false;
                        JSONArray jsonArray = jsonObject.getJSONArray("resultData");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Videos videos = new Videos();
                            videos.setId(object.getString("id"));
                            videos.setTitle(object.getString("title"));
                            videos.setDescription(object.getString("description"));
                            videos.setIsCharged(object.getString("isCharged"));
                            videos.setPrice(object.getString("price"));
                            videos.setType(object.getString("type"));
                            videos.setIoc(object.getString("coverpageLocation"));
                            videos.setTeacherId(object.getString("teacherId"));
                            videos.setTeacherName(object.getString("teacherName"));
                            videos.setPreviewAddress(object.getString("previewAddress"));
                            videos.setAccessingPortal(object.getString("accessingPortal"));
                            videos.setPhone(object.has("mobilePhone") == false ? "021-5590-0526" : object.getString("mobilePhone"));
                            adapter.addItem(videos);
                        }
                        adapter.notifyDataSetChanged();
                        if (down) {
                            mPullGridView.setAdapter(adapter);
                        }

//                        if (pull) {
//                            ToolAlert.toastShort(page + "");
//                            mPullGridView.smoothScrollToPosition(page + 1 * 10);
//                        }
                        mPullGridView.onRefreshComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, page + "", "10");
    }
}
