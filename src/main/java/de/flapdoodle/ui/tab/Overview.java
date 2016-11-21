package de.flapdoodle.ui.tab;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

@Deprecated
public class Overview extends Control {

	public Overview() {
		setPrefHeight(300);
		setPrefWidth(300);
		
		setSkin(new OverviewSkin(this));
		Paint paint=new Color(0.8,0.8,0.8,0.8);
		CornerRadii rad=new CornerRadii(5.0);
		Insets insets=new Insets(4, 4, 4, 4);
		setBackground(new Background(new BackgroundFill(paint, rad, insets)));
		
		Button btn = new Button();
		btn.setText("overview");
		getChildren().add(btn);
	}
	
	private static class OverviewSkin extends SkinBase<Overview> {

		protected OverviewSkin(Overview control) {
			super(control);
		}
	}
}
