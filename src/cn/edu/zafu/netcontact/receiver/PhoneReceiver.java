package cn.edu.zafu.netcontact.receiver;

import java.lang.reflect.Method;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.application.MyApplication;
import cn.edu.zafu.netcontact.database.DBPersonManager;
import cn.edu.zafu.netcontact.model.Person;
import cn.edu.zafu.netcontact.utility.DensityUtil;
import cn.edu.zafu.netcontact.utility.SharedPreferencesUtil;

import com.android.internal.telephony.ITelephony;

public class PhoneReceiver extends BroadcastReceiver {

	private static WindowManager wm = null;
	private static View view;
	private WindowManager.LayoutParams wmParams;
	public static String IN_NUMBER = "";
	public static String DAIL_NUMBER = "";
	private static int lastX;
	private static int lastY;
	private static int X;
	private static int Y;
	private int int_thread=0;
	private static Thread thread=null;
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("action" + intent.getAction());
		
		if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
			DAIL_NUMBER = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			createView(context, DAIL_NUMBER);
			MyApplication.isCalling=true;
			thread=new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while(MyApplication.isCalling){
						Thread.sleep(100);
						int_thread++;
						if(int_thread==30)
							MyApplication.isCalling=false;
						System.out.println("-----------"+MyApplication.isCalling);
						}
						onCloseView();
					} catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			});
			thread.start();
			System.out.println("执行");
		} else {
			TelephonyManager manager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			IN_NUMBER = intent.getStringExtra("incoming_number");
			switch (manager.getCallState()) {
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("已挂断");
				onCloseView();
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				/*ITelephony iTelephony = getITelephony(context); // 获取电话接口
				try {
					iTelephony.endCall();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // 挂断电话
*/				System.out.println("接通");
				createView(context, IN_NUMBER);
				MyApplication.lastState = TelephonyManager.CALL_STATE_RINGING;
				System.out.println(view == null);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				System.out.println("通话中");
				if(MyApplication.lastState==TelephonyManager.CALL_STATE_RINGING)
				   onCloseView();
				break;
			}
		}
	}

	private View createView(Context context, String phone) {
		final Context c = context;
		wm = (WindowManager) context.getApplicationContext().getSystemService(
				Context.WINDOW_SERVICE);
		wmParams = new WindowManager.LayoutParams();

		wmParams.type = LayoutParams.TYPE_PHONE;
		wmParams.format = PixelFormat.RGBA_8888;

		wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;
		wmParams.gravity = Gravity.TOP;
		wmParams.x = SharedPreferencesUtil.getX(context);

		wmParams.y = SharedPreferencesUtil.getY(context);

		wmParams.width = DensityUtil.dip2px(context, 300);
		wmParams.height = DensityUtil.dip2px(context, 150);

		String str_company = "";
		String str_name = "";
		DBPersonManager db = new DBPersonManager(context);
		List<Person> persons = db.queryByPhone(phone);
		if (persons.size() == 0) {
			str_company = "未知联系人";
			str_name = "未知姓名";
			return null;
		} else {
			str_company = persons.get(0).getSubCompany()
					+ persons.get(0).getCompany()
					+ persons.get(0).getDepartment()
					+ persons.get(0).getWorkgroup();
			str_name = persons.get(0).getRealName();
		}
		// view = LayoutInflater.from(context).inflate(R.layout.layout_show,
		// null);
		view = MyApplication.view;
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					X = (int) event.getRawX();
					Y = (int) event.getRawY();
					wmParams.x = wmParams.x + X - lastX;
					wmParams.y = wmParams.y + Y - lastY;
					lastX = X;
					lastY = Y;
					SharedPreferencesUtil.setXY(c, wmParams.x + "", wmParams.y
							+ "");
					wm.updateViewLayout(view, wmParams);
					break;
				}
				return false;
			}
		});
		Button close = (Button) view.findViewById(R.id.close);
		TextView num = (TextView) view.findViewById(R.id.num);
		TextView company = (TextView) view.findViewById(R.id.company);
		TextView name = (TextView) view.findViewById(R.id.name);
		num.setText(phone);
		company.setText(str_company);
		name.setText(str_name);
		close.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("按钮关闭");
				onCloseView();
			}
		});

		wm.addView(view, wmParams);
		return view;
	}

	private void onCloseView() {
		if (wm != null && view != null) {
			System.out.println("关闭");
			wm.removeView(view);
			wm = null;
		}
	}

	private static ITelephony getITelephony(Context context) {
		ITelephony iTelephony = null;
		TelephonyManager mTelephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		Class<TelephonyManager> c = TelephonyManager.class;
		Method getITelephonyMethod = null;
		try {
			getITelephonyMethod = c.getDeclaredMethod("getITelephony",
					(Class[]) null); // 获取声明的方法
			getITelephonyMethod.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		try {
			iTelephony = (ITelephony) getITelephonyMethod.invoke(
					mTelephonyManager, (Object[]) null); // 获取实例
			return iTelephony;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iTelephony;
	}

}
