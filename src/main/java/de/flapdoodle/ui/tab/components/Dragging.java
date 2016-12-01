package de.flapdoodle.ui.tab.components;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;

public abstract class Dragging {

	private static final class DragContext {

		protected double mouseAnchorX;
		protected double mouseAnchorY;
		protected double initialTranslateX;
		protected double initialTranslateY;
		
	}
	
	private static class MouseCoords {
		double x;
		double y;
//		double layoutX;
//		double layoutY;
		boolean dragStarted;
	}

	
	public static void moveNodeBy(Node movingNode, Node triggerNode) {
		MouseCoords coords=new MouseCoords();
		
		triggerNode.addEventFilter(MouseEvent.ANY, e -> {
			if (coords.dragStarted) {
//				System.out.println(e);
				if (e.getEventType()==MouseEvent.MOUSE_RELEASED) {
					coords.dragStarted=false;
				}
				if (e.getEventType()!=MouseEvent.MOUSE_DRAGGED) {
					e.consume();
				}
			}
		});
		
		triggerNode.setOnMousePressed(e -> {
//			System.out.println(e);
			coords.x = e.getX();
			coords.y = e.getY();
//			coords.layoutX=movingNode.getLayoutX();
//			coords.layoutY=movingNode.getLayoutY();
			coords.dragStarted=true;
		});
		
		triggerNode.setOnMouseDragged(e -> {
//			System.out.println(e);
//			System.out.println((e.getX()-coords.x)+":"+(e.getY()-coords.y));
			movingNode.setLayoutX(e.getX()-coords.x+movingNode.getLayoutX());
			movingNode.setLayoutY(e.getY()-coords.y+movingNode.getLayoutY());
		});

	}
	
	public static Node makeDraggable(final Node triggerNode, Node movingNode) {
	    final DragContext dragContext = new DragContext();
	    final Group wrapGroup = new Group(movingNode);
	    AtomicBoolean dragModeActiveProperty=new AtomicBoolean(true);
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.ANY,
	        new EventHandler<MouseEvent>() {
	            public void handle(final MouseEvent mouseEvent) {
	            	Node pick = mouseEvent.getPickResult().getIntersectedNode();
					System.out.println("pick:    -> "+System.identityHashCode(pick)+pick);
					System.out.println("trigger: -> "+System.identityHashCode(triggerNode)+triggerNode);
	            	if (pick==triggerNode) {
//	                if (dragModeActiveProperty.get()) {
	                    // disable mouse events for all children
	                    mouseEvent.consume();
	                }
	             }
	        });
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_PRESSED,
	        new EventHandler<MouseEvent>() {
	            public void handle(final MouseEvent mouseEvent) {
	            	if (mouseEvent.getPickResult().getIntersectedNode()==triggerNode) {
						System.out.println("move");
//	                if (dragModeActiveProperty.get()) {
	                    // remember initial mouse cursor coordinates
	                    // and node position
	                    dragContext.mouseAnchorX = mouseEvent.getX();
	                    dragContext.mouseAnchorY = mouseEvent.getY();
	                    dragContext.initialTranslateX =
	                        triggerNode.getTranslateX();
	                    dragContext.initialTranslateY =
	                        triggerNode.getTranslateY();
	                }
	            }
	        });
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_DRAGGED,
	        new EventHandler<MouseEvent>() {
	            public void handle(final MouseEvent mouseEvent) {
	            	if (mouseEvent.getPickResult().getIntersectedNode()==triggerNode) {
						System.out.println("move-->");
//	                if (dragModeActiveProperty.get()) {
	                    // shift node from its initial position by delta
	                    // calculated from mouse cursor movement
	                	movingNode.setTranslateX(
	                        dragContext.initialTranslateX
	                            + mouseEvent.getX()
	                            - dragContext.mouseAnchorX);
	                	movingNode.setTranslateY(
	                        dragContext.initialTranslateY
	                            + mouseEvent.getY()
	                            - dragContext.mouseAnchorY);
	                }
	            }
	        });
	 
	    return wrapGroup;

	}

	
	public static Node makeDraggable(final Node node) {
	    final DragContext dragContext = new DragContext();
	    final Group wrapGroup = new Group(node);
//	    wrapGroup.setStyle("-fx-background-color: #f0f000");
//		BorderStrokeStyle style=BorderStrokeStyle.DOTTED;
//		CornerRadii radii=new CornerRadii(3);
//		BorderWidths widths=BorderWidths.DEFAULT;
//		Paint stroke=Color.BLUEVIOLET;
		//Border border=new Border();
//		wrapGroup.setBorder(new Border(new BorderStroke(stroke, style, radii, widths)));
//		wrapGroup.setMouseTransparent(false);
		
	    AtomicBoolean dragModeActiveProperty=new AtomicBoolean(false);
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.ANY,
	        mouseEvent -> {
		    	EventType<? extends MouseEvent> eventType = mouseEvent.getEventType();
				//System.out.println(eventType+" "+dragModeActiveProperty+":"+mouseEvent.getPickResult().getIntersectedNode());
		    	System.out.println("Group "+wrapGroup.getBoundsInLocal()+" : "+wrapGroup.getBoundsInParent());
		    	
	        	if (dragModeActiveProperty.get()) {
	        		if (eventType==MouseEvent.MOUSE_PRESSED) {
	        			dragModeActiveProperty.set(false);
	        		}
	        		if (eventType==MouseEvent.MOUSE_MOVED || eventType==MouseEvent.MOUSE_ENTERED) {
	        			if (!shouldDrag(mouseEvent.getPickResult().getIntersectedNode())) {
	        				dragModeActiveProperty.set(false);
	        			}
	        		}
	        	} else {
	        		if (eventType!=MouseEvent.MOUSE_CLICKED) {
	        			if (shouldDrag(mouseEvent.getPickResult().getIntersectedNode())) {
	        				dragModeActiveProperty.set(true);
	        			}
	        		}
	        	}
//	        	if (mouseEvent.getEventType()!=MouseEvent.MOUSE_CLICKED && mouseEvent.getEventType()!=MouseEvent.MOUSE_DRAGGED) {
//					if (shouldDrag(mouseEvent.getPickResult().getIntersectedNode())) {
//						dragModeActiveProperty.set(true);
//				    } else {
//				    	dragModeActiveProperty.set(false);
//				    }
//	        	}
	        	
	        	if (dragModeActiveProperty.get()) {
			        // disable mouse events for all children
					mouseEvent.consume();
	        	}
			 });
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_PRESSED,
	        mouseEvent -> {
				if (dragModeActiveProperty.get()) {
			    	//System.out.println("pressed"); 
//					dragModeActiveProperty.set(true);
//					mouseEvent.consume();
					
			        // remember initial mouse cursor coordinates
			        // and node position
			        dragContext.mouseAnchorX = mouseEvent.getX();
			        dragContext.mouseAnchorY = mouseEvent.getY();
			        dragContext.initialTranslateX =
			            node.getTranslateX();
			        dragContext.initialTranslateY =
			            node.getTranslateY();
			    }
			});
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.MOUSE_DRAGGED,
	        mouseEvent -> {
			    if (dragModeActiveProperty.get()) {
			    	//System.out.println("dragged"); 
			        // shift node from its initial position by delta
			        // calculated from mouse cursor movement
			        node.setTranslateX(
			            dragContext.initialTranslateX
			                + mouseEvent.getX()
			                - dragContext.mouseAnchorX);
			        node.setTranslateY(
			            dragContext.initialTranslateY
			                + mouseEvent.getY()
			                - dragContext.mouseAnchorY);
			    }
			});
//	    wrapGroup.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> { 
//	    	System.out.println("released"); 
//	    	dragModeActiveProperty.set(false);
//	    });
	 
	    return wrapGroup;

	}


	protected static boolean shouldDrag(Node node) {
		return node != null && 
				(node.getProperties().get(DRAG_ME) != null 
					|| shouldDrag(node.getParent()));
//		return false;
	}

	public static final String DRAG_ME=UUID.randomUUID().toString();
}
