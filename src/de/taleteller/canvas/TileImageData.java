/**
 * www.taleteller.de
 * 
 * TaletellerCanvas
 *   TileImageData
 * 
 * Summary:
 *   
 * 
 * History:
 *   09.06.2018 - Cleaning of code and comments
 *   
 * 
 * Ideas:
 *   - TODO not pre-defined colours, but
 *       allow for all possible colours
 *   - TODO all setters and getters
 *   - TODO also have setters for normal 
 *       Image types for convenience
 * 
 * Stephan Hogrefe, Edinburgh, 2018
 */
package de.taleteller.canvas;

import java.util.ArrayList;
import java.util.Arrays;

import de.taleteller.animation.DrawableImage;
import de.taleteller.animation.PlainImage;
import javafx.scene.image.Image;

/**
 * Convenience class holding various image data
 * so they can be easily loaded from an image
 * directory containing the various colored
 * images for (hex-)tiles.
 */
public class TileImageData {

	/** default DrawableImage */
	DrawableImage img_default;
	/** selected */
	DrawableImage img_selected;
	/** hover */
	DrawableImage img_hover;
	
	/* borders */
	DrawableImage img_border_top;
	DrawableImage img_border_topright;
	DrawableImage img_border_bottomright;
	DrawableImage img_border_bottom;
	DrawableImage img_border_bottomleft;
	DrawableImage img_border_topleft;
	
	/* colours */
	DrawableImage img_black;
	DrawableImage img_darkgrey;
	DrawableImage img_grey;
	DrawableImage img_lightgrey;
	DrawableImage img_white;
	DrawableImage img_pink;
	
	DrawableImage img_red;
	DrawableImage img_redorange;
	DrawableImage img_orange;
	DrawableImage img_orangeyellow;
	DrawableImage img_yellow;
	DrawableImage img_yellowgreen;
	DrawableImage img_green;
	DrawableImage img_greenblue;
	DrawableImage img_blue;

	//////////////////////////////////////////////////////
	
	/** state images, ie the images used for draw states as
	 *  implemented in TTSDK-Grid */
	ArrayList<DrawableImage> state_images;
	
	//TODO other colors
	//TODO all setters and getters
	//TODO also have setters for normal Image type for convenience
	
	public TileImageData(DrawableImage img_default) {
		this.img_default = img_default;
		
		state_images = new ArrayList<>();
	}

	public TileImageData(Image image) {
		this.img_default = new PlainImage(image);
		state_images = new ArrayList<>();
	}
	
	public TileImageData(String path) {
		// load
		/** default */
		img_default = new PlainImage(new Image("file:" + path + "/img_default.png"));
		/** selected */
		img_selected = new PlainImage(new Image("file:" + path + "/img_selected.png"));
		/** hover */
		img_hover = new PlainImage(new Image("file:" + path + "/img_hover.png"));
		
		/* border images */
		img_border_top = new PlainImage(new Image("file:" + path + "/img_border_top.png"));
		img_border_topright = new PlainImage(new Image("file:" + path + "/img_border_topright.png"));
		img_border_bottomright = new PlainImage(new Image("file:" + path + "/img_border_bottomright.png"));
		img_border_bottom = new PlainImage(new Image("file:" + path + "/img_border_bottom.png"));
		img_border_bottomleft = new PlainImage(new Image("file:" + path + "/img_border_bottomleft.png"));
		img_border_topleft = new PlainImage(new Image("file:" + path + "/img_border_topleft.png"));
		
		/* colours */
		img_white = new PlainImage(new Image("file:" + path + "/img_white.png"));
		img_green = new PlainImage(new Image("file:" + path + "/img_green.png"));
		img_yellowgreen = new PlainImage(new Image("file:" + path + "/img_yellowgreen.png"));
		img_yellow = new PlainImage(new Image("file:" + path + "/img_yellow.png"));
		img_orangeyellow = new PlainImage(new Image("file:" + path + "/img_orangeyellow.png"));
		img_orange = new PlainImage(new Image("file:" + path + "/img_orange.png"));
		img_redorange = new PlainImage(new Image("file:" + path + "/img_redorange.png"));
		img_red = new PlainImage(new Image("file:" + path + "/img_red.png"));
		
		
		img_pink = new PlainImage(new Image("file:" + path + "/img_pink.png"));
		img_greenblue = new PlainImage(new Image("file:" + path + "/img_greenblue.png"));
		img_blue = new PlainImage(new Image("file:" + path + "/img_blue.png"));
		
		img_lightgrey = new PlainImage(new Image("file:" + path + "/img_lightgrey.png"));
		img_grey = new PlainImage(new Image("file:" + path + "/img_grey.png"));
		img_darkgrey = new PlainImage(new Image("file:" + path + "/img_darkgrey.png"));
		img_black = new PlainImage(new Image("file:" + path + "/img_black.png"));
		
		setState_images(
				img_white, img_green, img_yellowgreen, img_yellow,
				img_orangeyellow, img_orange, img_redorange, img_red
				);
	}
	
	//////////////////////////////////////////////////////

	public DrawableImage getImg_default() {
		return img_default;
	}

	public DrawableImage getImg_hover() {
		if(img_hover != null)
			return img_hover;
		else
			return img_default;
	}
	
	public DrawableImage getImg_selected() {
		if(img_selected != null)
			return img_selected;
		else
			return img_default;
	}
	
	public DrawableImage getFromValue(double value, double max) {
		if(state_images.size() <= 0)
			return img_default;
		int index
			= Math.min(state_images.size()-1, Math.max(0, (int)(value/max * state_images.size())));
		return state_images.get(index);
	}

	public DrawableImage getImg_black() {
		return img_black;
	}

	public DrawableImage getImg_darkgrey() {
		return img_darkgrey;
	}

	public DrawableImage getImg_grey() {
		return img_grey;
	}

	public DrawableImage getImg_lightgrey() {
		return img_lightgrey;
	}

	public DrawableImage getImg_white() {
		return img_white;
	}

	public DrawableImage getImg_pink() {
		return img_pink;
	}

	public DrawableImage getImg_red() {
		return img_red;
	}

	public DrawableImage getImg_redorange() {
		return img_redorange;
	}

	public DrawableImage getImg_orange() {
		return img_orange;
	}

	public DrawableImage getImg_orangeyellow() {
		return img_orangeyellow;
	}

	public DrawableImage getImg_yellow() {
		return img_yellow;
	}

	public DrawableImage getImg_yellowgreen() {
		return img_yellowgreen;
	}

	public DrawableImage getImg_green() {
		return img_green;
	}

	public DrawableImage getImg_greenblue() {
		return img_greenblue;
	}

	public DrawableImage getImg_blue() {
		return img_blue;
	}

	public ArrayList<DrawableImage> getState_images() {
		return state_images;
	}

	public void setImg_default(DrawableImage img_default) {
		this.img_default = img_default;
	}

	public void setImg_selected(DrawableImage img_selected) {
		this.img_selected = img_selected;
	}

	public void setImg_hover(DrawableImage img_hover) {
		this.img_hover = img_hover;
	}

	public void setImg_black(DrawableImage img_black) {
		this.img_black = img_black;
	}

	public void setImg_darkgrey(DrawableImage img_darkgrey) {
		this.img_darkgrey = img_darkgrey;
	}

	public void setImg_grey(DrawableImage img_grey) {
		this.img_grey = img_grey;
	}

	public void setImg_lightgrey(DrawableImage img_lightgrey) {
		this.img_lightgrey = img_lightgrey;
	}

	public void setImg_white(DrawableImage img_white) {
		this.img_white = img_white;
	}

	public void setImg_pink(DrawableImage img_pink) {
		this.img_pink = img_pink;
	}

	public void setImg_red(DrawableImage img_red) {
		this.img_red = img_red;
	}

	public void setImg_redorange(DrawableImage img_redorange) {
		this.img_redorange = img_redorange;
	}

	public void setImg_orange(DrawableImage img_orange) {
		this.img_orange = img_orange;
	}

	public void setImg_orangeyellow(DrawableImage img_orangeyellow) {
		this.img_orangeyellow = img_orangeyellow;
	}

	public void setImg_yellow(DrawableImage img_yellow) {
		this.img_yellow = img_yellow;
	}

	public void setImg_yellowgreen(DrawableImage img_yellowgreen) {
		this.img_yellowgreen = img_yellowgreen;
	}

	public void setImg_green(DrawableImage img_green) {
		this.img_green = img_green;
	}

	public void setImg_greenblue(DrawableImage img_greenblue) {
		this.img_greenblue = img_greenblue;
	}

	public void setImg_blue(DrawableImage img_blue) {
		this.img_blue = img_blue;
	}

	public void setState_images(DrawableImage... state_images) {
		this.state_images = new ArrayList<>(Arrays.asList(state_images));
	}

	public DrawableImage getImg_border_top() {
		return img_border_top;
	}

	public DrawableImage getImg_border_topright() {
		return img_border_topright;
	}

	public DrawableImage getImg_border_bottomright() {
		return img_border_bottomright;
	}

	public DrawableImage getImg_border_bottom() {
		return img_border_bottom;
	}

	public DrawableImage getImg_border_bottomleft() {
		return img_border_bottomleft;
	}

	public DrawableImage getImg_border_topleft() {
		return img_border_topleft;
	}

	public void setImg_border_top(DrawableImage img_border_top) {
		this.img_border_top = img_border_top;
	}

	public void setImg_border_topright(DrawableImage img_border_topright) {
		this.img_border_topright = img_border_topright;
	}

	public void setImg_border_bottomright(DrawableImage img_border_bottomright) {
		this.img_border_bottomright = img_border_bottomright;
	}

	public void setImg_border_bottom(DrawableImage img_border_bottom) {
		this.img_border_bottom = img_border_bottom;
	}

	public void setImg_border_bottomleft(DrawableImage img_border_bottomleft) {
		this.img_border_bottomleft = img_border_bottomleft;
	}

	public void setImg_border_topleft(DrawableImage img_border_topleft) {
		this.img_border_topleft = img_border_topleft;
	}
	
}
