package com.techila.july.assign_management;

import java.util.ArrayList;
import java.util.HashMap;

import com.techila.july.assign_management.util.PrefSingleton;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class JobActivity extends Activity {

	Button otj, stj, ltj, sdj;
	private PrefSingleton mMyPreferences;

	ArrayList<HashMap<String, String>> GroupList;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_job);

		otj = (Button) findViewById(R.id.otj);
		stj = (Button) findViewById(R.id.stj);
		ltj = (Button) findViewById(R.id.ltj);
		sdj = (Button) findViewById(R.id.sdj);

		mMyPreferences = PrefSingleton.getInstance();
		mMyPreferences.Initialize(getApplicationContext());
		
		GroupList = (ArrayList<HashMap<String, String>>) getIntent()
				.getSerializableExtra("List");
		
		otj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mMyPreferences.setPreference("JobType", "One Time Job");
				Intent intent = new Intent(getApplicationContext(),
						Assign_Details.class);
				intent.putExtra("List", GroupList);
				startActivity(intent);
			}
		});

		stj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mMyPreferences.setPreference("JobType", "Short Term Job");
				Intent intent = new Intent(getApplicationContext(),
						Assign_Details.class);
				intent.putExtra("List", GroupList);
				startActivity(intent);
			}
		});

		ltj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mMyPreferences.setPreference("JobType", "Long Term Job");
				Intent intent = new Intent(getApplicationContext(),
						Assign_Details.class);
				intent.putExtra("List", GroupList);
				startActivity(intent);
			}
		});

		sdj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mMyPreferences.setPreference("JobType", "Specific Date Job");
				Intent intent = new Intent(getApplicationContext(),
						Assign_Details.class);
				intent.putExtra("List", GroupList);
				startActivity(intent);
			}
		});
	}

}
