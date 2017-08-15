package com.mytv365.view.fragment.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseFragment;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.view.dialog.MyDialog;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.entity.TeacherService;
import com.mytv365.http.UserServer;

import org.apache.http.Header;
import org.json.JSONObject;

/***
 * 服务
 *
 * @author 张国浩
 * @date 2016年6月29日 下午2:47:38
 */
public class TeacherServerFragment extends BaseFragment {
    private LinearLayout priceLay;
    /*价格*/
    private TextView roomPrice;
    /*购买人数*/
    private TextView userNumber;
    /*服务介绍*/
    private TextView description;
    /*购买*/
    private Button buy;
    /* 服务标示 */
    private ImageView iocServer;
    /*服务信息*/
    private TeacherService teacherService = null;

    /*服务详情界面*/
    private View dialogServer;
    /*服务详情*/
    private MyDialog dialogSer;
    /*服务描述*/
    private TextView describe;
    /*服务价格*/
    private TextView price;

    private AlertDialog dialog;

    @Override
    public int bindLayout() {
        return R.layout.teacher_server_fragment;
    }

    @Override
    public void initParams(Bundle params) {
    }

    @Override
    public void initView(View view) {
        priceLay = (LinearLayout) findViewById(R.id.price_lay);
        iocServer = (ImageView) findViewById(R.id.ioc_server);
        iocServer.bringToFront();
        roomPrice = (TextView) findViewById(R.id.room_price);
        userNumber = (TextView) findViewById(R.id.user_number);
        description = (TextView) findViewById(R.id.description);
        buy = (Button) findViewById(R.id.buy);

        dialogServer = LayoutInflater.from(mContext).inflate(R.layout.dialog_server, null);
        describe = (TextView) dialogServer.findViewById(R.id.describe);
        price = (TextView) dialogServer.findViewById(R.id.price);
        dialogSer = new MyDialog(mContext, dialogServer);

        describe.setText(teacherService.getTeacherName() + "-" + teacherService.getType());
        price.setText("价格：" + teacherService.getRoomPrice());

        roomPrice.setText(teacherService.getRoomPrice());
        description.setText(teacherService.getDescription());
        userNumber.setText(teacherService.getUserNumber());
        if (teacherService.isAllowBuy()) {
            buy.setText("立即购买");
        } else {
            buy.setText(teacherService.getPhone());
        }
        if (Double.parseDouble(teacherService.getRoomPrice()) < 1000000) {
            priceLay.setVisibility(View.VISIBLE);
        } else {
            priceLay.setVisibility(View.INVISIBLE);
        }

        switch (teacherService.getItem()) {
            case 0:
                iocServer.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.th_server_01));
                break;
            case 1:
                iocServer.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.th_server_02));
                break;
            case 2:
                iocServer.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.th_server_03));
                break;
            case 3:
                iocServer.setImageDrawable(ContextCompat.getDrawable(mContext, R.mipmap.th_server_04));
                break;
        }
    }

    @Override
    public void doBusiness(final Context mContext) {

        initOnClick();
    }

    /***
     * 调用点击事件
     */
    public void initOnClick() {
        buy.setOnClickListener(new MyOnClick(1));
    }

    /***
     * 点击事件
     */
    private class MyOnClick implements View.OnClickListener {
        public int item;

        public MyOnClick(int item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (item) {
                case 1://购买服务
                    if (Constant.userinfo == null) {
                        startActivity(new Intent(getActivity(), com.mytv365.view.activity.user.LoginActivity.class));
                        return;
                    }
                    if (teacherService.isAllowBuy()) {
                        dialogSer.setIsCancelable(false);
                        dialogSer.initTile("服务购买", "立即购买", new MyOnClick(2));
                        dialogSer.showDialog();
                    } else {
                        String ph = buy.getText().toString().trim();
                        ph = ph.replace("-", "");
                        ToolAlert.toastShort(ph);
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ph));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    break;
                case 2://购买
                    // TODO: 2016/9/18  购买服务 1：验证，2：购买
                    UserServer.buyServiceVerify(getActivity(), new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            dialog = ToolAlert.dialog(mContext, R.layout.public_dialog_load);
                            dialog.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            dialog.hide();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            try {
                                JSONObject jsonObjet = new JSONObject(responseString);
                                if (jsonObjet.getString("type").equals("1")) {
                                    if (jsonObjet.getJSONObject("obj").getInt("resultCode") == 7) {
                                        dialog.hide();
                                        Intent intent = new Intent(mContext, com.mytv365.view.activity.teacher.TeacherServerActivity.class);
                                        intent.putExtra("id", teacherService.getId());
                                        mContext.startActivity(intent);
                                        return;
                                    }
                                    JSONObject obj = jsonObjet.getJSONObject("obj");
                                    ToolAlert.toastShort(obj.getString("resultMsg"));
                                    int resultCode = obj.getInt("resultCode");
                                    if (resultCode == 1) {
                                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                        boolean agreeClause = preferences.getBoolean("agree_clause", false);
                                        if (!agreeClause) {
                                            getOperation().forward(com.mytv365.view.activity.AgreementActivty.class);
                                        } else {
                                            //直接购买
                                            orderSend();
                                        }
                                    }
                                } else {
                                    ToolAlert.toastShort(jsonObjet.getString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                dialog.hide();
                            }
                        }
                    }, teacherService.getId(), teacherService.getTeacherId(), Constant.userinfo.getUserid(), Constant.userinfo.getToken());
                    break;
            }
        }
    }

    private void orderSend() {
        UserServer.orderSend(getActivity(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dialog.hide();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                dialog.hide();
                try {
                    JSONObject jsonObjet = new JSONObject(responseString);
                    if (jsonObjet.getString("msg").equals("success")) {
                        String orderid = jsonObjet.getString("orderid");
                        wxpay(orderid);
//                        ToolAlert.toastShort(orderid);
                    } else {
                        ToolAlert.toastShort("提交订单失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, teacherService.getRoomPrice(), Constant.userinfo.getUserid(), Constant.userinfo.getEmail(), teacherService.getDescription(), "WAP", teacherService.getId());
    }

    private void wxpay(String orderid) {
        UserServer.wxpay(getActivity(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (!TextUtils.isEmpty(responseString)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(responseString));
                    startActivity(intent);
                }
            }
        }, orderid);
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
}