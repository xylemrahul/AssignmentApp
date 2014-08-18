package com.techila.july.assign_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techila.july.assign_management.R;
import com.techila.july.assign_management.util.JSONParser;
import com.techila.july.assign_management.util.PrefSingleton;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DeferredActivity extends ListActivity {

	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String, String>> AssignmentList;
	private PrefSingleton mMyPreferences;
	HashMap<String, String> map;
	HashMap<String, String> map1;
	// products JSONArray
	JSONArray inbox = null;
	ProgressDialog prg;
	String Member_Id, jsonArray = null;

	// Inbox JSON url
	private static final String ASSIGNMENT_URL = "http://phbjharkhand.in/AssignmentApplication/Get_Type_Member_Status_Wise_Details.php";

	// ALL JSON node names
	private static final String TAG_DATE = "createdDate";
	public static final String TAG_RESULT = "result";
	private static final String TAG_STATUS = "status";
	private static final String TAG_NAME = "assignmentName";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending);

		mMyPreferences = PrefSingleton.getInstance();
		mMyPreferences.Initialize(getApplicationContext());
		// Hashmap for ListView
		AssignmentList = new ArrayList<HashMap<String, String>>();

		Member_Id = mMyPreferences.getPreference("Mem_Id");

		// Loading INBOX in Background Thread
		new LoadDeferred().execute();
	}

	/**
	 * Background Async Task to Load all INBOX messages by making HTTP Request
	 * */
	class LoadDeferred extends AsyncTask<Void, Void, Integer> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			prg = new ProgressDialog(DeferredActivity.this);
			prg.setMessage("Loading List ...");
			prg.setIndeterminate(false);
			prg.setCancelable(false);
			prg.show();

			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(Void... params) {

			List<NameValuePair> params1 = new ArrayList<NameValuePair>();

			if ("One Time Job".equals(mMyPreferences.getPreference("JobType"))) {
				params1.add(new BasicNameValuePair("assType", mMyPreferences
						.getPreference("JobType")));
			} else if ("Short Term Job".equals(mMyPreferences
					.getPreference("JobType"))) {
				params1.add(new BasicNameValuePair("assType", mMyPreferences
						.getPreference("JobType")));
			} else if ("Long Term Job".equals(mMyPreferences
					.getPreference("JobType"))) {
				params1.add(new BasicNameValuePair("assType", mMyPreferences
						.getPreference("JobType")));
			} else if ("Specific Date Job".equals(mMyPreferences
					.getPreference("JobType"))) {
				params1.add(new BasicNameValuePair("assType", mMyPreferences
						.getPreference("JobType")));
			}

			params1.add(new BasicNameValuePair("memberID", Member_Id));
			params1.add(new BasicNameValuePair("status", "Deferred"));

			String error_code = null;

			// getting JSON string from URL
			JSONObject json = jsonParser.makeHttpRequest(ASSIGNMENT_URL,
					"POST", params1);

			// Check your log cat for JSON response
			Log.d("Deferred JSON: ", json.toString());

			try {
				JSONObject jsonObj = json.getJSONObject("data");
				error_code = jsonObj.getString("Error_Code");
				if ("1".equals(error_code)) {

					JSONArray jsonArray = null;
					jsonArray = jsonObj.getJSONArray(TAG_RESULT);
					// looping through All messages
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject c = jsonArray.getJSONObject(i);

						map = new HashMap<String, String>();
						// Storing each json item in variable
						String date = c.getString(TAG_DATE);
						String status = c.getString(TAG_STATUS);
						String name = c.getString(TAG_NAME);
						map.put(TAG_DATE, date);
						map.put(TAG_NAME, name);
						map.put(TAG_STATUS, status);
						if (status.equals("Deferred")) {
							// adding HashList to ArrayList
							AssignmentList.add(map);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return Integer.parseInt(error_code);
		}

		@Override
		protected void onPostExecute(Integer error_num) {
			// TODO Auto-generated method stub
			super.onPostExecute(error_num);
			if (prg.isShowing()) {
				prg.dismiss();
			}

			if (error_num == 2) {

				Toast.makeText(getApplicationContext(),
						"There are no assignments", Toast.LENGTH_LONG).show();
			}

			ListAdapter adapter = new SimpleAdapter(DeferredActivity.this,
					AssignmentList, R.layout.activity_tab_list_view,
					new String[] { TAG_NAME, TAG_DATE }, new int[] {
							R.id.status, R.id.created_date });
			setListAdapter(adapter);

		}
	}

}
