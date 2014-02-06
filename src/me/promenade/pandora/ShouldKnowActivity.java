package me.promenade.pandora;

import java.io.InputStream;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

public class ShouldKnowActivity extends SherlockActivity {

	private TextView mTitle ;
	private TextView mShouldKnow = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_should_know);
		
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffdb2672));
		getSupportActionBar().setIcon(android.R.color.transparent);
		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); 
		getSupportActionBar().setCustomView(R.layout.abs_layout);
		
		mTitle = (TextView) getSupportActionBar().getCustomView().findViewById(R.id.txt_title);
		mTitle.setText( R.string.txt_should_know_check );
		

		mShouldKnow = (TextView) findViewById(R.id.txt_should_know_content);
		
		try {
	        Resources res = getResources();
	        InputStream in_s = res.openRawResource(R.raw.should_know);
	        
	        byte[] b = new byte[in_s.available()];
	        in_s.read(b);
	        mShouldKnow.setText(new String(b, "utf-8"));
	    } catch (Exception e) {
	         e.printStackTrace();
	    }
	}

}
