package cn.edu.zafu.netcontact.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.zafu.netcontact.model.Person;

public class DBPersonManager {
	private DBHelper helper;
	private SQLiteDatabase db;

	public DBPersonManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public void save(Person person) {
		db.beginTransaction();
		db.execSQL("insert into person values(?,?,?,?,?,?,?,?,?,?)",
				new Object[] { person.getPerId(),person.getRealName(),person.getSubCompany(),person.getCompany(),person.getDepartment(),person.getWorkgroup(),person.getTel1(),person.getTel2(),person.getTel3(),person.getModifyDate()});
		db.setTransactionSuccessful();
		db.endTransaction();
		System.out.println("------------added a person------------");
	}

	public void save(List<Person> persons) {
		db.beginTransaction();

		for (Person person : persons) {
			db.execSQL("insert into person values(?,?,?,?,?,?,?,?,?,?)",
					new Object[] { person.getPerId(),person.getRealName(),person.getSubCompany(),person.getCompany(),person.getDepartment(),person.getWorkgroup(),person.getTel1().trim(),person.getTel2().trim(),person.getTel3().trim(),person.getModifyDate()});
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		System.out.println("----------add persons-----------");
	}

	public void update(Person person) {
		ContentValues cv = new ContentValues();
		cv.put("RealName", person.getRealName());
		cv.put("SubCompany", person.getSubCompany());
		cv.put("Company", person.getCompany());
		cv.put("Department", person.getDepartment());
		cv.put("Workgroup", person.getWorkgroup());
		cv.put("Tel1", person.getTel1());
		cv.put("Tel2", person.getTel2());
		cv.put("Tel3", person.getTel3());
		cv.put("ModifyDate", person.getModifyDate());

		db.update("person", cv, "PerId=?", new String[] {person.getPerId() });
		System.out.println("----------update a person------------");
	}

	public void delete(Person person) {
		db.delete("person", "PerId=?",
				new String[] {person.getPerId()});
		System.out.println("----------delete a person-------------");
	}

	public List<Person> query() {
		ArrayList<Person> persons = new ArrayList<Person>();
		Cursor c = getCursor();
		while (c.moveToNext()) {
			Person person = new Person();
			person.setPerId(c.getString(c.getColumnIndex("PerId")));
			person.setRealName(c.getString(c.getColumnIndex("RealName")));
			person.setSubCompany(c.getString(c.getColumnIndex("SubCompany")));
			person.setCompany(c.getString(c.getColumnIndex("Company")));
			person.setDepartment(c.getString(c.getColumnIndex("Department")));
			person.setWorkgroup(c.getString(c.getColumnIndex("Workgroup")));
			person.setTel1(c.getString(c.getColumnIndex("Tel1")));
			person.setTel2(c.getString(c.getColumnIndex("Tel2")));
			person.setTel3(c.getString(c.getColumnIndex("Tel3")));
			person.setModifyDate(c.getString(c.getColumnIndex("ModifyDate")));
			persons.add(person);
		}
		c.close();
		System.out.println("------------query persons------------");
		return persons;
	}
	public List<Person> query(String subcompany,String company,String department,String workgroup) {
		ArrayList<Person> persons = new ArrayList<Person>();
		String sql="select * from person where SubCompany='SUBCOMPANY' and Company='COMPANY'  and Department='DEPARTMENT'  and Workgroup='WORKGROUP'";
		sql=sql.replace("SUBCOMPANY", subcompany);
		sql=sql.replace("COMPANY", company);
		sql=sql.replace("DEPARTMENT", department);
		sql=sql.replace("WORKGROUP", workgroup);
		Cursor c =db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Person person = new Person();
			person.setPerId(c.getString(c.getColumnIndex("PerId")));
			person.setRealName(c.getString(c.getColumnIndex("RealName")));
			person.setSubCompany(c.getString(c.getColumnIndex("SubCompany")));
			person.setCompany(c.getString(c.getColumnIndex("Company")));
			person.setDepartment(c.getString(c.getColumnIndex("Department")));
			person.setWorkgroup(c.getString(c.getColumnIndex("Workgroup")));
			person.setTel1(c.getString(c.getColumnIndex("Tel1")));
			person.setTel2(c.getString(c.getColumnIndex("Tel2")));
			person.setTel3(c.getString(c.getColumnIndex("Tel3")));
			person.setModifyDate(c.getString(c.getColumnIndex("ModifyDate")));
			persons.add(person);
		}
		c.close();
		System.out.println("------------query persons------------");
		return persons;
	}
	public List<Person> query(String content) {
		ArrayList<Person> persons = new ArrayList<Person>();
		String sql="select * from person where RealName like '%CONTENT%' or SubCompany like '%CONTENT%' or Company like '%CONTENT%'  or Department like '%CONTENT%' or Workgroup like '%CONTENT%' or Tel1 like '%CONTENT%' or Tel2 like '%CONTENT%' or Tel3 like '%CONTENT%'";
		sql=sql.replace("CONTENT", content);
		Cursor c =db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Person person = new Person();
			person.setPerId(c.getString(c.getColumnIndex("PerId")));
			person.setRealName(c.getString(c.getColumnIndex("RealName")));
			person.setSubCompany(c.getString(c.getColumnIndex("SubCompany")));
			person.setCompany(c.getString(c.getColumnIndex("Company")));
			person.setDepartment(c.getString(c.getColumnIndex("Department")));
			person.setWorkgroup(c.getString(c.getColumnIndex("Workgroup")));
			person.setTel1(c.getString(c.getColumnIndex("Tel1")));
			person.setTel2(c.getString(c.getColumnIndex("Tel2")));
			person.setTel3(c.getString(c.getColumnIndex("Tel3")));
			person.setModifyDate(c.getString(c.getColumnIndex("ModifyDate")));
			persons.add(person);
		}
		c.close();
		System.out.println("------------query persons------------");
		return persons;
	}
	public List<Person> queryByPhone(String content) {
		ArrayList<Person> persons = new ArrayList<Person>();
		String sql="select * from person where Tel1='CONTENT' or Tel2='CONTENT' or Tel3='CONTENT'";
		sql=sql.replace("CONTENT", content.trim());
		System.out.println(sql);
		Cursor c =db.rawQuery(sql, null);
		while (c.moveToNext()) {
			Person person = new Person();
			person.setPerId(c.getString(c.getColumnIndex("PerId")));
			person.setRealName(c.getString(c.getColumnIndex("RealName")));
			person.setSubCompany(c.getString(c.getColumnIndex("SubCompany")));
			person.setCompany(c.getString(c.getColumnIndex("Company")));
			person.setDepartment(c.getString(c.getColumnIndex("Department")));
			person.setWorkgroup(c.getString(c.getColumnIndex("Workgroup")));
			person.setTel1(c.getString(c.getColumnIndex("Tel1")));
			person.setTel2(c.getString(c.getColumnIndex("Tel2")));
			person.setTel3(c.getString(c.getColumnIndex("Tel3")));
			person.setModifyDate(c.getString(c.getColumnIndex("ModifyDate")));
			persons.add(person);
			System.out.println(person);
		}
		c.close();
		System.out.println("------------query persons------------");
		return persons;
	}
	public Cursor getCursor() {
		return db.rawQuery("select * from person", null);
	}

	public void closeDB() {
		db.close();
	}
}
