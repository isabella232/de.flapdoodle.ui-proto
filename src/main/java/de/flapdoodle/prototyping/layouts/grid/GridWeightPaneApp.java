package de.flapdoodle.prototyping.layouts.grid;

import de.flapdoodle.prototyping.layouts.Shapes;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GridWeightPaneApp extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Layout Prototype");

		GridWeightPane root=new GridWeightPane();
		
		
		root.getChildren().add(Shapes.rectangleOf(10, 10, Color.RED));
		root.getChildren().add(Shapes.rectangleOf(20, 10, Color.GREEN));
		root.getChildren().add(Shapes.rectangleOf(10, 20, Color.BLUE));
		root.getChildren().add(Shapes.rectangleOf(20, 20, Color.BLACK));
		
		
		Paint stroke=Color.BLUE;
		BorderStrokeStyle style=BorderStrokeStyle.DOTTED;
		CornerRadii radii=new CornerRadii(3);
		BorderWidths widths=new BorderWidths(2);
		root.setBorder(new Border(new BorderStroke(stroke, style, radii, widths)));
		
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
	}
}
