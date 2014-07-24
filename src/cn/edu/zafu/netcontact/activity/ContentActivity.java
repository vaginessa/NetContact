package cn.edu.zafu.netcontact.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.database.DBModelManager;
import cn.edu.zafu.netcontact.model.Model;

public class ContentActivity extends Activity {

	private ExpandableListView expandableListView = null;
	private TextView title;
	List<Map<String, String>> groups = new ArrayList<Map<String, String>>();
	List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
	String company_name;
	String subcompany_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content);
		expandableListView = (ExpandableListView) findViewById(R.id.expandableList);
		title = (TextView) findViewById(R.id.company_name);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		company_name = bundle.getString("company_name");
		subcompany_name = bundle.getString("subcompany_name");
		Integer company_id = bundle.getInt("company_id");

		title.setText(subcompany_name + "-" + company_name);
		DBModelManager department = new DBModelManager(getApplicationContext(),
				"department");
		List<Model> dep = department.query(company_id.toString());

		for (int i = 0; i < dep.size(); i++) {
			Map<String, String> title = new HashMap<String, String>();
			title.put("parent", dep.get(i).getRealName());
			DBModelManager workgroup = new DBModelManager(
					getApplicationContext(), "workgroup");
			List<Model> wor = workgroup.query(dep.get(i).getId().toString());
			List<Map<String, String>> child = new ArrayList<Map<String, String>>();
			for (int j = 0; j < wor.size(); j++) {

				Map<String, String> content = new HashMap<String, String>();
				content.put("child", wor.get(j).getRealName());
				child.add(content);
			}
			workgroup.closeDB();
			childs.add(child);
			groups.add(title);

		}
		department.closeDB();

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				this, groups, R.layout.parent, new String[] { "parent" },
				new int[] { R.id.parent }, childs, R.layout.child,
				new String[] { "child" }, new int[] { R.id.child });
		// expandableListView.setIndicatorBounds(DensityUtil.dip2px(getApplicationContext(),
		// 20), DensityUtil.dip2px(getApplicationContext(), 40));
		expandableListView.setAdapter(adapter);
		expandableListView.setOnChildClickListener(listener);

	}

	private OnChildClickListener listener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			/*
			 * Toast.makeText( getApplicationContext(), "组：" + groupPosition +
			 * " 孩子：" + childPosition + " " +
			 * groups.get(groupPosition).get("parent") + " " +
			 * childs.get(groupPosition).get(childPosition) .get("child"),
			 * Toast.LENGTH_SHORT).show();
			 */

			Intent intent = new Intent(ContentActivity.this, CallActivity.class);
			String department_name = groups.get(groupPosition).get("parent");
			String workgroup_name = childs.get(groupPosition)
					.get(childPosition).get("child");
			if (subcompany_name.equals("未分组"))
				subcompany_name = "";
			if (company_name.equals("未分组"))
				company_name = "";
			if (department_name.equals("未分组"))
				department_name = "";
			if (workgroup_name.equals("未分组"))
				workgroup_name = "";
			intent.putExtra("subcompany_name", subcompany_name);
			intent.putExtra("company_name", company_name);
			intent.putExtra("department_name", department_name);
			intent.putExtra("workgroup_name", workgroup_name);
			startActivity(intent);
			System.out.println("---------" + subcompany_name + company_name
					+ department_name + workgroup_name);
			return false;
		}

	};
}
