package cn.edu.zafu.netcontact.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.adapter.ContentPagerAdapter;
import cn.edu.zafu.netcontact.database.DBModelManager;
import cn.edu.zafu.netcontact.fregment.MenuFragment;
import cn.edu.zafu.netcontact.model.Model;
import cn.edu.zafu.netcontact.utility.DensityUtil;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends FragmentActivity {

	private ViewPager vp_content;
	private TextView tc_contact;
	private TextView tc_search;
	private SlidingMenu slidingMenu;
	private ImageButton ibtn_trigger;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findView();
		init();
	}

	private void findView() {
		vp_content = (ViewPager) findViewById(R.id.vp_content);
		tc_contact = (TextView) findViewById(R.id.tc_contact);
		tc_search = (TextView) findViewById(R.id.tc_search);
		ibtn_trigger = (ImageButton) findViewById(R.id.ibtn_right_menu);
		title = (TextView) findViewById(R.id.title_name);

	}

	private void init() {
		DBModelManager subcompany = new DBModelManager(getApplicationContext(),
				"subcompany");
		List<Model> sub = subcompany.query();
		title.setText(sub.get(0).getRealName());
		// slide_title.setText(sub.get(0).getRealName());
		/*
		 * for(int i=0;i<sub.size();i++){
		 * title.setText(sub.get(i).getRealName()); }
		 */
		subcompany.closeDB();
		vp_content.setAdapter(new ContentPagerAdapter(
				getSupportFragmentManager()));
		vp_content.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setCurrentPage(position);
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// ignore
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// ignore
			}
		});

		ibtn_trigger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				slidingMenu.toggle();
			}
		});

		slidingMenu = new SlidingMenu(this);

		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE); // 滑动方式
		slidingMenu.setShadowDrawable(R.drawable.shadow_right); // 阴影
		slidingMenu.setShadowWidth(DensityUtil.dip2px(getApplicationContext(),
				30)); // 阴影宽度
		slidingMenu.setBehindOffset(DensityUtil.dip2px(getApplicationContext(),
				100)); // 前面的视图剩下多少
		slidingMenu.setMode(SlidingMenu.RIGHT); // 左滑出不是右滑出
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setMenu(R.layout.menu_frame); // 设置menu容器
		addButtonListener();
		FragmentManager fm = getSupportFragmentManager();
		fm.beginTransaction().replace(R.id.menu_frame, new MenuFragment())
				.commit();
	}

	// 按下返回键时
	@Override
	public void onBackPressed() {
		if (slidingMenu != null && slidingMenu.isMenuShowing()) {
			slidingMenu.showContent();
		} else {
			super.onBackPressed();
		}

		/*
		 * new AlertDialog.Builder(MainActivity.this).setTitle("提示")
		 * .setMessage("您确定要退出?").setPositiveButton("确定", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * 
		 * MainActivity.this.finish(); System.exit(0); } }).show();
		 */
	}

	// 按下菜单键时
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			slidingMenu.toggle();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void setCurrentPage(int current) {
		if (current == 0) {
			tc_contact.setBackgroundResource(R.drawable.title_menu_current);
			tc_contact.setTextColor(getResources().getColor(R.color.blue));
			tc_search.setBackgroundResource(R.drawable.title_menu_bg);
			tc_search.setTextColor(getResources().getColor(R.color.grey));
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			tc_search.setBackgroundResource(R.drawable.title_menu_current);
			tc_search.setTextColor(getResources().getColor(R.color.blue));
			tc_contact.setBackgroundResource(R.drawable.title_menu_bg);
			tc_contact.setTextColor(getResources().getColor(R.color.grey));
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
	}

	private void addButtonListener() {
		tc_contact.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vp_content.setCurrentItem(0);
				tc_contact.setBackgroundResource(R.drawable.title_menu_current);
				tc_contact.setTextColor(getResources().getColor(R.color.blue));
				tc_search.setBackgroundResource(R.drawable.title_menu_bg);
				tc_search.setTextColor(getResources().getColor(R.color.grey));
				slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			}
		});
		tc_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vp_content.setCurrentItem(1);
				tc_search.setBackgroundResource(R.drawable.title_menu_current);
				tc_search.setTextColor(getResources().getColor(R.color.blue));
				tc_contact.setBackgroundResource(R.drawable.title_menu_bg);
				tc_contact.setTextColor(getResources().getColor(R.color.grey));
				slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			}
		});
	}
}
