package de.flapdoodle.ui.tab.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javaslang.collection.HashSet;
import javaslang.collection.List;

public class ColumnsTest {

	@Test
	public void howToUseThisApi() {
		Columns columns = new Columns();
		EntityId<Column> stringColumnId = columns.add(Column.of(ColumnType.String, "One","Two",null));
		
		assertEquals(HashSet.of(stringColumnId), columns.columns());
		assertEquals("Two",columns.column(stringColumnId).get().values().get(1));
		
		EntityId<Column> intColumnId = columns.add(Column.of(ColumnType.Integer, 1,2));
		
		assertEquals(HashSet.of(stringColumnId, intColumnId), columns.columns());
		assertEquals(3,columns.column(intColumnId).get().values().size());
		
		columns.insert(intColumnId, 3, Column.of(ColumnType.Integer, 10,11));
		
		assertEquals(5,columns.column(stringColumnId).get().values().size());
		assertEquals(5,columns.column(intColumnId).get().values().size());
		assertEquals(List.of(1,2,null,10,11),columns.column(intColumnId).get().values());
	}
}
