package cn.edu.zafu.netcontact.fregment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.activity.CallActivity;
import cn.edu.zafu.netcontact.database.DBPersonManager;
import cn.edu.zafu.netcontact.model.Person;

public class SearchFragment extends Fragment {

	private final String[] from = new String[] { "iv_icon", "tv_name",
			"tv_organization", "tv_phone" };
	private final int[] to = new int[] { R.id.iv_icon, R.id.tv_name,
			R.id.tv_organization, R.id.tv_phone };
	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	private EditText search_content;
	private Button search;
	private ListView search_result;
	private SimpleAdapter adapter;
	private String phone;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_search, container, false);
		init(view);
		return view;
	}

	public void init(View view) {

		search_content = (EditText) view.findViewById(R.id.search_content);
		search = (Button) view.findViewById(R.id.search);
		search_result = (ListView) view.findViewById(R.id.search_listview);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String content = search_content.getText().toString();
				DBPersonManager mgr = new DBPersonManager(getActivity());
				List<Person> persons = mgr.query(content);

				list.clear();
				adapter.notifyDataSetChanged();
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
					adapter.notifyDataSetChanged();
				}
				mgr.closeDB();
			}
		});
		adapter = new SimpleAdapter(getActivity(), list,
				R.layout.listview_item, from, to);
		search_result.setAdapter(adapter);
		search_result.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				phone = ((TextView) view.findViewById(R.id.tv_phone)).getText()
						.toString();
				new AlertDialog.Builder(new ContextThemeWrapper(getActivity(),
						R.style.dialog))
						.setMessage(
								"请选择操作！\n联系号码为："
										+ ((TextView) view
												.findViewById(R.id.tv_phone))
												.getText())
						.setPositiveButton("拨打电话",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										/*
										 * Toast.makeText(getApplicationContext()
										 * , "拨打电话", Toast.LENGTH_SHORT)
										 * .show();
										 */
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
										/*
										 * Toast.makeText(getApplicationContext()
										 * , "发送短信", Toast.LENGTH_SHORT)
										 * .show();
										 */
										Uri uri = Uri.parse("smsto:" + phone);
										Intent intent = new Intent(
												Intent.ACTION_SENDTO, uri);

										startActivity(intent);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whichButton) {
										Toast.makeText(getActivity(), "取消",
												Toast.LENGTH_SHORT).show();
									}
								}).create().show();
			}
		});

	}

}