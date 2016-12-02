package de.flapdoodle.ui.tab.components;

import java.time.LocalDateTime;

import de.flapdoodle.prototyping.layouts.Shapes;
import de.flapdoodle.ui.components.BoxBuilder;
import de.flapdoodle.ui.components.Sizes;
import de.flapdoodle.ui.tab.data.Columns;
import de.flapdoodle.ui.tab.data.EntityId;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

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
		
		
		Label dragMeHere = new Label("[d]");
		
		VBox content = BoxBuilder.vbox()
			.add(new Label(columnsId.uuid().toString()))
			.add(BoxBuilder.hbox()
					.add(Priority.ALWAYS,Sizes.asBigAsPossible(new Label("???")))
					.add(Priority.SOMETIMES,dragMeHere)
					.build())
			.add(Priority.ALWAYS, dummyTable())
			.build();
		
		
		
//		Button btn = new Button();
//		btn.setText(columnsId.uuid().toString());
//		//getChildren().add(btn);
//		Button dragBtn = new Button();
//		dragBtn.setText("[d]");
//		
//		VBox layout = new VBox(2);
//		layout.getChildren().addAll(btn, dragBtn);
//		getChildren().add(layout);
//		
//		this.dragButton=dragBtn;
//		this.dragButton.getProperties().put(Dragging.DRAG_ME, true);
		
		getChildren().add(content);
		
		Dragging.moveNodeBy(this, dragMeHere);
	}
	
	private TableView<Integer> dummyTable() {
		TableView<Integer> tableView = new TableView<>();
		tableView.setEditable(true);
		TableColumn<Integer, String> scolumn=new TableColumn<>("Text");
		scolumn.setEditable(true);
		scolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<String>(""+param.getValue()));
		TableColumn<Integer, Integer> icolumn=new TableColumn<>("Zahl");
		icolumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<Integer>(param.getValue()));
		icolumn.setEditable(true);
		
		tableView.setItems(FXCollections.observableArrayList(1,2,3,4));
		
		tableView.getColumns().addAll(scolumn, icolumn);
		return tableView;
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
