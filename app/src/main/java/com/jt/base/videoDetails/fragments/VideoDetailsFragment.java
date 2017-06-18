package com.jt.base.videoDetails.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.jt.base.R;
import com.jt.base.ui.verticalpager.HorizontalPageLayoutManager;
import com.jt.base.ui.verticalpager.PagingItemDecoration;
import com.jt.base.ui.verticalpager.PagingScrollHelper;
import com.jt.base.videoDetails.adapters.MyAdapter;

import org.xutils.common.util.LogUtil;


public class VideoDetailsFragment extends Fragment implements PagingScrollHelper.onPageChangeListener {
    private RecyclerView recyclerView;
    private RecyclerView.ItemDecoration lastItemDecoration = null;
    private HorizontalPageLayoutManager horizontalPageLayoutManager = null;
    private LinearLayoutManager hLinearLayoutManager = null;
    private LinearLayoutManager vLinearLayoutManager = null;
    private DividerItemDecoration hDividerItemDecoration = null;
    private DividerItemDecoration vDividerItemDecoration = null;
    private PagingItemDecoration pagingItemDecoration = null;
    private ViewPager viewpager;
    private MyAdapter myAdapter;
    VrPanoramaView panoWidgetView;
    PagingScrollHelper scrollHelper ;

    public VideoDetailsFragment(VrPanoramaView panoWidgetView) {
        this.panoWidgetView = panoWidgetView;
    }

    /**
     * Toast.makeText(getActivity(),"进入播放器",Toast.LENGTH_SHORT).show();
     * Intent i = new Intent(getContext(),PlayActivity.class);
     * i.putExtra(Definition.KEY_PLAY_URL, "rtmp://9250.liveplay.myqcloud.com/live/9250_0601HK");
     * SPUtils.put(getContext(),Definition.HISTORY_URL,"rtmp://9250.liveplay.myqcloud.com/live/9250_1111112111");
     * getContext().startActivity(i);
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_details, container, false);
        scrollHelper = new PagingScrollHelper(panoWidgetView);
        init(view);
        initListenter();
        return view;
    }


    private void initListenter() {
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                LogUtil.i(newState+"");
//
//            }
//        });

    }

    private void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        scrollHelper.setUpRecycleView(recyclerView);
        scrollHelper.setOnPageChangeListener(this);

        RecyclerView.LayoutManager layoutManager = null;
        RecyclerView.ItemDecoration itemDecoration = null;

        vDividerItemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        vLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        layoutManager = vLinearLayoutManager;
        itemDecoration = vDividerItemDecoration;

        if (layoutManager != null) {
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.removeItemDecoration(lastItemDecoration);
            recyclerView.addItemDecoration(itemDecoration);
            scrollHelper.updateLayoutManger();
            lastItemDecoration = itemDecoration;
        }
    }

    @Override
    public void onPageChange(int index) {

        panoWidgetView.setVisibility(View.VISIBLE);
    }


}
