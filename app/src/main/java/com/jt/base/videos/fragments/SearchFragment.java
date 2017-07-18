package com.jt.base.videos.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.http.responsebean.SearchTopicBean;
import com.jt.base.http.responsebean.SearchVideoBean;
import com.jt.base.updtaeapk.CheckUpdate;
import com.jt.base.utils.LocalUtils;
import com.jt.base.utils.LongLogUtil;
import com.jt.base.utils.NetUtil;
import com.jt.base.utils.UIUtils;
import com.jt.base.videos.adapters.SearchAdapter;
import com.jt.base.videos.adapters.SearchHAdapter;
import com.jt.base.videos.adapters.SearchHeadTypeAdapter;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {

    private static final int HTTP_SUCCESS = 0;
    private RecyclerView mRvsearchvideo;
    private SearchHAdapter mSearchHistoryAdapter;
    private SearchAdapter mSearchAdapter;
    private SearchHeadTypeAdapter mSearchHeadTypeAdapter;
    private LinearLayout mLlsearchresult;
    private RecyclerView mRvsearchhistory;
    private EditText mEtsearch;
    private List<String> searchHistorys ;
    private List<SearchTopicBean.ResultBean> mSearchTopicResult = new ArrayList<>();
    private TextView mTvSearchCancel;
    private LinearLayout mLlsearchHistory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initView(v);
        initResultRecyclerView();
        initHistoryRecyclerView();
        intListener();
        return v;
    }


    private void intListener() {
        mEtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) mEtsearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String search = mEtsearch.getText().toString();
                    if (!TextUtils.isEmpty(search)) {
                        LogUtil.i(search);
                        searchHistorys.add(0, search);
                        mSearchHistoryAdapter.notifyDataSetChanged();
                        LocalUtils.addSearchHistory(getContext(), search);
                        search(search, 1 + "");
                    }
                    return true;
                }
                return false;
            }
        });

        mTvSearchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLlsearchresult.setVisibility(View.GONE);
                mLlsearchHistory.setVisibility(View.VISIBLE);

            }
        });
    }

    private void initView(View v) {
        mRvsearchvideo = (RecyclerView) v.findViewById(R.id.rv_search_video);
        mRvsearchhistory = (RecyclerView) v.findViewById(R.id.rv_search_history);
        mLlsearchresult = (LinearLayout) v.findViewById(R.id.ll_search_result);
        mLlsearchHistory = (LinearLayout) v.findViewById(R.id.ll_search_history);
        mEtsearch = (EditText) v.findViewById(R.id.et_search);
        mTvSearchCancel = (TextView) v.findViewById(R.id.tv_search_cancel);
    }


    private void initResultRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvsearchvideo.setNestedScrollingEnabled(false);
        mRvsearchvideo.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

    }


    //初始化搜索历史
    private void initHistoryRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvsearchhistory.setNestedScrollingEnabled(false);
        mRvsearchhistory.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        searchHistorys = LocalUtils.getSearchHistory(getContext());
        if (searchHistorys != null) {
            UIUtils.showTip(searchHistorys.size() + "");
            mSearchHistoryAdapter = new SearchHAdapter(getActivity(), searchHistorys);
            mRvsearchhistory.setAdapter(mSearchHistoryAdapter);
            setHistoryFoot();
            mSearchHistoryAdapter.setOnItemClickListener(new SearchHAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    UIUtils.showTip(position + "");
                }
            });
        }
    }

    //设置话题的头部局
    private void setHead(List<SearchVideoBean.ResultBean> results) {
        View view = View.inflate(getContext(), R.layout.search_head, null);
        RecyclerView SearchHead = (RecyclerView) view.findViewById(R.id.rv_search_topic_head);
        RelativeLayout relativeLayoutVideos = (RelativeLayout) view.findViewById(R.id.rl_main_more_video);
        RelativeLayout relativeLayoutTopics = (RelativeLayout) view.findViewById(R.id.rl_main_more);
        TextView totalVideo = (TextView) view.findViewById(R.id.tv_total_video);
        TextView totalVideos = (TextView) view.findViewById(R.id.tv_total_videos);
        //话题的显示
        if (results != null && results.size() > 0) {
            relativeLayoutVideos.setVisibility(View.VISIBLE);
            totalVideo.setText("相关视频" + results.size() + "个");
        } else {
            relativeLayoutVideos.setVisibility(View.GONE);
        }

        //视频标题的显示
        if (mSearchTopicResult != null && mSearchTopicResult.size() > 0) {
            relativeLayoutTopics.setVisibility(View.VISIBLE);
            totalVideos.setText("相关话题" + mSearchTopicResult.size() + "个");
        } else {
            relativeLayoutTopics.setVisibility(View.GONE);
            SearchHead.setVisibility(View.GONE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        SearchHead.setNestedScrollingEnabled(false);
        SearchHead.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mSearchHeadTypeAdapter = new SearchHeadTypeAdapter(getActivity(), mSearchTopicResult);
        SearchHead.setAdapter(mSearchHeadTypeAdapter);
        mSearchAdapter.setHeaderView(view);
    }

    //设置话题的脚部局
    private void setHistoryFoot() {
        View view = View.inflate(getContext(), R.layout.search_history_foot, null);
        TextView SearchHistoryHead = (TextView) view.findViewById(R.id.search_history_delete_all);
        SearchHistoryHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalUtils.deleteAllSearchHistory(getContext());
                searchHistorys.clear();
                mSearchHistoryAdapter.notifyDataSetChanged();
            }
        });
        mSearchHistoryAdapter.setFooterView(view);
        //检查版本更新
        CheckUpdate.getInstance().startCheck(getContext(), true);
    }


    private void search(String keywords, String page) {

        HttpSearchTopic(keywords, 1 + "");
    }

    /**
     * 根据关键词搜索视频
     */
    private void HttpSearchVod(String keywords, String page) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.vod);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("keywords", keywords);
        requestParams.addBodyParameter("count", "5");
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("sourceNum", "222");
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {
            @Override
            public void onSuccess(String result) {
                List<SearchVideoBean.ResultBean> results = new ArrayList<>();
                LongLogUtil.e("-----------", result);
                SearchVideoBean searchVideoBean = new Gson().fromJson(result, SearchVideoBean.class);
                if (searchVideoBean.getCode() == HTTP_SUCCESS) {
                    results = searchVideoBean.getResult();
                    mSearchAdapter = new SearchAdapter(getActivity(), results);
                    setHead(results);
                    mRvsearchvideo.setAdapter(mSearchAdapter);
                    mLlsearchresult.setVisibility(View.VISIBLE);
                    mLlsearchHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }


    /**
     * 根据关键词搜索话题
     */
    private void HttpSearchTopic(final String keywords, String page) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.Topic);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("keywords", keywords);
        requestParams.addBodyParameter("count", "5");
        requestParams.addBodyParameter("page", page);
        requestParams.addBodyParameter("sourceNum", "222");
        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("-----------", result);
                SearchTopicBean searchTopicBean = new Gson().fromJson(result, SearchTopicBean.class);
                if (searchTopicBean.getCode() == HTTP_SUCCESS) {
                    mSearchTopicResult = searchTopicBean.getResult();

                    HttpSearchVod(keywords, 1 + "");

                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
        });
    }


}







