package com.newfeature.msg.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.newfeature.msg.R;
import com.newfeature.msg.adapters.MyAdapter;
import com.newfeature.msg.asynctask.GetCodeAsyncTask;
import com.newfeature.msg.database.SparksContract.Promotions;
import com.newfeature.msg.interfaces.FragmentCallback;

public class PromotionFragment extends Fragment implements OnRefreshListener, LoaderCallbacks<Cursor> {

	public static final String DEVICE_ID = "device_id";
	
	private View view;
	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;;
	private MyAdapter adapter;

	private String deviceID;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.promotion_layout, null);
			initFragment();
		} else {
			((ViewGroup) view.getParent()).removeView(view);
		}
		return view;
	}
	
	private void initFragment() {
		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#891223"), Color.parseColor("#812239"), Color.parseColor("#122389"), Color.parseColor("#128923"));
		
		listView = (ListView) view.findViewById(R.id.promotion_listview);
		adapter = new MyAdapter(getActivity(), null, false);
		listView.setAdapter(adapter);
		
		getLoaderManager().initLoader(1, null, this);
		
		deviceID = getArguments().getString(DEVICE_ID);
	}

	@Override
	public void onRefresh() {
		swipeRefreshLayout.setRefreshing(true);
		GetCodeAsyncTask asyncTask = new GetCodeAsyncTask(getActivity(), new FragmentCallback() {
			@Override
			public void onPreExecute() {
				
			}
			@Override
			public void onPostExecute(Object... objects) {
				swipeRefreshLayout.setRefreshing(false);
			}
			@Override
			public void onError(Object... objects) {
				swipeRefreshLayout.setRefreshing(false);
			}
		});
		asyncTask.execute(deviceID);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(), Promotions.CONTENT_URI, null, null, null, BaseColumns._ID + " desc");
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		adapter.swapCursor(arg1);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.changeCursor(null);
	}
}
