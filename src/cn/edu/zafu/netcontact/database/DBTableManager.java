package cn.edu.zafu.netcontact.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBTableManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBTableManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	public void drop(){
		String sql_person = "DROP TABLE [person]";
		db.execSQL(sql_person);
		System.out.println("drop person");
		
		String sql_subcompany="DROP TABLE [subcompany]";
		db.execSQL(sql_subcompany);
		System.out.println("drop subcompany");
		
		String sql_company="DROP TABLE [company] ";
		db.execSQL(sql_company);
		System.out.println("drop company");
		
		String sql_department="DROP TABLE [department]";
		db.execSQL(sql_department);
		System.out.println("drop department");
		
		String sql_workgroup="DROP TABLE [workgroup]";
		db.execSQL(sql_workgroup);
		System.out.println("drop workgroup");
	}
	public void create(){
		String sql_person = "CREATE TABLE [person] ([PerId] VARCHAR(50)  UNIQUE NOT NULL PRIMARY KEY,[RealName] VARCHAR(20)  NULL,[SubCompany] VARCHAR(50)  NULL,[Company] VARCHAR(50)  NULL,[Department] VARCHAR(50)  NULL,[Workgroup] VARCHAR(50)  NULL,[Tel1] VARCHAR(20)  NOT NULL,[Tel2] VARCHAR(20)  NULL,[Tel3] VARCHAR(20)  NULL,[ModifyDate] VARCHAR(30)  NULL)";
		db.execSQL(sql_person);
		System.out.println("create person");
		
		String sql_subcompany="CREATE TABLE [subcompany] ([id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[RealName] VARCHAR(50)  NOT NULL,[parent] INTEGER  NOT NULL)";
		db.execSQL(sql_subcompany);
		System.out.println("create subcompany");
		
		String sql_company="CREATE TABLE [company] ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[RealName] VARCHAR(50)  NOT NULL,[parent] INTEGER  NOT NULL)";
		db.execSQL(sql_company);
		System.out.println("create company");
		
		String sql_department="CREATE TABLE [department] ([id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[RealName] VARCHAR(50)  NOT NULL,[parent] INTEGER  NOT NULL)";
		db.execSQL(sql_department);
		System.out.println("create department");
		
		String sql_workgroup="CREATE TABLE [workgroup] ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[RealName] VARCHAR(50)  NOT NULL,[parent] INTEGER  NOT NULL)";
		db.execSQL(sql_workgroup);
		System.out.println("create workgroup");
	}
}
