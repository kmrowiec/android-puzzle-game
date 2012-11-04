package com.kmrowiec.puzzlegame;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapOperator {

	
	public static Bitmap rotateBitmap(Bitmap b, int degrees) {
	    if (degrees != 0 && b != null) {
	        Matrix m = new Matrix();

	        m.setRotate(degrees, (float) b.getWidth() / 2, (float) b.getHeight() / 2);
	        try {
	            Bitmap b2 = Bitmap.createBitmap(
	                    b, 0, 0, b.getWidth(), b.getHeight(), m, true);
	            if (b != b2) {
	                b.recycle();
	                b = b2;
	            }
	        } catch (OutOfMemoryError ex) {
	           throw ex;
	        }
	    }
	    return b;
	}
}
