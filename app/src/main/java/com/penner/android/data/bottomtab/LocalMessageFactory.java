package com.penner.android.data.bottomtab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.penner.android.data.LocalFactoryBase;

/**
 * Created by PennerYu on 15/11/4.
 */
public class LocalMessageFactory extends LocalFactoryBase<MessageInfo> {

    public static final String tableName = "message_info";

    public LocalMessageFactory(Context context) {
        super(context);
    }

    public static void createTable(SQLiteDatabase db) {
        String sql = "create table if not exists message_info(id integer primary key,type int,direct int,state int,convId int,flags int,time long,fromId varchar,toId varchar,body text)";
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
    protected MessageInfo createModel(Cursor cursor) {
        MessageInfo info = new MessageInfo();
        info.id = cursor.getInt(cursor.getColumnIndex("id"));
        info.type = cursor.getInt(cursor.getColumnIndex("type"));
        info.direct = cursor.getInt(cursor.getColumnIndex("direct"));
        info.state = cursor.getInt(cursor.getColumnIndex("state"));
        info.convId = cursor.getInt(cursor.getColumnIndex("convId"));
        info.flags = cursor.getInt(cursor.getColumnIndex("flags"));
        info.time = cursor.getLong(cursor.getColumnIndex("time"));
        info.fromId = cursor.getString(cursor.getColumnIndex("fromId"));
        info.toId = cursor.getString(cursor.getColumnIndex("toId"));
        info.body = cursor.getString(cursor.getColumnIndex("body"));
        return info;
    }

    @Override
    protected void insertRecord(SQLiteDatabase db, MessageInfo record) {
        String sql = "insert into message_info(id,type,direct,state,convId,flags,time,fromId,toId,body) values(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql, new Object[] {null,
                record.type, record.direct,record.state,record.convId,record.flags,record.time,
                record.fromId, record.toId, record.body});
    }
}
