package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by wzq930102 on 2017/8/2.
 */

public class RecordDao {
    private HistorySpliteOpenHelper mySqliteOpenHelper;

    public RecordDao(Context context) {
        //创建一个帮助类对象
        mySqliteOpenHelper = new HistorySpliteOpenHelper(context);

    }
    public void add(RecordDao bean){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getWritableDatabase();
        //sql:sql语句，  bindArgs：sql语句中占位符的值
//        db.execSQL("insert into record(name,pic_url,percentage,video_url) values(?,?);", new Object[]{bean.name,bean.pic_url,bean.percentage,bean.video_url});

    }

    public void del(String name){

        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句，  bindArgs：sql语句中占位符的值
        db.execSQL("delete from record where name=?;", new Object[]{name});


    }
    public void update(RecordDao bean){
        //执行sql语句需要sqliteDatabase对象
        //调用getReadableDatabase方法,来初始化数据库的创建
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        //sql:sql语句，  bindArgs：sql语句中占位符的值
//        db.execSQL("update record set video_url=? where name=?;", new Object[]{bean.name,bean.pic_url,bean.percentage,bean.video_url});


    }
}