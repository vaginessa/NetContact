package cn.edu.zafu.netcontact.fregment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.activity.ContentActivity;
import cn.edu.zafu.netcontact.adapter.MenuItemAdapter;
import cn.edu.zafu.netcontact.database.DBModelManager;
import cn.edu.zafu.netcontact.model.MenuItem;
import cn.edu.zafu.netcontact.model.Model;

public class ContactFragment extends Fragment {
	private GridView gv_menu;
	List<MenuItem> menus;
	List<Model> model;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_contact, container, false);
		findView(view);
		init();
		return view;
	}

	private void findView(View v) {
		gv_menu = (GridView) v.findViewById(R.id.gv_menu);
	}

	private void init() {
		menus = new ArrayList<MenuItem>();
		DBModelManager company=new DBModelManager(getActivity(), "company");
		model=company.query();
		
		for(int i=0;i<model.size();i++){
			menus.add(new MenuItem(R.drawable.menu_item, model.get(i).getRealName()));
		}
		company.closeDB();
		
		/*menus.add(new MenuItem(R.drawable.menu_item, "信息工程学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "理学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "文化学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "经济管理学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "外国语学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "工程学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "法政学院"));
		menus.add(new MenuItem(R.drawable.menu_item, "集贤学院"));*/
		// 计算margin
		int margin = (int) (getResources().getDisplayMetrics().density * 14 * 13 / 9);
		MenuItemAdapter adapter = new MenuItemAdapter(getActivity(), menus, margin);
		gv_menu.setAdapter(adapter);
		gv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//Toast.makeText(getActivity(), "当前项"+position+" "+menus.get(position).menuTitle, Toast.LENGTH_SHORT).show();
				String subcompany_name=((TextView)getActivity().findViewById(R.id.title_name)).getText().toString();
				Intent intent = new Intent(getActivity(),ContentActivity.class);
				intent.putExtra("company_name", model.get(position).getRealName());
				intent.putExtra("company_id", model.get(position).getId());
				intent.putExtra("subcompany_name", subcompany_name);
				System.out.println("---company_id---"+model.get(position).getId()+" "+model.get(position).getRealName());
				startActivity(intent);
			}
		});
	}
}