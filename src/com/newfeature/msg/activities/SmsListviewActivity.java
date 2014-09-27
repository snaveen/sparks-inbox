package com.newfeature.msg.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.newfeature.msg.R;
import com.newfeature.msg.asynctask.SendNotificationAsyncTask;
import com.newfeature.msg.fragments.PersonalFragment;
import com.newfeature.msg.fragments.PromotionFragment;
import com.newfeature.msg.interfaces.FragmentCallback;

public class SmsListviewActivity extends ActionBarActivity {

	static {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();
		StrictMode.setThreadPolicy(policy);
	} 

	private ViewPager viewPager;
	private ActionBar actionBar;
	private ProgressDialog pg;
//	private boolean notificationSent = true;
	private String android_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_view_pager);

		init();

		actionBar = getSupportActionBar();
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

		tab = actionBar.newTab()
				.setText(R.string.promotion)
				.setTabListener(new TabListener(
						this, "album"));
		actionBar.addTab(tab);
	}

	class TabListener implements ActionBar.TabListener {
		public TabListener(Context context, String string) {
		}

		@Override
		public void onTabReselected(Tab tag, FragmentTransaction fragmentTransaction) {

		}

		@Override
		public void onTabSelected(Tab tag, FragmentTransaction fragmentTransaction) {
			viewPager.setCurrentItem(tag.getPosition());
		}

		@Override
		public void onTabUnselected(Tab tag, FragmentTransaction fragmentTransaction) {

		}
	}

	class MyLocationManager implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			if (location != null) {
				Log.d("LOCATION CHANGED", location.getLatitude() + "");
				Log.d("LOCATION CHANGED", location.getLongitude() + "");
				Toast.makeText(SmsListviewActivity.this,
						location.getLatitude() + "" + location.getLongitude(),
						Toast.LENGTH_LONG).show();
				String locationString = location.getLatitude() + "," + location.getLongitude();
				sendNotificationMessage(locationString);
			}
		}

		@Override
		public void onProviderDisabled(String arg0) {

		}

		@Override
		public void onProviderEnabled(String arg0) {
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

		}

	}

	private void init() {
//		notificationSent = false;
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationManager();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyPageTransformer(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		sendNotificationMessage("ss,ss");
	}

	private void sendNotificationMessage(String locationString) {
//		if(!notificationSent) {
//			notificationSent = true;
			pg = new ProgressDialog(SmsListviewActivity.this);
			SendNotificationAsyncTask asyncTask = new SendNotificationAsyncTask(SmsListviewActivity.this, new FragmentCallback() {
				@Override
				public void onPreExecute() {
					if(pg != null) {
						pg.show();
					}
				}
				@Override
				public void onPostExecute(Object... objects) {
					if(pg != null) {
						pg.dismiss();
					}
				}
				@Override
				public void onError(Object... objects) {
					if(pg != null) {
						pg.dismiss();
					}
				}
			});

			android_id = Secure.getString(getContentResolver(),
					Secure.ANDROID_ID);
			asyncTask.execute(android_id, locationString);
//		}
	}

	class MyPageTransformer extends FragmentPagerAdapter {

		public MyPageTransformer(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			PersonalFragment personalFragment = new PersonalFragment();
			Bundle bundle = new Bundle();
			switch(position) {
			case 0:
				bundle.putString(PersonalFragment.SELECTION, "address like ?");
				bundle.putStringArray(PersonalFragment.SELECTION_ARGS, new String[] {"+%"});
				personalFragment.setArguments(bundle);
				return personalFragment;
			case 1:
				bundle.putString(PersonalFragment.SELECTION, "address not like ?");
				bundle.putStringArray(PersonalFragment.SELECTION_ARGS, new String[] {"+%"});
				personalFragment.setArguments(bundle);
				return personalFragment;
			case 2:
				PromotionFragment promotionFragment = new PromotionFragment();
				bundle.putString(PromotionFragment.DEVICE_ID, android_id);
				promotionFragment.setArguments(bundle);
				return promotionFragment;
			}
			return null;
		}


		@Override
		public int getCount() {
			return 3;
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
}
