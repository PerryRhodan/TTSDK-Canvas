/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   ObjectsLevel
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

import java.util.LinkedList;

import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.levels.objects.DrawableObject;
import javafx.application.Platform;


/**
 * Implementation of a DrawingLevel that draws drawable objects.
 * These can be simple shapes like a rect, an image loaded from file,
 * or any other implementation of the DrawableObject abstract class.
 */
public class ObjectsLevel extends DrawingLevel {

	/** the objects added to the level.
	 *  They can still be hidden and therefore
	 *  be unseen. */
	LinkedList<DrawableObject> objects;
	
	public ObjectsLevel(int width, int height) {
		super(width, height);
		objects = new LinkedList<>();		
	}
	
	@Override
	public boolean NeedsUpdate() {
		for (DrawableObject obj : objects) {
			if(obj.needsUpdate())
				return true;
		}
		return false;
	}

	@Override
	public void forceUpdate() {
		for (DrawableObject obj : objects) {
			obj.forceUpdate();
		}
	}
	
	@Override
	public void onFocusUpdate() {
		// update the level
		this.forceUpdate();
	}
	
	///////////////////////////////////////////////

	@Override
	public void Draw(Focus focus) {
		for (DrawableObject obj : objects) {
			obj.Draw(getGraphicsContext2D(), focus);
		}
	}
	
	private void clearAll() {
		this.getGraphicsContext2D().clearRect(0
				, 0, this.getWidth(), this.getHeight());
	}
	
	///////////////////////////////////////////////
	
	public void addObject(DrawableObject obj) {
		objects.add(obj);
	}
	
	public void removeObject(DrawableObject obj) {
		objects.remove(obj);
		this.forceUpdate();
		
		// force a clear after objects were removed
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clearAll();
			}
		});
	}
	
	public void removeAllObjects() {
		objects.clear();
		this.forceUpdate();
		
		// force a clear after objects were removed
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				clearAll();
			}
		});
	}

	public LinkedList<DrawableObject> getObjects() {
		return objects;
	}
	
}
