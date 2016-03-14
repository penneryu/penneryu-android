# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/sh587/softwares/develop/android-sdk-macosx/tools/proguard/proguard-android.txt
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

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

#不混淆第三方jar
-keep public class org.apache.** {*;}
-keep public class com.crashlytics.** {*;}
-keep public class android.ppmedia.** {*;}
-keep public class android.support.v4.** {*;}
-keep public class android.support.v7.** {*;}
-keep public class android.annotation.** {*;}
-keep class com.nostra13.universalimageloader.** {*;}
-keep interface com.nostra13.universalimageloader.** {*;}
-keep class com.penner.android.R.**{*;}

#不混淆native方法
-keepclasseswithmembernames class * {
    native <methods>;
}

# 不混淆自定义控件类
-keep public class * extends android.widget.** {*;}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}


#butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

#kotlin
-dontwarn kotlin.**
-dontwarn org.w3c.dom.events.*
-dontwarn org.jetbrains.kotlin.di.InjectorForRuntimeDescriptorLoader

#retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Keep our interfaces so they can be used by other ProGuard rules.
# See http://sourceforge.net/p/proguard/bugs/466/
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**

-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keep class io.fabric.sdk.android.** { *; }
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.commonsware.cwac.anddown.** { *; }
-keep class org.eclipse.paho.** { *; }

-dontwarn com.baidu.**
-keep class com.baidu.**{*; }

# We only want obfuscation
-keepattributes InnerClasses,Signature

# Sdk
-keep public interface com.zendesk.** { *; }
-keep public class com.zendesk.** { *; }
-dontwarn java.awt.**

# Appcompat and support
-keep interface android.support.v7.** { *; }
-keep class android.support.v7.** { *; }
-keep interface android.support.v4.** { *; }
-keep class android.support.v4.** { *; }

# Gson
-keep interface com.google.gson.** { *; }
-keep class com.google.gson.** { *; }

# Retrofit
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class retrofit.** { *; }
-keep interface retrofit.** { *; }
-dontwarn rx.**
-dontwarn com.google.appengine.api.urlfetch.**
-dontwarn okio.**
-keep class rx.** { *; }

#Picasso
-dontwarn okhttp3.**
-dontwarn com.squareup.okhttp.**
-dontwarn com.squareup.sqlbrite.**
-keep class okhttp3.** { *; }

-keep class com.tencent.mm.sdk.** { *; }
-keep class com.alibaba.** { *; }
-keep class com.newrelic.** { *; }
-keep class com.avos.** { *; }
-keep class org.springframework.** { *; }
-keep class javax.** { *; }
-keep class java.** { *; }