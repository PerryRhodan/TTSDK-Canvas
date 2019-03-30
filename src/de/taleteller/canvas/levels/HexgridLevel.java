/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   HexgridLevel
 * 
 * Summary:
 *   
 * History:
 *   09.06.2018 - Cleaning of code and comments
 * 
 * Ideas:
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas.levels;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.taleteller.animation.DrawableImage;
import de.taleteller.animation.TileImageData;
import de.taleteller.animation.DrawPosition;
import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.TileHoveredListener;
import de.taleteller.canvas.TileSelectedListener;
import de.taleteller.grid.Tile;
import de.taleteller.grid.World2D;
import de.taleteller.grid.drawstate.TileDrawStateActivationListener;
import de.taleteller.grid.drawstate.TileDrawStateMemberData;
import de.taleteller.grid.tile.Hextile;
import javafx.scene.input.MouseEvent;


/**
 * Implementation of a DrawingLevel that draws a hex grid
 * from a World2D using Hextiles in TTSDK-Grid.
 * !!T_Tiledata and T_Tile MUST be the same as used in World2D!! 
 */
public class HexgridLevel
<T_Tiledata, T_Tile extends Hextile<T_Tiledata>>
extends DrawingLevel
implements TileDrawStateActivationListener
{
	/** when true triggers a redraw of the level on
	 * the next iteration */
	boolean needs_redraw;
	
	/* Registered listeners, ie those who care about when
	 * a tile is hovered or selected. */
	List<TileHoveredListener> listeners_hover;
	List<TileSelectedListener> listeners_selected;
	
	/** represented grid drawn by this canvas */
	@SuppressWarnings("rawtypes")
	World2D grid;
	
	/** the image data set used to draw the tiles
	 *  if they do not have any defined on their
	 *  own. */
	TileImageData base_image_data;
	
	/** currently hovered tile */
	T_Tile hovered_tile;
	
	/** currently selected tile */
	T_Tile selected_tile;
	
	/** moving target to draw the tiles */
	DrawPosition target;
	
	///////////////////////////////////////////////////////////////

	public HexgridLevel(int x, int y
			, World2D<T_Tiledata, T_Tile> world
			, TileImageData baseImageData) {
		super(x, y);
		this.grid = world;
		this.base_image_data = baseImageData;
		
		listeners_hover = new ArrayList<>();
		listeners_selected = new ArrayList<>();
		
		target = new DrawPosition(0, 0, 0, 0);
	}

	///////////////////////////////////////////////////////////////

	public void Draw(Focus focus) {
		DrawableImage img = base_image_data.getImg_default();
		T_Tile tile = null;
		
		int focus_bound_X_left = calcFocusBound_X_left(focus);
		int focus_bound_X_right = calcFocusBound_X_right(focus);
		int focus_bound_Y_top = calcFocusBound_Y_top(focus);
		int focus_bound_Y_bottom = calcFocusBound_Y_bottom(focus);
		
		for (int i = focus_bound_X_left; i < focus_bound_X_right; i++) {
			for (int j = focus_bound_Y_top; j < focus_bound_Y_bottom; j++) {
				tile = (T_Tile) grid.getTiles()[i][j];
				
				/* set default state.. */
				tile.setSelected(false);
				tile.setHovered(false);
				/* and then update it accordingly */
				if(tile.equals(selected_tile)) {
					img = base_image_data.getImg_selected();
					tile.setSelected(true);
					drawTile(img, grid.getTiles()[i][j], focus, 0.5); // TODO make this alpha value configurable as well
				}
				if(tile.equals(hovered_tile)) {
					img = base_image_data.getImg_hover();
					tile.setHovered(true);
					drawTile(img, grid.getTiles()[i][j], focus, 0.5); // TODO make this alpha value configurable as well
				}
				/* draw states */
				for (TileDrawStateMemberData stateData : tile.getDrawStateMemberData()) {
					if(stateData.getState().isActive()) {
						/* use specific image data of the
						 * tile state if available */
						TileImageData used_img_data = base_image_data;
						if(stateData.getState().getImage_data() != null)
							used_img_data = stateData.getState().getImage_data();
						
						/* tile images */
						img = used_img_data.getFromValue(stateData.getDraw_value()
									, stateData.getDraw_value_max());
						
						drawTile(img, tile, focus, stateData.getAlpha());

						/* border images */
						if(stateData.isDrawTop())
							drawTile(used_img_data.getImg_border_top()
									, tile, focus, stateData.getAlpha());
						if(stateData.isDrawTopright())
							drawTile(used_img_data.getImg_border_topright()
									, tile, focus, stateData.getAlpha());
						if(stateData.isDrawBottomright())
							drawTile(used_img_data.getImg_border_bottomright()
									, tile, focus, stateData.getAlpha());
						if(stateData.isDrawBottom())
							drawTile(used_img_data.getImg_border_bottom()
									, tile, focus, stateData.getAlpha());
						if(stateData.isDrawBottomleft())
							drawTile(used_img_data.getImg_border_bottomleft()
									, tile, focus, stateData.getAlpha());
						if(stateData.isDrawTopleft())
							drawTile(used_img_data.getImg_border_topleft()
									, tile, focus, stateData.getAlpha());
					
					}
				}
				
			}
		}
		
		needs_redraw = false;
	}

	@Override
	public boolean NeedsUpdate() {
		return needs_redraw;
	}
	
	@Override
	public void forceUpdate() {
		needs_redraw = true;
	}
	
	@Override
	public void onFocusUpdate() {
		needs_redraw = true;
	}
	
	///////////////////////////////////////////////////////////////
	
	protected void drawTile(DrawableImage img, Tile tile, Focus focus, double alpha){
		int x = tile.getX();
		int y = tile.getY();

		if (img == null)
			return;

		double off = 0.0;
		if (x % 2 != 0)
			off = (double) (focus.getZoom() * (grid.getTile_size() / 2));

		/* filter invisible/out of view (based on current focus) tiles */
		if (outOfFocus(x, y, focus))
			return;

		/* draw image */
		target.setX((int)(0.75 * x * grid.getTile_size()));
		target.setY((int)(y * grid.getTile_size() + off/focus.getZoom()));
		target.setW((int)grid.getTile_size());
		target.setH((int)grid.getTile_size());
		target.setAlpha(alpha);
		img.Draw(getGraphicsContext2D(), target, focus);	//this method also takes care of zoom
	}

	// Test whether or not given hextile at (x,y) is currently visible
	// based on the given focus.
	protected boolean outOfFocus(int x, int y, Focus focus) {
		if(calcFocusBound_X_right(focus) < x)
			return true;
		if(calcFocusBound_Y_bottom(focus) < y)
			return true;
		if(calcFocusBound_X_left(focus) > x)
			return true;
		if(calcFocusBound_Y_top(focus) > y)
			return true;

		return false;
	}

	@Override
	public void onClick(MouseEvent e, Focus focus) {
		/** get hovered tile from mouse position */
		Point hote = getHoveredTile(e, focus);
		int hote_x = hote.x;
		int hote_y = hote.y;
		
		int focus_bound_X_left = calcFocusBound_X_left(focus);
		int focus_bound_X_right = calcFocusBound_X_right(focus);
		int focus_bound_Y_top = calcFocusBound_Y_top(focus);
		int focus_bound_Y_bottom = calcFocusBound_Y_bottom(focus);
		
		// check for bounderies
		if (hote_x >= focus_bound_X_left && hote_x < focus_bound_X_right) {
			if (hote_y >= focus_bound_Y_top && hote_y < focus_bound_Y_bottom) {
	
				// set hovered tile
				if(!grid.getTiles()[hote_x][hote_y].equals(selected_tile)) {
					selected_tile = (T_Tile) grid.getTiles()[hote_x][hote_y];
					needs_redraw = true;

					// inform listeners
					for (TileSelectedListener listener : listeners_selected) {
						listener.onSelection(selected_tile);
					}
				}
			}
		}	
	}
	

	@Override
	public void onHover(MouseEvent e, Focus focus) {
		/** get hovered tile from mouse position */
		Point hote = getHoveredTile(e, focus);
		int hote_x = hote.x;
		int hote_y = hote.y;
		
		int focus_bound_X_left = calcFocusBound_X_left(focus);
		int focus_bound_X_right = calcFocusBound_X_right(focus);
		int focus_bound_Y_top = calcFocusBound_Y_top(focus);
		int focus_bound_Y_bottom = calcFocusBound_Y_bottom(focus);

		// check for bounderies
		if (hote_x >= focus_bound_X_left && hote_x < focus_bound_X_right) {
			if (hote_y >= focus_bound_Y_top && hote_y < focus_bound_Y_bottom) {
	
				// set hovered tile
				if(!grid.getTiles()[hote_x][hote_y].equals(hovered_tile)) {
					hovered_tile = (T_Tile) grid.getTiles()[hote_x][hote_y];
					needs_redraw = true;
					
					// inform listeners
					for (TileHoveredListener listener : listeners_hover) {
						listener.onHovered(hovered_tile);
					}
				}
			}
		}	
	}

	private Point getHoveredTile(MouseEvent e, Focus focus) {
		/* Tile selection: */
		// add focus
		int fx = (int) (e.getX() - focus.getX());
		int fy = (int) (e.getY() - focus.getY());
		// consider rotation
		// TODO apply current focus rotation
		
		// check if an even or an off column was hit:
		int off = 0;
		if ((int) (fx / (focus.getZoom() * 0.75 * grid.getTile_size())) % 2 == 0)
			off = 0;
		else
			off = (int) (focus.getZoom() * (grid.getTile_size() / 2));
		
		int hote_x = (int) (fx / (0.75 * grid.getTile_size() * focus.getZoom()));
		int hote_y = (int) ((fy - off) / (grid.getTile_size() * focus.getZoom()));
		
		return new Point(hote_x, hote_y);
	}

	/** Return the canvas pixel position of given tile coordingates */
	public Point getTilePosition(int tile_x, int tile_y, Focus focus) {
	
		int off = 0;
		if (tile_x % 2 != 0)
			off = (int) (focus.getZoom() * (grid.getTile_size() / 2));
		
		int x_pxl = (int) (focus.getZoom() * 0.75 * (tile_x * grid.getTile_size()) + focus.getX());
		int y_pxl = (int) ((focus.getZoom() * tile_y * grid.getTile_size()) + focus.getY() + off);
		
		return new Point(x_pxl, y_pxl);
	}
	
	//////////////////////////////////////////////////////////////////
	
	// TODO for now assume 1920pixels in width
	// and 1080 in height TODO
	
	private int calcFocusBound_X_left(Focus focus) {
		return Math.max(0
				, (int)(
							(
									-(focus.getX() * 1.34)
									/(double)grid.getTile_size()
							) / focus.getZoom()
						)  - 1
				);
	}
	
	private int calcFocusBound_X_right(Focus focus) {
		return Math.min(grid.getTiles().length
				,(int) (
						calcFocusBound_X_left(focus)
						+ (1920.0/(grid.getTile_size() * 0.75))/focus.getZoom()
						+ 1)
				);
	}

	private int calcFocusBound_Y_top(Focus focus) {
		return Math.max(0
				, (int)(
							(
									-(focus.getY() * 1.00)
									/(double)grid.getTile_size()
							) / focus.getZoom()
						) - 1
				);
	}
	
	private int calcFocusBound_Y_bottom(Focus focus) {
		return Math.min(grid.getTiles()[0].length
				, (int) (
						calcFocusBound_Y_top(focus)
						+ (1080.0/(double)grid.getTile_size())/focus.getZoom()
						+ 1)
				);
	}
	
	//////////////////////////////////////////////////////////////////
	
	public void addOnSelectListener(TileSelectedListener listener) {
		listeners_selected.add(listener);
	}
	
	public void addOnHoverListener(TileHoveredListener listener) {
		listeners_hover.add(listener);
	}

	@Override
	public void onTileDrawState_ActivationChange() {
		this.forceUpdate();
	}

	
}
