# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-ignorewarnings

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
-dontoptimize
-dontpreverify

# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
#-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-keepattributes *Annotation*,InnerClasses
-keepattributes *JavascriptInterface*
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

#支付宝
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

#微信/QQ
-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

#银联
-dontwarn com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn cn.gov.pbc.**
-keep class cn.gov.pbc.** {*;}

-dontwarn com.UCMobile.PayPlugin.**
-keep class com.UCMobile.PayPlugin.** {*;}

#百度
-dontwarn com.baidu.**
-keep class com.baidu.** {*;}

-dontwarn com.gieseckedevrient.android.**
-keep class com.gieseckedevrient.android.** {*;}

-dontwarn com.lenovo.**
-keep class com.lenovo.** {*;}

-dontwarn com.samsung.android.sdk.**
-keep class com.samsung.android.sdk.** {*;}

#京东
-dontwarn wangyin.app.**
-keep class wangyin.app.** {*;}

-dontwarn com.google.android.gms.**
-keep class com.google.android.gms.** {*;}

-dontwarn sun.misc.**
-keep class sun.misc.** {*;}

-dontwarn com.wangyin.**
-keep class com.wangyin.** {*;}

#MergepaySDK
-dontwarn com.mergepay.sdk.**
-keep class com.mergepay.sdk.** {*;}

-dontwarn com.yjt.bridge.**
-keep class com.yjt.bridge.** {*;}

-dontwarn android.net.**
-dontwarn org.apache.**
-keep class android.net.** {*;}
-keep class com.android.internal.http.** {*;}
-keep class org.apache.** {*;}

-dontwarn com.google.zxing.**
-keep class com.google.zxing.** {*;}

-dontwarn com.google.code.gson.**
-keep class com.google.code.gson.** {*;}

