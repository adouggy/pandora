package me.promenade.pandora.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyVibrateView extends View {
	public static final String TAG = "MyVibrateView";
	
	private Paint p = new Paint();
	private static int WIDTH = 0;
	private static int HEIGHT = 0;
	private int squareWidth = 0;
	private int squareHeight = 0;
	// private Context mContext = null;
	private final int COLUMN = 14;
	private final int ROW = 5;
	private final float xPaddingRatio = 0.01f;
	private final float yPaddingRatio = 0.02f;
	private int xPadding = 0;
	private int yPadding = 0;
	private static LinearGradient mLinearGradient = null;
	private int mCurrX, mCurrY;
	
	private int[] mData = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	public MyVibrateView(Context context) {
		super(context);
		init(context);
	}

	public MyVibrateView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init(context);
	}

	public void setData(
			int[] data) {

		if (data.length != COLUMN) {
			return;
		}
		
		this.mData = data;
		// deal data
		for (int i = 0; i < mData.length; i++) {
			if (mData[i] > ROW) {
				mData[i] = ROW;
			} else if (mData[i] < 0) {
				mData[i] = 0;
			}
		}
	}

	private void init(
			Context ctx) {
		// this.mContext = ctx;

	}

	@Override
	protected void onMeasure(
			int widthMeasureSpec,
			int heightMeasureSpec) {

		WIDTH = MeasureSpec.getSize(widthMeasureSpec);
		HEIGHT = MeasureSpec.getSize(heightMeasureSpec);

		xPadding = (int) (xPaddingRatio * WIDTH);
		yPadding = (int) (yPaddingRatio * HEIGHT);
		squareWidth = (WIDTH - xPadding) / COLUMN - xPadding;
		squareHeight = (HEIGHT - yPadding) / ROW - yPadding;

//		if (mLinearGradient == null)
		mLinearGradient = new LinearGradient(0, 0, 0, HEIGHT, new int[] { Color.RED, Color.YELLOW, Color.GREEN }, new float[] { 0f, 0.4f, 1f }, TileMode.CLAMP);

		Log.i(TAG,
				"width:" + WIDTH + ", height:" + HEIGHT);

		super.onMeasure(widthMeasureSpec,
				heightMeasureSpec);
	}

	@Override
	protected void onDraw(
			Canvas canvas) {

		p.setColor(Color.RED);

		p.setStyle(Paint.Style.FILL_AND_STROKE);

		for (int r = 0; r < ROW; r++)
			for (int c = 0; c < COLUMN; c++) {
				drawSqure(r,
						c,
						canvas);
			}

		super.onDraw(canvas);
	}

	private void drawSqure(
			int r,
			int c,
			Canvas canvas) {
		Log.d( TAG, "r=" + r + ", c=" + c );
		
		int x = c * (squareWidth + xPadding);
		int y = r * (squareHeight + yPadding);

		if ( (4-r) < mData[c]) {
			p.setShader(mLinearGradient);
			p.setStyle(Paint.Style.FILL_AND_STROKE);
		} else {
			p.setShader(null);
			p.setStyle(Paint.Style.STROKE);
		}

		RectF rect = new RectF(x + xPadding, y + yPadding, x + squareWidth, y + squareHeight);
		canvas.drawRoundRect(rect,
				5,
				5,
				p);
	}

	private int getRow(
			int y) {
		return (int) (((float) y / HEIGHT) * ROW);
	}

	private int getColumn(
			int x) {
		return (int) (((float) x / WIDTH) * COLUMN);
	}

	@Override
	public boolean onTouchEvent(
			MotionEvent event) {
		mCurrX = (int) event.getX();
		mCurrY = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			int r = getRow(mCurrY);
			int c = getColumn(mCurrX);
			Log.d(TAG,
					"touched at row:" + r + ", column:" + c);

			mData[c] = ROW - r;

			break;
		default:
			break;
		}

		invalidate();
		return true;
	}
}
