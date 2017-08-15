package com.mytv365.view.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.third.universalimageloader.core.ImageLoader;
import com.fhrj.library.tools.ToolLog;
import com.fhrj.library.tools.ToolToast;
import com.fhrj.library.view.imageview.RoundImageView;
import com.mytv365.R;
import com.mytv365.entity.CourseInfo;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by zhangguohao on 16/8/29.
 */
public class VideoActivty extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String url;
    private String title;
    private String thumb;
    private String courseId;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private VideoCourseAdapter mAdapter;
    private CourseInfo courseInfo;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private ImageLoader imageLoader;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        thumb = getIntent().getStringExtra("thumb");
        courseId = getIntent().getStringExtra("courseId");
        initView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void initView() {
        courseInfo = new CourseInfo();
        imageLoader = ImageLoader.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
//                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new GridLayoutManager(this, 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new VideoCourseAdapter(courseInfo, 2);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setAdapter(mAdapter);
        if (!TextUtils.isEmpty(courseId)) {
            onRefresh();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToolLog.i("VideoActivty", "onResume");
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
        ToolLog.i("VideoActivty", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ToolLog.i("VideoActivty", "onStop");
    }

    @Override
    public void onRefresh() {
        UserServer.getCourse(this, new TextHttpResponseHandler() {
            @Override
            public void onStart() {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getInt("resultCode") == 1) {
                        JSONObject data = jsonObject.getJSONObject("resultData");
                        if (courseInfo == null) {
                            courseInfo = new CourseInfo();
                        }
                        courseInfo.setId(data.getInt("id"));
                        courseInfo.setIsCharged(data.getInt("isCharged"));
                        courseInfo.setPrice(data.getInt("price"));
                        courseInfo.setTeacherId(data.getInt("teacherId"));
                        courseInfo.setPhotoLocation(data.getString("photoLocation"));
                        courseInfo.setPosition(data.getString("position"));
                        courseInfo.setPreviewAddress(data.getString("previewAddress"));
                        courseInfo.setTeacherName(data.getString("teacherName"));
                        courseInfo.setTitle(data.getString("title"));
                        List<CourseInfo.Course> lists = new ArrayList<>();
                        JSONArray phoneList = data.getJSONArray("coursePhoneList");
                        for (int i = 0; i < phoneList.length(); i++) {
                            JSONObject object = phoneList.getJSONObject(i);
                            CourseInfo.Course course = new CourseInfo.Course();
                            course.setId(object.getInt("id"));
                            course.setIsCharged(object.getInt("isCharged"));
                            course.setPrice(object.getInt("price"));
                            course.setTeacherId(object.getInt("teacherId"));
                            course.setAccessingPortal(object.getString("accessingPortal"));
                            course.setCoverpageLocation(object.getString("coverpageLocation"));
                            course.setDescription(object.getString("description"));
                            course.setPreviewAddress(object.getString("previewAddress"));
                            course.setTeacherName(object.getString("teacherName"));
                            course.setTitle(object.getString("title"));
                            course.setType(object.getString("type"));
                            lists.add(course);
                        }
                        courseInfo.addCoursePhoneList(lists);
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToolToast.showShort(jsonObject.getString("resultMessage"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, courseId);
    }

    class VideoCourseAdapter extends RecyclerView.Adapter {
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_INFO = 1;
        private static final int TYPE_ITEM = 2;
        private int columns;
        private CourseInfo courseInfo;

        public VideoCourseAdapter(CourseInfo courseInfo, int columns) {
            this.courseInfo = courseInfo;
            this.columns = columns;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == TYPE_HEADER) {
                view = LayoutInflater.from(com.mytv365.view.activity.VideoActivty.this).inflate(R.layout.item_video_header, null, false);
                return new VideoHolder(view);
            } else if (viewType == TYPE_INFO) {
                view = LayoutInflater.from(com.mytv365.view.activity.VideoActivty.this).inflate(R.layout.item_video_info, null, false);
                return new InfoHolder(view);
            } else {
                view = LayoutInflater.from(com.mytv365.view.activity.VideoActivty.this).inflate(R.layout.item_video_course, null, false);
                return new ItemHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof VideoHolder) {
                if (!TextUtils.isEmpty(url) && jcVideoPlayerStandard != null) {
                    jcVideoPlayerStandard.setUp(url
                            , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, title);
                    jcVideoPlayerStandard.startPlayLogic();
                }
            } else if (holder instanceof InfoHolder) {
                imageLoader.displayImage(courseInfo.getPhotoLocation(), ((InfoHolder) holder).ioc);
                ((InfoHolder) holder).name.setText(courseInfo.getTeacherName());
                ((InfoHolder) holder).position.setText(courseInfo.getPosition());
                ((InfoHolder) holder).description.setText(courseInfo.getTitle());
            } else if (holder instanceof ItemHolder) {
                final CourseInfo.Course course = courseInfo.getCoursePhoneList().get(position - 2);
                imageLoader.displayImage(course.getCoverpageLocation(), ((ItemHolder) holder).iv_img);
                ((ItemHolder) holder).tv_name.setText(course.getTitle());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(course.getAccessingPortal()) && jcVideoPlayerStandard != null) {
                            url = course.getAccessingPortal();
                            title = course.getTitle();
                            jcVideoPlayerStandard.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, title);
                            jcVideoPlayerStandard.startPlayLogic();
                        }
                    }
                });
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == 1) {
                return TYPE_INFO;
            } else {
                return TYPE_ITEM;
            }
        }

        @Override
        public int getItemCount() {
            return courseInfo.getCoursePhoneList().size() + 2;
        }

        public int getItemColumnSpan(int position) {
            switch (getItemViewType(position)) {
                case TYPE_ITEM:
                    return 1;
                default:
                    return columns;
            }
        }

        class VideoHolder extends RecyclerView.ViewHolder {

            public VideoHolder(View itemView) {
                super(itemView);
                jcVideoPlayerStandard = (JCVideoPlayerStandard) itemView.findViewById(R.id.custom_videoplayer_standard);
            }
        }

        class InfoHolder extends RecyclerView.ViewHolder {
            private RoundImageView ioc;
            private TextView name;
            private TextView position;
            private TextView description;

            public InfoHolder(View itemView) {
                super(itemView);
                ioc = (RoundImageView) itemView.findViewById(R.id.ioc);
                name = (TextView) itemView.findViewById(R.id.name);
                position = (TextView) itemView.findViewById(R.id.position);
                description = (TextView) itemView.findViewById(R.id.description);
            }
        }

        class ItemHolder extends RecyclerView.ViewHolder {
            private ImageView iv_img;
            private TextView tv_name;

            public ItemHolder(View itemView) {
                super(itemView);
                iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }

}
