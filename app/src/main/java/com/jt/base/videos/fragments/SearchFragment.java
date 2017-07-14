package com.jt.base.videos.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jt.base.R;
import com.jt.base.videos.adapters.MainAdapter;
import com.jt.base.videos.adapters.MainVideosAdapter;
import com.jt.base.videos.adapters.SearchAdapter;
import com.jt.base.videos.adapters.SearchHeadTypeAdapter;

import static com.jt.base.R.drawable.position;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {

    private RecyclerView mRvsearchvideo;
    private RecyclerView mRvsearchtopic;
    private ImageView mImsearch;
    private RecyclerView mRvsearchTopic;
    private SearchAdapter mSearchAdapter;
    private SearchHeadTypeAdapter mSearchHeadTypeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        initView(v);
        initRecyclerView();
        return v;
    }

    private void initView(View v) {
        mRvsearchvideo = (RecyclerView) v.findViewById(R.id.rv_search_video);
    }


    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRvsearchvideo.setNestedScrollingEnabled(false);
        mRvsearchvideo.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchAdapter = new SearchAdapter(getActivity());
        setHead();
        mRvsearchvideo.setAdapter(mSearchAdapter);
    }


    //设置话题的头部局
    private void setHead() {
        View view = View.inflate(getContext(), R.layout.search_head, null);
        RecyclerView SearchHead = (RecyclerView) view.findViewById(R.id.rv_search_topic_head);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        SearchHead.setNestedScrollingEnabled(false);
        SearchHead.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mSearchHeadTypeAdapter = new SearchHeadTypeAdapter(getActivity());
        SearchHead.setAdapter(mSearchHeadTypeAdapter);
        mSearchAdapter.setHeaderView(view);
    }


}







