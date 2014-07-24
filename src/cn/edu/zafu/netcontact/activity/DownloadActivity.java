package cn.edu.zafu.netcontact.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.edu.zafu.netcontact.R;
import cn.edu.zafu.netcontact.database.DBModelManager;
import cn.edu.zafu.netcontact.database.DBPersonManager;
import cn.edu.zafu.netcontact.database.DBTableManager;
import cn.edu.zafu.netcontact.model.Model;
import cn.edu.zafu.netcontact.model.Person;
import cn.edu.zafu.netcontact.model.Root;
import cn.edu.zafu.netcontact.utility.NetUtil;
import cn.edu.zafu.netcontact.utility.SharedPreferencesUtil;
import cn.edu.zafu.netcontact.utility.SignUtil;
import cn.edu.zafu.netcontact.utility.ZipUtils;

import com.google.gson.Gson;

public class DownloadActivity extends Activity {

	private static final String TAG = "ASYNC_TASK";

	private ProgressBar progressBar;
	private TextView textView;

	private MyTask mTask;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		textView = (TextView) findViewById(R.id.jindu);
		if (NetUtil.isConnect(this) == false) {
			new AlertDialog.Builder(new ContextThemeWrapper(
					DownloadActivity.this, R.style.dialog))
					.setTitle("网络错误")
					.setMessage("网络连接失败，请确认网络连接")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0,
										int arg1) {
									android.os.Process
											.killProcess(android.os.Process
													.myPid());
									System.exit(0);
								}
							}).show();
			return;
		}

		startTask();
		// delay();
	}

	private void startTask() {
		mTask = new MyTask();
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		String apiKey = SignUtil.key.getApiKey();
		String secretKey = SignUtil.key.getSecretKey();
		Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put("PageIndex", "-1");
		parameters.put("PageSize", "-1");
		parameters.put("SubCompany", "力软集团");
		parameters.put("Timestamp", timestamp);
		// parameters.put("ApiKey", "2819ab5c-7b63-4f29-af61-14af246a021e");
		String sign = SignUtil.getSignature(parameters, secretKey);
		// mTask.execute("http://m.zafu.edu.cn/wx/json.html");
		// mTask.execute("http://phone.wupeng.cn/json?Method=GET&ApiKey=2819ab5c-7b63-4f29-af61-14af246a021e&Sign=e62ac24761730adfe6e97375c78e2a14&PageIndex=-1&PageSize=-1&SubCompany=力软集团");
		// mTask.execute("http://1.lzqtest.sinaapp.com/json.html");
		// mTask.execute("http://lzqtest.sinaapp.com/1406084088648.zip","1406084088648");
		String url = "http://phone.wupeng.cn/json?Method=GET&ApiKey=2819ab5c-7b63-4f29-af61-14af246a021e&Sign=SIGN&PageIndex=-1&PageSize=-1&SubCompany=力软集团&Timestamp=TIMESTAMP";
		url = url.replace("SIGN", sign);
		url = url.replace("TIMESTAMP", timestamp);
		System.out.println("--------------" + url);
		mTask.execute(url, sign + timestamp);

	}

	private void delay() {
		textView.postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(DownloadActivity.this,
						MainActivity.class);
				startActivity(intent);
				DownloadActivity.this.finish();
			}
		}, 0);
	}

	private class MyTask extends AsyncTask<String, Integer, String> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute() called");

			textView.setText("数据加载中...");
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		/*
		 * @Override protected String doInBackground(String... params) {
		 * Log.i(TAG, "doInBackground(Params... params) called"); try {
		 * HttpClient client = new DefaultHttpClient(); HttpGet get = new
		 * HttpGet(params[0]); HttpResponse response = client.execute(get); if
		 * (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		 * HttpEntity entity = response.getEntity(); InputStream is =
		 * entity.getContent(); long total = entity.getContentLength();
		 * ByteArrayOutputStream baos = new ByteArrayOutputStream(); byte[] buf
		 * = new byte[1024]; int count = 0; int length = -1; while ((length =
		 * is.read(buf)) != -1) { baos.write(buf, 0, length); count += length;
		 * // 调用publishProgress公布进度,最后onProgressUpdate方法将被执行
		 * publishProgress((int) ((count / (float) total) * 100)); //
		 * 为了演示进度,休眠500毫秒 // Thread.sleep(100); } return new
		 * String(baos.toByteArray(), "UTF-8"); } } catch (Exception e) {
		 * Log.e(TAG, e.getMessage()); } return null; }
		 */

		protected String doInBackground(String... params) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(params[0]);

			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);

				StatusLine statusLine = httpResponse.getStatusLine();
				if (statusLine.getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();
					long total = entity.getContentLength();

					String filePath = getApplicationContext().getFilesDir()
							+ "/" + params[1] + ".zip"; // 文件路径
					File file = new File(filePath);
					FileOutputStream outputStream = new FileOutputStream(file);
					InputStream inputStream = entity.getContent();
					byte buf[] = new byte[1024];
					int length = -1;
					int count = 0;
					while ((length = inputStream.read(buf)) != -1) {
						outputStream.write(buf, 0, length);
						count += length;
						publishProgress((int) ((count / (float) total) * 100));
					}
					outputStream.flush();
					outputStream.close();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				httpClient.getConnectionManager().shutdown();
			}

			File out = new File(getApplicationContext().getFilesDir() + "/"
					+ params[1] + ".zip");
			try {
				ZipUtils.upZipFile(out, getApplicationContext().getFilesDir()
						+ "/");
			} catch (ZipException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File out1 = new File(getApplicationContext().getFilesDir() + "/"
					+ params[1] + ".txt");
			String rs = "";
			try {
				FileReader fileread = new FileReader(getApplicationContext()
						.getFilesDir() + "/" + params[1] + ".txt");
				BufferedReader br = new BufferedReader(fileread);
				String str = "";
				while ((str = br.readLine()) != null) {
					rs += str;
				}
				System.out.println(rs);
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(rs);
			return rs;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
			Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
			progressBar.setProgress(progresses[0]);
			textView.setText("数据已下载：" + progresses[0] + "%");
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {
			Log.i(TAG, "onPostExecute(Result result) called");
			textView.setText("数据已同步完毕，正在跳转，请稍后...");
			if (SharedPreferencesUtil.hasBind(getApplicationContext())) {
				DBTableManager db = new DBTableManager(getApplicationContext());
				db.drop();
				db.create();
			}
			try {
				print(result);
			} catch (NullPointerException e) {
				textView.setText("认证失败或服务器出错，请联系相关人员");
				return;
			}
			SharedPreferencesUtil.setBind(getApplicationContext(), true);
			delay();
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
			Log.i(TAG, "onCancelled() called");

			textView.setText("cancelled");
			progressBar.setProgress(0);
		}

		private void print(String str) {
			DBPersonManager mgr = new DBPersonManager(getApplicationContext());
			DBModelManager subcompany = new DBModelManager(
					getApplicationContext(), "subcompany");
			DBModelManager company = new DBModelManager(
					getApplicationContext(), "company");
			DBModelManager department = new DBModelManager(
					getApplicationContext(), "department");
			DBModelManager workgroup = new DBModelManager(
					getApplicationContext(), "workgroup");
			System.out.println("数据" + str);
			int num_subcompany = 0;
			int num_company = 0;
			int num_department = 0;
			int num_workgroup = 0;
			Gson gson = new Gson();
			Root root = gson.fromJson(str, Root.class);
			for (int i = 0; i < root.getSubCompanys().size(); i++) {
				System.out.println(root.getSubCompanys().get(i));
				subcompany.save(new Model(null, root.getSubCompanys().get(i)
						.getRealName(), 0));
				num_subcompany++;
				for (int j = 0; j < root.getSubCompanys().get(i).getCompanys()
						.size(); j++) {
					System.out.println(root.getSubCompanys().get(i)
							.getCompanys().get(j));
					company.save(new Model(null, root.getSubCompanys().get(i)
							.getCompanys().get(j).getRealName(), num_subcompany));
					num_company++;
					for (int k = 0; k < root.getSubCompanys().get(i)
							.getCompanys().get(j).getDepartments().size(); k++) {
						System.out.println(root.getSubCompanys().get(i)
								.getCompanys().get(j).getDepartments().size()
								+ "部门数量");
						System.out.println(root.getSubCompanys().get(i)
								.getCompanys().get(j).getDepartments().get(k));
						department.save(new Model(null, root.getSubCompanys()
								.get(i).getCompanys().get(j).getDepartments()
								.get(k).getRealName(), num_company));
						num_department++;
						for (int l = 0; l < root.getSubCompanys().get(i)
								.getCompanys().get(j).getDepartments().get(k)
								.getWorkGroups().size(); l++) {
							System.out.println(root.getSubCompanys().get(i)
									.getCompanys().get(j).getDepartments()
									.get(k).getWorkGroups().get(l));
							workgroup.save(new Model(null, root
									.getSubCompanys().get(i).getCompanys()
									.get(j).getDepartments().get(k)
									.getWorkGroups().get(l).getRealName(),
									num_department));
							num_workgroup++;
							List<Person> persons = root.getSubCompanys().get(i)
									.getCompanys().get(j).getDepartments()
									.get(k).getWorkGroups().get(l).getPersons();
							mgr.save(persons);

							for (int m = 0; m < root.getSubCompanys().get(i)
									.getCompanys().get(j).getDepartments()
									.get(k).getWorkGroups().get(l).getPersons()
									.size(); m++) {
								System.out.println(root.getSubCompanys().get(i)
										.getCompanys().get(j).getDepartments()
										.get(k).getWorkGroups().get(l)
										.getPersons().get(m));
							}
						}
					}
				}
			}

			subcompany.closeDB();
			company.closeDB();
			department.closeDB();
			workgroup.closeDB();
			mgr.closeDB();
		}
	}
}
