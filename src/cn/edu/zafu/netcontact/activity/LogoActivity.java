package cn.edu.zafu.netcontact.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.utility.SharedPreferencesUtil;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		View view = getLayoutInflater().inflate(R.layout.activity_logo, null);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(2000);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				skip();
			}
		});
		view.setAnimation(animation);
		setContentView(view);
	}

	private void skip() {
		Intent intent = null;
		if (SharedPreferencesUtil.hasBind(getApplicationContext())) {
			intent = new Intent(this, MainActivity.class);
		} else {
			intent = new Intent(this, DownloadActivity.class);
		}
		startActivity(intent);
		finish();
	}
}
