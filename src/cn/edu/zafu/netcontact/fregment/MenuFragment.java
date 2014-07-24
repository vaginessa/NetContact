package cn.edu.zafu.netcontact.fregment;

import java.util.List;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.activity.CallActivity;
import cn.edu.zafu.netcontact.activity.DownloadActivity;
import cn.edu.zafu.netcontact.database.DBModelManager;
import cn.edu.zafu.netcontact.model.Model;

public class MenuFragment extends Fragment {
	private RelativeLayout menu_setting;
	private RelativeLayout menu_logout;
	private RelativeLayout menu_about;
	private RelativeLayout menu_update;
	private TextView slide_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_menu, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();

	}

	private void init() {
		MenuClick menuClick = new MenuClick();
		menu_setting = (RelativeLayout) getView().findViewById(
				R.id.menu_setting);
		menu_logout = (RelativeLayout) getView().findViewById(R.id.menu_logout);
		menu_about = (RelativeLayout) getView().findViewById(R.id.menu_about);
		menu_update = (RelativeLayout) getView().findViewById(R.id.menu_update);
		menu_setting.setOnClickListener(menuClick);
		menu_logout.setOnClickListener(menuClick);
		menu_about.setOnClickListener(menuClick);
		menu_update.setOnClickListener(menuClick);
		
		slide_title=(TextView)getView().findViewById(R.id.slide_title);
		DBModelManager subcompany=new DBModelManager(getActivity(), "subcompany");
		List<Model> sub=subcompany.query();
		subcompany.closeDB();
		
		slide_title.setText(sub.get(0).getRealName());
	}

	private class MenuClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.menu_update:
				startActivity(new Intent(getActivity(),DownloadActivity.class));
				//Toast.makeText(getActivity(), "更新数据", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_setting:
				Toast.makeText(getActivity(), "暂未开发", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_about:
				new AlertDialog.Builder(new ContextThemeWrapper(
						getActivity(), R.style.dialog))
						.setMessage("本软件由浙江农林大学版权所有").create().show();
				//Toast.makeText(getActivity(), "关于", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_logout:
				System.exit(0);
				break;
			}
		}

	}
}