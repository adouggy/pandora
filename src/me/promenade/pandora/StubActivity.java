package me.promenade.pandora;

import java.io.IOException;
import java.util.ArrayList;

import me.promenade.pandora.adapter.MyFragmentPagerAdapter;
import me.promenade.pandora.fragment.ChatFragment;
import me.promenade.pandora.fragment.MyVibrateViewFragment;
import me.promenade.pandora.fragment.TestFragment;
import me.promenade.pandora.util.VibrateUtil;

import org.xmlpull.v1.XmlPullParserException;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.StateSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class StubActivity extends SherlockFragmentActivity implements OnClickListener {
	private static final String TAG = "StubActivity";
	private ViewPager mPager;
	private ArrayList<Fragment> mFragmentsList;
	private TextView[] mTxtList;

	private int currIndex = 0;

	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		setTheme(R.style.Theme_Styled);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_stub);

		initActionBar();
		initTextView();
		initViewPager();
	}

	private void initActionBar() {
		// This is a workaround for http://b.android.com/15340 from
		// http://stackoverflow.com/a/5852198/132047
		// if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
		BitmapDrawable bg = (BitmapDrawable) getResources().getDrawable(R.drawable.img_top_bar_bg);
		bg.setTileModeXY(TileMode.REPEAT,
				TileMode.CLAMP);
		getSupportActionBar().setBackgroundDrawable(bg);

		// BitmapDrawable bgSplit = (BitmapDrawable)
		// getResources().getDrawable(R.drawable.bg_striped_split_img);
		// bgSplit.setTileModeXY(TileMode.REPEAT,
		// TileMode.REPEAT);
		// getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
		// }
	}

	private void initTextView() {
		mTxtList = new TextView[5];
		mTxtList[0] = (TextView) findViewById(R.id.txt_tab1);
		mTxtList[1] = (TextView) findViewById(R.id.txt_tab2);
		mTxtList[2] = (TextView) findViewById(R.id.txt_tab3);
		mTxtList[3] = (TextView) findViewById(R.id.txt_tab4);
		mTxtList[4] = (TextView) findViewById(R.id.txt_tab5);

		/**
		 * 文字的颜色，根据状态变换
		 */
		XmlResourceParser xrp = getResources().getXml(R.drawable.tab_text_color);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);
			for (TextView tv : mTxtList) {
				tv.setTextColor(csl);
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		/**
		 * 写selector xml只能根据状态改背景图，如果用compound drawable只能这么做，丫的。
		 */
		StateListDrawable d1 = new StateListDrawable();
		d1.addState(new int[] { android.R.attr.state_selected },
				getResources().getDrawable(R.drawable.tab_icon_home_selected));
		d1.addState(StateSet.WILD_CARD,
				getResources().getDrawable(R.drawable.tab_icon_home));

		StateListDrawable d2 = new StateListDrawable();
		d2.addState(new int[] { android.R.attr.state_selected },
				getResources().getDrawable(R.drawable.tab_icon_chat_selected));
		d2.addState(StateSet.WILD_CARD,
				getResources().getDrawable(R.drawable.tab_icon_chat));

		StateListDrawable d3 = new StateListDrawable();
		d3.addState(new int[] { android.R.attr.state_selected },
				getResources().getDrawable(R.drawable.tab_icon_vibration_selected));
		d3.addState(StateSet.WILD_CARD,
				getResources().getDrawable(R.drawable.tab_icon_vibration));

		StateListDrawable d4 = new StateListDrawable();
		d4.addState(new int[] { android.R.attr.state_selected },
				getResources().getDrawable(R.drawable.tab_icon_vibrator_selected));
		d4.addState(StateSet.WILD_CARD,
				getResources().getDrawable(R.drawable.tab_icon_vibrator));

		StateListDrawable d5 = new StateListDrawable();
		d5.addState(new int[] { android.R.attr.state_selected },
				getResources().getDrawable(R.drawable.tab_icon_settings_selected));
		d5.addState(StateSet.WILD_CARD,
				getResources().getDrawable(R.drawable.tab_icon_settings));

		/**
		 * 把icon搞小一些……也只能programmatically...丫的
		 */
		int width = d1.getIntrinsicWidth() - 20;
		int height = d1.getIntrinsicHeight() - 20;
		d1.setBounds(0,
				0,
				width,
				height);
		mTxtList[0].setCompoundDrawables(null,
				d1,
				null,
				null);

		d2.setBounds(0,
				0,
				width,
				height);
		mTxtList[1].setCompoundDrawables(null,
				d2,
				null,
				null);

		d3.setBounds(0,
				0,
				width,
				height);
		mTxtList[2].setCompoundDrawables(null,
				d3,
				null,
				null);

		d4.setBounds(0,
				0,
				width,
				height);
		mTxtList[3].setCompoundDrawables(null,
				d4,
				null,
				null);

		d5.setBounds(0,
				0,
				width,
				height);
		mTxtList[4].setCompoundDrawables(null,
				d5,
				null,
				null);

		for (TextView tv : mTxtList) {
			tv.setOnClickListener(this);
		}
	}

	private void initViewPager() {
		mPager = (ViewPager) findViewById(R.id.viewPager);
		mFragmentsList = new ArrayList<Fragment>();

		Fragment f1 = TestFragment.newInstance("Hello 首页.");
		ChatFragment f2 = new ChatFragment();
		MyVibrateViewFragment f3 = new MyVibrateViewFragment();
		Fragment f4 = TestFragment.newInstance("Hello Ma");
		Fragment f5 = TestFragment.newInstance("Hello 更多");

		mFragmentsList.add(f1);
		mFragmentsList.add(f2);
		mFragmentsList.add(f3);
		mFragmentsList.add(f4);
		mFragmentsList.add(f5);

		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	@Override
	public void onClick(
			View v) {
		switch (v.getId()) {
		case R.id.txt_tab1:
			mPager.setCurrentItem(0);
			break;
		case R.id.txt_tab2:
			mPager.setCurrentItem(1);
			break;
		case R.id.txt_tab3:
			mPager.setCurrentItem(2);
			break;
		case R.id.txt_tab4:
			mPager.setCurrentItem(3);
			break;
		case R.id.txt_tab5:
			mPager.setCurrentItem(4);
			break;
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(
				int arg0) {
			switch (arg0) {
			case 0:
				makeTabSelected(0);
				getSupportActionBar().setTitle(R.string.txt_tab1);
				break;
			case 1:
				makeTabSelected(1);
				getSupportActionBar().setTitle(R.string.txt_tab2);
				break;
			case 2:
				makeTabSelected(2);
				getSupportActionBar().setTitle(R.string.txt_tab3);
				break;
			case 3:
				VibrateUtil.INSTANCE.test();
				makeTabSelected(3);
				getSupportActionBar().setTitle(R.string.txt_tab4);
				break;
			case 4:
				makeTabSelected(4);
				getSupportActionBar().setTitle(R.string.txt_tab5);
				break;
			}

		}

		@Override
		public void onPageScrolled(
				int arg0,
				float arg1,
				int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(
				int arg0) {
		}
	}

	private void clearAllTabStatus() {
		for (TextView tv : mTxtList) {
			tv.setSelected(false);
		}
	}

	private void makeTabSelected(
			int index) {
		clearAllTabStatus();
		mTxtList[index].setSelected(true);
		currIndex = index;
		Log.d(TAG, "changed to index;" + currIndex);
	}
}
