package de.flapdoodle.ui.tab;

import de.flapdoodle.ui.tab.data.Document;
import de.flapdoodle.ui.tab.threading.SynchronizedDelegate;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DocumentUI extends Control {

	public DocumentUI(SynchronizedDelegate<Document> document) {
		setPrefHeight(300);
		setPrefWidth(300);
		
		setSkin(new DocumentUISkin(this));
		Paint paint=new Color(0.9,0.9,0.9,0.9);
		CornerRadii rad=new CornerRadii(5.0);
		Insets insets=new Insets(4, 4, 4, 4);
		setBackground(new Background(new BackgroundFill(paint, rad, insets)));
		
		Button btn = new Button();
		btn.setText("doc");
//		getChildren().add(btn);
		
		Button btn2 = new Button();
		btn2.setText("doc2");
		btn2.setTranslateX(100);
		btn2.setTranslateY(100);
		
		ScrollPane area = new ScrollPane();
//		Pane pane = new Pane();
		Group group=new Group();
//		pane.setPrefSize(200, 200);
//		pane.getChildren().addAll(btn, btn2);
		group.getChildren().addAll(btn, btn2);
		area.setContent(group);
		area.setManaged(true);
		getChildren().add(area);
	}
	
	private static class DocumentUISkin extends SkinBase<DocumentUI> {

		protected DocumentUISkin(DocumentUI control) {
			super(control);
		}
	}
}
