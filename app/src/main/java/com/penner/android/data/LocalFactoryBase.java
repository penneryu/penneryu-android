package com.penner.android.data;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.penner.android.util.LogUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by PennerYu on 15/10/13.
 */
public abstract class LocalFactoryBase<T> {

    protected LocalOpenHelper helper = null;

    public LocalFactoryBase(Context context) {
        helper = new LocalOpenHelper(context);
    }

    protected abstract String getTableName();
    protected abstract String getprimaryKey();
    protected abstract T createModel(Cursor cursor);
    protected abstract void insertRecord(SQLiteDatabase db, T record);

    protected String getOrderColumnName() {
        return null;
    }

    protected String getOrderType() {
        return "desc";
    }

    protected long getMaxCount() {
        return 0;
    }

    /**
     * 插入一条记录
     * @param record
     */
    public void insertRecord(T record) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            if (getMaxCount() > 0) {
                long curCount = DatabaseUtils.queryNumEntries(db, getTableName());
                if (curCount >= getMaxCount()) {
                    deleteOldRecord(db);
                }
            }
            insertRecord(db, record);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 批量插入记录
     * @param records
     */
    public void insertRecord(List<T> records) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            int size = records.size();
            long max = getMaxCount();
            if (max > 0) {
                long curCount = DatabaseUtils.queryNumEntries(db, getTableName());
                long total = curCount + size;
                if (total >= max) {
                    long del = total - max + 1;
                    for (int i = 0; i < del; i ++) {
                        deleteOldRecord(db);
                    }
                }
            }
            for (T record : records) {
                insertRecord(db, record);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 查询所有记录
     * @return
     */
    public ArrayList<T> findRecords() {
        SQLiteDatabase db = null;
        ArrayList<T> result = new ArrayList();
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql;
            if (!TextUtils.isEmpty(getOrderColumnName())) {
                sql = String.format("select * from %s order by %s %s", getTableName(), getOrderColumnName(), getOrderType());
            } else {
                sql = String.format("select * from %s", getTableName());
            }
            cursor = db.rawQuery(sql, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    result.add(createModel(cursor));
                }
                return result;
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
        return result;
    }

    public void sqlBriteFindRecords(Action1<List<T>> callback) {
        SqlBrite sqlBrite = SqlBrite.create();
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper);
        try {
            Observable<SqlBrite.Query> lists = db.createQuery(getTableName(), String.format("select * from %s", getTableName()));
            lists.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(query ->
                    query.asRows(cursor -> createModel(cursor))
                        .onBackpressureBuffer()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .toList()
                ).subscribe(callback, throwable -> throwable.printStackTrace());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 分页查询
     * @param firstResult
     * @param maxResult
     * @return
     */
    public ArrayList<T> findRecords(int firstResult, int maxResult) {
        return findRecords(firstResult, maxResult, null, null);
    }

    public ArrayList<T> findRecords(int firstResult, int maxResult, String selection, String[] selectionArgs) {
        SQLiteDatabase db = null;
        ArrayList<T> result = new ArrayList(maxResult);
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            cursor = findCursorRecords(db, firstResult, maxResult, selection, selectionArgs);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    result.add(createModel(cursor));
                }
                return result;
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return result;
    }

    /**
     * 返回分页记录的游标，适配CursorAdapter
     * @param firstResult
     * @param maxResult
     * @return
     */
    public Cursor findCursorRecords(SQLiteDatabase db, int firstResult, int maxResult, String selection, String[] selectionArgs) {
        String sql;
        String where = "";
        String[] args = selectionArgs;
        if (!TextUtils.isEmpty(selection)) {
            int length = args.length;
            where = String.format("where %s", selection);
            args = new String[length + 2];
            for (int i = 0; i < length; i++) {
                args[i] = selectionArgs[i];
            }
            args[length] = String.valueOf(firstResult);
            args[length + 1] = String.valueOf(maxResult);
        }
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("select * from %s %s order by %s %s limit ?,?", getTableName(), where, getOrderColumnName(), getOrderType());
        } else {
            sql = String.format("select * from %s %s limit ?,?", getTableName(), where);
        }
        try {
            return db.rawQuery(sql, args);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 返回所有记录的游标，适配CursorAdapter
     * @return
     */
    public Cursor findCursorRecords(SQLiteDatabase db) {
        String sql;
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("select * from %s order by %s %s", getTableName(), getOrderColumnName(), getOrderType());
        } else {
            sql = String.format("select * from %s", getTableName());
        }
        try {
            return db.rawQuery(sql, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 根据主键查询一条记录
     * @param primaryKey
     * @return
     */
    public T findRecord(int primaryKey) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s where %s=?", getTableName(), getprimaryKey());
            cursor = db.rawQuery(sql, new String[] {String.valueOf(primaryKey)});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
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
        return null;
    }

    /**根据一个键值对查询
     *
     * @param property
     * @param val
     * @return
     */
    public T findByProperty(String property, String value) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s where %s=?", getTableName(), property);
            cursor = db.rawQuery(sql, new String[] {value});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
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
        return null;
    }

    /**
     * 删除所有记录
     */
    public void deletedRecords() {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("delete from %s", getTableName());
            db.execSQL(sql, new String[]{});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 根据主键删除
     * @param primaryKey
     */
    public void deletedRecord(int primaryKey) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            String sql = String.format("delete from %s where %s=?", getTableName(), getprimaryKey());
            db.execSQL(sql, new Integer[] {primaryKey});
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 记录数量
     * @return
     */
    public long getRecordCount() {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            return DatabaseUtils.queryNumEntries(db, getTableName());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public long getRecordCount(String selection, String[] selectionArgs) {
        SQLiteDatabase db = null;
        try {
            db = helper.getWritableDatabase();
            return DatabaseUtils.queryNumEntries(db, getTableName(), selection, selectionArgs);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 删除旧的记录
     * @param db
     */
    private void deleteOldRecord(SQLiteDatabase db) {
        String sql;
        if (!TextUtils.isEmpty(getOrderColumnName())) {
            sql = String.format("delete from %s where %s in (select %s from %s order by %s %s limit 1)",
                    getTableName(), getprimaryKey(), getprimaryKey(), getTableName(), getOrderColumnName(), "desc".equals(getOrderType()) ? "asc" : "desc");
        } else {
            sql = String.format("delete from %s where %s in (select %s from %s limit 1)",
                    getTableName(), getprimaryKey(), getprimaryKey(), getTableName());
        }
        db.execSQL(sql);
    }

    /**
     * 查询最新的一条记录
     */
    public T findNewestRecord() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();
            String sql = String.format("select * from %s order by %s %s limit 1", getTableName(), getOrderColumnName(), getOrderType());
            cursor = db.rawQuery(sql, new String[] {});
            T result = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    result = createModel(cursor);
                }
            }
            return result;
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
        return null;
    }

    /**
     * drop table
     * */
    public static void dropTable(SQLiteDatabase db, String tableName){
        try {
            db.execSQL(String.format("DROP TABLE IF EXISTS %s", tableName));
        } catch (Exception e) {
            LogUtils.e("LocalFactoryBase", "drop table:" + tableName + "error.");
        }
    }
}
