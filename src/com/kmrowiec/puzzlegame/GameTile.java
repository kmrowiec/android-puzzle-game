/**
 * 
 */
package com.kmrowiec.puzzlegame;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author kamil
 *
 */
public class GameTile extends ImageView {

	public Dimension pos; // position on the board, not on the screen!
	public RelativeLayout.LayoutParams params;
	private int checkNumber;
	
	public GameTile(Context context, int imgId) {
		super(context);
		this.setImageResource(imgId);
		
		
	}
	
	public GameTile(Context context, Drawable source, int n) {
		super(context);
		this.setImageDrawable(source);
		checkNumber = n;
		
	}
	
	public GameTile(Context context, int imgId, int n) {
		super(context);
		this.setImageResource(imgId);
		this.checkNumber = n;
		
	}

	public int getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(int checkNumber) {
		this.checkNumber = checkNumber;
	}

	
	
	

}
