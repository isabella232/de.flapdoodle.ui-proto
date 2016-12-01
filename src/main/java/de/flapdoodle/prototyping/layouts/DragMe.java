package de.flapdoodle.prototyping.layouts;

import java.util.concurrent.ThreadLocalRandom;

import de.flapdoodle.ui.tab.components.ColumnsUI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Control;
import javafx.scene.control.SkinBase;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class DragMe extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Drag Me");
		
		Pane root=new Pane();
		
		root.getChildren().add(new DragDrop());
		root.getChildren().add(new MoveMe());
		
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
	}
	
	public static class MoveMe extends Control {

		MouseCoords coords=new MouseCoords();
		
		public MoveMe() {
			setSkin(new GenericSkin<Control>(this));
			
			setLayoutX(ThreadLocalRandom.current().nextInt(100));
			setLayoutY(ThreadLocalRandom.current().nextInt(100));
			
			Paint paint=new Color(0.6,0.6,0.6,0.6);
			CornerRadii rad=new CornerRadii(5.0);
			Insets insets=new Insets(-4, -4, -4, -4);
			setBackground(new Background(new BackgroundFill(paint, rad, insets)));

			getChildren().add(Shapes.rectangleOf(50, 50, Color.RED));

			addEventFilter(MouseEvent.ANY, e -> {
				if (coords.dragStarted) {
					System.out.println(e);
					if (e.getEventType()==MouseEvent.MOUSE_RELEASED) {
						coords.dragStarted=false;
					}
					if (e.getEventType()!=MouseEvent.MOUSE_DRAGGED) {
						e.consume();
					}
				}
			});
			
			setOnMousePressed(e -> {
				System.out.println(e);
				coords.x = e.getX();
				coords.y = e.getY();
				coords.layoutX=getLayoutX();
				coords.layoutY=getLayoutY();
				coords.dragStarted=true;
			});
			
			setOnMouseDragged(e -> {
				System.out.println(e);
				System.out.println((e.getX()-coords.x)+":"+(e.getY()-coords.y));
				setLayoutX(e.getX()-coords.x+getLayoutX());
				setLayoutY(e.getY()-coords.y+getLayoutY());
			});
			
		}
	}
	
	public static class DragDrop extends Control {
		
		public DragDrop() {
			setSkin(new GenericSkin<Control>(this));
			
			setLayoutX(ThreadLocalRandom.current().nextInt(100));
			setLayoutY(ThreadLocalRandom.current().nextInt(100));
			
			Paint paint=new Color(0.6,0.6,0.6,0.6);
			CornerRadii rad=new CornerRadii(5.0);
			Insets insets=new Insets(-4, -4, -4, -4);
			setBackground(new Background(new BackgroundFill(paint, rad, insets)));

			getChildren().add(Shapes.rectangleOf(50, 50, Color.BLUE));
			
			// Manage drag
			setOnDragDetected(event -> {
			    /* drag was detected, start a drag-and-drop gesture*/
			    Dragboard db = this.startDragAndDrop(TransferMode.MOVE);

			    // Visual during drag
			    SnapshotParameters snapshotParameters = new SnapshotParameters();
			    snapshotParameters.setFill(Color.TRANSPARENT);
			    db.setDragView(snapshot(snapshotParameters, null));

			    /* Put a string on a dragboard */
			    ClipboardContent content = new ClipboardContent();
			    content.putString("Fooo");
			    db.setContent(content);

			    event.consume();
			});
		}
	}
	
	public static class GenericSkin<T extends Control> extends SkinBase<T> {

		protected GenericSkin(T control) {
			super(control);
		}
	}
	
	private static class MouseCoords {
		double x;
		double y;
		double layoutX;
		double layoutY;
		boolean dragStarted;
	}
}
