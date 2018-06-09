/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   LeveledCanvas
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.levels.DrawingLevel;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;


/**
 * The LeveledCanvas offers base functionality to add 
 * different types of drawing levels. The class itself
 * is abstract, and an extending class must implements
 * its behavior in regards to input events.
 */
public abstract class LeveledCanvas extends Pane {
	/** reference to itself */
	LeveledCanvas self = this;
	
	/** general run flag for its thread */
	boolean isrunning = true;
	/** the refresh rate in milli-seconds */
	int m_nRefreshRate = 10;
	
	/* size of the canvas in pixels */
	int width;
	int height;
	
	/** List of the drawing levels in this leveled canvas. */
	ArrayList<DrawingLevel> levels;

	/** update thread, used for focus jumps */
	Thread updatethread;
	
	/** current canvas focus */
	Focus focus;
	/** current canvas rotation */
	double rotation;
	int rotation_x;
	int rotation_y;
	
	/* --- focus jumps --- */
	// A focus jump basically is to automatically 
	// zoom and scroll onto target position using
	// a generic update function.
	/** target focus, to make jumps */
	Focus target_focus;
	/** flag to trigger jump to target focus */
	boolean do_focus_jump;
	UpdateFunction focus_jump_updatefunction_xy;
	UpdateFunction focus_jump_updatefunction_zoom;
	
	/** time to do the jump */
	int focus_jump_time;	//TODO unused for now
	/* --- ----- ----- --- */
	
	///////////////////////////////////////////////////////////////

	
	public LeveledCanvas(int width, int height, Focus focus) {
		this.width = width;
		this.height = height;
		this.focus = focus;
		
		levels = new ArrayList<>();
		target_focus = Focus.createNew();
		
		// set some default update functions
		focus_jump_updatefunction_xy = new UpdateFunction_Linear();
		focus_jump_updatefunction_zoom = new UpdateFunction_Linear();
		
		// default rotation center
		rotation_x = width/2;
		rotation_y = height/2;
		
		/* make sure keyevents work */
		this.setFocusTraversable(true);
		this.addEventFilter(MouseEvent.ANY, (e) -> this.requestFocus());
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> this.onClick(e));
		this.addEventHandler(MouseEvent.MOUSE_MOVED, (e) -> this.onHover(e));
		// TODO on mouse entered, left
		this.addEventHandler(ScrollEvent.SCROLL, (e) -> this.OnScrollEvent(e));
		this.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> this.OnKeyEvent_KeyPressed(e));
		this.addEventHandler(KeyEvent.KEY_PRESSED, (e) -> this.OnKeyEvent_KeyReleased(e));
		
		/** Create the update thread. It is
		 *  used to allow for focus jumps. */ 
		updatethread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(self.isrunning) {
					// Check if focus jump is currently ongoing
					if(self.do_focus_jump) {
						// Update focus based on set function and
						// required distance to update.
						int difference = target_focus.getX() - focus.getX();
						focus.AddTo_X(focus_jump_updatefunction_xy
								.apply(difference));
						difference = target_focus.getY() - focus.getY();
						focus.AddTo_Y(focus_jump_updatefunction_xy
								.apply(difference));
						
						double difference_zoom = target_focus.getZoom() - focus.getZoom();
							focus.AddTo_Zoom(focus_jump_updatefunction_zoom
									.apply(difference_zoom));
						double differece_rotation = target_focus.getRotation() - focus.getRotation();
							focus.AddTo_Rotation(focus_jump_updatefunction_zoom
									.apply(differece_rotation));	//TODO use own function here, not zooms
						
						/* check if jump is complete */
						if(Math.abs(target_focus.getX() - focus.getX()) < 3 &&
							 Math.abs(target_focus.getY() - focus.getY()) < 3 &&
								Math.abs(target_focus.getZoom() - focus.getZoom()) < 0.05) {
							synchronized (self) {
								self.do_focus_jump = false;
							}
						}
					}

					try {
						Thread.sleep(m_nRefreshRate);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}
			}
		});
		// start thread immediately
		updatethread.start();
		
		this.setPrefSize(width, height);
	}
	
	/**
	 * Adds a drawing level, sorting all levels by the z-values.
	 * @param level
	 */
	public void addLevel(DrawingLevel level) {
		level.setParent(this);
		focus.register(level);

		levels.add(level);
		Collections.sort(levels, new Comparator<DrawingLevel>() {
			@Override
			public int compare(DrawingLevel o1, DrawingLevel o2) {
				if(o1.getZ() > o2.getZ())
					return  1;
				if(o2.getZ() > o1.getZ())
					return -1;
				return 0;
			}   
        });
		
		this.getChildren().add(level);
	}
	
	/**
	 * Update current focus to given target parameters.
	 * Wont do anything if given update functions are null.
	 * @param tx
	 * @param ty
	 * @param tzoom
	 * @param focus_jump_updatefunction_xy
	 * @param focus_jump_updatefunction_zoom
	 */
	public void jumpFocus(int tx, int ty, double tzoom,
			double trotation,	//TODO also add rotation center 
			UpdateFunction focus_jump_updatefunction_xy,
			UpdateFunction focus_jump_updatefunction_zoom) {
		if(focus_jump_updatefunction_xy == null ||
				focus_jump_updatefunction_zoom == null)
			return;
		this.do_focus_jump = true;
		this.target_focus.setX(tx);
		this.target_focus.setY(ty);
		this.target_focus.setZoom(tzoom);
		this.target_focus.setRotation(trotation);
		this.focus_jump_updatefunction_xy = focus_jump_updatefunction_xy;
		this.focus_jump_updatefunction_zoom = focus_jump_updatefunction_zoom;
	}
	
	///////////////////////////////////////////////////////////////

	public abstract void OnKeyEvent_KeyPressed(KeyEvent event);
	
	public abstract void OnScrollEvent(ScrollEvent event);
	
	public abstract void OnKeyEvent_KeyReleased(KeyEvent event);
	
	///////////////////////////////////////////////////////////////

	/** Mouse clicked */
	private void onClick(MouseEvent e) {
		for (DrawingLevel level : levels) {
			level.onClick(e, focus);
		}
	}
	
	/** Mouse moved */
	private void onHover(MouseEvent e) {
		for (DrawingLevel level : levels) {
			level.onHover(e, focus);
		} 
	}
	
	///////////////////////////////////////////////////////////////

	/**
	 * Makes sure all levels are redrawn upon a
	 * focus update.
	 */
	public void onFocusUpdate() {
		forceUpdate();
	}
	
	/** calls update function immediately */
	public void forceUpdate() {
		for (DrawingLevel level : levels) {
			level.forceUpdate();
		}
	}

	/** End all levels update threads */
	public void destroy() {
		isrunning = false;
		for (DrawingLevel level : levels) {
			level.destroy();
		}
	}
	
	public Focus getFocus() {
		return focus;
	}
	
}
