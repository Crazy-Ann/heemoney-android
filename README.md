# heemoney-android
## Mergepay Android SDK 使用文档

###简介
SDK支持以下支付渠道

+ 支付宝钱包
+ 微信APP支付
+ QQ钱包支付
+ 银联手机控件支付
+ 京东钱包
+ 百度钱包支付
+ 扫码功能

### 一、快速体验

Mergepay SDK 为开发者提供了 demo 程序，可以快速体验 MergepaySDK 接入流程。下载 MergepaySDK_Demo 之后将整个目录导入到您的 Android Studio（建议）。

### 二、支付流程

![流程图](https://github.com/Heemoney/heemoney-android/blob/master/doc/image/DFD.png "支付流程图")

1. 商户客户端向商户服务器发送支付请求，携带商品信息，商户服务器按汇元聚合支付统一下单接口的要求进行组织参数，发送预支付请求。
2. 汇元聚合支付后台收到商户的预支付请求后，按照通道类型，向相关通道发起下单请求，通道下单后，将相关预支付要素返回商户后台。
3. 商户App将支付相关参数通过汇元聚合支付SDK提供的接口传入SDK。
4. 汇元聚合支付SDK对传入的参数校验后，按照通道类型唤起对应的支付页面。用户输入密码，选择确认支付之后完成支付操作。
5. 支付完成后，由支付通道将支付结果同步返回商户APP，用于展示本次支付状态**（同步回调结果只作为界面展示的依据，不能作为订单的最终支付结果，最终支付结果应以异步回调为准）。**
6. 支付通道将支付结果异步返回汇元聚合支付后台。
7. 汇元聚合支付后台将支付结果异步返回商户后台。

### 三、快速集成

#### 导入MergepaySDK_v1.0.aar

##### Android Studio
1. 将`MergepaySDK_v1.0.aar`文件拷贝到`libs`目录。
    <img src="https://github.com/Heemoney/heemoney-android/blob/master/doc/image/import-aar-01.png" width="290" >
2. 在`build.gradle`文件中添加

            android 标签下添加
            sourceSets{
                main{
                    jniLibs.srcDirs = ['libs']
                    assets.srcDirs = ['src/main/assets']
                }
            }
            repositories {
                flatDir {
                    dirs 'libs'
                }
            }

        dependencies 标签下添加
        compile(name: 'MergepaySDK_v1.0', ext: 'aar')

	<img src="https://github.com/Heemoney/heemoney-android/blob/master/doc/image/import-aar-02.png" width="450" >

    <img src="https://github.com/Heemoney/heemoney-android/blob/master/doc/image/import-aar-03.png" width="450" >

3. 配置支付方式所需文件(建议到相应官方地址下载最新依赖包)

   (1) [支付宝支付](https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.qVzefW&treeId=193&articleId=104509&docType=1)

        libs\alipaySdk-20161222.jar

        compile files('libs/alipaySdk-20161222.jar')
   (2) [微信支付](https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1419319167&token=&lang=zh_CN)

        libs\libammsdk.jar

        compile files('libs/libammsdk.jar')
   (3) [百度支付](https://b.baifubao.com/sp_register/0/page_controller/0?page=access_process)

        libs\armeabi\libbankcardprocess.so
        libs\armeabi\libbd_wsp_v1_0.so
        libs\armeabi\libidl_license.so
        libs\armeabi\librabjni_V2_3_0.so
        libs\armeabi\libyuv.so
        libs\x86\libbankcardprocess.so
        libs\x86\libbd_wsp_v1_0.so
        libs\x86\libidl_license.so
        libs\x86\librabjni_V2_3_0.so
        libs\x86\libyuv.so

        libs\galaxy.jar
        libs\async-httpclient-1.6.0.jar
        libs\sapi-core-6.15.7.jar
        libs\walletsdk_v5.8.1.19_20160607_1941_obfuscated.jar

        assets\bankcard\mean\data_mean_32_bank_card_sparse
        assets\bankcard\mean\data_mean_32_bank_card_sparse
        assets\bankcard\model\bank_card_captcha.txt
        assets\bankcard\model\cdnn_model_bank_card_32_sparse
        assets\bankcard\model\dtc_model_bank_card_fst_64_sparse
        assets\bankcard\model\dtc_model_bank_card_scd_64_sparse
        assets\sapi_theme\btn_back.png
        assets\sapi_theme\logo.png
        assets\sapi_theme\style.css
        assets\wappass.baidu.com\passport\login.html
        assets\open_sdk_file.dat
        assets\service.cfg
        assets\service4.cfg

        compile project(':BDSDK')//引入BDSDK依赖
        compile files('libs/galaxy.jar')
        compile files('libs/async-httpclient-1.6.0.jar')
        compile files('libs/sapi-core-6.15.7.jar')
        compile files('libs/walletsdk_v5.8.1.19_20160607_1941_obfuscated.jar')
   (4) [QQ支付](http://support.tenpay.com/qpay_app.php)

        libs\mqqopenpay.jar

        compile files('mqqopenpay.jar')
   (5) [银联支付](https://open.unionpay.com/ajweb/help/file/techFile?productId=3)(手机控件支付开发包SDKPro(安卓版))

        libs\arm64-v8a\libentryexpro.so
        libs\arm64-v8a\libuptsmaddon.so
        libs\armeabi\libentryexpro.so
        libs\armeabi\libuptsmaddon.so
        libs\armeabi-v7a\libentryexpro.so
        libs\armeabi-v7a\libuptsmaddon.so
        libs\mips\libentryexpro.so
        libs\mips\libuptsmaddon.so
        libs\x86\libentryexpro.so
        libs\x86\libuptsmaddon.so
        libs\x86_64\libentryexpro.so
        libs\x86_64\libuptsmaddon.so

        libs\UPPayAssistEx.jar
        libs\UPPayPluginExPro.jar

        assets\data.bin

        compile files('libs/UPPayAssistEx.jar')
        compile files('libs/UPPayPluginExPro.jar')
   (6) [京东支付](http://www.chinabank.com.cn/service/support.jsp)  (需先向官方提出申请方可获取最新开发包)

        libs\wepay-1.0.0.jar
        libs\wypmaframe-1.0.0.jar
        libs\wypnetwork-1.0.0.jar
        libs\rt.jar
        libs\google-play-services-analytics.jar
        libs\app-server-util-1.0.0-SNAPSHOT.jar

        compile project(':JDSDK')
        compile files('libs/wepay-1.0.0.jar')
        compile files('libs/wypmaframe-1.0.0.jar')
        compile files('libs/wypnetwork-1.0.0.jar')
        compile files('libs/rt.jar')
        compile files('libs/google-play-services-analytics.jar')
        compile files('libs/app-server-util-1.0.0-SNAPSHOT.jar')

   (7) MergepaySDK

        libs\arm64-v8a\libencrypt.so
        libs\armeabi\libencrypt.so
        libs\armeabi-v7a\libencrypt.so
        libs\mips\libencrypt.so
        libs\mips64\libencrypt.so
        libs\x86\libencrypt.so
        libs\x86_64\libencrypt.so

        libs\MergepaySDK_v1.0.aar

        compile(name: 'MergepaySDK_v1.0', ext: 'aar')
        compile 'com.google.code.gson:gson:2.8.0'

   (8) [扫码功能]

        compile 'com.google.zxing:core:3.2.0'

4. 权限声明，在AndroidManifest.xml中添加permission

######通用权限

      <uses-permission android:name="android.permission.INTERNET"/>
      <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
      <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
      <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
      <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

######银联

    <uses-permission android:name="android.permission.NFC"/>
    <uses-permission-sdk-23 android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission-sdk-23 android:name="android.permission.NFC"/>

######百度

    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.READ_SMS"/>
    <uses-permission android:name="android.permission.CALL_PHONE"/>
    <uses-permission android:name="android.permission.DOWNLOAD_WITHOUT_NOTIFICATION"/>
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-permission android:name="android.hardware.camera"/>
    <uses-permission android:name="android.permission.FLASHLIGHT"/>
    <uses-permission android:name="org.fidoalliance.uaf.permissions.FIDO_CLIENT"/>
    <uses-permission android:name="android.permission.USE_FINGERPRINT"/>
    <uses-permission android:name="com.fingerprints.service.ACCESS_FINGERPRINT_MANAGER"/>
    <uses-permission android:name="com.sec.feature.fingerprint_manager_service"/>
    <uses-feature android:name="android.hardware.camera" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.front" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.autofocus" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.flash" android:required="false"/>

######京东

    <uses-permission android:name="android.permission.RECEIVE_SMS"/>

5. 在AndroidManifest.xml中注册activity

######汇元聚合支付

	<activity
        android:name="com.mergepay.sdk.NativeInterfaceActivity"
        android:configChanges="orientation|keyboardHidden|navigation|screenSize"
        android:launchMode="singleTop"
        android:theme="@android:style/Theme.Translucent.NoTitleBar">
    </activity>

     <!-- 扫码页面-->
    <activity
        android:name="com.mergepay.sdk.scanner.CaptureActivity"
        android:launchMode="singleTask"
        android:screenOrientation="portrait"
        android:theme="@style/Theme.AppCompat.NoActionBar"
        android:windowSoftInputMode="stateAlwaysHidden|adjustPan"/>

######支付宝

     <activity
        android:name="com.alipay.sdk.app.H5PayActivity"
        android:configChanges="orientation|keyboardHidden|navigation|screenSize"
        android:exported="false"
        android:screenOrientation="behind"
        android:windowSoftInputMode="adjustResize|stateHidden"/>
######微信

     <activity-alias
        android:name=".wxapi.WXPayEntryActivity"
        android:exported="true"
        android:targetActivity="com.mergepay.sdk.NativeInterfaceActivity"/>

######QQ
            在 NativeInterfaceActivity 标签下填加
           <!--QQ配置 开始-->
              <intent-filter>
                  <action android:name="android.intent.action.VIEW"/>
                  <category android:name="android.intent.category.BROWSABLE"/>
                  <category android:name="android.intent.category.DEFAULT"/>
                  <data android:scheme="your QQ app id"/>
              </intent-filter>
            <!--QQ配置 结束-->
######银联

    <activity
        android:name="com.unionpay.uppay.PayActivity"
        android:configChanges="orientation|keyboardHidden"
        android:excludeFromRecents="true"
        android:screenOrientation="portrait"
        android:windowSoftInputMode="adjustResize"/>
######百度

    <activity
        android:name="com.baidu.wallet.passport.PassLoginActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:hardwareAccelerated="false"
        android:theme="@style/EbpayThemeActivitTranslucent"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.LightappBrowseActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:hardwareAccelerated="true"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.WelcomeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:taskAffinity=":bdwalletWelcomeTask"
        android:theme="@style/EbpayThemeActivityWelcome"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PayResultActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PwdCheckActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateVisible"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PwdSetAndConfirmActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateVisible"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PwdPayActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivitTranslucent"
        android:windowSoftInputMode="stateVisible"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.WebViewActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.SelectBindCardActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.SecurityCenterActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustUnspecified|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PrivacyProtectionActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustUnspecified|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PassWordFreeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustUnspecified|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.FingerprintSwitchActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustUnspecified|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.core.plugins.pluginproxy.WalletProxyActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustResize|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.core.plugins.pluginproxy.WalletProxyActivity2"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivitTranslucent"
        android:windowSoftInputMode="adjustResize|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.core.plugins.pluginmanager.WalletPluginActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivityWelcome"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PassNormalizeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:hardwareAccelerated="false"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.ConfirmPayOrderActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.DiscountListActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.SelectPayWayActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivitTranslucent"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.H5PayWebViewActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.BindCardImplActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTask"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.SignChannelListActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.WalletSmsActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.PwdManagerActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="adjustUnspecified|stateHidden"/>
    <activity
        android:name="com.baidu.wallet.scancode.ui.ScanCodePwdPayActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivitTranslucent"
        android:windowSoftInputMode="stateVisible|adjustResize"/>
    <activity
        android:name="com.baidu.wallet.paysdk.ui.AuthorizeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthPublicSecurityActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthSuccessActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthingActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthFailActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthManualAuthActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTask"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthCardListActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthResultActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.RNAuthManualCertActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTask"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.qrcodescanner.QRScanCodeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:hardwareAccelerated="false"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"/>
    <activity
        android:name="com.baidu.wallet.qrcodescanner.ComfirmOrderActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:hardwareAccelerated="false"
        android:theme="@style/EbpayThemeActivit"
        android:windowSoftInputMode="stateHidden"/>
    <activity
        android:name="com.baidu.wallet.bankdetection.BankCardDetectionActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="@bool/bd_wallet_switch_global_debug"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:theme="@style/EbpayThemeActivit"/>
    <activity
        android:name="com.baidu.wallet.bankdetection.BankCardResultActivity"
        android:label="@string/app_name"
        android:screenOrientation="portrait"
        android:theme="@style/CameraMist"/>
    <activity
        android:name="com.baidu.wallet.rnauth.ui.IdentityCardDetectionActivity"
        android:label="@string/app_name"
        android:screenOrientation="portrait"
        android:theme="@style/IdcardDetectStyle"/>
    <activity
        android:name="com.lenovo.fido.framework.IntentHelperActivity"
        android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
    <activity
        android:name="com.baidu.wallet.scheme.WalletSchemeActivity"
        android:configChanges="keyboardHidden|navigation|orientation|screenSize"
        android:excludeFromRecents="true"
        android:exported="true"
        android:launchMode="singleTop"
        android:screenOrientation="portrait"
        android:taskAffinity=":bdwalletWelcomeTask"
        android:theme="@style/EbpayThemeActivit">
        <intent-filter>
            <action android:name="android.intent.action.VIEW"/>

              <category android:name="android.intent.category.DEFAULT"/>
              <category android:name="android.intent.category.BROWSABLE"/>

            <data android:scheme="@string/bd_wallet_scheme"/>
        </intent-filter>
    </activity>

#####京东

    <activity
        android:name="com.wangyin.wepay.kuang.ui.WePayActivity"
        android:screenOrientation="portrait"
        android:theme="@style/WePayTheme"/>

### 四、获得支付必要参数
支付必要参数是一个包含支付信息的 JSON 对象，是 Mergepay SDK 发起支付的必要参数。该参数需要请求用户服务器获得，服务端生成支付必要参数的方式参考 [ Mergepay SDK 统一下单说明](http://192.168.2.95/StaticMergepay/Doc/Index.html?type=UnifiedOrder&id=1)。 SDK 中的 demo 里面提供了如何获取支付必要参数的实例方法，供用户参考。

### 五、发起支付

    HMApi.doPayment(activity, parameters);

### 六、获取支付状态(详见demo)
从 Activity 的 onActivityResult 方法中获得支付结果。支付成功后，用户服务器也会收到 Mergepay SDK 服务器发送的异步通知。 最终支付成功请根据服务端异步通知为准。

##### Constant.Extra.RESULT_CODE：插件内部返回支付结果编码
##### Constant.Extra.RESULT_MESSAGE：自定义支付结果信息
##### Constant.Extra.RESULT_STATUS：插件内部返回支付结果信息

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //建议对resultCode进行校验
        if (data != null && resultCode == Constant.Common.RESULT_CODE_PAYMENT) {
            switch (requestCode) {
                case Constant.SandBox.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "测试(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.AL.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "支付宝(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.WX.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "微信(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.BD.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "百度(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.UN.REQUEST_CODE_PAYMENT:
                    if (data.getIntExtra(Constant.Extra.RESULT_CODE, -1) == Constant.Common.PAYMENT_ERROR) {//内部异常校验请勿省略
                        Toast.makeText(this, "银联(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                                + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                                + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    } else { // 银联支付返回结果
                        Toast.makeText(this, "银联(" + data.getStringExtra(Constant.UN.PAY_RESULT) + ")", Toast.LENGTH_LONG).show();
                    }
                    break;
                case Constant.QQ.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "QQ(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                            + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    break;
                case Constant.JD.REQUEST_CODE_PAYMENT:
                    if (data.getIntExtra(Constant.Extra.RESULT_CODE, -1) == Constant.Common.PAYMENT_ERROR) {//内部异常校验请勿省略
                        Toast.makeText(this, "京东(result_code:" + data.getIntExtra(Constant.Extra.RESULT_CODE, -1)
                                + ",result_message:" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                                + ",result_status:" + data.getStringExtra(Constant.Extra.RESULT_STATUS) + ")", Toast.LENGTH_LONG).show();
                    } else { // 京东支付返回结果
                        TradeResultInfo info = (TradeResultInfo) data
                                .getExtras().getSerializable(WePay.PAY_RESULT);
                        if (info != null) {
                            if (info.isResultSuccess()) {
                                Toast.makeText(this, "京东(" + "支付成功)", Toast.LENGTH_LONG).show();
                            } else if (info.isResultFail()) {
                                Toast.makeText(this, "京东(" + "支付失败)", Toast.LENGTH_LONG).show();
                            } else if (info.isUndo()) {
                                Toast.makeText(this, "京东(" + "取消支付)", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    break;
                case Constant.Scanner.REQUEST_CODE_PAYMENT:
                    Toast.makeText(this, "扫码结果：" + data.getStringExtra(Constant.Extra.RESULT_MESSAGE)
                            , Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }
    }

### 七、注意事项
1. Android 不允许在 UI 线程中进行网络请求，所以请求支付参数的时候请使用 Thread+Handler 或者使用 AsyncTask 。 demo 中的示例程序使用的就是 Thread+Handler 方式请求用户服务器。
2. 汇元聚合支付及银联官方提供了支持所有 cpu 类型的 .so 文件，但百度只提供了部分 cpu 类型的 .so 文件，所以用户需要使用百度支付的话要注意银联只能使用部分 cpu 类型的 .so 文件 (详见 demo )。

3. 微信支付接入注意
    wx 渠道是通过向微信客户端发起请求进行支付的，要求： 手机必须安装微信。 应用包名和签名必须与填写在微信开放平台上的一致，微信平台上的签名需是 MD5 且不带冒号的格式。
    调试的时候必须打包出来测试，否则无法调用微信支付控件。
    应用包名：是在APP项目配置文件AndroidManifest.xml中声明的package值，例如DEMO中的package="com.mergepay.sdk.demo"。
    应用签名：根据项目的应用包名和编译使用的keystore，可由签名工具生成一个32位的md5串，在调试的手机上安装签名工具后，运行可生成应用签名串

4. QQ钱包支付需在AndroidManifest.xml中配QQAppid

### 八、关于定制
用户可以根据需求自行定制一个或者多个支付渠道。但是定制SDK的时候需要注意以下几点

1. `MergepaySDK_v1.0.aar`这个 aar 包是必须的。
2. `NativeInterfaceActivity`必须在 AndroidManifest.xml 文件里面声明。

3. 权限

    ##### 微信/QQ支付渠道是通过向“微信/QQ”客户端发起请求进行支付的，要求手机必须安装微信/QQ。如果没有安装， Mergepay SDK 会在支付结果中给予通知。
    ##### 各支付方式所需权限可根据上述快速集成中的说明自行配制，通用权限为共有权限。
4. 依赖

    ##### 各支付方式所依赖文件可根据上述快速集成中的说明自行配制。
5. 用户如果选择不使用某种渠道，可以把该渠道相关的权限声明、 Activity 声明、依赖文件从工程中删除。

### 九、混淆设置
用户进行 apk 混淆打包的时候，为了不影响 Mergepay SDK 以及渠道 SDK 的使用，请在 proguard-rules 中添加一下混淆规则。

######支付宝
	-dontwarn com.alipay.**
	-keep class com.alipay.** {*;}

	-dontwarn  com.ta.utdid2.**
	-keep class com.ta.utdid2.** {*;}

	-dontwarn  com.ut.device.**
	-keep class com.ut.device.** {*;}

######微信、QQ
	-dontwarn  com.tencent.**
	-keep class com.tencent.** {*;}

######银联
	-dontwarn com.unionpay.**
	-keep class com.unionpay.** {*;}

	-dontwarn cn.gov.pbc.**
    -keep class cn.gov.pbc.** {*;}

    -dontwarn com.UCMobile.PayPlugin.**
    -keep class com.UCMobile.PayPlugin.** {*;}

######百度钱包
	-dontwarn com.baidu.**
	-keep class com.baidu.** {*;}

	-dontwarn com.gieseckedevrient.android.**
    -keep class com.gieseckedevrient.android.** {*;}

    -dontwarn com.lenovo.**
    -keep class com.lenovo.** {*;}

    -dontwarn com.samsung.android.sdk.**
    -keep class com.samsung.android.sdk.** {*;}

######京东钱包混淆
    -dontwarn wangyin.app.**
    -keep class wangyin.app.** {*;}

    -dontwarn com.google.android.gms.**
    -keep class com.google.android.gms.** {*;}

    -dontwarn sun.misc.**
    -keep class sun.misc.** {*;}

    -dontwarn com.wangyin.**
    -keep class com.wangyin.** {*;}

######mergepaySDK混淆

    -dontwarn com.mergepay.sdk.**
    -keep class com.mergepay.sdk.** {*;}

    -dontwarn com.yjt.bridge.**
    -keep class com.yjt.bridge.** {*;}

    -dontwarn com.google.gson.**
    -keep class com.google.gson.** {*;}

    -dontwarn com.google.zxing.**
    -keep class com.google.zxing.** {*;}

    -dontwarn android.net.**
    -dontwarn org.apache.**
    -keep class android.net.** {*;}
    -keep class com.android.internal.http.** {*;}
    -keep class org.apache.** {*;}

