package net.bfci.android.clubnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * Created by Genius on 30/07/2018.
 */


public class DbAdapterClubTable {
    public static final String KEY_name ="club_name";
    public static final String KEY_rate = "rate";
    public static final String imag = "image";
    public static final String short_name="short_name";
    public static final String foundation_year="foundation_year";
    public static final String stadium="stadium";
    public static final String trainer="trainer";
    public static final String star="star";
    public static final String nick_name="nick_name";
    public static final String local_ch="local_ch";
    public static final String world_ch="world_ch";
    private static final String DATABASE_NAME = "MyDB";
    private static final String DATABASE_TABLE1 = "my_teams";
    private static final String DATABASE_TABLE2 = "my_leagues";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE1 =
            "create table if not exists my_teams (club_name text not null, "
                    +"short_name text not null, " +"foundation_year text not null,"+"stadium text not null," +"trainer text not null,"+"trainer text not null,"
                    +"star text not null,"+"nick_name text not null,"+"local_ch text not null,"+"world_ch text not null,"
                    +"image blob not null," +"rate text not null);";
    private static final String DATABASE_CREATE2 = "creat table if not exists my_leagues (club_name text not null,"+"rate text not null);";
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DbAdapterClubTable(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS studens");
            onCreate(db);
        }
    }
    //---opens the database---
    public DbAdapterClubTable open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    //---insert a contact into the database---
    public long insertStudent(byte[] image,String... datafields)
    {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_name, datafields[0]);
        initialValues.put(short_name, datafields[1]);
        initialValues.put(foundation_year, datafields[2]);
        initialValues.put( stadium, datafields[3]);
        initialValues.put(trainer, datafields[4]);
        initialValues.put( star, datafields[5]);
        initialValues.put(nick_name, datafields[6]);
        initialValues.put(KEY_rate, datafields[7]);
        initialValues.put(local_ch, datafields[8]);
        initialValues.put(world_ch, datafields[9]);
        initialValues.put(imag, image);
        db.insert(DATABASE_TABLE1, null, initialValues);
        initialValues = new ContentValues();
        initialValues.put(KEY_name,datafields[0]);
        initialValues.put(KEY_rate,datafields[7]);
        return db.insert(DATABASE_TABLE2,null, initialValues);

    }
    //---deletes a particular contact---
    public boolean deleteContact(String name)
    {
        db.delete(DATABASE_TABLE2, KEY_name+"="+name,null);
        return db.delete(DATABASE_TABLE1, KEY_name + "=" + name, null) > 0;
    }
    //---retrieves all the contacts---
    public Cursor getAllTeams()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_name, KEY_rate}, null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getContact(String name) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE1, new String[] {KEY_name,
                                short_name,foundation_year,stadium,trainer,star,nick_name,KEY_rate,local_ch,world_ch}, KEY_name + "=" + name, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            return mCursor;
        }
        return null;
    }
    //---updates a contact---
    public boolean updateContact(long rowId, String name){
        ContentValues value = new ContentValues();
        value.put(KEY_name, name);
        return db.update(DATABASE_TABLE1, value, KEY_name + "=" + rowId, null) > 0;
    }

}
