package de.flapdoodle.ui.components;

import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.Lists;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Builder;

public abstract class BoxBuilder<B extends Builder<?>> {
	protected List<Node> children=Lists.newArrayList();
	protected boolean fill=true;
	
	private final BiConsumer<Node, Priority> addWithPriorityPostProcessing;
	
	protected BoxBuilder(BiConsumer<Node, Priority> addWithPriorityPostProcessing) {
		this.addWithPriorityPostProcessing = addWithPriorityPostProcessing;
	}
	
	public B add(Node node) {
		return add(Priority.NEVER, node);
	}
	
	public B add(Priority grow, Node node) {
		children.add(node);
		addWithPriorityPostProcessing.accept(node, grow);
		return (B) this;
	}
	
	public B fill(boolean fill) {
		this.fill=fill;
		return (B) this;
	}
	
	public static HBoxBuilder hbox() {
		return new HBoxBuilder();
	}
	
	public static VBoxBuilder vbox() {
		return new VBoxBuilder();
	}
	
	public static class HBoxBuilder extends BoxBuilder<HBoxBuilder> implements Builder<HBox> {

		protected HBoxBuilder() {
			super((node,prio) -> HBox.setHgrow(node, prio));
		}

		@Override
		public HBox build() {
			HBox ret = new HBox();
			ret.setFillHeight(fill);
			ret.getChildren().addAll(children);
			
			return ret;
		}
		
	}

	public static class VBoxBuilder extends BoxBuilder<VBoxBuilder> implements Builder<VBox> {

		protected VBoxBuilder() {
			super((node,prio) -> VBox.setVgrow(node, prio));
		}

		@Override
		public VBox build() {
			VBox ret = new VBox();
			ret.setFillWidth(fill);
			ret.getChildren().addAll(children);
			
			return ret;
		}
		
	}
}
