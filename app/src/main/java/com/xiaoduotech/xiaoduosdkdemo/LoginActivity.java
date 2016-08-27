package com.xiaoduotech.xiaoduosdkdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.xiaoduotech.cvd_sdk.R;
import com.xiaoduotech.sdk.cvdframework.CVDUIConfig;
import com.xiaoduotech.sdk.cvdsdk.CVDManager;
import com.xiaoduotech.sdk.widget.CVDCustomActionbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.switch_btn)
    SwitchCompat switchBtn;
    private CVDUIConfig config;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        configuserInfo();
        config =  new CVDUIConfig(this);
        config.clear();
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    configUI();
                    btn.setBackgroundResource(R.drawable.selector_btn);
                }else {
                    config.clear();
                    btn.setBackgroundResource(R.drawable.selector_btn_primary);
                }
            }
        });

        navi2conversation();


    }

    private void configuserInfo() {
        CVDManager.getInstance(this).configUserInfo("",
                "小美",
                "http://p3.wmpic.me/article/2015/03/16/1426483394_eJakzHWr.jpeg",
                "成都",
                "商品"

        );
    }

    private void navi2conversation() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MyConversationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configUI() {

                //statusbar
                config.setCVDStatusBarColor(getResources().getColor(R.color.color_material_cyan))
                //toolbar
                .setCVDtoolbarBackgroundColor(getResources().getColor(R.color.color_material_cyan))
                .setCVDtoolbarButtonDrawable(R.drawable.ic_account_circle_white_18dp)
                .setCVDtoolbarTittlePosition(CVDCustomActionbar.POSITION_LEFT)
                .setCVDtoolbarTittleSize(18)
                .setCVDtoolbarTittleColor(Color.WHITE)
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

