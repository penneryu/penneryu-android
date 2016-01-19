package com.penner.android.model;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.penner.android.utils.Constants;
import com.penner.android.utils.LogUtils;
import com.penner.android.utils.PennerUtils;
import com.squareup.okhttp.OkHttpClient;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by PennerYu on 15/10/14.
 */
public class PennerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        leanCloudInit();
        frescoInit(getApplicationContext());
    }

    private void leanCloudInit() {
        AVOSCloud.initialize(this, "zxm3yzVUdbppiv1l1BSXredl", "KN5vk3uPcWqHLBTG71Cdzhk7");
    }

    private void frescoInit(Context context) {

        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

        OkHttpClient okHttpClient = new OkHttpClient();
        ImagePipelineConfig.Builder configBuilder =
                OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
        configureCaches(configBuilder, context);
        configureLoggingListeners(configBuilder);

        ImagePipelineConfig imagePipelineConfig = configBuilder.build();
        Fresco.initialize(context, imagePipelineConfig);
    }

    private static void configureCaches(
            ImagePipelineConfig.Builder configBuilder,
            Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                Constants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                Constants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        configBuilder
                .setBitmapMemoryCacheParamsSupplier(() -> bitmapCacheParams)
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(PennerUtils.getImageCacheDirectory(context))
                                .setBaseDirectoryName(Constants.IMAGE_PIPELINE_CACHE_DIR)
                                .setMaxCacheSize(Constants.MAX_DISK_CACHE_SIZE)
                                .build());
    }

    private static void configureLoggingListeners(ImagePipelineConfig.Builder configBuilder) {
        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        configBuilder.setRequestListeners(requestListeners);
    }
}
