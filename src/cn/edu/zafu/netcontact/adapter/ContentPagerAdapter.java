package cn.edu.zafu.netcontact.adapter;

import cn.edu.zafu.netcontact.fregment.SearchFragment;
import cn.edu.zafu.netcontact.fregment.ContactFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContentPagerAdapter extends FragmentPagerAdapter {

	private Fragment[] fragments;

	public ContentPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments = new Fragment[2];
		fragments[0] = new ContactFragment();
		fragments[1] = new SearchFragment();
	}

	@Override
	public Fragment getItem(int position) {
		return fragments[position];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}
}