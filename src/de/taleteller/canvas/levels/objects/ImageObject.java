/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   ImageObject
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

import de.taleteller.animation.DrawableImage;
import de.taleteller.animation.focus.Focus;
import de.taleteller.canvas.UpdateFunction;
import de.taleteller.canvas.UpdateFunction_Linear;
import javafx.scene.canvas.GraphicsContext;



public class ImageObject extends DrawableObject {

	/** image to draw */
	DrawableImage image;

	public ImageObject(int x, int y, int w, int h
			, DrawableImage image) {
		super(x, y, w, h, 0, new UpdateFunction_Linear(), 1.0);
		this.image = image;
	}
	
	public ImageObject(int x, int y, int w, int h
			, DrawableImage image, UpdateFunction updatetype, double speedmod) {
		super(x, y, w, h, 0, updatetype, speedmod);
		this.image = image;
	}

	@Override
	public void Draw(GraphicsContext context, Focus focus) {
		if(!this.visibility)
			return;
		//rotation and zoom handled in images draw function
		image.Draw(context, position_current, focus);
	}

}
