package me.promenade.pandora;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyVibrateView extends View {
	public static final String TAG = "MyVibrateView";
	private Paint p = new Paint();
	private int WIDTH = 0;
	private int HEIGHT = 0;
	private int squareWidth = 0;
	private int squareHeight = 0;
	private Context mContext = null;
	private final int column = 14;
	private final int row = 5;
	private final float xPaddingRatio = 0.01f;
	private final float yPaddingRatio = 0.02f;
	private int xPadding = 0;
	private int yPadding = 0;
	private LinearGradient lg = null;

	private int[] data = { 0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 1, 2, 3 };
	
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

		// deal data
		for (int i = 0; i < data.length; i++) {
			if (data[i] > 5) {
				data[i] = 5;
			} else if (data[i] < 0) {
				data[i] = 0;
			}

			data[i] = 5 - data[i];
		}
	}

	@Override
	protected void onMeasure(
			int widthMeasureSpec,
			int heightMeasureSpec) {

		WIDTH = MeasureSpec.getSize(widthMeasureSpec);
		HEIGHT = MeasureSpec.getSize(heightMeasureSpec);

		xPadding = (int) (xPaddingRatio * WIDTH);
		yPadding = (int) (yPaddingRatio * HEIGHT);
		squareWidth = (WIDTH - xPadding) / column - xPadding;
		squareHeight = (HEIGHT - yPadding) / row - yPadding;
		
		lg =  new LinearGradient(0, 0, 0, HEIGHT, new int[] { Color.RED, Color.YELLOW, Color.GREEN }, new float[] { 0f, 0.4f, 1f }, TileMode.CLAMP);

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

		for (int r = 0; r < row; r++)
			for (int c = 0; c < column; c++) {
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
		int x = c * (squareWidth + xPadding);
		int y = r * (squareHeight + yPadding);

		if (r + 1 >= data[c]) {
			p.setShader(lg);
			p.setStyle(Paint.Style.FILL_AND_STROKE);
		} else {
			p.setShader(null);
			p.setStyle(Paint.Style.STROKE);
		}

		RectF rect = new RectF(x + xPadding,
				y + yPadding,
				x + squareWidth,
				y + squareHeight);
		canvas.drawRoundRect(rect, 5, 5, p);

	}
}
