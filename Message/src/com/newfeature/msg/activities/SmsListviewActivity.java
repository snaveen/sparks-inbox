package com.newfeature.msg.activities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.newfeature.msg.R;
import com.newfeature.msg.adapters.SmsListAdapter;

public class SmsListviewActivity extends ActionBarActivity implements LoaderCallbacks<Cursor> {

	private ListView listView;
	private SmsListAdapter adapter;
	
	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_view_pager);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(false);

		Tab tab = actionBar.newTab()
				.setText(R.string.personal)
				.setTabListener(new TabListener(
						this, "artist"));
		actionBar.addTab(tab);

		tab = actionBar.newTab()
				.setText(R.string.notification)
				.setTabListener(new TabListener(
						this, "album"));
		actionBar.addTab(tab);

		init();

		getSupportLoaderManager().initLoader(1, null, this);
	}
	
	class TabListener implements ActionBar.TabListener {
		public TabListener(Context context, String string) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTabReselected(Tab tag, FragmentTransaction fragmentTransaction) {
			
		}

		@Override
		public void onTabSelected(Tab tag, FragmentTransaction fragmentTransaction) {
			
		}

		@Override
		public void onTabUnselected(Tab tag, FragmentTransaction fragmentTransaction) {
			
		}
	}

	private void init() {
//		listView = (ListView) findViewById(R.id.sms_listview);
//		adapter = new SmsListAdapter(this, null, false);
//		listView.setAdapter(adapter);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyPageTransformer(getSupportFragmentManager()));
	}
	
	class MyPageTransformer extends FragmentPagerAdapter {

		public MyPageTransformer(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = new CursorLoader(this, Uri.parse("content://sms/inbox"), null, null, null, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		//		Log.e(getClass().toString(), "cursor: " + cursor.getCount());

		//		cursor.moveToFirst();
		//		for(int i = 0; i < cursor.getColumnCount(); i++) {
		//			Log.e(getClass().toString(), "cursor: " + cursor.getColumnName(i));	
		//		}
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {

	}
}
