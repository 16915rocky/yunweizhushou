package com.chinamobile.yunweizhushou.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATAABASE_NAME = "yunweizhshou.db";
	private static final int DATABASE_VERSION = 5;

	public DatabaseHelper(Context context) {
		super(context, DATAABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists " + UserTable.USERTABLE_NAME + " (" + UserTable.USERNAME
				+ " varchar primary key, " + UserTable.PASSWORD + " varchar, " + UserTable.SESSIONID + " varchar, "
				+ UserTable.GESTURE + " varchar, " +UserTable.PHONE + " varchar, "+UserTable.NICKNAME+"  varchar ) ");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists " + UserTable.USERTABLE_NAME);
		db.execSQL("create table if not exists " + UserTable.USERTABLE_NAME + " (" + UserTable.USERNAME
				+ " varchar primary key, " + UserTable.PASSWORD + " varchar, " + UserTable.SESSIONID + " varchar, "
				+ UserTable.GESTURE + " varchar, "+ UserTable.PHONE + " varchar, "+UserTable.NICKNAME+" varchar ) ");
	}

}
