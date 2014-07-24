package cn.edu.zafu.netcontact.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.model.MenuItem;

public class MenuItemAdapter extends BaseAdapter {

	private List<MenuItem> menus;
	private LayoutInflater inflater;
	private int margin;

	public MenuItemAdapter(Context context, List<MenuItem> menus, int margin) {
		inflater = LayoutInflater.from(context);
		this.menus = menus;
		this.margin = margin;
	}

	@Override
	public int getCount() {
		return menus.size();
	}

	@Override
	public Object getItem(int position) {
		return menus.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int height = parent.getHeight() / 3 - margin;
		AbsListView.LayoutParams param = new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.menu_item, parent, false);
			holder = new ViewHolder();
			holder.iv_menuIcon = (ImageView) convertView.findViewById(R.id.iv_menu_icon);
			holder.tv_menuTitle = (TextView) convertView.findViewById(R.id.tv_menu_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		MenuItem item = menus.get(position);
		holder.iv_menuIcon.setImageResource(item.menuIconRes);
		holder.tv_menuTitle.setText(item.menuTitle);
		convertView.setLayoutParams(param);
		return convertView;
	}

	private class ViewHolder {
		ImageView iv_menuIcon;
		TextView tv_menuTitle;
	}

}