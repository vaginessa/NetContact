package cn.edu.zafu.netcontact.application;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import cn.edu.zafu.netcontact.R;

public class MyApplication extends Application {
	public static View view = null;
    public static int lastState;
    public static boolean isCalling=false;
	@Override
	public void onCreate() {
		super.onCreate();
		view=LayoutInflater.from(this).inflate(R.layout.layout_show, null);
	}

}