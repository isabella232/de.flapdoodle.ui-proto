package de.flapdoodle.ui.sample;

import javafx.scene.control.Button;
import javafx.scene.control.Control;

public class MyControl extends Control {
	public MyControl() {
		getStyleClass().add("custom-control");
		//setSkinClassName(MySkin.class.getName());
		setSkin(new MySkin(this));
		Button btn = new Button();
		btn.setText("Whoohoo");
		getChildren().add(btn);
	}

	@Override
	public String getUserAgentStylesheet() {
		return MyControl.class.getResource("customcontrol.css").toExternalForm();
	}
}
