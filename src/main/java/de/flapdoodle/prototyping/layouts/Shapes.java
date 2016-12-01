package de.flapdoodle.prototyping.layouts;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public abstract class Shapes {

	public static Rectangle rectangleOf(double width, double height, Paint fill) {
		return new Rectangle(width, height, fill);
	}
}
