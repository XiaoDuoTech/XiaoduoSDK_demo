# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangchong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangchong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangchong/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
#-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keep class com.tencent.**{*;}
-dontwarn com.tencent.**

-keep class tencent.**{*;}
-dontwarn tencent.**

-keep class com.qq.**{*;}
-dontwarn com.qq.**

-keep class qalsdk.**{*;}
-dontwarn qalsdk.**
# 忽略所有的警告
-ignorewarnings
-keep public class org.jsoup.** {
public *;
}
-keep class com.xiaoduotech.htmlpreview.CVDHtmlSourceContent
-keep class android.support.annotation.** { *; }
-keep interface android.support.annotation.** { *; }
-dontwarn android.support.annotation.**

-keep class okio.** { *; }
-keep interface okio.** { *; }
-dontwarn okio.**

-dontwarn java.lang.invoke.*


-keep class javax.inject.** { *; }
-keep interface javax.inject.** { *; }
-dontwarn javax.inject.**

-keep class javax.annotation.** { *; }
-keep interface javax.annotation.** { *; }
-dontwarn javax.annotation.**

 -keepnames class * implements android.os.Parcelable {
     public static final ** CREATOR;
 }


 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }

 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }


# 不混淆 Android 组件
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * implements java.io.Serializable {*;}
-keep class **.R$* { *;}


-dontwarn android.support.**
-dontwarn com.android.support.**
-keep class android.support.v7.** { *; }
-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

#针对5.0以上
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
