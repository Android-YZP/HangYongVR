package com.jt.base.login;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.jt.base.R;
import com.jt.base.http.HttpURL;
import com.jt.base.http.JsonCallBack;
import com.jt.base.login.takepic.PermissionsActivity;
import com.jt.base.login.takepic.PermissionsChecker;
import com.jt.base.login.takepic.PhotoUtil;
import com.jt.base.utils.UIUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static android.os.Environment.getExternalStorageState;
import static com.jt.base.login.takepic.PhotoUtil.compressScale;

public class PersonalActivity extends AppCompatActivity {
    private ImageView iv;
    private Button take_btn, album_btn;

    private PermissionsChecker mPermissionsChecker; // 权限检测器
    private static final int REQUEST_PERMISSION = 4;  //权限请求
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private static final int PHOTO_REQUEST_CAMERA = 0;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 1;// 从相册中选择
    static final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    private PhotoUtil photoUtil;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_personal);



        }


//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //检查权限(6.0以上做权限判断)
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//                        startPermissionsActivity();
//                    } else {
//                        //打开相机
//                        imageUri = photoUtil.takePhoto(PHOTO_REQUEST_CAMERA);
//                    }
//                } else {
//                    imageUri = photoUtil.takePhoto(PHOTO_REQUEST_CAMERA);
//                }
//            }
//        });
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
//                        startPermissionsActivity();
//                    } else {
//                        //打开相册
//                        photoUtil.callPhoto(PHOTO_REQUEST_GALLERY);
//                    }
//                } else {
//                    photoUtil.callPhoto(PHOTO_REQUEST_GALLERY);
//                }
//            }
//        });
//        iv = (ImageView) findViewById(R.id.imageView);
//        mPermissionsChecker = new PermissionsChecker(this);
//        photoUtil = new PhotoUtil(this);
//
//    }


//    /**
//     * 剪切图片
//     */
//    private void crop(Uri uri) {
//        // 裁剪图片意图
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        // 裁剪框的比例，1：1
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // 裁剪后输出图片的尺寸大小
//        intent.putExtra("outputX", 250);
//        intent.putExtra("outputY", 250);
//        // 图片格式
//        intent.putExtra("outputFormat", "JPEG");
//        intent.putExtra("noFaceDetection", true);// 取消人脸识别
//        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
//        startActivityForResult(intent, PHOTO_REQUEST_CUT);
//    }
//
//
//    /**
//     * 更新头像
//     */
//    private void HttpUpdatePic(File file) {
//        //使用xutils3访问网络并获取返回值
//        RequestParams requestParams = new RequestParams(HttpURL.UpdateHeadPic);
//        requestParams.addHeader("token", HttpURL.Token);
//        //包装请求参数
//        requestParams.addBodyParameter(file.getPath().replace("/", ""), file);//用户名
//        //获取数据
//        // 有上传文件时使用multipart表单, 否则上传原始文件流.
//        requestParams.setMultipart(true);
//        x.http().post(requestParams, new JsonCallBack() {
//
//            @Override
//            public void onSuccess(String result) {
//                LogUtil.i(result);
//
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                UIUtils.showTip(ex.getMessage());
//            }
//        });
//    }
//
//    /**
//     * 更新用户信息
//     */
//    private void HttpUpdateUserInfo(File file) {
//        //使用xutils3访问网络并获取返回值
//        RequestParams requestParams = new RequestParams(HttpURL.UpdateUserInfo);
//        requestParams.addHeader("token", HttpURL.Token);
//        //包装请求参数
//        requestParams.addBodyParameter(file.getPath().replace("/", ""), file);//用户名
//        //获取数据
//        // 有上传文件时使用multipart表单, 否则上传原始文件流.
//        requestParams.setMultipart(true);
//        x.http().post(requestParams, new JsonCallBack() {
//
//            @Override
//            public void onSuccess(String result) {
//                LogUtil.i(result);
//
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                UIUtils.showTip(ex.getMessage());
//            }
//        });
//
//
//    }
//
//
//    /**
//     * 保存文件
//     *
//     * @param bm
//     * @throws IOException
//     */
//    public File saveFile(Bitmap bm) throws IOException {
//        File path = Environment.getExternalStorageDirectory() ;
//        File dirFile = new File(path.toString());
//        if (!dirFile.exists()) {
//            dirFile.mkdir();
//        }
//        File myCaptureFile = new File(path + "/head.jpg");
//        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//        bos.flush();
//        bos.close();
//        return myCaptureFile;
//    }
//
//    private void startPermissionsActivity() {
//        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSION,
//                PERMISSIONS);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Bitmap bitmap;
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PHOTO_REQUEST_CAMERA) {
//
//            try {
//                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                // 得到图片的全路径
//                crop(uri);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        } else if (requestCode == PHOTO_REQUEST_GALLERY) {
//            //从相册选择
//
//            if (data != null) {
//                // 得到图片的全路径
//                Uri uri = data.getData();
//                crop(uri);
//            }
//        } else if (requestCode == PHOTO_REQUEST_CUT) {
//            try {
//                bitmap = data.getParcelableExtra("data");
//                iv.setImageBitmap(bitmap);
//
//                //上传图片
//                HttpUpdatePic(saveFile(bitmap));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    }
}
