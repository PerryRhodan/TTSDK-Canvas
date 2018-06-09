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

import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.UpdateFunction;
import de.taleteller.canvas.UpdateFunction_Linear;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

public class OvalObject extends DrawableObject {

	public OvalObject(int x, int y, int w, int h
			, UpdateFunction updatetype, double speedmod) {
		super(x, y, w, h, 0, updatetype, speedmod);
	}
	
	public OvalObject(int x, int y, int w, int h) {
		super(x, y, w, h, 0, new UpdateFunction_Linear(), 1.0);
	}

	@Override
	public void Draw(GraphicsContext context, Focus focus) {
		if(!this.visibility)
			return;
		context.save();
		// apply rotation
		Rotate r = new Rotate(focus.getRotation() + position_current.getRotation()
				, focus.getRotation_x() + position_current.getRotation_x() * focus.getZoom() + focus.getX()
				, focus.getRotation_y() + position_current.getRotation_y() * focus.getZoom() + focus.getY());
        context.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		context.strokeOval(
				position_current.getX() * focus.getZoom() + focus.getX(),
				position_current.getY() * focus.getZoom() + focus.getY(),
				position_current.getW() * focus.getZoom(),
				position_current.getH() * focus.getZoom());
		context.restore();
	}

}
