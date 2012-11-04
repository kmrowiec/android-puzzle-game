/**
 * 
 */
package com.kmrowiec.puzzlegame;

/**
 * Short class to hold x and y cord. of different kinds.
 * @author kamil
 *
 */
public class Dimension {

	public final int x;
	public final int y;
	
	public Dimension(){
		x=0; y=0;
	}
	
	public Dimension(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "x:" + x + " y:" + y;
	}

	@Override
	public boolean equals(Object o) {
		Dimension other = (Dimension) o;
		if(other.x==this.x && other.y==this.y) return true;
		else return false;
		
	}
	
	public boolean equals(int x, int y) {
		if(x==this.x && y==this.y) return true;
		else return false;
		
	}
	
}
