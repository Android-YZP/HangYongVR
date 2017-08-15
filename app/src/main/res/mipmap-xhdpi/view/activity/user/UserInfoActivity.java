package com.mytv365.view.activity.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fhrj.library.base.impl.BaseActivity;
import com.fhrj.library.third.asynchttp.TextHttpResponseHandler;
import com.fhrj.library.third.universalimageloader.core.ImageLoader;
import com.fhrj.library.tools.ToolAlert;
import com.fhrj.library.tools.ToolImage;
import com.fhrj.library.tools.ToolLog;
import com.fhrj.library.tools.ToolToast;
import com.fhrj.library.view.imageview.RoundImageView;
import com.mytv365.R;
import com.mytv365.common.Constant;
import com.mytv365.entity.Userinfo;
import com.mytv365.holder.PhotoSelectHolder;
import com.mytv365.http.UserServer;
import com.nineoldandroids.animation.ValueAnimator;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author   :hymanme
 * Email    :hymanme@163.com
 * Create at 2016/8/31
 * Description:
 */
public class UserInfoActivity extends BaseActivity implements View.OnClickListener {
    private static final int FLAG_ALBUM = 1;
    private static final int FLAG_CAMERA = 2;
    private PopupWindow mPopupWindow;
    private ValueAnimator animator;
    private RelativeLayout rl_avatar;
    private boolean isEditable;

    private RoundImageView iv_avatar;
    private EditText et_nickname_value;
    private Spinner sp_gender_value;
    private Spinner sp_age_value;
    private EditText et_address_value;
    private EditText et_job_value;
    private EditText et_education_value;
    private EditText et_qq_value;
    private EditText et_intro_value;
    private TextView tv_logout;
    private AlertDialog dialog;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isEditable = savedInstanceState.getBoolean("isEditable");
        }
        imageLoader = ToolImage.getImageLoader();
        super.onCreate(savedInstanceState);

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        iniTitle("个人资料");
        initRightDoneBtn("编辑", new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (!isEditable) {
                    setEditable();
                } else {
                    Userinfo userinfo = getUserInfo();
                    UserServer.userInfoUpdate(getContext(), new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            if (dialog == null) {
                                dialog = ToolAlert.dialog(com.mytv365.view.activity.user.UserInfoActivity.this, R.layout.public_dialog_load);
                            }
                            dialog.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            dialog.hide();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            dialog.hide();
                            setUnEditable();
                            Log.i("=====", responseString);
                            try {
                                JSONObject jsonobj = new JSONObject(responseString);
                                if (jsonobj.getString("type").equals("1")) {
                                    ToolAlert.toastShort("修改成功");
                                } else {
                                    ToolAlert.toastShort(jsonobj.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, userinfo);
                }
            }
        });
        rl_avatar = (RelativeLayout) findViewById(R.id.rl_avatar);
        rl_avatar.setOnClickListener(this);
        iv_avatar = (RoundImageView) findViewById(R.id.iv_avatar);
        et_nickname_value = (EditText) findViewById(R.id.et_nickname_value);
        et_nickname_value.clearFocus();
        sp_gender_value = (Spinner) findViewById(R.id.sp_gender_value);
        String[] gender = getResources().getStringArray(R.array.gender);
        sp_gender_value.setAdapter(new ArrayAdapter<>(this, R.layout.sp_item_textview, gender));
        sp_age_value = (Spinner) findViewById(R.id.sp_age_value);
        et_address_value = (EditText) findViewById(R.id.et_address_value);
        et_job_value = (EditText) findViewById(R.id.et_job_value);
        et_education_value = (EditText) findViewById(R.id.et_education_value);
        et_qq_value = (EditText) findViewById(R.id.et_qq_value);
        et_intro_value = (EditText) findViewById(R.id.et_intro_value);
        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_logout.setOnClickListener(this);
        ArrayList<Integer> ages = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            ages.add(i);
        }
        sp_age_value.setAdapter(new ArrayAdapter<>(this, R.layout.sp_item_textview, ages));
        setUnEditable();
    }

    public Userinfo getUserInfo() {
        Userinfo userinfo = Constant.userinfo;
        userinfo.setNickname(et_nickname_value.getText().toString());
        userinfo.setGender(sp_gender_value.getSelectedItem().toString());
        userinfo.setAge(sp_age_value.getSelectedItem().toString());
        userinfo.setAddress(et_address_value.getText().toString());
        userinfo.setJob(et_job_value.getText().toString());
        userinfo.setEducation(et_education_value.getText().toString());
        userinfo.setQq(et_qq_value.getText().toString());
        userinfo.setAutograph(et_intro_value.getText().toString());
        return userinfo;
    }

    private void setEditable() {
        et_nickname_value.setEnabled(true);
        et_nickname_value.requestFocus();
        sp_gender_value.setEnabled(true);
        sp_age_value.setEnabled(true);
        et_address_value.setEnabled(true);
        et_job_value.setEnabled(true);
        et_education_value.setEnabled(true);
        et_qq_value.setEnabled(true);
        et_intro_value.setEnabled(true);
        initRightDoneBtn("完成", null);
        isEditable = true;
    }

    private void setUnEditable() {
        et_nickname_value.setEnabled(false);
        sp_gender_value.setEnabled(false);
        sp_age_value.setEnabled(false);
        et_address_value.setEnabled(false);
        et_job_value.setEnabled(false);
        et_education_value.setEnabled(false);
        et_qq_value.setEnabled(false);
        et_intro_value.setEnabled(false);
        initRightDoneBtn("编辑", null);
        isEditable = false;
    }

    @Override
    public void doBusiness(Context mContext) {
        Userinfo userinfo = Constant.userinfo;
        if (userinfo != null) {
            if (!TextUtils.isEmpty(userinfo.getHeadimgurl())) {
                imageLoader.displayImage(userinfo.getHeadimgurl(), iv_avatar);
            }
            et_nickname_value.setText(userinfo.getNickname());
            switch (userinfo.getGender()) {
                case "男":
                    sp_gender_value.setSelection(1);
                    break;
                case "女":
                    sp_gender_value.setSelection(2);
                    break;
                case "未知":
                    sp_gender_value.setSelection(0);
                    break;
            }
            sp_age_value.setSelection(Integer.parseInt(userinfo.getAge()) - 1);
            et_address_value.setText(userinfo.getAddress());
            et_job_value.setText(userinfo.getJob());
            et_education_value.setText(userinfo.getEducation());
            et_qq_value.setText(userinfo.getQq());
            et_intro_value.setText(userinfo.getAutograph());
        }
    }

    private void iniTitle(String title) {
        setTintManager(R.color.touming);
        initBackTitleBar(title, Gravity.CENTER);
    }

    private void showPopWindow() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (mPopupWindow == null) {
            final PhotoSelectHolder holder = new PhotoSelectHolder(this, LayoutInflater.from(this).inflate(R.layout.pop_photo, null));
            mPopupWindow = new PopupWindow(holder.getContentView(),
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopupWindow.setFocusable(true);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    background1();
                }
            });
            holder.setOnCancelListener(new PhotoSelectHolder.onClickListener() {
                @Override
                public void onAlbum() {
                    Intent getAlbum = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(getAlbum, FLAG_ALBUM);
                }

                @Override
                public void onCamera() {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            Uri.fromFile(UserInfoActivity.this.getExternalCacheDir()));
                    startActivityForResult(intent, FLAG_CAMERA);
                }

                @Override
                public void onCancel() {
                    mPopupWindow.dismiss();
                }
            });
        }
        mPopupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
        background0();
        if (animator != null) {
            animator.start();
        }
    }

    private void background0() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
            lp.dimAmount = 0.7f; //0.0-1.0
            lp.alpha = 0.7f; //0.0-1.0
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            animator = ValueAnimator.ofFloat(1f, 0.7f);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lp.alpha = (float) animation.getAnimatedValue();
                    getWindow().setAttributes(lp);
                }
            });
        }
    }

    private void background1() {
        final WindowManager.LayoutParams lp = getWindow().getAttributes();
        if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
            lp.dimAmount = 1.0f; //0.0-1.0
            lp.alpha = 1.0f; //0.0-1.0
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            ValueAnimator animator = ValueAnimator.ofFloat(0.7f, 1f);
            animator.setDuration(500);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    lp.alpha = (float) animation.getAnimatedValue();
                    getWindow().setAttributes(lp);
                }
            });
            animator.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (isEditable) {
            setUnEditable();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //album
            mPopupWindow.dismiss();
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                if (cursor == null) {
                    ToolLog.e("Album", "选择图片失败");
                    return;
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                final String picturePath = cursor.getString(columnIndex);
                cursor.close();
                if (picturePath == null) {
                    ToolLog.e("Album", "选择图片失败");
                    return;
                }
                UserServer.updateUserAvatar(com.mytv365.view.activity.user.UserInfoActivity.this, new TextHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        if (dialog == null) {
                            dialog = ToolAlert.dialog(com.mytv365.view.activity.user.UserInfoActivity.this, R.layout.public_dialog_load);
                        }
                        dialog.show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        dialog.hide();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        dialog.hide();
                        Log.i("=====", responseString);
                        try {
                            JSONObject jsonobj = new JSONObject(responseString);
                            if (jsonobj.getString("type").equals("1")) {
                                ToolAlert.toastShort("上传成功");
                                imageLoader.displayImage("file://" + picturePath, iv_avatar);
                                ToolLog.i("path======", "file://" + picturePath);
                            } else {
                                ToolAlert.toastShort(jsonobj.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, Constant.userinfo.getUserid(), Constant.userinfo.getToken(), picturePath);
            } else {
                ToolLog.e("Album", "选择图片失败");
            }
        } else if (requestCode == 2) {
            //camera
            mPopupWindow.dismiss();
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras == null) {
                    ToolAlert.toastShort("上传失败");
                    return;
                }
                final Bitmap b = (Bitmap) extras.get("data");
                if (b == null) {
                    ToolAlert.toastShort("上传失败");
                    return;
                }
                String name = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                String fileNmae = Environment.getExternalStorageDirectory().toString() + File.separator + "dong/image/" + name + ".jpg";
                File myCaptureFile = new File(fileNmae);
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        if (!myCaptureFile.getParentFile().exists()) {
                            myCaptureFile.getParentFile().mkdirs();
                        }
                        BufferedOutputStream bos;
                        bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                        b.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                        bos.flush();
                        bos.close();
                        UserServer.updateUserAvatar(com.mytv365.view.activity.user.UserInfoActivity.this, new TextHttpResponseHandler() {
                            @Override
                            public void onStart() {
                                if (dialog == null) {
                                    dialog = ToolAlert.dialog(com.mytv365.view.activity.user.UserInfoActivity.this, R.layout.public_dialog_load);
                                }
                                dialog.show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                dialog.hide();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                dialog.hide();
                                Log.i("=====", responseString);
                                try {
                                    JSONObject jsonobj = new JSONObject(responseString);
                                    if (jsonobj.getString("type").equals("1")) {
                                        ToolAlert.toastShort("上传成功");
                                        iv_avatar.setImageBitmap(b);
                                    } else {
                                        ToolAlert.toastShort(jsonobj.getString("msg"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, Constant.userinfo.getUserid(), Constant.userinfo.getToken(), fileNmae);
                    } else {
                        ToolToast.showShort(com.mytv365.view.activity.user.UserInfoActivity.this, "保存失败，SD卡无效");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isEditable", isEditable);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rl_avatar) {
            showPopWindow();
        } else if (view.getId() == R.id.tv_logout) {
            AlertDialog logout = ToolAlert.dialog(this, "提示", "确定退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    UserServer.userLogout(com.mytv365.view.activity.user.UserInfoActivity.this, new TextHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            if (com.mytv365.view.activity.user.UserInfoActivity.this.dialog == null) {
                                com.mytv365.view.activity.user.UserInfoActivity.this.dialog = ToolAlert.dialog(com.mytv365.view.activity.user.UserInfoActivity.this, R.layout.public_dialog_load);
                            }
                            com.mytv365.view.activity.user.UserInfoActivity.this.dialog.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            com.mytv365.view.activity.user.UserInfoActivity.this.dialog.hide();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseString) {
                            com.mytv365.view.activity.user.UserInfoActivity.this.dialog.hide();
                            Log.i("=====", responseString);
                            try {
                                JSONObject jsonobj = new JSONObject(responseString);
                                if (jsonobj.getString("type").equals("1")) {
                                    ToolAlert.toastShort("退出成功");
                                    Constant.userinfo = null;
                                    finish();
                                } else {
                                    ToolAlert.toastShort(jsonobj.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, Constant.userinfo.getUserid(), Constant.userinfo.getToken());
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
    }
}
