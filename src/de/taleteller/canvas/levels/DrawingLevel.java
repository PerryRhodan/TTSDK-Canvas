/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   DrawingLevel
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

import de.taleteller.animation.focus.Focus;
import de.taleteller.animation.focus.FocusUpdateListener;
import de.taleteller.canvas.LeveledCanvas;
import de.taleteller.core.logging.Log;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 * A DrawingLevel can be added to a LeveledCanvas, and 
 * updates automatically. 
 */
public abstract class DrawingLevel extends Canvas implements FocusUpdateListener {
	
	/** parent TTC_canvas */
	private LeveledCanvas parent;
	/** self */
	private DrawingLevel self = this;
	
	/** z value */
	int z;
	
	/** the actual thread to run the code on */
	private Thread updatethread;
	/** flag to run/stop thread execution */
	private boolean isrunning = true;
	/** Iteration rate in ms */
	private int m_nRefreshRate = 10;
	
	////////////////////////////////////////////////////////////
	
	/** expects dimensions in pixels */
	public DrawingLevel(int width, int height) {
		super(width, height);
		
		/** Create the update thread */
		updatethread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(self.isrunning) {
					// only redraw when necessary
					if(self.needsUpdate())
						self.update();
					
					try {
						Thread.sleep(m_nRefreshRate);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		});

	}
	
	////////////////////////////////////////////////////////////
	
	public abstract void Draw(Focus focus);

	public abstract boolean NeedsUpdate();
	
	////////////////////////////////////////////////////////////
	
	public void forceUpdate() {
		update();
	}
	
	public void setParent(LeveledCanvas parent) {
		boolean hasParent = (this.parent != null);
		if(!hasParent) {
			this.parent = parent;
			// start the thread once a parent has been set
			updatethread.start();
		} else
			Log.ERROR("TaletellerCanvas::DrawingLevel:: Tried to set parent "
					+ "LeveledCanvas to a DrawingLevel that already has a parent!");
	}

	public void destroy() {
		isrunning = false;
	}
	
	public void onClick(MouseEvent e, Focus focus) {
		Log.WARN("TaletellerCanvas::DrawingLevel:: onClick(MouseEvent e, Focus focus)"
				+ " should be overridden!");
	}

	public void onHover(MouseEvent e, Focus focus) {
		Log.WARN("TaletellerCanvas::DrawingLevel:: onHover(MouseEvent e, Focus focus)"
				+ " should be overridden!");
	}
	
	////////////////////////////////////////////////////////////

	private void update() {
		// Redraws the whole level
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clearAll();
				Draw(parent.getFocus());
			}
		});
	}
	
	private void clearAll() {
		//clear background
		this.getGraphicsContext2D().clearRect(0, 0
				, this.getWidth(), this.getHeight());
	}
	
	private boolean needsUpdate() {
		if(NeedsUpdate())
			return true;
		
		return false;		
	}
	
	////////////////////////////////////////////////////////////
	
	public int getZ() {
		return z;
	}
	
}
