package de.flapdoodle.prototyping.layouts;

import de.flapdoodle.ui.components.Constraint;
import de.flapdoodle.ui.components.GridLayoutBuilder;
import de.flapdoodle.ui.components.MinMax;
import de.flapdoodle.ui.components.Percent;
import de.flapdoodle.ui.components.Weight;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class WeightedLayout extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}

	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Layout Prototype");
		
		//Button root=new Button("foo");
		
		HBox hbox = new HBox(3);
		
		hbox.setFillHeight(true);
		
		Paint stroke=Color.BLUE;
		BorderStrokeStyle style=BorderStrokeStyle.DOTTED;
		CornerRadii radii=new CornerRadii(3);
		BorderWidths widths=new BorderWidths(2);
		hbox.setBorder(new Border(new BorderStroke(stroke, style, radii, widths)));
		
		hbox.getChildren().add(sampleElement());
		hbox.getChildren().add(sampleElement());
		hbox.getChildren().add(sampleElement());

		GridPane grid=new GridPane();
		{
			grid.getChildren().add(sampleElement());
			grid.getChildren().add(sampleElement());
			grid.getChildren().add(sampleElement());
			
			
			ColumnConstraints c=new ColumnConstraints();
			c.setPercentWidth(50);
			grid.getColumnConstraints().add(c);
		}

		Parent root = grid;
		
		root = new GridLayoutBuilder()
			.add(sampleElement(), 0, 0)
			.add(sampleElement(), 1, 0)
			.add(sampleElement(), 2, 0)
			.add(sampleElement(), 0, 1)
			.add(sampleElement(), 1, 1)
			.add(sampleElement(), 2, 1)
			.add(sampleElement(), 0, 2)
			.add(sampleElement(), 1, 2)
			.add(sampleElement(), 2, 2)
			.setColumnConstraint(0, Constraint.of(MinMax.of(30, 100)))
			.setColumnConstraint(1, Percent.of(30))
			.setColumnConstraint(2, Constraint.of(MinMax.of(30, 100)))
			.setRowConstraint(0, Weight.of(1))
			.setRowConstraint(1, Weight.of(2))
			.setRowConstraint(2, Weight.of(7))
			.build();
		
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
	}

	private Node sampleElement() {
		//VBox vbox=new VBox(new Button("a"));
		Button ret = new Button("a");
		ret.setMaxHeight(Double.MAX_VALUE);
		ret.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(ret, Priority.ALWAYS);
		
		
		return ret;
	}
}
