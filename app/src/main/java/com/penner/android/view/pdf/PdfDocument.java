package com.penner.android.view.pdf;

import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by penneryu on 16/7/16.
 */
public class PdfDocument {

    public final Object Lock = new Object();

    /*package*/ PdfDocument(){}

    /*package*/ long mNativeDocPtr;

    /*package*/ final Map<Integer, Long> mNativePagesPtr = new ArrayMap<>();
    public boolean hasPage(int index){ return mNativePagesPtr.containsKey(index); }
}
