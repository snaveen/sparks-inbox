package com.newfeature.msg.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.newfeature.msg.R;
import com.newfeature.msg.adapters.SmsListAdapter;

public class PersonalFragment extends Fragment implements LoaderCallbacks<Cursor> {

	public static final String SELECTION = "selection";
	public static final String SELECTION_ARGS = "selection_args";
	private View view;
	private ListView listView;
	private SmsListAdapter adapter;
	private String selection;
	private String[] selectionArgs;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if(view == null) {
			view = inflater.inflate(R.layout.activity_main, null);
			
			initFragment();
			getLoaderManager().initLoader(1, null, this);
		} else {
			((ViewGroup) view.getParent()).removeView(view);
		}
		return view;
	}
	
	private void initFragment() {
		selection = getArguments().getString(SELECTION);
		selectionArgs = getArguments().getStringArray(SELECTION_ARGS);
		
		listView = (ListView) view.findViewById(R.id.sms_listview);
		adapter = new SmsListAdapter(getActivity(), null, false);
		listView.setAdapter(adapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		CursorLoader cursorLoader = new CursorLoader(getActivity(), Uri.parse("content://sms/inbox"), null, selection, selectionArgs, null);
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		for(int i = 0; i < cursor.getColumnCount(); i++) {
//			Log.e(getClass().toString(), cursor.getColumnName(i));
		}
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
	}
}
