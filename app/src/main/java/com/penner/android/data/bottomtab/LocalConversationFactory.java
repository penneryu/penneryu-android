package com.penner.android.data.bottomtab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.penner.android.data.LocalFactoryBase;

/**
 * Created by PennerYu on 15/11/4.
 */
public class LocalConversationFactory extends LocalFactoryBase<ConversationInfo> {

    public static final String tableName = "conversation_info";

    public LocalConversationFactory(Context context) {
        super(context);
    }

    public static void createTable(SQLiteDatabase db) {
        String sql = "create table if not exists conversation_info(id integer primary key,type int,unreadCount int,flags int,time long,rank long,userId varchar,userName varchar,avatarUrl varchar,messageTips text)";
        db.execSQL(sql);
    }

    @Override
    protected String getTableName() {
        return tableName;
    }

    @Override
    protected String getprimaryKey() {
        return "id";
    }

    @Override
    protected String getOrderColumnName() {
        return "rank";
    }

    @Override
    protected ConversationInfo createModel(Cursor cursor) {
        ConversationInfo info = new ConversationInfo();
        info.id = cursor.getInt(cursor.getColumnIndex("id"));
        info.type = cursor.getInt(cursor.getColumnIndex("type"));
        info.unreadCount = cursor.getInt(cursor.getColumnIndex("unreadCount"));
        info.flags = cursor.getInt(cursor.getColumnIndex("flags"));
        info.time = cursor.getLong(cursor.getColumnIndex("time"));
        info.rank = cursor.getLong(cursor.getColumnIndex("rank"));
        info.userId = cursor.getString(cursor.getColumnIndex("userId"));
        info.userName = cursor.getString(cursor.getColumnIndex("userName"));
        info.avatarUrl = cursor.getString(cursor.getColumnIndex("avatarUrl"));
        info.messageTips = cursor.getString(cursor.getColumnIndex("messageTips"));
        return info;
    }

    @Override
    protected void insertRecord(SQLiteDatabase db, ConversationInfo record) {
        String sql = "insert into conversation_info(id,type,unreadCount,flags,time,rank,userId,userName,avatarUrl,messageTips) values(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new Object[] {null,
                record.type, record.unreadCount,record.flags,record.time,record.rank,
                record.userId, record.userName, record.avatarUrl,record.messageTips});
    }

    public void updateRecord(ConversationInfo record) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("update %s set time=?,messageTips=? where id=?", tableName);
            db.execSQL(sql, new Object[]{record.time, record.messageTips, record.id});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public ConversationInfo findRecord(String userId, String userName, String avatarUrl) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        ConversationInfo conversationInfo = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("select * from %s where userId=?", getTableName());
            cursor = db.rawQuery(sql, new String[]{String.valueOf(userId)});
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    conversationInfo = createModel(cursor);
                }
            }
            if (conversationInfo == null) {
                conversationInfo = new ConversationInfo();
                conversationInfo.type = ConversationInfo.PERSONAL_TYPE;
                conversationInfo.time = System.currentTimeMillis();
                conversationInfo.unreadCount = 0;
                conversationInfo.userName = userName;
                conversationInfo.userId = userId;
                conversationInfo.avatarUrl = avatarUrl;
                insertRecord(db, conversationInfo);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return conversationInfo;
    }
}
