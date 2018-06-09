/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   TileHoveredListener
 * 
 * Summary:
 *   
 * 
 * History:
 *   09.06.2018 - Cleaning of code
 *   
 * 
 * Ideas:
 *   
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas;

import de.taleteller.grid.Tile;

public interface TileHoveredListener {

	/**
	 * Presents the hovered tile
	 * @param tile
	 */
	public void onHovered(Tile tile);
	
}
