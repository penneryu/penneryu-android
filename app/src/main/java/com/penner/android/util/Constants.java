package com.penner.android.util;

import com.facebook.common.util.ByteConstants;

/**
 * Created by PennerYu on 15/10/23.
 */
public final class Constants {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;

    public static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";

    public final static String CONVINFO_EXTRA = "convinfo";
}
