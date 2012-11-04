/**
 * 
 */
package com.kmrowiec.puzzlegame;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

/**
 * @author kamil
 *
 */
public class TileAnimation implements Runnable, AnimationListener {

	private GameTile tileToMove;
	Dimension destination;
	GameBoard gameBoard;
	
	public TileAnimation(GameTile toMove, Dimension dest, GameBoard board){
		tileToMove = toMove;
		destination = dest;
		gameBoard = board;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		
		Dimension empty = gameBoard.getEmptyPosition();
		Dimension emptyOnScreen = gameBoard.getOnScreenCord(empty);
		
		//calculating the translation for animation
		int changeX = 0;
		int changeY = 0;
		if(tileToMove.pos.x==empty.x && empty.y==tileToMove.pos.y-1){
			//empty to the north
			changeY = -gameBoard.getTileSize();
		}else if(tileToMove.pos.x==empty.x && empty.y==tileToMove.pos.y+1){
			//empty to the south
			changeY = gameBoard.getTileSize();
		}else if(tileToMove.pos.y==empty.y && empty.x==tileToMove.pos.x-1){
			//empty to the left
			changeX = -gameBoard.getTileSize();
		}else changeX = gameBoard.getTileSize(); // empty to the right
		
		//setting the animation
		TranslateAnimation anim = new TranslateAnimation(0, changeX, 0, changeY);
        anim.setDuration(1000);
        //anim.setFillAfter(true);
       
        
        //after animation need to really change a position of tile
        //and change position of the empty space
        //the checker will do that
        anim.setAnimationListener(this);
        
        //and the animation starts!
       
        tileToMove.startAnimation(anim);

	}

	public void onAnimationEnd(Animation animation) {
		gameBoard.moveTileToEmpty(tileToMove);
		
	}

	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

}
