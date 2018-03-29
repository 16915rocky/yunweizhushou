package com.chinamobile.yunweizhushou.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chinamobile.yunweizhushou.bean.UserBean;

public class 	DBUserManager {

	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public DBUserManager(Context context) {
		dbHelper = new DatabaseHelper(context);
	}

	private void insert(UserBean userBean) {
		if (dbHelper == null) {
			return;
		}
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(UserTable.USERNAME, userBean.getUserName());
		values.put(UserTable.PASSWORD, userBean.getPassword());
		values.put(UserTable.SESSIONID, userBean.getSessionId());
		values.put(UserTable.GESTURE, userBean.getGesture());
		values.put(UserTable.PHONE, userBean.getPhone());
		values.put(UserTable.NICKNAME, userBean.getNickName());
		db.insert(UserTable.USERTABLE_NAME, null, values);
		db.close();
	}

	public void saveUserInfo(UserBean userBean) {
		UserBean ub = getUserByUserName(userBean.getUserName());
		if (ub != null) {
			delete(userBean.getUserName());
		}
		insert(userBean);
	}

	public void delete(String userName) {
		if (dbHelper == null)
			return;
		db = dbHelper.getWritableDatabase();
		db.delete(UserTable.USERTABLE_NAME, UserTable.USERNAME + " = ?", new String[] { userName });
		db.close();
	}

	// public void clearAll(){
	// if (dbHelper == null)
	// return;
	// db = dbHelper.getWritableDatabase();
	// db.delete(UserTable.USERTABLE_NAME, null, null);
	// db.close();
	// }

	public UserBean getUserByUserName(String userName) {
		if (dbHelper == null) {
			return null;
		}
		db = dbHelper.getReadableDatabase();
		UserBean bean = new UserBean();
		Cursor cursor = null;
		String sql = "select * from " + UserTable.USERTABLE_NAME + " where " + UserTable.USERNAME + "=?";
		cursor = db.rawQuery(sql, new String[] { userName });
		while (cursor.moveToNext()) {
			bean.setUserName(cursor.getString(0));
			bean.setPassword(cursor.getString(1));
			bean.setSessionId(cursor.getString(2));
			bean.setGesture(cursor.getString(3));
			bean.setPhone(cursor.getString(4));
			bean.setNickName(cursor.getString(5));
		}
		cursor.close();
		db.close();
		return bean;
	}

	public UserBean getLastUser() {
		if (dbHelper == null) {
			return null;
		}
		db = dbHelper.getReadableDatabase();
		UserBean bean = new UserBean();
		Cursor cursor = null;
		String sql = "select * from " + UserTable.USERTABLE_NAME;
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			bean.setUserName(cursor.getString(0));
			bean.setPassword(cursor.getString(1));
			bean.setSessionId(cursor.getString(2));
			bean.setGesture(cursor.getString(3));
			bean.setPhone(cursor.getString(4));
			bean.setNickName(cursor.getString(5));

		}
		cursor.close();
		db.close();
		return bean;
	}

	public UserBean getUserWithPhone() {
		if (dbHelper == null) {
			return null;
		}
		db = dbHelper.getReadableDatabase();
		UserBean bean = new UserBean();
		Cursor cursor = null;
		String sql = "select * from " + UserTable.USERTABLE_NAME;
		cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			bean.setUserName(cursor.getString(0));
			bean.setPassword(cursor.getString(1));
			bean.setSessionId(cursor.getString(2));
			bean.setGesture(cursor.getString(3));
			bean.setPhone(cursor.getString(4));
		}
		cursor.close();
		db.close();
		return bean;
	}

}
