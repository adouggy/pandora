package me.promenade.pandora;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyVibrateView extends View {
	public static final String TAG = "MyVibrateView";
	private Paint p = new Paint();
	private int WIDTH = 0;
	private int HEIGHT = 0;
	private Context mContext = null;
	private final int column = 14;
	private final int row = 5;

	public MyVibrateView(Context context) {
		super(context);
		init(context);
	}

	public MyVibrateView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}

	private void init(
			Context ctx) {
		this.mContext = ctx;
	}

	@Override
	protected void onMeasure(
			int widthMeasureSpec,
			int heightMeasureSpec) {

		WIDTH = MeasureSpec.getSize(widthMeasureSpec);
		HEIGHT = MeasureSpec.getSize(heightMeasureSpec);
		
		Log.i(TAG, "width:" + WIDTH + ", height:" + HEIGHT);

		super.onMeasure(widthMeasureSpec,
				heightMeasureSpec);
	}

	@Override
	protected void onDraw(
			Canvas canvas) {

		p.setColor(Color.RED);// 设置红色

		p.setStyle(Paint.Style.STROKE);

		for( int r=0; r<row; r++ )
			for( int c=0; c<column; c++ ){
				drawSqure( r, c, canvas );
			}

		super.onDraw(canvas);
	}
	
	private void drawSqure(int r, int c, Canvas canvas){
		int w = WIDTH / column;
		int h = HEIGHT / row;
		
		
		
		canvas.drawRect( c*w, r*h, c*w+w, r*h+h, p );
	}
}
