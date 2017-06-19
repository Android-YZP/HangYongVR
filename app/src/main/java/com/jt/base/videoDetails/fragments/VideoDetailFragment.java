package com.jt.base.videoDetails.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.videoDetails.adapters.RvAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailFragment extends Fragment {

    private RecyclerViewPager mRvvideodetaillist;
    private List<String> mDatas = new ArrayList<>();
    private VrPanoramaView panoWidgetView;

    public VideoDetailFragment() {
    }

    public VideoDetailFragment(VrPanoramaView panoWidgetView) {
            this.panoWidgetView = panoWidgetView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_detail, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 21; i++) {
            mDatas.add("" + i);
        }

// setLayoutManager like normal RecyclerView, you do not need to change any thing.
        LinearLayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRvvideodetaillist.setLayoutManager(layout);
        mRvvideodetaillist.setAdapter(new RvAdapter(getContext(), mDatas));
    }

    private void initView(View view) {
        mRvvideodetaillist = (RecyclerViewPager) view.findViewById(R.id.rv_video_detail_list);
    }



}
