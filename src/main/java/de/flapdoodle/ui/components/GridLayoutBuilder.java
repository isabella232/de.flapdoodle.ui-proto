package de.flapdoodle.ui.components;

import java.util.function.BiConsumer;

import com.google.common.base.Preconditions;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraint;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.ColumnConstraintsBuilder;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPain;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraint;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Builder;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.LinkedHashMap;
import javaslang.collection.List;
import javaslang.collection.Map;
import javaslang.collection.Seq;
import javaslang.collection.Stream;
import javaslang.control.Either;
import javaslang.control.Option;

public class GridLayoutBuilder implements Builder<GridPane> {

	Map<Node, Tuple2<Integer, Integer>> nodes=LinkedHashMap.empty();

	Map<Integer, Constraint> columnConstraint=LinkedHashMap.empty();
	Map<Integer, Constraint> rowConstraint=LinkedHashMap.empty();

	public GridLayoutBuilder add(Node node,int x, int y) {
		Preconditions.checkArgument(!nodes.containsKey(node),"node %s already added",node);
		nodes = nodes.put(node, Tuple.of(x,y));
		return this;
	}
	
	public GridLayoutBuilder setColumnConstraint(int column, Weight weight){
		return setColumnConstraint(column, Constraint.of(weight));
	}
	public GridLayoutBuilder setRowConstraint(int row, Weight weight){
		return setRowConstraint(row, Constraint.of(weight));
	}

	public GridLayoutBuilder setColumnConstraint(int column, Percent percent){
		return setColumnConstraint(column, Constraint.of(percent));
	}
	public GridLayoutBuilder setRowConstraint(int row, Percent percent){
		return setRowConstraint(row, Constraint.of(percent));
	}

	public GridLayoutBuilder setColumnConstraint(int column, Constraint constraint){
		Preconditions.checkArgument(!columnConstraint.containsKey(column),"constraint for %s already set",column);
		columnConstraint = columnConstraint.put(column, constraint);
		return this;
	}
	
	public GridLayoutBuilder setRowConstraint(int column, Constraint constraint){
		Preconditions.checkArgument(!rowConstraint.containsKey(column),"constraint for %s already set",column);
		rowConstraint = rowConstraint.put(column, constraint);
		return this;
	}
	
	@Override
	public GridPane build() {
		GridPane ret = new GridPane();
		
		Paint stroke=Color.BLUE;
		BorderStrokeStyle style=BorderStrokeStyle.DOTTED;
		CornerRadii radii=new CornerRadii(3);
		BorderWidths widths=new BorderWidths(2);
		ret.setBorder(new Border(new BorderStroke(stroke, style, radii, widths)));

		Weight columnWeights=sum(columnConstraint.values());
		Weight rowWeights=sum(rowConstraint.values());
		
		forEachInRange(columnConstraint, (column, constraint) -> {
			ColumnConstraints constr = new ColumnConstraints();
			if (constraint.isDefined()) {
				Constraint c = constraint.get();
				if (c.size().isLeft()) {
					Either<Weight, Percent> weightOrPercent = c.size().getLeft();
					if (weightOrPercent.isLeft()) {
						constr.setPercentWidth(columnWeights.asPercent(weightOrPercent.getLeft()).asDouble(100.0));
					} else {
						constr.setPercentWidth(weightOrPercent.swap().getLeft().asDouble(100.0));
					}
				} else {
					constr.setMinWidth(c.size().swap().getLeft().min().getOrElse(Control.USE_COMPUTED_SIZE));
					constr.setMaxWidth(c.size().swap().getLeft().max().getOrElse(Control.USE_COMPUTED_SIZE));
					constr.setHgrow(Priority.SOMETIMES);
				}
			}
			
			ret.getColumnConstraints().add(column, constr);
		});
		
		forEachInRange(rowConstraint, (row, constraint) -> {
			RowConstraints constr = new RowConstraints();
			if (constraint.isDefined()) {
				Constraint c = constraint.get();
				if (c.size().isLeft()) {
					Either<Weight, Percent> weightOrPercent = c.size().getLeft();
					if (weightOrPercent.isLeft()) {
						constr.setPercentHeight(rowWeights.asPercent(weightOrPercent.getLeft()).asDouble(100.0));
					} else {
						constr.setPercentHeight(weightOrPercent.swap().getLeft().asDouble(100.0));
					}
				} else {
					constr.setMinHeight(c.size().swap().getLeft().min().getOrElse(Control.USE_COMPUTED_SIZE));
					constr.setMaxHeight(c.size().swap().getLeft().max().getOrElse(Control.USE_COMPUTED_SIZE));
					constr.setVgrow(Priority.SOMETIMES);
				}
			}
			
			ret.getRowConstraints().add(row, constr);
		});
		
		nodes.forEach((node, coord) -> {
			ret.add(node, coord._1, coord._2);
		});
		return ret;
	}
	
	public static Weight sum(Seq<Constraint> constrains) {
		return Weight.of(constrains.filter(c -> c.size().isLeft())
			.map(c -> c.size().getLeft())
			.filter(w -> w.isLeft())
			.map(w -> w.getLeft())
			.map(Weight::value)
			.fold(0, (a,b) -> a+b));
	}

	private static <T> void forEachInRange(Map<Integer, T> map, BiConsumer<Integer, Option<T>> consumer) {
		Option<Integer> maxColumn = map.keySet().max();
		Option<Integer> minColumn = map.keySet().min();
		if (minColumn.isDefined() && maxColumn.isDefined()) {
			List.rangeBy(minColumn.get(), maxColumn.get()+1, 1)
			.forEach(column -> {
				consumer.accept(column, map.get(column));
			});
		}
	}
	
}
