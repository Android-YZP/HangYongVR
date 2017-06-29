package com.jt.base.videos.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt.base.R;
import com.jt.base.videos.adapters.MainAdapter;
import com.jt.base.videos.adapters.MainPicListAdapter;
import com.jt.base.videos.adapters.SpacesItemDecoration;
import com.lsjwzh.widget.recyclerviewpager.LoopRecyclerViewPager;


//rtmp://9250.liveplay.myqcloud.com/live/9250_0601HK
public class MainFragment extends Fragment {

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycler;
    private MainAdapter mMainAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecycler = (RecyclerView) view.findViewById(R.id.re_main_recycler);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecycler.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
    }

    private void initRecycleView() {
        mMainAdapter = new MainAdapter(getActivity());
        mRecycler.setAdapter(mMainAdapter);
        setHeaderView(mRecycler);
    }

    /**
     * 设置头布局
     */
    private void setHeaderView(RecyclerView view){
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.my_head_view, view, false);
        LoopRecyclerViewPager  mHeadPicRecycler = (LoopRecyclerViewPager) header.findViewById(R.id.lrvp_viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mHeadPicRecycler.setLayoutManager(layout);
        mHeadPicRecycler.setAdapter(new MainPicListAdapter(getActivity()));
        mHeadPicRecycler.setHasFixedSize(true);
        mHeadPicRecycler.setLongClickable(true);
        mHeadPicRecycler.addItemDecoration(new SpacesItemDecoration(0, mHeadPicRecycler.getAdapter().getItemCount()));
        mMainAdapter.setHeaderView(header);
    }
}
