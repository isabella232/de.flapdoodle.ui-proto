package de.flapdoodle.prototyping.layouts.grid;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class GridWeightPane extends Pane {

	private boolean performingLayout;

	@Override
	protected void layoutChildren() {
        performingLayout = true;
        List<Node> managed = getManagedChildren();
        Insets insets = getInsets();
        double width = getWidth();
        double height = getHeight();
        double top = snapSpace(insets.getTop());
        double left = snapSpace(insets.getLeft());
        double bottom = snapSpace(insets.getBottom());
        double right = snapSpace(insets.getRight());
        double space = snapSpace(2.0);
		
		System.out.println("layoutChildren");
		double baselineOffset = -1;
		for (int i = 0, size = managed.size(); i < size; i++) {
            Node child = managed.get(i);
            Insets margin = null; //getMargin(child);
            double x=i*10;
			double y=i*10;
			double w=50;
			double h=50;
			layoutInArea(child, x, y, w, h,
                    baselineOffset, margin, true, true,
                    HPos.CENTER, VPos.CENTER);
            //x += actualAreaWidths[0][i] + space;
        }
		
		
        performingLayout = false;
	}
}
