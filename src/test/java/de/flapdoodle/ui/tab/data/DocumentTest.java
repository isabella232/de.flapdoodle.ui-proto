package de.flapdoodle.ui.tab.data;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void howToUseThisApi() {
		Document doc = new Document();
		EntityId<Columns> id = doc.add(new Columns());
		
		doc.columns(id).get().add(Column.of(ColumnType.String, "Foo","Bar"));
	}
}
