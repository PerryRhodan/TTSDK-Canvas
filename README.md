# TTSDK-Canvas

Offeres a Leveled Canvas to provide a convenient and generic way to draw images, grids, and shapes on a JavaFX Canvas. This library depends on TTSDK-Core, TTDSK-Animation, TTSDK-Grid.

Each Leveld Canvas can have multiple abstract Drawing Levels. So far there a two types of Drawing Levels implemented: HexgridLevel and ObjectsLevel.
  - A HexgridLevel takes a Hexworld from TTSDK-Grid and draws it using the given image set.
  - The ObjectsLevel can draw DrawableObjcects. This is for instance an ImageObject: An image to be drawn a certain positon.
  
The LeveledCanvas itself is abstract where the handling of key and mouse scrolling input needs to be implemented by an extending class. Input can also be handled individually in each DrawingLevel.

The following example shows how this could be used to draw several layers of images. Each image could be a unit or player object - all it requires is a DrawableImage as defined in TTSDK-Animation.

```java
////// create levels and add them to the canvas
// One level for the background
PlainImage img_background = new PlainImage(new Image("file:res/img_background.png"));
ObjectsLevel level_background = new ObjectsLevel(width, height);
level_background.addObject(new ImageObject(0, 0, width, height, img_background ));
```

Set up all the levels you require. As I mentioned above, each DrawableObject, in this case an ImageObject, has a position which could correspond to a unit's position for instance.

```java
// ... set up objects for this level
ObjectsLevel level_middleground = new ObjectsLevel(width, height);
		
// ... set up objects for this level
ObjectsLevel level_foreground = new ObjectsLevel(width, height);
		
// then simply add them to the canvas
leveled_canvas.addLevel(level_background);
leveled_canvas.addLevel(level_middleground);
leveled_canvas.addLevel(level_foreground);
```

If the drawn scene is something that should move based on the player pposition/view, the focus of the LeveledCanvas can be updated.
```
leveled_canvas.getFocus().AddTo_X(...);
```
Of course, this could be done directly via the key input events. Updating the focus of the canvas will affect all of its drawing layers, as they all use their parent's focus to draw whatever it is they draw.


TODO explain drawing of hexgrid, drawing using tiledrawstates
