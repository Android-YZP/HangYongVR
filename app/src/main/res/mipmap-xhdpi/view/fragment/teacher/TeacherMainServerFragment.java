package com.mytv365.view.fragment.teacher;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.base.impl.BaseMAdapter;
import com.fhrj.library.common.BasicDataAdapter;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.Tool;
import com.fhrj.library.tools.ToolAlert;
import com.mytv365.R;
import com.mytv365.adapter.listview.TeacherServerAdapter;
import com.mytv365.entity.TeacherService;
import com.mytv365.http.HTTPTeacherServer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * 课件
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class TeacherMainServerFragment extends BaseFragment {
    private ListView listView;
    private BaseMAdapter<TeacherService> adapter;
    private String type = "*";

    @Override
    public int bindLayout() {
        return R.layout.list_view;
    }

    @Override
    public void initParams(Bundle params) {
    }

    @Override
    public void initView(View view) {
        listView = (ListView) findViewById(R.id.listview);
        listView.setEmptyView(findViewById(R.id.emptyElement));
    }

    @Override
    public void doBusiness(Context mContext) {
        adapter = new BasicDataAdapter<TeacherService>(new TeacherServerAdapter(mContext));
        listView.setAdapter(adapter);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    /***
     * 老师相关数据
     */
    public void getData() {
        HTTPTeacherServer.teacherServer(mContext, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
//                    ToolAlert.toastShort(responseString);
                    JSONObject jsonObject = new JSONObject(responseString);
                    if (jsonObject.getString("resultType").equals("success")) {
                        adapter = new BasicDataAdapter<TeacherService>(new TeacherServerAdapter(mContext));
                        JSONObject object = jsonObject.getJSONObject("resultData");
                        //老师服务
                        JSONArray victoryWinLit = object.getJSONArray(type + "Lit");
                        if (victoryWinLit.length() > 0) {
                            adapter.clear();
                        }
                        for (int i = 0; i < victoryWinLit.length(); i++) {
                            JSONObject object1 = victoryWinLit.getJSONObject(i);
                            TeacherService teacherService = new TeacherService();
                            teacherService.setItem(i);
                            teacherService.setId(object1.getString("id"));
                            teacherService.setType(object1.getString("type"));
                            teacherService.setDescription(object1.getString("description"));
                            teacherService.setRoomPrice(object1.getString("roomPrice"));
                            teacherService.setUserNumber(object1.has("userNumber") == false ? "0" : object1.getString("userNumber"));
                            teacherService.setAllowBuy(object1.getBoolean("allowBuy"));
                            teacherService.setTeacherId(object1.getString("teacherId"));
                            teacherService.setPhone(object1.has("mobilePhone") == false ? "0" : object1.getString("mobilePhone"));
                            teacherService.setTeacherName(object1.has("teacherName") == false ? "0" : object1.getString("teacherName"));
                            teacherService.setIoc(object1.getString("photoLocation"));
                            adapter.addItem(teacherService);

                        }
//                        adapter.notifyDataSetChanged();
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetInvalidated();
                        Tool.setListViewHeightBasedOnChildren(listView);

                    } else {
                        ToolAlert.toastShort(jsonObject.getString("resultMessage"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, type, "*", "*");
    }

    public int getListViewHeight() {
        return listView.getHeight();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}