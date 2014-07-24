package cn.edu.zafu.netcontact.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.database.DBPersonManager;
import cn.edu.zafu.netcontact.model.Person;

public class CallActivity extends Activity {
	private ListView listView;
	private Intent intent;
	private Bundle bundle;
	private String subcompany;
	private String company;
	private String department;
	private String workgroup;
	private String phone;
	private final String[] from = new String[] { "iv_icon", "tv_name",
			"tv_organization", "tv_phone" };
	private final int[] to = new int[] { R.id.iv_icon, R.id.tv_name,
			R.id.tv_organization, R.id.tv_phone };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		init();
	}

	private void init() {
		intent = getIntent();
		bundle = intent.getExtras();
		subcompany = bundle.getString("subcompany_name");
		company = bundle.getString("company_name");
		department = bundle.getString("department_name");
		workgroup = bundle.getString("workgroup_name");
		listView = (ListView) findViewById(R.id.listview);

		// listView.setSelector(R.drawable.listview_selector);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getSimpleData(), R.layout.listview_item, from, to);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				phone=((TextView) view
						.findViewById(R.id.tv_phone))
						.getText().toString();
				new AlertDialog.Builder(new ContextThemeWrapper(
						CallActivity.this, R.style.dialog))
						.setMessage(
								"请选择操作！\n联系号码为："
										+ ((TextView) view
												.findViewById(R.id.tv_phone))
												.getText())
						.setPositiveButton("拨打电话",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										/*Toast.makeText(getApplicationContext(),
												"拨打电话", Toast.LENGTH_SHORT)
												.show();*/
										Uri uri = Uri.parse("tel:" + phone);
										Intent intent = new Intent(
												Intent.ACTION_DIAL, uri);

										startActivity(intent);
									}
								})
						.setNeutralButton("发送短信",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										/*Toast.makeText(getApplicationContext(),
												"发送短信", Toast.LENGTH_SHORT)
												.show();*/
										Uri uri = Uri.parse("smsto:"+phone);
										Intent intent= new Intent(Intent.ACTION_SENDTO,uri);  
										
										startActivity(intent);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										Toast.makeText(getApplicationContext(),
												"取消", Toast.LENGTH_SHORT)
												.show();
									}
								}).create().show();
			}
		});
	}

	private void filter() {
		if (subcompany.equals("未分组"))
			subcompany = "";
		if (company.equals("未分组"))
			company = "";
		if (department.equals("未分组"))
			department = "";
		if (workgroup.equals("未分组"))
			workgroup = "";
	}

	private List<Map<String, Object>> getSimpleData() {
		filter();

		DBPersonManager mgr = new DBPersonManager(getApplicationContext());
		List<Person> persons = mgr.query(subcompany, company, department,
				workgroup);
		mgr.closeDB();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < persons.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("iv_icon", R.drawable.logo_home);
			map.put("tv_name", persons.get(i).getRealName());
			map.put("tv_organization", persons.get(i).getSubCompany()
					+ persons.get(i).getCompany()
					+ persons.get(i).getDepartment()
					+ persons.get(i).getWorkgroup());
			map.put("tv_phone", persons.get(i).getTel1());
			list.add(map);
		}

		return list;

	}
}
