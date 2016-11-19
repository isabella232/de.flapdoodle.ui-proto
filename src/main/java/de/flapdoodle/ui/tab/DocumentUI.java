package de.flapdoodle.ui.tab;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import de.flapdoodle.ui.tab.data.Columns;
import de.flapdoodle.ui.tab.data.Document;
import de.flapdoodle.ui.tab.data.EntityId;
import de.flapdoodle.ui.tab.threading.SynchronizedDelegate;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javaslang.Predicates;
import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.collection.LinkedHashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;

public class DocumentUI extends Control {

	private Map<EntityId<Columns>, Node> columnUis=LinkedHashMap.empty();
	private final Group columnUiSpace;
	
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
		
		this.columnUiSpace=group;
		
		updateUI(document);
	}
	
	private void updateUI(SynchronizedDelegate<Document> document) {
		Set<EntityId<Columns>> columnIds = document.get(Document::allColumns);
		
		Map<EntityId<Columns>, Node> stillActiveUis = columnUis.filter(t -> columnIds.contains(t._1()));
		
		columnUis.filter(t -> stillActiveUis.containsKey(t._1()))
			.values()
			.forEach(b -> columnUiSpace.getChildren().remove(b));
		
		Set<EntityId<Columns>> newUis = columnIds.diff(stillActiveUis.keySet());
		
		Map<EntityId<Columns>, Node> newColumnUis = newUis.toMap(id -> Tuple.of(id, columnUiOf(id)));

		newColumnUis.forEach((id,ui) -> columnUiSpace.getChildren().add(ui));
		
		columnUis=columnUis.merge(newColumnUis);
	}

	private Node columnUiOf(EntityId<Columns> id) {
		Button ret = new Button();
		ret.setText("id:"+id.uuid().toString());
		ret.setTranslateX(ThreadLocalRandom.current().nextInt(300));
		ret.setTranslateY(ThreadLocalRandom.current().nextInt(300));
		return ret;
	}

	private static class DocumentUISkin extends SkinBase<DocumentUI> {

		protected DocumentUISkin(DocumentUI control) {
			super(control);
		}
	}
}
