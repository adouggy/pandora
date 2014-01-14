package me.promenade.pandora.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;

public enum ImageUtil {
	INSTANCE;

	public static final String TAG = "ImageUtil";

	public static final int HEIGHT = 128;
	public static final int WIDTH = 128;

	private Paint p = new Paint();

	public void initial(
			Context ctx) {
		p.setColor(Color.BLACK);
		p.setStyle(Style.FILL_AND_STROKE);
	}

	private ImageUtil() {
	}

	public Bitmap String2Bitmap(
			String str) {
		byte[] bArr = Base64.decode(str,
				Base64.DEFAULT);
		
		return bytes2Bitmap(bArr);
	}

	public String Bitmap2String(
			Bitmap bm) {
		byte[] bArr = bitmap2Bytes(bm);
		return new String(Base64.encode(bArr,
				Base64.DEFAULT));
	}

	public byte[] bitmap2Bytes(
			Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG,
				100,
				baos);
		return baos.toByteArray();
	}

	public Bitmap bytes2Bitmap(
			byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b,
					0,
					b.length);
		} else {
			return null;
		}
	}

	public String compressImageToString(Bitmap bmp) {
		Log.i(TAG,
				"compressImage");
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		// by setting this field as true, the actual bitmap pixels are not
		// loaded in the memory. Just the bounds are loaded. If
		// you try the use the bitmap here, you will get null.
		options.inJustDecodeBounds = true;
		
//		Bitmap bmp = BitmapFactory.decodeFile(filePath,
//				options);
		byte[] bArr = bitmap2Bytes(bmp);
		BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

		// max Height and width values of the compressed image is taken
		float maxHeight = HEIGHT;
		float maxWidth = WIDTH;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		// width and height values are set maintaining the aspect ratio of the
		// image
		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;
			}
		}

		// setting inSampleSize value allows to load a scaled down version of
		// the original image
		options.inSampleSize = calculateInSampleSize(options,
				actualWidth,
				actualHeight);

		// inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;

		// this options allow android to claim the bitmap memory if it runs low
		// on memory
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

//		try {
//			// load the bitmap from its path
//			bmp = BitmapFactory.decodeFile(filePath,
//					options);
//		} catch (OutOfMemoryError exception) {
//			exception.printStackTrace();
//		}

		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth,
					actualHeight,
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX,
				ratioY,
				middleX,
				middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp,
				middleX - bmp.getWidth() / 2,
				middleY - bmp.getHeight() / 2,
				new Paint(Paint.FILTER_BITMAP_FLAG));

		// check the rotation of the image and display it properly
//		ExifInterface exif;
//		try {
//			exif = new ExifInterface(filePath);
//
//			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//					0);
//			Log.d("EXIF",
//					"Exif: " + orientation);
//			Matrix matrix = new Matrix();
//			if (orientation == 6) {
//				matrix.postRotate(90);
//				Log.d("EXIF",
//						"Exif: " + orientation);
//			} else if (orientation == 3) {
//				matrix.postRotate(180);
//				Log.d("EXIF",
//						"Exif: " + orientation);
//			} else if (orientation == 8) {
//				matrix.postRotate(270);
//				Log.d("EXIF",
//						"Exif: " + orientation);
//			}
//			scaledBitmap = Bitmap.createBitmap(scaledBitmap,
//					0,
//					0,
//					scaledBitmap.getWidth(),
//					scaledBitmap.getHeight(),
//					matrix,
//					true);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		// combine with background
		Bitmap sizedBmp = combineBitmap(scaledBitmap);

//		FileOutputStream out = null;
//		try {
//			out = new FileOutputStream(newFilePath);
//
//			// write the compressed bitmap at the destination specified by
//			// filename.
//			sizedBmp.compress(Bitmap.CompressFormat.JPEG,
//					80,
//					out);
//			Log.i(TAG,
//					newFilePath + " created");
//		} catch (FileNotFoundException e) {
//			Log.d(TAG,
//					e.getMessage());
//			e.printStackTrace();
//		} finally {
//			if (out != null) {
//				try {
//					out.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

		scaledBitmap.recycle();
//		bmp.recycle();
//		sizedBmp.recycle();
		
		return Bitmap2String(sizedBmp);
	}

	public int calculateInSampleSize(
			BitmapFactory.Options options,
			int reqWidth,
			int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

	public Bitmap combineBitmap(
			Bitmap foreground) {
		int fgWidth = foreground.getWidth();
		int fgHeight = foreground.getHeight();

		Bitmap newmap = Bitmap.createBitmap(WIDTH,
				HEIGHT,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(newmap);

		Rect r = new Rect(0, 0, WIDTH, HEIGHT);

		canvas.drawRect(r,
				p);
		canvas.drawBitmap(foreground,
				(WIDTH - fgWidth) / 2,
				(HEIGHT - fgHeight) / 2,
				null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return newmap;
	}

	public String getExifDatetime(
			String path) {
		ExifInterface exif;
		try {
			exif = new ExifInterface(path);
			String dateTime = exif.getAttribute(ExifInterface.TAG_DATETIME);
			if (dateTime == null) {
				File f = new File(path);
				Date lastModDate = new Date(f.lastModified());
				dateTime = DateFormat.getDateTimeInstance().format(lastModDate);
			}
			return dateTime;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}
}
