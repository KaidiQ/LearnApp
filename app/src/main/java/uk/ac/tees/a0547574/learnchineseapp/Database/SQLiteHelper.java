package uk.ac.tees.a0547574.learn_chinese_app.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import uk.ac.tees.a0547574.learn_chinese_app.Info.NotepadBean;
import uk.ac.tees.a0547574.learn_chinese_app.Util.DBUtils;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;  //获取一个数据库类型的对象
    public SQLiteHelper(Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERSION);
        sqLiteDatabase=this.getWritableDatabase();  //获取到一个可以读写的数据库对象
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DBUtils.DATABASE_TABLE+"("+DBUtils.NOTEPAD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                DBUtils.NOTEPAD_CONTENT+" TEXT,"+DBUtils.NOTEPAD_TIME+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { }

    //添加数据
    public boolean insertData(String userContent,String userTime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME,userTime);
        long i= sqLiteDatabase.insert(DBUtils.DATABASE_TABLE,null,contentValues);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }
    //删除数据
    public boolean deleteData(String id){
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String[] strings=new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(DBUtils.DATABASE_TABLE,sql,strings)>0;
    }
    //修改数据
    public boolean updateData(String id,String userContent,String userTime){
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT,userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME,userTime);
        String sql=DBUtils.NOTEPAD_ID+"=?";
        String[] strings=new String[]{id};
        return sqLiteDatabase.update(DBUtils.DATABASE_TABLE,contentValues,sql,strings)>0;
    }
    //查询数据
    public List<NotepadBean> query(){
        List<NotepadBean> list=new ArrayList<NotepadBean>();
        Cursor cursor = sqLiteDatabase.query(DBUtils.DATABASE_TABLE,null,null,
                null,null,null,DBUtils.NOTEPAD_ID+" desc"); //查询并倒序排列结果
        if(cursor!=null){
            while(cursor.moveToNext()){
                NotepadBean noteInfo=new NotepadBean();
                //从cursor中的一条记录中获取数据
                int column1=cursor.getColumnIndex(DBUtils.NOTEPAD_ID);
                String id=String.valueOf(cursor.getInt(column1));
                int column2=cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT);
                String content=cursor.getString(column2);
                int column3=cursor.getColumnIndex(DBUtils.NOTEPAD_TIME);
                String time=cursor.getString(column3);
                //将数据赋给NotepadBean类型的变量，然后添加到list列表
                noteInfo.setId(id);
                noteInfo.setNotepadContent(content);
                noteInfo.setNotepadTime(time);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }
}
