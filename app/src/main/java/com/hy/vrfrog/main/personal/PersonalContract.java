package com.hy.vrfrog.main.personal;

import android.content.Context;

import com.hy.vrfrog.base.BasePresenter;
import com.hy.vrfrog.base.BaseView;
import com.hy.vrfrog.http.responsebean.CreateHouseBean;
import com.hy.vrfrog.http.responsebean.CreateLiveRoom;

import java.util.List;

/**
 * Created by qwe on 2017/8/16.
 */

public class PersonalContract {

    public interface View extends BaseView<Presenter> {

        void showTakePhotoForAlbum();

        void showWakePhotoForCamera();

        void openCamera();

        void openAlbum();

        void showId(int id,CreateLiveRoom createLiveRoom);

        void showCreateHouseData(CreateHouseBean createHouseBean);

        void goLogin();


    }

    interface Presenter extends BasePresenter {

        void getHttpEditRoom(String uid, String isTranscribe, String channelName, String isCharge, String price, String introduce);

        void showPhotoDialog(Context context);

        void openCamera();

        void openAlbum();

        void createHouseData();

    }


}
