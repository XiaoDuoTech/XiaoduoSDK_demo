#晓多智能客服平台 SDK for Android 使用指南
----

>[TOC]


----
**注意：**
- SDK 支持android sdk  14－24
- 目前只支持android studio，后续版本将支持eclipse
<h1 id="config"></h1>
##1 Android Studio 配置


使用`Android studio`的开发者只需在app module下的`build.gradle`中添加依赖:
```groovy
  dependencies{
  compile 'com.android.support:appcompat-v7:＋' //可以将‘＋’换成你的版本号
  compile 'com.xiaoduoteck.xiaoduosdk:xiaoduosdk:0.1.3@aar'
  }
```

SDK 需要如下权限，如果缺失，需要在`AndroidManifest`中添加权限:

```xml
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.CAMERA" />
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
<h1 id="init"></h1>
##2 初始化

在使用**CVD SDK**前需要对SKD初始化，如果你继承了`Application`，需要在你的 Application中初始化SDK：

```java
public class BaseApplication extends Application{

    ... 
   @Override
   public void onCreate() {
        super.onCreate();
        //3个参数中src 和 appkey 为必传参数，在CVD官网注册后可获取src 和 appkey。
        CVDManager.getInstance(this).init(src,channelId,appKey);

   }
    ...
}
   
```

**参数说明**
|参数 |说明|
|:--:|:--:|
|src|由管理台分发,在注册时获取|
|channelId| 细分渠道号,用户自定义|
|appKey|由管理台分发,在注册时获取|
**方法原型**
```java
public void init(@NonNull String src, @NonNull int channelId, @NonNull String appKey)
```

##3 启动对话界面
开发者可以不进行任何二次开发直接使用**CVD SDK**会话界面，在完成 [配置](#config) 、[初始化](#init) 后可以直接跳转SDK的对话界面，自动接入客服会话。
###3.1 配置用户信息
在跳转之前传入用户信息，用户信息将会在后台传给客服端，供客服人员查看。其中**昵称**和**头像**会在会话中显示。
```java
CVDManager.getInstance(this).configUserInfo(tid,
                nickname,
                avatar,
                position,
                title

        );
```
**参数说明**
|参数 |说明|
|:--:|:--:|
|tid|  用户tid,用户自定义|
|nickName| 用户昵称,用户自定义|
|faceUrl|用户头像链接,用户自定义|
|position|用户地理位置,用户自定义|
|title|当前会话主题,用户自定义|
**方法原型**
```java
    public void configUserInfo(String tid, String nickName, String faceUrl, String position, String title)
```


###3.2跳转对话界面
用户信息配置后即可跳转对话界面：

```java
               ...
                Intent intent = new Intent(LoginActivity.this, CVDConversationActivity.class);
                startActivity(intent);
               ...
           
```
##4 自定义UI配置
在进入对话界面前可以进行自定义UI配置，CVD SDK 提供了丰富的UI配置接口，可以让开发者最大限度免于二次开发。
``` java

...  
private CVDUIConfig config;
...

...
  config =  new CVDUIConfig(this);
...
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
```


**方法说明**

-  `ICVDConfig setCVDStatusBarColor(@ColorInt int cvdStatusBarColor);`    设置 *statusbar* 颜色
- `ICVDConfig setCVDtoolbarBackgroundColor(@ColorInt int cvDtoolbarBackgroundColor); `   设置 *toolbar* 背景颜色
-  `ICVDConfig setCVDtoolbarTittlePosition(@CVDCustomActionbar.TittlePosition int position); ` 设置 *toolbar* 标题位置 
-  `ICVDConfig setCVDtoolbarTittleColor(@ColorInt int cvDtoolbarTittleColor); ` 设置 *toolbar* 标题 颜色
- `ICVDConfig setCVDtoolbarTittleSize(int cvDtoolbarTittleSize);`   设置 *toolbar*  标题字号
- ` ICVDConfig setCVDtoolbarButtonDrawable(@DrawableRes int cvDtoollbarButtonDrawable);`  设置  *toolbar* 返回键图片

- ` ICVDConfig setCVDOthersBubbleBackgroundColor(@ColorInt int cvdOthersBubbleBackgroundColor); `设置客服聊天气泡颜色

- ` ICVDConfig setCVDBubbleTextSize(@ColorInt int cvdBubbleTextSize); `设置聊天字体大小

- `ICVDConfig setCVDSelfBubbleBackgroundColor(@ColorInt int cvdSelfBubbleBackgroundColor);`设置用户聊天气泡颜色

- ` ICVDConfig setCVDLeftAvatarSizeDp( int cvdLeftAvatarSize);` 设置客服头像大小

- `ICVDConfig setCVDRightAvatarSizeDp( int cvdRightAvatarSize);` 设置用户头像大小
- `ICVDConfig setCVDSelfBubbleTextColor(@ColorInt int cvdSelfBubbleTextColor);`设置用户聊天字体颜色

- ` ICVDConfig setCVDOthersBubbleTextColor(@ColorInt int cvdOthersBubbleTextColor); `设置客服聊天字体颜色

- `ICVDConfig setCVDChatMainBackgroundColor(@ColorInt int cvdChatMainBackgroundColor);`设置聊天页背景颜色

- ` ICVDConfig setCVDChatMainBackgroundDrawableRes(@DrawableRes int cvdChatMainBackgroundDrawableRes);`设置聊天页背景图片

- `ICVDConfig setCVDSelfBubbleDrawable(@DrawableRes int cvdSelfBubbleDrawable); `   设置设置用户聊天气泡图片，必须用.9的图片

- ` ICVDConfig setCVDOtherBubbleDrawable(@DrawableRes int cvdOtherBubbleDrawable); `设置客服聊天气泡图片，必须用.9的图片

- `ICVDConfig setCVDSendMessageButtonBackGroundSelector(@DrawableRes int cvdSendMessageButtonBackGroundSelector);`设置发送消息按钮背景selector

- `ICVDConfig setCVDSendMessageButtonTextColorSelector(@ColorRes int cvdSendMessageButtonTextColorSelector);` 设置发送消息按钮文字的颜色selector


- ` ICVDConfig setCVDSendMessageButtonTextSize(int cvdSendMessageButtonTextSize);` 设置发送消息按钮文字大小

##5 Proguard 
在 module 的 `proguard-rules.pro` 中添加：
```xml
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

-keep class tencent.**{*;}
-dontwarn tencent.**

-keep class com.qq.**{*;}
-dontwarn com.qq.**

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**

-keep public class org.jsoup.** {public *;}
```

