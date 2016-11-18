package com.xiaoduotech.xiaoduosdkdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;

import com.xiaoduotech.cvd_sdk.R;
import com.xiaoduotech.sdk.cvdframework.CVDManager;
import com.xiaoduotech.sdk.cvdframework.CVDUIConfig;
import com.xiaoduotech.sdk.presentation.CVDConversationActivity;
import com.xiaoduotech.sdk.widget.TitlePosition;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btn;
    @BindView(R.id.switch_btn)
    SwitchCompat switchBtn;
    private CVDUIConfig config;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        config = new CVDUIConfig(this);
        config.clear();
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    configUI();
                    btn.setBackgroundResource(R.drawable.selector_btn);
                } else {
                    config.clear();
                    btn.setBackgroundResource(R.drawable.selector_btn_primary);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configuserInfo();
                navi2conversation();
            }
        });

    }


    private void configuserInfo() {
        CVDManager.getInstance().configUserInfo("2",
                "Kenny",
                "http://www.zzxu.cn/pic.asp?url=http://www.qqw21.com/article/uploadpic/2012-8/2012826225054894.jpg",
                "成都",
                "商品"

        );
    }

    private void navi2conversation() {
        Intent intent = new Intent(LoginActivity.this, CVDConversationActivity.class);
        startActivity(intent);
    }

    private void configUI() {
        //statusbar
        config.setCVDStatusBarColor(getResources().getColor(R.color.color_material_cyan))
                //toolbar
                .setCVDtoolbarBackgroundColor(getResources().getColor(R.color.color_material_cyan))
                .setCVDtoolbarButtonDrawable(R.drawable.ic_account_circle_white_18dp)
                .setCVDtoolbarTitlePosition(TitlePosition.POSITION_LEFT)
                .setCVDtoolbarTitleSize(18)
                .setCVDtoolbarTitleColor(Color.WHITE)
                //sendmessagebutton
                .setCVDSendMessageButtonBackGroundSelector(R.drawable.selector_btn)
                .setCVDSendMessageButtonTextColorSelector(R.color.text_color_grey2anything)
                .setCVDSendMessageButtonTextSize(14)
                //ohters bubble
                .setCVDBubbleTextSize(14)
                .setCVDOtherBubbleDrawable(R.drawable.bubble_gray)
//                .setCVDOthersBubbleBackgroundColor(Color.GREEN)
                .setCVDOthersBubbleTextColor(Color.BLACK)
                //self bubble
                .setCVDSelfBubbleDrawable(R.drawable.bubble_blue)
//                .setCVDSelfBubbleBackgroundColor(Color.YELLOW)
                .setCVDSelfBubbleTextColor(Color.WHITE)
                // main background
                .setCVDChatMainBackgroundColor(Color.DKGRAY)
                .setCVDChatMainBackgroundDrawableRes(R.drawable.gradient_drawable)

        ;
    }
}

