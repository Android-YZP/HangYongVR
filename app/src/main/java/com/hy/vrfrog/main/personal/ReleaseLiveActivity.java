package com.hy.vrfrog.main.personal;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hy.vrfrog.R;
import com.hy.vrfrog.http.HttpURL;
import com.hy.vrfrog.http.JsonCallBack;
import com.hy.vrfrog.http.responsebean.CreateHouseBean;
import com.hy.vrfrog.http.responsebean.CreateLiveRoom;
import com.hy.vrfrog.http.responsebean.PictureBean;
import com.hy.vrfrog.main.living.push.PushActivity;
import com.hy.vrfrog.utils.ImageUtil;
import com.hy.vrfrog.utils.SPUtil;
import com.hy.vrfrog.utils.UIUtils;
import com.hy.vrfrog.videoDetails.VedioContants;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qwe on 2017/8/15.
 */

public class ReleaseLiveActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener
        , PersonalContract.View {

    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CROP_PHOTO = 3;

    private ImageView mBack;
    private RadioGroup mVideoRg;
    private RadioGroup mChargeRg;
    private EditText mHouseNameEdt;
    private EditText mMoneyEdt;
    private Button mReleaseBtn;
    private String isTranscribe = String.valueOf(1);
    private String isCharge = String.valueOf(1);
    private String mHouseName;
    private String mMoney;
    private ImageView mCover;
    PersonalPresenter mPresenter;
    //动态获取权限监听
    private static IPermissionListener mListener;

    private String cachPath;

    private File cacheFile;
    private File cameraFile;
    private Uri imageUri;
    private File mFrontPhoto;

    private RadioButton mVideoRbYes;
    private RadioButton mVideoRbNo;

    private RadioButton mChargeRbYes;
    private RadioButton mChargeRbNo;

    private int mid;
    private boolean isChoosed = false;

    private RelativeLayout mLayout;

    private CheckBox mAgreeCb;
    private ImageView mBackGroundImg;
    private TextView mBackGroundTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_release_live);
        initView();
        initListener();
        mPresenter.createHouseData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isChoosed = false;
    }

    private void initView() {

        cachPath= getDiskCacheDir(this)+ "/certificate.jpg";//图片路径
        cacheFile = getCacheFile(new File(getDiskCacheDir(this)),"certificate.jpg");

        mBack = (ImageView)findViewById(R.id.img_release_live_return);
        mHouseNameEdt = (EditText)findViewById(R.id.edt_release_live_house_name);
        mMoneyEdt = (EditText)findViewById(R.id.edt_release_live_money);
        mReleaseBtn = (Button)findViewById(R.id.btn_release_click);
        mVideoRg = (RadioGroup)findViewById(R.id.rg_release_live_video);
        mVideoRbYes = (RadioButton)findViewById(R.id.rb_release_live_video_save);
        mVideoRbNo = (RadioButton)findViewById(R.id.rb_release_live_video_not_save);

        mChargeRg = (RadioGroup)findViewById(R.id.rg_release_live_charge);
        mChargeRbYes = (RadioButton)findViewById(R.id.rb_release_live_charge_save);
        mChargeRbNo = (RadioButton)findViewById(R.id.rb_release_live_charge_not_save);

        mCover = (ImageView)findViewById(R.id.img_release_cover);

        mLayout = (RelativeLayout)findViewById(R.id.rl_release_live_money);
        mAgreeCb = (CheckBox)findViewById(R.id.cb_agree);

        mBackGroundImg = (ImageView)findViewById(R.id.img_background);
        mBackGroundTv = (TextView)findViewById(R.id.tv_background);

        new PersonalPresenter(this);

    }

    private void initListener() {

        mBack.setOnClickListener(this);
        mVideoRg.setOnCheckedChangeListener(this);
        mChargeRg.setOnCheckedChangeListener(this);
        mReleaseBtn.setOnClickListener(this);
        mCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.showPhotoDialog(ReleaseLiveActivity.this);
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_release_live_return:
                finish();
                break;
            case R.id.btn_release_click:
//                Intent intent = new Intent(ReleaseLiveActivity.this, PushActivity.class);//测试数据
//                startActivity(intent);
//
                if (TextUtils.isEmpty(mHouseNameEdt.getText().toString())) {
                    UIUtils.showTip("房间名称不能为空");
                    return;
                }

                if (TextUtils.isEmpty(mMoneyEdt.getText().toString()) ){
                    UIUtils.showTip("收费金额不能为空");
                    return;
                }

                if (mAgreeCb.isChecked()) {
                    UIUtils.showTip("请选择我已阅读并同意直播协议");
                    return;
                }


                if (SPUtil.getUser() != null) {
                    mPresenter.getHttpEditRoom(String.valueOf(SPUtil.getUser().getResult().getUser().getUid()), isTranscribe, mHouseNameEdt.getText().toString(), isCharge, mMoneyEdt.getText().toString(), "");
                }

                break;

        }

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        if (i == R.id.rb_release_live_video_save) {

            isTranscribe = String.valueOf(1);

        } else if (i == R.id.rb_release_live_video_not_save) {

            isTranscribe = String.valueOf(0);

        } else if (i == R.id.rb_release_live_charge_save) {

            isCharge = String.valueOf(1);
            mLayout.setVisibility(View.VISIBLE);

        } else if (i == R.id.rb_release_live_charge_not_save) {
            isCharge = String.valueOf(0);
            mLayout.setVisibility(View.GONE);
            mMoneyEdt.setText(String.valueOf(0));
        }

    }


    @Override
    public void setPresenter(PersonalContract.Presenter presenter) {
        this.mPresenter = (PersonalPresenter) presenter;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showTakePhotoForAlbum() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new IPermissionListener() {
            @Override
            public void onGranted() {

                mPresenter.openAlbum();

            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    @Override
    public void showWakePhotoForCamera() {
        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestRuntimePermission(permissions, new IPermissionListener() {
            @Override
            public void onGranted() {
                mPresenter.openCamera();
            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }

    @Override
    public void openCamera() {
        cameraFile = getCacheFile(new File(getDiskCacheDir(this)), "output_image.jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            imageUri = Uri.fromFile(cameraFile);
        } else {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = FileProvider.getUriForFile(ReleaseLiveActivity.this, "com.hy.vrfrog.fileprovider", cameraFile);
        }
        // 启动相机程序
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void showId(int id, CreateLiveRoom createLiveRoom) {
        LogUtil.i("id = " + id);
        this.mid = id;
        if (mFrontPhoto == null && isChoosed) {//从网络获取了原来的图片
            Intent intent = new Intent(ReleaseLiveActivity.this, PushActivity.class);
            intent.putExtra(VedioContants.LivingPushUrl, createLiveRoom.getResult().getUpstreamAddress());
            intent.putExtra(VedioContants.ChannelId, createLiveRoom.getResult().getChannelId());
            intent.putExtra(VedioContants.ChannelName, createLiveRoom.getResult().getChannelName());
            intent.putExtra(VedioContants.GroupID, (String) createLiveRoom.getResult().getAlipay());
            intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST + createLiveRoom.getResult().getImg());
            startActivity(intent);
            ReleaseLiveActivity.this.finish();
        } else {
            uploadCertificaton(mFrontPhoto, mid, createLiveRoom);
        }

    }

    @Override
    public void showCreateHouseData(CreateHouseBean createHouseBean) {
        if (createHouseBean.getCode() == 0) {
            if (createHouseBean.getResult().getChannelName() != null) {
                mHouseNameEdt.setText(createHouseBean.getResult().getChannelName());
            }

            if (createHouseBean.getResult().getPrice() != 0) {
                mMoneyEdt.setText(String.valueOf(createHouseBean.getResult().getPrice()));
            }

            if (createHouseBean.getResult().getImg() != null) {
                Glide.with(ReleaseLiveActivity.this).load(HttpURL.IV_PERSON_HOST + createHouseBean.getResult().getImg()).asBitmap().into(mCover);
                mBackGroundImg.setVisibility(View.GONE);
                mBackGroundTv.setVisibility(View.GONE);
                isChoosed = true;
            }

            if (createHouseBean.getResult().getIsTranscribe() == 1) {
                mVideoRbYes.setChecked(true);
                isTranscribe = String.valueOf(1);

            } else {
                mVideoRbNo.setChecked(true);
                isTranscribe = String.valueOf(0);
            }

            if ((Integer) createHouseBean.getResult().getIsCharge() == 1) {
                mChargeRbYes.setChecked(true);
                isCharge = String.valueOf(1);

            } else {
                mChargeRbNo.setChecked(true);
                isCharge = String.valueOf(0);
                mMoneyEdt.setText(String.valueOf(0));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        startPhotoZoom(cameraFile, 350);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            case CROP_PHOTO:
                try {
                    if (resultCode == RESULT_OK) {

                        mFrontPhoto = new File(cachPath);
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(new File(cachPath))));
                        mCover.setImageBitmap(bitmap);
                        mBackGroundImg.setVisibility(View.GONE);
                        mBackGroundTv.setVisibility(View.GONE);
                        isChoosed = true;//已经选择图片

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void uploadCertificaton(File frontPhoto, int id, final CreateLiveRoom createLiveRoom) {

        RequestParams requestParams = new RequestParams(HttpURL.UpdatePersonLvbImg);
        requestParams.addHeader("token", HttpURL.Token);

        //包装请求参数
        requestParams.addBodyParameter("file", frontPhoto);//用户名
        requestParams.addBodyParameter("id", id + "");//用户名
        LogUtil.i(frontPhoto.getPath() + "----------");
        //获取数据
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        requestParams.setMultipart(true);
        x.http().post(requestParams, new JsonCallBack() {

            @Override
            public void onSuccess(String result) {
                LogUtil.i("上传证件 =" + result);
                PictureBean pictureBean = new Gson().fromJson(result, PictureBean.class);
                if (pictureBean.getCode() == 0) {
                    UIUtils.showTip("上传成功");
                    Intent intent = new Intent(ReleaseLiveActivity.this, PushActivity.class);
                    intent.putExtra(VedioContants.LivingPushUrl, createLiveRoom.getResult().getUpstreamAddress());
                    intent.putExtra(VedioContants.ChannelId, createLiveRoom.getResult().getChannelId());
                    intent.putExtra(VedioContants.ChannelName, createLiveRoom.getResult().getChannelName());
                    intent.putExtra(VedioContants.GroupID, (String) createLiveRoom.getResult().getAlipay());
                    intent.putExtra(VedioContants.RoomImg, HttpURL.IV_PERSON_HOST + createLiveRoom.getResult().getImg());
                    startActivity(intent);
                    ReleaseLiveActivity.this.finish();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                UIUtils.showTip("网络异常");
            }

            @Override
            public void onFinished() {
                super.onFinished();
                UIUtils.showTip("ex.getMessage()");
            }
        });


    }


    private File getCacheFile(File parent, String child) {
        // 创建File对象，用于存储拍照后的图片
        File file = new File(parent, child);

        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        String imagePath = uriToPath(uri);
//        displayImage(imagePath); // 根据图片路径显示图片

        Log.i("TAG", "file://" + imagePath + "选择图片的URI" + uri);
        startPhotoZoom(new File(imagePath), 350);
    }

    private String uriToPath(Uri uri) {
        String path = null;
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                path = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            path = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            path = uri.getPath();
        }
        return path;
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
//        displayImage(imagePath);
        Log.i("TAG", "file://" + imagePath + "选择图片的URI" + uri);
        startPhotoZoom(new File(imagePath), 350);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 将图片存到本地
     * 获得本地图片路径
     * @param context
     * @return
     */
    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 剪裁图片
     */
    private void startPhotoZoom(File file, int size) {
        Log.i("TAG", getImageContentUri(this, file) + "裁剪照片的真实地址");
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(getImageContentUri(this, file), "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra("scale", true);
            intent.putExtra("return-data", false);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1.6);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cacheFile));//定义输出的File Uri
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            startActivityForResult(intent, CROP_PHOTO);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化地址为content开头
     * @param context
     * @param imageFile
     * @return
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    List<String> deniedPermissions = new ArrayList<>();
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()) {
                        mListener.onGranted();
                    } else {
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void requestRuntimePermission(String[] permissions, IPermissionListener listener) {

        mListener = listener;
        List<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(ReleaseLiveActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }
        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(ReleaseLiveActivity.this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {
            mListener.onGranted();
        }
    }

    public interface IPermissionListener {
        /**
         * 成功获取权限
         */
        void onGranted();

        /**
         * 为获取权限
         * @param deniedPermission
         */
        void onDenied(List<String> deniedPermission);

    }
}
