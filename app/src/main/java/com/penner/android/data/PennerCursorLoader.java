package com.penner.android.data;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by PennerYu on 15/10/13.
 */
public class PennerCursorLoader extends CursorLoader {

    public PennerCursorLoader(Context context) {
        super(context);
    }

    public PennerCursorLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
