package de.flapdoodle.ui.tab.block;

import javafx.scene.control.Button;
import javafx.scene.control.Control;

public class Block extends Control {

	public Block() {
		Button btn = new Button();
		btn.setText("Whoohoo");
		getChildren().add(btn);
	}
}
