package com.kmrowiec.puzzlegame;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

public class SavedPuzzle implements Serializable {

	int[][] order;
	Dimension boardSize;
	
	public static SavedPuzzle loadPuzzle(File dir){
		
		Dimension loadedBoardSize;
		int loadedTileSize;
		Bitmap loadedSourceImage;
		/*
		ArrayList<Bitmap> bitmapTiles = new ArrayList<Bitmap>();
		int n = 0;
		for(int y = 0; y<loadedBoardSize.y; y++){
			for(int x = 0; x<loadedBoardSize.x; x++){
				
				// If it is not the last tile, that should be empty.
				if(!(x==loadedBoardSize.x-1 && y==loadedBoardSize.y-1)){
					Bitmap temp = Bitmap.createBitmap(loadedSourceImage, x*loadedTileSize, y*loadedTileSize, 
							loadedTileSize, loadedTileSize);
					bitmapTiles.add(temp);
					
				}
				n++;
			}
		}
		
		*/
		return null;
	}
	
	public static void savePuzzle(File dir, GameBoard board){
		
	}
}
