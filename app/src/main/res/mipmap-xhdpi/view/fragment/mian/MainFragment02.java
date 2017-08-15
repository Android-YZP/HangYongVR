package com.mytv365.view.fragment.mian;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.ToolResource;
import com.fhrj.library.view.ProgressActivity;
import com.fhrj.library.view.pulltorefresh.PullToRefreshBase;
import com.fhrj.library.view.pulltorefresh.PullToRefreshListView;
import com.mytv365.R;
import com.mytv365.adapter.listview.TeacherListAdapter;
import com.mytv365.entity.Teacher;
import com.mytv365.http.HttpServer;
import com.mytv365.view.activity.teacher.TeacherMainActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * 名师
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class MainFragment02 extends BaseFragment {
    private TextView title;

    private ProgressActivity progressActivity;
    /*滚动条*/
    private ListView listlView;
    /*下拉刷新*/
    private PullToRefreshListView mPullListView;

    private BaseMAdapter<Teacher> adapter;
    private String teacherId[];
    @Override
    public int bindLayout() {
        return R.layout.main_fragment02;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(mContext.getResources().getString(ToolResource.getStringId("fragment02")));

        progressActivity = (ProgressActivity) findViewById(R.id.progressActivity);
        progressActivity.showLoading();
        mPullListView = (PullToRefreshListView) findViewById(R.id.listview);
    }

    /**
     * @param mContext 当前Activity对象
     */
    @Override
    public void doBusiness(final Context mContext) {
        initPullListView();
        initList();
        listlView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getOperation().addParameter("teacherId",teacherId[position-1]);
                getOperation().forward(TeacherMainActivity.class);
            }
        });
        listlView.setEmptyView(findViewById(R.id.emptyElement));
    }

    /***
     * 初始化下啦刷新
     */
    public void initPullListView(){
        adapter = new BasicDataAdapter<Teacher>(new TeacherListAdapter(mContext));

        mPullListView
                .setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        String label = DateUtils.formatDateTime(mContext,
                                System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME
                                        | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy()
                                .setLastUpdatedLabel(label);
                        // 更新最后一次刷新时间
                        refreshView.getLoadingLayoutProxy();

                        // foundPageIndex = 1;
                        initList();
                    }
                });

        listlView = mPullListView.getRefreshableView();

        listlView.setVerticalScrollBarEnabled(false);
    }
    /**
     * 初始化老师列表
     */
    public void initList(){
        HttpServer.teacherList(mContext,new TextHttpResponseHandler() {
            @Override
            public void onStart() {

            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressActivity.showContent();
                mPullListView.onRefreshComplete();
                try {
                    JSONObject jsonObject=new JSONObject(responseString);
                    if(jsonObject.getString("resultType").equals("success")){
                        adapter.clear();
                        JSONArray jsonArray=jsonObject.getJSONArray("resultData");
                        teacherId=new String[jsonArray.length()];
                        Log.i("====",responseString);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject object=jsonArray.getJSONObject(i);
                            Teacher teacher=new Teacher();
                            teacher.setId(object.getString("id"));
                            teacher.setName(object.getString("name"));
                            teacher.setIoc(object.getString("photoLocation"));
                            teacher.setPosition(object.getString("position"));
                            teacher.setDescription(object.getString("description"));
                            teacherId[i]=object.getString("id");
                            adapter.addItem(teacher);
                        }
                        listlView.setAdapter(adapter);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressActivity.showContent();
                mPullListView.onRefreshComplete();
            }
        },"*","*");
    }
}
