package com.jt.base.videos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt.base.R;
import com.jt.base.videos.adapters.MainReAdapter;

//rtmp://9250.liveplay.myqcloud.com/live/9250_0601HK
public class MainFragment extends Fragment {


    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecycler;

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
        mRecycler.setAdapter(new MainReAdapter(getActivity(),mRecycler));
    }
}
