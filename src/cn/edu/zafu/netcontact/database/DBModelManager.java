package cn.edu.zafu.netcontact.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.zafu.netcontact.model.Model;

public class DBModelManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	private String table;

	public DBModelManager(Context context, String table) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		this.table = table;
	}
	public void save(Model model) {
		db.beginTransaction();
		String sql = "insert into TABLE values(?,?,?)";
		sql = sql.replace("TABLE", table);
		db.execSQL(sql,
				new Object[] { null, model.getRealName(), model.getParent() });
		db.setTransactionSuccessful();
		db.endTransaction();
		System.out.println(table+"------------added a model------------");
	}

	public void save(List<Model> models) {
		db.beginTransaction();
		String sql = "insert into TABLE values(?,?,?)";
		sql = sql.replace("TABLE", table);
		for (Model model : models) {
			db.execSQL(sql,new Object[] { null, model.getRealName(), model.getParent() });
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		System.out.println(table+"----------add models-----------");
	}

	public void update(Model model) {
		ContentValues cv = new ContentValues();
		cv.put("RealName", model.getRealName());
		cv.put("parent", model.getParent());
		db.update(table, cv, "id=?", new String[] { model.getId().toString() });
		System.out.println(table+"----------update a model------------");
	}

	public void delete(Model model) {
		db.delete(table, "id=?", new String[] { model.getId().toString() });
		System.out.println(table+"----------delete a model-------------");
	}

	public List<Model> query() {
		ArrayList<Model> models = new ArrayList<Model>();
		Cursor c = getCursor();
		while (c.moveToNext()) {
			Model model = new Model();
			model.setId(c.getInt(c.getColumnIndex("id")));
			model.setRealName(c.getString(c.getColumnIndex("RealName")));
			model.setParent(c.getInt(c.getColumnIndex("parent")));
			models.add(model);
		}
		c.close();
		System.out.println(table+"------------query models------------");
		return models;
	}
	public List<Model> query(String id) {
		ArrayList<Model> models = new ArrayList<Model>();
		Cursor c = db.rawQuery("select * from "+table+" where parent="+id, null);
		while (c.moveToNext()) {
			Model model = new Model();
			model.setId(c.getInt(c.getColumnIndex("id")));
			model.setRealName(c.getString(c.getColumnIndex("RealName")));
			model.setParent(c.getInt(c.getColumnIndex("parent")));
			models.add(model);
		}
		c.close();
		System.out.println(table+"------------query models------------");
		return models;
	}

	public Cursor getCursor() {
		return db.rawQuery("select * from "+table, null);
	}

	public void closeDB() {
		db.close();
	}
}
