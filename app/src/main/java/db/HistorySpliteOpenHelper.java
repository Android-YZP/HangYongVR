package db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wzq930102 on 2017/8/2.
 */

public class HistorySpliteOpenHelper extends SQLiteOpenHelper {

    public HistorySpliteOpenHelper(Context context) {
        super(context, "record.db", null, 1);
    }

    public HistorySpliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

      //oncreate方法是数据库第一次创建的时候会被调用;  特别适合做表结构的初始化,需要执行sql语句；SQLiteDatabase db可以用来执行sql语句
    @Override
    public void onCreate(SQLiteDatabase db) {
     //通过SQLiteDatabase执行一个创建表的sql语句
        db.execSQL("create table record (_id integer primary key autoincrement,name ,pic_url ,percentage ,video_url ");
    }

     //onUpgrade数据库版本号发生改变时才会执行； 特别适合做表结构的修改
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //添加一个字段
        System.out.println("更改数据库");
    }
}
