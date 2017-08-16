package com.hy.vrfrog.drone.vrpersonrepository;

import com.hy.vrfrog.drone.IVrPersonData;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.utils.LongLogUtil;
import com.hy.vrfrog.utils.NetUtil;
import com.hy.vrfrog.utils.UIUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by ZhengShiCheng on 2017/5/25.
 */
public class VrPersonRepository implements IVrPersonData {

    private static VrPersonRepository mInstance;

    public static VrPersonRepository newInstance() {
        synchronized(VrPersonRepository.class) {
            if (mInstance == null) {
                mInstance = new VrPersonRepository();
            }
        }
        return mInstance;
    }

    @Override
    public void getHttpEditRoom(String uid, String isTranscribe, String channelName, String isCharge, String price, String introduce) {
        if (!NetUtil.isOpenNetwork()) {
            UIUtils.showTip("请打开网络");
            return;
        }
        //使用xutils3访问网络并获取返回值
        RequestParams requestParams = new RequestParams(HttpURL.editRoom);
        requestParams.addHeader("token", HttpURL.Token);
        requestParams.addBodyParameter("uid", uid);
        requestParams.addBodyParameter("isTranscribe", isTranscribe);
        requestParams.addBodyParameter("channelName", channelName);
        requestParams.addBodyParameter("isCharge", isCharge);
        requestParams.addBodyParameter("price", price);
        requestParams.addBodyParameter("introduce", introduce);

        //获取数据
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LongLogUtil.e("-----------", result);

//                Intent intent = new Intent(PushSettingActivity.this, PushActivity.class);
//                String PushUrl = "rtmp://9250.livepush.myqcloud.com/live/9250_e75874b6?bizid=9250&txSecret=24c3c5841d23c03031f03a10c8149f18&txTime=59931A7F";
//                intent.putExtra(TCConstants.PUBLISH_URL, PushUrl);
//                startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onFinished() {
                super.onFinished();
            }
        });
    }
}
