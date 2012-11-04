/**
 * 
 */
package com.kmrowiec.puzzlegame;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

/**
 * @author kamil
 *
 */
public class PuzzleCreator {
	
	private Bitmap sourceImage;
	private ArrayList<PuzzleTile> result = new ArrayList<PuzzleTile>();
	private Dimension outputBoardSize;
	private int outputTileSize;
	private GameBoard board;
	
	private class Randomizer{
		
		PuzzleTile[][] array; //array of puzzleTiles, will be used to shuffle it.
		Dimension nullPos;
		Random gen;
		
		public Randomizer(ArrayList<PuzzleTile> list){
			//Firstly, putting the puzzle in the 2d array, so that is is easier to simulate a board.
			array = new PuzzleTile[outputBoardSize.x][outputBoardSize.y];
			int z=0;
			for(int y=0;y<outputBoardSize.y;y++){
				for(int x=0;x<outputBoardSize.x;x++){
					if(x==outputBoardSize.x-1 && y==outputBoardSize.y-1)
						break;
					else{
						array[x][y] = result.get(z);
						z++;
					}
				}
			}
			nullPos = new Dimension(outputBoardSize.x-1, outputBoardSize.y-1);
			gen = new Random();
		}
		
		/**
		 * Shuffles the board. To make sure that the puzzle are solvable, it cannot simply shuffle the array,
		 * but it has to made by randomly moving tiles to empty spot, and that is what the method does.
		 */
		private void shuffle(){						
			//Now we simulate moving the tile.
			int n = 50; // n - the number of moves 
			//For many tiles 50 is not enough.
			if(outputBoardSize.equals(6, 10)) n = 100;
			
			
			// to control which one was previously selected
			// so that it wont go back at the same place.
			Dimension previous = new Dimension(); 
			Dimension current = null;
			for(int i = 0; i<n; i++){
				do{
					current = pickRandomTileToMove();
				}while(current.equals(previous));
				previous = new Dimension(nullPos.x,nullPos.y);
				switchToEmpty(current);
			}
		}
		
		/**
		 * Simulates move of the tile.
		 * @param pos Position of the tile to move.
		 */
		private void switchToEmpty(Dimension pos){
			array[nullPos.x][nullPos.y] = array[pos.x][pos.y];
			array[pos.x][pos.y] = null;
			nullPos = new Dimension(pos.x, pos.y);
		}
		
		/**
		 * Randomly selects a tile that can be moved (i. e. lays next to empty spot.
		 * @return Position of chosen tile.
		 */
		private Dimension pickRandomTileToMove(){
			if(nullPos.equals(0,0)){
				//Null at upper left corner of the board.
				switch(gen.nextInt(2)){
					case 0:
						return getSouthern(nullPos);
					case 1:
						return getEastern(nullPos);
				}
			}
			else if(nullPos.equals(outputBoardSize.x-1, 0)){
				//Null at upper right corner.
				switch(gen.nextInt(2)){
				case 0:
					return getSouthern(nullPos);
				case 1:
					return getWestern(nullPos);
				}
				
			}else if(nullPos.equals(0, outputBoardSize.y-1)){
				//Null at lower left corner.
				switch(gen.nextInt(2)){
				case 0:
					return getNorthern(nullPos);
				case 1:
					return getEastern(nullPos);
				}
			}else if(nullPos.equals(outputBoardSize.x-1, outputBoardSize.y-1)){
				//Null at lower right corner.
				switch(gen.nextInt(2)){
				case 0:
					return getNorthern(nullPos);
				case 1:
					return getWestern(nullPos);
				}
			}else{
				//Now we have to check if null is on one of sides of the board,
				//what would mean we have three options.
				if(nullPos.x==0){
					switch(gen.nextInt(3)){
					case 0:
						return getNorthern(nullPos);
					case 1:
						return getSouthern(nullPos);
					case 2:
						return getEastern(nullPos);
					}
				}
				else if(nullPos.x==outputBoardSize.x-1){
					switch(gen.nextInt(3)){
					case 0:
						return getNorthern(nullPos);
					case 1:
						return getSouthern(nullPos);
					case 2:
						return getWestern(nullPos);
					}
				}else if(nullPos.y==0){
					switch(gen.nextInt(3)){
					case 0:
						return getSouthern(nullPos);
					case 1:
						return getWestern(nullPos);
					case 2:
						return getEastern(nullPos);
					}
				}else if(nullPos.y==outputBoardSize.y-1){
					switch(gen.nextInt(3)){
					case 0:
						return getNorthern(nullPos);
					case 1:
						return getWestern(nullPos);
					case 2:
						return getEastern(nullPos);
					}
				}
			}
			//And now, finally, the situation when null is fully "inside" the board.
			switch(gen.nextInt(4)){
			case 0:
				return getNorthern(nullPos);
			case 1:
				return getWestern(nullPos);
			case 2:
				return getEastern(nullPos); 
			case 3:
				return getSouthern(nullPos);
			}
			
			return null;
		}
		
		private Dimension getNorthern(Dimension pos){
			return new Dimension(pos.x,pos.y-1);
		}
		private Dimension getSouthern(Dimension pos){
			return new Dimension(pos.x,pos.y+1);
		}
		private Dimension getEastern(Dimension pos){
			return new Dimension(pos.x+1,pos.y);
		}
		private Dimension getWestern(Dimension pos){
			return new Dimension(pos.x-1,pos.y);
		}
		
	} // END OF RANDOMIZER CLASS
	
	
	
	public PuzzleCreator(Bitmap source, GameBoard board){
		sourceImage = source;
		outputBoardSize = board.getGameSize();
		outputTileSize = board.getTileSize();
		this.board = board;
		
	}
	
	public PuzzleCreator(Bitmap source, Dimension boardSize, int tileSize){
		sourceImage = source;
		outputBoardSize = boardSize;
		outputTileSize = tileSize;
	}
	
	
	public PuzzleTile[][] createPuzzle(){
		
		if(sourceImage == null || outputBoardSize == null || outputTileSize <= 0)
			throw new RuntimeException("Missing parameter to create puzzle");
		
		/*
		//TODO automatic image cropping
		if(sourceImage.getWidth() != outputBoardSize.x*outputTileSize ||
				sourceImage.getHeight() != outputBoardSize.y*outputTileSize){
			throw new RuntimeException("Source image size does not fit with expected output. " +
					"Is "+ sourceImage.getWidth() + "x" + sourceImage.getHeight()+ ", " +
					"should be " + outputBoardSize.x*outputTileSize + "x" + outputBoardSize.y*outputTileSize);
		} */
		
		//TODO Make it right, so that proportions will always be preserverd.
		Dimension neededRes = new Dimension(outputBoardSize.x*outputTileSize,outputBoardSize.y*outputTileSize);
		sourceImage = Bitmap.createScaledBitmap(sourceImage, neededRes.x, neededRes.y, true);
		
		
		result = new ArrayList<PuzzleTile>();
		
		int n = 0;
		for(int y = 0; y<outputBoardSize.y; y++){
			for(int x = 0; x<outputBoardSize.x; x++){
				
				// If it is not the last tile, that should be empty.
				if(!(x==outputBoardSize.x-1 && y==outputBoardSize.y-1)){
					Bitmap temp = Bitmap.createBitmap(sourceImage, x*outputTileSize, y*outputTileSize, 
							outputTileSize, outputTileSize);
					result.add(new PuzzleTile(new BitmapDrawable(temp), n));
					//TODO change this deprecated shit to how it should really be.
				}
				n++;
			}
		}
		
		//Everyday I'm shuf-f-fling!
		Randomizer randomizer = new Randomizer(result);
		randomizer.shuffle();
		
		return randomizer.array;
	}
	
	
	

	public Bitmap getSourceImage() {
		return sourceImage;
	}

	public void setSourceImage(Bitmap sourceImage) {
		this.sourceImage = sourceImage;
	}

	public ArrayList<PuzzleTile> getResult() {
		return result;
	}

	public Dimension getOutputBoardSize() {
		return outputBoardSize;
	}

	public void setOutputBoardSize(Dimension outputBoardSize) {
		this.outputBoardSize = outputBoardSize;
	}

	public int getOutputTileSize() {
		return outputTileSize;
	}

	public void setOutputTileSize(int outputTileSize) {
		this.outputTileSize = outputTileSize;
	}
	
	

}
