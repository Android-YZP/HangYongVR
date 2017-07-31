package com.jt.base.personinformation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.jt.base.R;

/**
 * Created by qwe on 2017/7/31.
 */

public class UploadingDocumentsActivity extends Activity{

    private ImageButton mBack;
    private RelativeLayout mUploadIdentityCardFront;
    private RelativeLayout mUploadIdentityCardBack;
    private Button uploadSummitBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploading_documents);
        mBack = (ImageButton)findViewById(R.id.ib_upload_back);
        mUploadIdentityCardFront = (RelativeLayout)findViewById(R.id.rl_upload_identity_card_front);
        mUploadIdentityCardBack = (RelativeLayout)findViewById(R.id.rl_upload_identity_card_back);
        uploadSummitBtn = (Button)findViewById(R.id.btn_upload_identity_summit);

    }
}
