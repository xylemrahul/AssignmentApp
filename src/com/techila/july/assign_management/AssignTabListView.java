package com.techila.july.assign_management;

import com.techila.july.assign_management.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class AssignTabListView extends ActivityGroup {
	// TabSpec Names
	private static final String CANCEL = "Cancel";
	private static final String DONE = "Done";
	private static final String DEFERRED = "Deferred";

	TabHost my_tab;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assignment_tabs);

		my_tab = (TabHost) findViewById(R.id.tabhost);
		my_tab.setup(this.getLocalActivityManager());

		TabSpec doneSpec = my_tab.newTabSpec(DONE);
		Intent done = new Intent().setClass(this, DoneActivity.class);
		doneSpec.setContent(done);
		doneSpec.setIndicator("",
				getResources().getDrawable(R.drawable.icon_done));
		my_tab.addTab(doneSpec);

		TabSpec cancelSpec = my_tab.newTabSpec(CANCEL);
		cancelSpec.setContent(new Intent(this, CancelActivity.class));
		cancelSpec.setIndicator("",
				getResources().getDrawable(R.drawable.icon_cancel));
		my_tab.addTab(cancelSpec);
		
		TabSpec deferredSpec = my_tab.newTabSpec(DEFERRED);
		Intent deferred = new Intent().setClass(this, DeferredActivity.class);
		deferredSpec.setContent(deferred);
		deferredSpec.setIndicator("",
				getResources().getDrawable(R.drawable.icon_deferred));
		my_tab.addTab(deferredSpec);
	}
}