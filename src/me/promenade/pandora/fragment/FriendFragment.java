package me.promenade.pandora.fragment;

import java.util.ArrayList;

import me.promenade.pandora.R;
import me.promenade.pandora.adapter.FriendListAdapter;
import me.promenade.pandora.asynjob.AddPartnerJob;
import me.promenade.pandora.bean.Friend;
import me.promenade.pandora.bean.RunningBean;
import me.promenade.pandora.util.Constants;
import me.promenade.pandora.util.NameUtil;
import me.promenade.pandora.util.SharedPreferenceUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class FriendFragment extends SherlockFragment implements OnClickListener {
	public static final String TAG = "FriendFragment";

	private static ListView mList = null;
	public static FriendListAdapter mAdapter = null;
	private static Button mAddButton = null;
	public static final int WHAT_REFRESH_FRIEND = 2;

	public static Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case WHAT_REFRESH_FRIEND:
				String partnerName = RunningBean.INSTANCE.getPartnerName();
				String partnerPhoto = SharedPreferenceUtil.INSTANCE
						.getData(Constants.SP_PARTNER_PHOTO);
				Friend f = new Friend();
				f.setUsername(partnerName);
				f.setPhoto(partnerPhoto);
				ArrayList<Friend> list = new ArrayList<Friend>();
				list.add(f);
				mAdapter.setData(list);

				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.fragment_friend, container, false);
		setHasOptionsMenu(true);

		mList = (ListView) view.findViewById(R.id.list_friend);
		mList.setDivider(null);

		mAdapter = new FriendListAdapter(this.getActivity());
		mList.setAdapter(mAdapter);

		mAddButton = (Button) view.findViewById(R.id.btn_add_friend);

		mAddButton.setOnClickListener(this);

		int userId = RunningBean.INSTANCE.getUserId();
		if (userId != -1) {
			mHandler.obtainMessage(WHAT_REFRESH_FRIEND).sendToTarget();
		}
		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_friend:
			if (RunningBean.INSTANCE.getPartnerId() > 0) {
				Toast.makeText(getActivity(), "您已有了伴侣", Toast.LENGTH_SHORT)
						.show();
				break;
			}

			AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
			b.setTitle("请输入好友昵称");
			final EditText input = new EditText(getActivity());
			b.setView(input);
			b.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					String partnerName = NameUtil.INSTANCE.parseName(input
							.getText().toString());
					String myName = RunningBean.INSTANCE.getUserName();

					AddPartnerJob job = new AddPartnerJob();
					job.setContext(getActivity());
					job.execute(myName, partnerName);

					// SharedPreferenceUtil.INSTANCE.setData(Constants.SP_FRIEND,
					// name);
					// RunningBean.INSTANCE.reloadFriend();
					// mAdapter.setData(RunningBean.INSTANCE.getFriend());
					// mAdapter.notifyDataSetInvalidated();
				}
			});
			b.setNegativeButton("取消", null);
			b.create().show();
			break;
		}
	}
}
