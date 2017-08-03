package com.hy.vrfrog.http;

import org.xutils.common.Callback;

/**
 * Created by m1762 on 2017/6/12.
 */

public abstract class JsonCallBack implements Callback.CommonCallback<String> {
    @Override
    public abstract void onSuccess(String result);

    @Override
    public abstract void onError(Throwable ex, boolean isOnCallback);

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
