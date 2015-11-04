package com.penner.android.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.penner.android.data.bottomtab.LocalConversationFactory;
import com.penner.android.data.bottomtab.LocalMessageFactory;

/**
 * Created by PennerYu on 15/10/13.
 */
public class LocalOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "penneryu_android.db";
    private static final int DATABASE_VERSION = 1;

    public LocalOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LocalConversationFactory.createTable(db);
        LocalMessageFactory.createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
