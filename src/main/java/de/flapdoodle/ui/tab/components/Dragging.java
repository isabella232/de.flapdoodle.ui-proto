package de.flapdoodle.ui.tab.components;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class Dragging {

	private static final class DragContext {

		protected double mouseAnchorX;
		protected double mouseAnchorY;
		protected double initialTranslateX;
		protected double initialTranslateY;
		
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
	    AtomicBoolean dragModeActiveProperty=new AtomicBoolean(false);
	 
	    wrapGroup.addEventFilter(
	        MouseEvent.ANY,
	        mouseEvent -> {
		    	EventType<? extends MouseEvent> eventType = mouseEvent.getEventType();
				System.out.println(eventType+" "+dragModeActiveProperty+":"+mouseEvent.getPickResult().getIntersectedNode()); 
		    	
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
			    	System.out.println("pressed"); 
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
			    	System.out.println("dragged"); 
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
