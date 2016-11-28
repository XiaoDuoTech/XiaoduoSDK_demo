
                        
                    
<br>
<img width="300" src="http://xdfe.oss-cn-hangzhou.aliyuncs.com/cvd/android/71CDA0C2721C49CD23CA618E8E1754AF.png"><br>

<h2 id="android-接入">Android 接入</h2>
<hr>
<div><div class="toc"><div class="toc">
<ul>
<li><ul>
<li><a href="#android-接入">Android 接入</a></li>
</ul>
</li>
<li><ul>
<li><a href="#1-android-studio-配置">1 Android Studio 配置</a></li>
</ul>
</li>
<li><ul>
<li><a href="#2-初始化">2 初始化</a></li>
<li><a href="#3-启动对话界面">3 启动对话界面</a><ul>
<li><a href="#31-配置用户信息">3.1 配置用户信息</a></li>
<li><a href="#32跳转对话界面">3.2跳转对话界面</a></li>
</ul>
</li>
<li><a href="#4-自定义ui配置">4 自定义UI配置</a></li>
<li><a href="#5-proguard">5 Proguard</a></li>
<li><a href="#6自定义开发">6自定义开发</a><ul>
<li><a href="#61会话-cvdconversation">6.1会话 CVDConversation</a></li>
<li><a href="#62-消息">6.2 消息</a><ul>
<li><a href="#621-发送消息">6.2.1 发送消息</a></li>
</ul>
</li>
<li><a href="#63-事件">6.3 事件</a></li>
<li><a href="#64-用户资料">6.4 用户资料</a></li>
</ul>
</li>
</ul>
</li>
</ul>
</div>
</div>
</div>

<hr>




**注意：**
- SDK 为用户端app接入提供简洁的接入方案，无需二次开发即可接入第三方app，与客服进行通信。
- SDK 支持android sdk  16－25
- 接入使用前需到[管理台](http://cvd.xiaoduotech.com/admin/)注册企业账号, 并配置app sdk信息, 获取**src**，**channelId**，**appkey**, 使用[demo](https://github.com/XiaoDuoTech/XiaoduoSDK_demo)无需配置
- 由于sdk的Activity继承自AppCompatActivity, 所以需要appcompat-v7版本不低于v22.2.1

<h1 id="config"></h1>

##1 Android Studio 配置

使用`Android studio`的开发者只需在app module下的`build.gradle`中添加依赖:

```gradle
  dependencies{
  //必须
  compile 'org.greenrobot:eventbus:3.0.0'
  compile 'com.squareup.picasso:picasso:2.5.2'
  compile 'com.google.code.gson:gson:2.7'
  compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'
  compile 'com.android.support:appcompat-v7:23+' //不低于v22.2.1
  compile 'com.android.support:recyclerview-v7:23+'//不低于v22.2.1
  compile 'com.xiaoduotech.xiaoduosdk:sdk:0.2.7@aar'
  compile 'com.xiaoduotech.xiaoduosdk:armv7a:0.2.7@aar'
   //可选，如果你已经有这些对应jni目录，则必须依赖对应jni库
   compile 'com.xiaoduotech.xiaoduosdk:armv5:0.2.7@aar'
   compile 'com.xiaoduotech.xiaoduosdk:arm64:0.2.7@aar'
   compile 'com.xiaoduotech.xiaoduosdk:x86:0.2.7@aar'
   compile 'com.xiaoduotech.xiaoduosdk:x86_64:0.2.7@aar'
}
```

**本地依赖aar:** 在网络不好的情况下，为了提高编译速度可以选择本地依赖。

1、 下载 [sdk-release.aar](http://cdn.xiaoduotech.com/cvd/android/sdk-release.aar), [armv7a-release.aar](http://cdn.xiaoduotech.com/cvd/android/armv7a-release.aar), 在`module`下新建`libs`目录，将下载好的[sdk-release.aar](http://cdn.xiaoduotech.com/cvd/android/sdk-release.aar),  [armv7a-release.aar](http://cdn.xiaoduotech.com/cvd/android/armv7a-release.aar)拷贝到`libs`目录下。

点击下载其他jni库： 
[armv5-release.aar](http://cdn.xiaoduotech.com/cvd/android/armv5-release.aar)
[armv64-release.aar](http://cdn.xiaoduotech.com/cvd/android/arm64-release.aar)
[x86-release.aar](http://cdn.xiaoduotech.com/cvd/android/x86-release.aar)
[x86_64-release.aar](http://cdn.xiaoduotech.com/cvd/android/x86_64-release.aar)

2、 在`module`的build.gradle文件添加如下内容

```gradle
repositories {
    flatDir {
        dirs 'libs' //this way we can find the .aar file in libs folder
    }
}
```

3、在`module`的build.gradle文件添加依赖

```gradle
dependencies {
  compile 'org.greenrobot:eventbus:3.0.0'
  compile 'com.squareup.picasso:picasso:2.5.2'
  compile 'com.google.code.gson:gson:2.7'
  compile 'com.squareup.okhttp3:logging-interceptor:3.4.1'
  compile 'com.android.support:appcompat-v7:23+' //不低于v22.2.1
  compile 'com.android.support:recyclerview-v7:23+'//不低于v22.2.1
  compile (name:'armv7a-release', ext:'aar')
  compile (name:'sdk-release', ext:'aar')
}
```

**权限**

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
        //在CVD官网注册后配置app sdk信息即可获取src，channelId和 appkey。
        CVDManager.getInstance().init(this,src,channelId,appKey, intentClass, backClass);

   }
    ...
}
   
```

**参数说明**

|参数 |说明|
|:--:|:--:|
|context|ApplicationContext|
|src|企业id,[管理台](http://cvd.xiaoduotech.com/admin/)获取|
|channelId| 细分渠道号,[管理台](http://cvd.xiaoduotech.com/admin/)获取|
|appKey|[管理台](http://cvd.xiaoduotech.com/admin/)获取|
|intentClass|点击推送消息后跳转的页面，通常为会话页,如`CVDConversationActivity.class`|
|backClass|点击推送跳转后，按返回键的跳转页面，通常为会话页面入口页|

**方法原型**

```java
public void init(Context context, @NonNull String src, @NonNull int channelId, @NonNull String appKey,Class<? extends Activity> intentClass, Class<? extends Activity> backClass)
```

##3 启动对话界面

开发者可以不进行任何二次开发直接使用**CVD SDK**会话界面，在完成 [配置](#config) 、[初始化](#init) 后可以直接跳转SDK的对话界面，自动接入客服会话。

###3.1 配置用户信息

在跳转之前传入用户信息，用户信息将会在后台传给客服端，供客服人员查看。其中**昵称**和**头像**会在会话中显示。

```java
CVDManager.getInstance().configUserInfo(tid,
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
                //statusbar颜色
               config.setCVDStatusBarColor(getResources().getColor(R.color.color_material_cyan))
                //toolbar背景色
                .setCVDtoolbarBackgroundColor(getResources().getColor(R.color.color_material_cyan))
                //toolbar返回键图标
                .setCVDtoolbarButtonDrawable(R.drawable.ic_account_circle_white_18dp)
                //toolbar的标题位置，支持左边，居中，以及右边
                .setCVDtoolbarTitlePosition(CVDCustomActionbar.POSITION_LEFT)
                //toolbar标题文字大小
                .setCVDtoolbarTitleSize(18)
                //toolbar标题文字颜色
                .setCVDtoolbarTitleColor(Color.WHITE)
                //发送消息按钮背景颜色状态
                .setCVDSendMessageButtonBackGroundSelector(R.drawable.selector_btn)
                //发送消息按钮文字颜色状态
                .setCVDSendMessageButtonTextColorSelector(R.color.text_color_grey2anything)
                //发送消息按钮文字大小
                .setCVDSendMessageButtonTextSize(14)
                //对方聊天文字大小
                .setCVDBubbleTextSize(14)
                //对方聊天气泡图片，一定要.9的图片
                .setCVDOtherBubbleDrawable(R.drawable.bubble_gray)
                //改变对方聊天气泡颜色
//                .setCVDOthersBubbleBackgroundColor(Color.GREEN)
				//对方聊天文字颜色
                .setCVDOthersBubbleTextColor(Color.BLACK)
                //自己聊天气泡图片，一定要.9的图片
                .setCVDSelfBubbleDrawable(R.drawable.bubble_blue)
                //自己聊天气泡颜色
//                .setCVDSelfBubbleBackgroundColor(Color.YELLOW)
				//改变对方聊天文字颜色
                .setCVDSelfBubbleTextColor(Color.WHITE)
                //聊天背景颜色
                .setCVDChatMainBackgroundColor(Color.DKGRAY)
                //聊天背景图片
                .setCVDChatMainBackgroundDrawableRes(R.drawable.gradient_drawable)

        ;
    }
```


**方法说明**

-  ` setCVDStatusBarColor(@ColorInt int cvdStatusBarColor);`    设置 *statusbar* 颜色
- ` setCVDtoolbarBackgroundColor(@ColorInt int cvDtoolbarBackgroundColor); `   设置 *toolbar* 背景颜色
-  ` setCVDtoolbarTitlePosition(@CVDCustomActionbar.TitlePosition int position); ` 设置 *toolbar* 标题位置 
-  ` setCVDtoolbarTitleColor(@ColorInt int cvDtoolbarTTitleolor); ` 设置 *toolbar* 标题 颜色
- ` setCVDtoolbarTitleSize(int cvDtoolbarTitleSize);`   设置 *toolbar*  标题字号
- `  setCVDtoolbarButtonDrawable(@DrawableRes int cvDtoollbarButtonDrawable);`  设置  *toolbar* 返回键图片

- `  setCVDOthersBubbleBackgroundColor(@ColorInt int cvdOthersBubbleBackgroundColor); `设置客服聊天气泡颜色

- `  setCVDBubbleTextSize(@ColorInt int cvdBubbleTextSize); `设置聊天字体大小

- ` setCVDSelfBubbleBackgroundColor(@ColorInt int cvdSelfBubbleBackgroundColor);`设置用户聊天气泡颜色

- `  setCVDLeftAvatarSizeDp( int cvdLeftAvatarSize);` 设置客服头像大小

- ` setCVDRightAvatarSizeDp( int cvdRightAvatarSize);` 设置用户头像大小
- ` setCVDSelfBubbleTextColor(@ColorInt int cvdSelfBubbleTextColor);`设置用户聊天字体颜色

- `  setCVDOthersBubbleTextColor(@ColorInt int cvdOthersBubbleTextColor); `设置客服聊天字体颜色

- ` setCVDChatMainBackgroundColor(@ColorInt int cvdChatMainBackgroundColor);`设置聊天页背景颜色

- `  setCVDChatMainBackgroundDrawableRes(@DrawableRes int cvdChatMainBackgroundDrawableRes);`设置聊天页背景图片

- ` setCVDSelfBubbleDrawable(@DrawableRes int cvdSelfBubbleDrawable); `   设置设置用户聊天气泡图片，必须用.9的图片

- `  setCVDOtherBubbleDrawable(@DrawableRes int cvdOtherBubbleDrawable); `设置客服聊天气泡图片，必须用.9的图片

- ` setCVDSendMessageButtonBackGroundSelector(@DrawableRes int cvdSendMessageButtonBackGroundSelector);`设置发送消息按钮背景selector

- ` setCVDSendMessageButtonTextColorSelector(@ColorRes int cvdSendMessageButtonTextColorSelector);` 设置发送消息按钮文字的颜色selector


- `  setCVDSendMessageButtonTextSize(int cvdSendMessageButtonTextSize);` 设置发送消息按钮文字大小

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

-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
-keep class com.xiaoduotech.sdk.cvdframework.cvdmodels.**{*;}

```

##6自定义开发

在之前的步骤进入会话页`CVDConversationActivity`会自动授权并登录，如果有自定义开发需求，同样需要用户先初始化
`CVDManager.getInstance().init(this,src,channelId,appKey, intentClass, backClass)`, 再配置个人信息`CVDManager.getInstance().configUserInfo(tid,nickname,avatar,position,title)`,最后在开启会话聊天之前，需要调用`CVDManager.getInstance().login(resultCallback)`方法登录，后台会在这个过程分配客服，**登录成功**后方可获取会话，并通过会话聊天。

```java
CVDManager.getInstance().login(new Action2<Boolean, Exception>() {
            @Override
            public void call(Boolean aBoolean, Exception e) {
                if (aBoolean) {   //登录成功,成功后部分数据会本地化，可以通过CVDManager.getInstance().getGroupId()方法获取groupId
                    getData();
                } else { //登录失败
                  ...  
            }
        });
```

**参数说明**

|参数 |说明|
|:--:|:--:|
|resultCallback|登录结果回调,`boolean`为`true`表示登录成功，`false`为失败，可通过`Exception`判断失败原因|

**方法原型**

```java
login(Action2<Boolean, Exception> resultCallback);
```

###6.1会话 CVDConversation

<img src="http://yuml.me/diagram/nofunky/class/[CVDConversation]^-[NormalConversation]"><br>

会话代表用户与客服的一个对话, 在 sdk中, 会话CVDConversation对象可以用以发送消息、拉取历史消息、获取未读消息数、最后一条消息、草稿、渠道信息、以及用以展示在ui上的头像地址、名称。在ui展示上, 一个会话代表一个服务用户。

- **获取所有会话**

|参数 |说明|
|:--:|:--:|
|onNext|  对话数据回调，得到会话列表|
|onError| 异常回调,得到Exception|

```java
//通过该方法拿取所有会话，该方法获取的会话包含所有需要的数据。
	CVDManager.getConversations(Action1<List<NormalConversation>> onNext, Action1<Exception> onError);
```

- **获取单个会话**  获取单个对话需要传群id参数, 通常这个参数会提前获得。

|参数 |说明|
|:--:|:--:|
|groupId|  对话群id|
|onNext|  对话数据回调|
|onError| 异常回调|

```java
CVDManager.getConversation(String groupId, Action1<NormalConversation> action1);
```

- **发送消息**  可以通过会话对象向会话成员发送消息。

|参数 |说明|
|:--:|:--:|
|cvdMessage|  消息内容|

```java
//注意！通过构造函数获取的会话对象没有头像，昵称等数据，如果需要相关数据请使用
CVDManager.getConversation(String groupId, Action1<NormalConversation> action1);
   CVDConversation conversation = new NormalConveration(groupId);
   conversation.sendNormalMessage(cvdMessage);
  
```

>为了防止内存泄漏以及消息状态及时更新，sdk接入了事件总线框架eventbus，发送消息的结果将通过EventBus发送，只需在必要的地方订阅SendingMessageResultEvent 即可。
 
```java

	 @Override
    protected void onStart() { //onstart里注册
	 super.onStart();
     EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() { //onstop里取消注册
     super.onStop();
     EventBus.getDefault().unregister(this);
    }
    
@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)//sticky 保证不在当前页面也能在下次进入后更新状态。
    public void onSendMessageResultEvent(SendingMessageResultEvent sendingMessageResultEvent) {
        if (sendingMessageResultEvent.isSuc()) {
           onSendMessageSuccess(sendingMessageResultEvent.getMessage());
        } else {
         onSendMessageFail(sendingMessageResultEvent.getException(), sendingMessageResultEvent.getMessage());
        }
        EventBus.getDefault().removeStickyEvent(sendingMessageResultEvent);
    }
```

- **获取历史消息** 可以通过会话对象向服务端请求历史消息消息。

|参数 |说明|
|:--:|:--:|
|count| 请求消息数|
|maxId| 返回消息Id<=maxId,消息Id可通过message.getMessageId()获取，传`0`时返回最新的消息|

>**实现分页请求** 服务端对该api的判断规则是返回`messageId <= maxId` 的`count` 条消息，如第一次请求了15条历史消息，取后续15条时，`maxId` 取上15条里值最小的`messageId`

```java
conversation.getHisMessages(int count,long maxId);
```

>获取结果将通过Eventbus 发送

```java
@Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onGetServiceMessageEvent(GetMessageResultEvent getMessageResultEvent) {
        EventBus.getDefault().removeStickyEvent(GetMessageResultEvent.class);
        if (getMessageResultEvent.isSuc()) {//获取消息成功
            showMessage(getMessageResultEvent.getCvdMessageList());
            }

        } else {//获取消息失败

        }
    }
```

- **获取会话展示数据** 需要的元素可以直接通过会话对象获取。

```java
conversation.getName();//获取会话名称
conversation.getAvatar();//获取会话展示头像
conversation.getChannelName();//获取渠道名称
conversation.getUnRead();//获取未读消息数
conversation.getLastMessage();//获取最后一条消息
conversation.getEditabledraft(Context context); //获取草稿
```

- **其他操作**

```java
conversation.saveDraft(@Nullable Editable draft)//保存草稿，可以在onStop()时调用该方法保存草稿，下次onStart()时显示
conversation.readAllMessage();//设置会话消息为已读，即将未读数置零

```

###6.2 消息

在sdk中消息主要作为类型占位符，adapter可以根据不同类型加载不同的viewholder，来显示对应消息。
具体消息内容在`CVDMessageElem`中，一个消息可以包含多个`CVDMessageElem`。
 
 <img src="http://yuml.me/diagram/nofunky/class/[CVDMessageElem]^-[CVDTextElem],[CVDMessageElem]^-[CVDFaceElem],[CVDMessageElem]^-[CVDImageElem],[CVDMessageElem]^-[CVDUrlElem]">
 
<img src="http://yuml.me/diagram/nofunky/class/[CVDMessage]^-[CVDTextMessage],[CVDMessage]^-[CVDImageMessage],[CVDMessage]^-[CVDNotifyMessage],[CVDMessage]^-[CVDUrlMessage]">


1. **文本消息:  `CVDTextMessage`** 

>包含 CVDTextElem  和 CVDFaceElem

2.  **图片消息 `CVDImageMessage `**

>包含CVDImageElem

3.  **通知消息 `CVDNotifyMessage `**

> 包含CVDTextElem和 CVDFaceElem, 本质上通知消息和文本消息是一样的，可以根据消息类型进行不同方式的展示。

4. **url消息` CVDUrlMessage`**

>包含CVDUrlElem , url消息是将用户输入的url的主要元素解析成卡片的形势展示在聊天界面

####6.2.1 发送消息

通过conversation向该会话里的用户发送消息

示例：发送文本消息

```java
 CVDConversation conversation = new NormalConveration(groupId);
 CVDMessage cvdMessage = new CVDTextMessage(groupId, 0,
                        true, String.valueOf(System.currentTimeMillis() / 1000L), CVDMessage.CVDMessageStatus.Sending);
   cvdMessage.addElems(MessageWrapper.editable2cvdElems(editt)); //添加消息元素。
   conversation.sendNormalMessage(cvdMessage);
```

示例：发送图片消息

```java
 CVDMessage message = new CVDImageMessage(groupId, 0,
                                true, System.currentTimeMillis() / 1000L + "", CVDMessage.CVDMessageStatus.Sending);
                        CVDImageElem cvdImageElem = new CVDImageElem();
                        cvdImageElem.setPath(path); //图片路径
                        cvdImageElem.setOri(isOri); //是否发送原图
                        message.addElem(cvdImageElem);
   conversation.sendNormalMessage(cvdMessage);
```

示例：发送Url链接

```java
     
                    ...
     if (SystemUtil.isUrl(userInput))  //判断用户输入是不是链接
        sendUrlMessage(msg.toString());
                    ...
    public void sendUrlMessage(final String url) {
        String tracerId = UUID.randomUUID().toString();
        //提前显示url消息为文本消息
        final CVDMessage textMessage = new CVDTextMessage(groupId, 0,
                true, String.valueOf(System.currentTimeMillis() / 1000L), CVDMessage.CVDMessageStatus.Sending
        );
        textMessage.setMessageUniId(tracerId);
        CVDTextElem textElem = new CVDTextElem();
        textElem.setText(url);
        textMessage.addElem(textElem);
         //待发送的url消息
        final CVDMessage htmlMessage = new CVDUrlMessage(groupId, 0,
                true, String.valueOf(System.currentTimeMillis() / 1000L), CVDMessage.CVDMessageStatus.Sending
        );
        htmlMessage.setMessageUniId(tracerId);
        
        if (isSuggustMessage) {
            htmlMessage.setMark(2);
            isSuggustMessage = false;
        }
        iurlCrawler.makePreview(url, new LinkPreviewCallback() {  //使用CVDUrlCrawler对象解析url
            @Override
            public void onPre() {
            //提前显示url消息为文本消息
                view.showMessage(textMessage); 
            }

            @Override
            public void onPos(final CVDUrlElem cvdHtmlElem, boolean isNull) {
                if (!isNull) {
                     //解析成功，发送的url消息
                    htmlMessage.addElem(cvdHtmlElem);
                    conversation.sendNormalMessage(htmlMessage);
                } else { //解析失败，发送文本消息
                    conversation.sendNormalMessage(textMessage);

                }
            }
        });

    }

```
###6.3 事件

当有新的事件时会通过`EventBus`发送对应事件以及相关数据，开发者可以根据不同业务需求对事件进行订阅消费。

|事件类型 |说明|
|:--:|:--:|
|CVDGroupSystemEvent|对话相关事件|
|CVDMessageEvent| 新消息事件|
|CVDUserEvent| 用户相关事件|
|CVDNetWorkEvent| 网络环境变化事件|

1. **登录成功**后订阅并消费事件

a.新消息

```java
@Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewMessageEvent(CVDMessageEvent messageEvent) { //收到新消息
        CVDMessage cvdMessage = messageEvent.getMessage();
        if (cvdMessage.getGroupId().equals(groupId)) {  //过滤别的对话消息
	        ...
           onNewMessageComing(cvdMessage);
		    ...
        }
    }
```
b. 用户事件

```java
@Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(CVDUserEvent CVDUserEvent) {
        switch (CVDUserEvent.getUserEventType()) {
            case CVDUserEvent.EVENT_MEMBER_JION: //用户加入对话
		        ...
                break;
            case CVDUserEvent.EVENT_MEMBER_KICKED://用户离开对话
	            ...
                break;
            case CVDUserEvent.EVENT_MEMBER_OFFLINE://用户上线
	            ...
                break;
            case CVDUserEvent.EVENT_MEMBER_ONLINE://用户下线
	            ...
                break;
        }
    }
```
c. 会话事件

```java
 @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGroupSystemEvent(CVDGroupSystemEvent cvdGroupSystemEvent) {
        switch (cvdGroupSystemEvent.getConversationEventType()) {
            case CVDGroupSystemEvent.EVENT_CONVERSATION_CLOSED://对话结束
	            onConversationDeleted();
                break;
            case CVDGroupSystemEvent.EVENT_CONVERSATION_REOPEN://对话重新开启
                onConversationStart();
                break;
        }
    }
```
###6.4 用户资料

在实际开发中，往往需要展示用户资料(如头像、昵称)，每个用户对应唯一uid,  通过uid可以获取用户相关资料。
 - 使用`CVDManager.getSendersMap(userIdList,callBack)`来获取用户资料，用户信息保存在`CVDUserProfile`中，包含了用户id，头像和昵称
 >注意:  使用 `conversation.getHisMessages(int count,long maxId);`获取历史消息时已经将资料设置进每个消息中，通过 `message.getSenderProfile()`即可得到`CVDUserProfile`，用以在消息列表上展示。
<br>

```java
CVDManager.getSendersMap(userIdlist, new Action1<Map<String, CVDSenderProfile>>() {
                    @Override
                    public void call(Map<String, CVDSenderProfile> stringCVDSenderProfileMap) {
                        for (int i = 0; i < cvdMessageList.size(); i++) {
                           
                            CVDSenderProfile senderProfile = message.getSenderProfile();
                            String uid = senderProfile.getUid();
                            String avatar = stringCVDSenderProfileMap.get(uid).getAvatar();
                            String nickName = stringCVDSenderProfileMap.get(uid).getNick();
                            ...
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                    
                    }
                }
        );

```











