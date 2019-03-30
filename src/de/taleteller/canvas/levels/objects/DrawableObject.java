/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   DrawableObject
 * 
 * Summary:
 *   
 * History:
 * 
 * Ideas:
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas.levels.objects;

import de.taleteller.animation.DrawPosition;
import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.UpdateFunction;
import javafx.scene.canvas.GraphicsContext;


/**
 * TaletellerAnimation
 *   DrawableObject
 * 
 * 
 * 
 * @author Stephan Hogrefe
 */
public abstract class DrawableObject {
	
	/** current position, x y positions, width, height, rotation (degree) */
	DrawPosition position_current;
	/** target position */
	DrawPosition position_target;
	
	/** update type */
	UpdateFunction updatetype;
	/** movement speed modifier */
	double speedmod;

	/** visibility flag, ie the object is only drawn when true */
	boolean visibility;
	
	/** flag to force a redraw upon a force update call */
	boolean update_forced;
	
	/**
	 * Creates a new abstract DrawableObject, with
	 * @param x position x
	 * @param y position y
	 * @param w width
	 * @param h height
	 * @param rot rotation in degrees
	 * @param updatetype update function
	 * @param speedmod speed mod factor on update
	 */
	public DrawableObject(int x, int y, int w, int h, int rot
			, UpdateFunction updatetype, double speedmod) {
		position_current = new DrawPosition(x-1, y, w, h, rot);
		position_target = new DrawPosition(x, y, w, h, rot);

		visibility = true;
		
		this.updatetype = updatetype;
		this.speedmod = speedmod;
	}
	
	/**
	 * Draws the object in the given context, at 
	 * given focus.
	 * @param context
	 * @param focus
	 */
	public abstract void Draw(GraphicsContext context, Focus focus);
	
	/**
	 * Updates target position
	 * @param tx target pos x
	 * @param ty target pos y
	 * @param tw target width
	 * @param th target height
	 * @param trot target rotation in degrees
	 */
	public void setTarget(int tx, int ty, int tw, int th, int trot) {
		position_target.setX(tx);
		position_target.setY(ty);
		position_target.setW(tw);
		position_target.setH(th);
		position_target.setRotation(trot);
	}
	
	/**
	 * Updates target position
	 * @param tx target pos x
	 * @param ty target pos y
	 */
	public void setTarget(int tx, int ty) {
		position_target.setX(tx);
		position_target.setY(ty);
		position_target.setW(position_target.getW());
		position_target.setH(position_target.getH());
		position_target.setRotation(position_target.getRotation());
	}
	
	/**
	 * private update call,
	 * adjusts position towards target, based on the given 
	 * update function.
	 */
	private void update() {
		if(position_current.getX() != position_target.getX()) {
			position_current.AddTo_X((int)(speedmod * updatetype.apply(
					position_target.getX() - position_current.getX())));
		}
		if(position_current.getY() != position_target.getY()) {
			position_current.AddTo_Y((int)(speedmod * updatetype.apply(
					position_target.getY() - position_current.getY())));
		}
		if(position_current.getW() != position_target.getW()) {
			position_current.AddTo_W(Math.max(1, (int)(speedmod * updatetype.apply(
					position_target.getW() - position_current.getW()))));
		}
		if(position_current.getH() != position_target.getH()) {
			position_current.AddTo_H(Math.max(1, (int)(speedmod * updatetype.apply(
					position_target.getH() - position_current.getH()))));
		}
		if(position_current.getRotation() != position_target.getRotation()) {
			position_current.AddTo_Rotation(Math.max(1, (int)(speedmod * updatetype.apply(
					position_target.getRotation()-position_current.getRotation()))));
		}
	}
	
	/**
	 * Set given update function.
	 * @param updatetype
	 */
	public void setUpdatetype(UpdateFunction updatetype) {
		this.updatetype = updatetype;
	}
	
	/**
	 * Forces an update calculation of the object.
	 */
	public void forceUpdate() {
		update();
		update_forced = true;
	}
	
	/** Returns whether or not the object requires an update,
	 *  ie if the current position is different from the 
	 *  target position. */
	public boolean needsUpdate() {
		if(update_forced) {
			update_forced = false;
			return true;
		}

		if(position_current.getX() != position_target.getX())
			return true;
		if(position_current.getY() != position_target.getY())
			return true;
		if(position_current.getW() != position_target.getW())
			return true;
		if(position_current.getH() != position_target.getH())
			return true;
		if(position_current.getRotation() != position_target.getRotation())
			return true;
		return false;
	}

	/** Returns whether or not this object is being drawn. */
	public boolean isVisible() {
		return visibility;
	}

	/** Determines whether or not this object is being drawn. */
	public void setVisible(boolean visibility) {
		this.visibility = visibility;
	}

}
