package de.flapdoodle.ui.tab.components;

import de.flapdoodle.ui.tab.data.Columns;
import de.flapdoodle.ui.tab.data.EntityId;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColumnsUI extends Control {

	private Button dragButton;

	private ColumnsUI(EntityId<Columns> columnsId) {
		setSkin(new ColumnsUISkin(this));
		//setPrefHeight(100);
		//setPrefWidth(50);
		
		Paint paint=new Color(0.9,0.9,0.9,0.9);
		CornerRadii rad=new CornerRadii(5.0);
		Insets insets=new Insets(-4, -4, -4, -4);
		setBackground(new Background(new BackgroundFill(paint, rad, insets)));
		
		Button btn = new Button();
		btn.setText(columnsId.uuid().toString());
		//getChildren().add(btn);
		Button dragBtn = new Button();
		dragBtn.setText("[d]");
		
		VBox layout = new VBox(2);
		layout.getChildren().addAll(btn, dragBtn);
		getChildren().add(layout);
		
		this.dragButton=dragBtn;
		this.dragButton.getProperties().put(Dragging.DRAG_ME, true);
		
	}
	
	public static Node of(EntityId<Columns> columnsId) {
		ColumnsUI columnsUI = new ColumnsUI(columnsId);
		return columnsUI; //Dragging.makeDraggable(columnsUI.dragButton, columnsUI); 
	}
	
	private static class ColumnsUISkin extends SkinBase<ColumnsUI> {

		protected ColumnsUISkin(ColumnsUI control) {
			super(control);
		}
	}

}
