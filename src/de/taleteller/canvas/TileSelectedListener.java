/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   TileSelectedListeners
 * 
 * Summary:
 *   
 * 
 * History:
 *   
 *   
 * 
 * Ideas:
 *  
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas;

import de.taleteller.grid.Tile;

public interface TileSelectedListener {

	public void onSelection(Tile tile);
	
	public void onSelection(Tile... tiles);
	
}
