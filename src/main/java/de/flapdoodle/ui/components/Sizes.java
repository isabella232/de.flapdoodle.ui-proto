package de.flapdoodle.ui.components;

import javafx.scene.layout.Region;

public abstract class Sizes {

	public static <T extends Region> T asBigAsPossible(T node) {
		node.setMaxWidth(Double.MAX_VALUE);
		node.setMaxHeight(Double.MAX_VALUE);
		return node;
	}
}
