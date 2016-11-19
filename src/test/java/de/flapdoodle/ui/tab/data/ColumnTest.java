package de.flapdoodle.ui.tab.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import javaslang.collection.List;

public class ColumnTest {

	@Test
	public void empty() {
		Column<String> emptyString = Column.of(ColumnType.String);
		assertTrue(emptyString.values().isEmpty());
	}
	
	@Test
	public void nullIsAllowed() {
		Column<String> stringColumn = Column.of(ColumnType.String,"A",null,"B");
		assertEquals(3,stringColumn.values().size());
		assertNull(stringColumn.values().get(1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void putBadAppleIntoColumn() {
		Column<String> stringColumn = Column.of(ColumnType.String,"A",null,"B");
		assertEquals(3,stringColumn.values().size());
		assertNull(stringColumn.values().get(1));
		
		Column stringColumnAsRaw=stringColumn;
		stringColumnAsRaw.append(Column.of(ColumnType.Integer, 2));
	}
	
	@Test
	public void joinEmptyGivesSecond() {
		Column<String> a = Column.of(ColumnType.String);
		Column<String> b = Column.of(ColumnType.String,"Second");
		assertTrue(b==Column.joinTwo(a,b));
		assertTrue(b==Column.joinTwo(b,a));
	}
	
	@Test
	public void joinNonEmptyGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), Column.joinTwo(a,b).values());
	}
	
	@Test
	public void joinThreeGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		Column<String> c = Column.of(ColumnType.String,"Third");
		assertEquals(List.of("First","Second","Third"), b.join(a,c).values());
	}
	
	@Test
	public void joinThreeWithOneEmptyGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		Column<String> c = Column.of(ColumnType.String);
		assertEquals(List.of("First","Second"), b.join(a,c).values());
		
		a = Column.of(ColumnType.String);
		b = Column.of(ColumnType.String,"First");
		c = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), b.join(a,c).values());
		
		a = Column.of(ColumnType.String,"First");
		b = Column.of(ColumnType.String);
		c = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), b.join(a,c).values());
	}
	
	@Test
	public void joinTwoGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), a.join(null,b).values());
		assertEquals(List.of("First","Second"), b.join(a,null).values());
	}
	
	@Test
	public void joinOneGivesInstance() {
		Column<String> a = Column.of(ColumnType.String,"First");
		assertTrue(a==a.join(null,null));
	}
	
	@Test
	public void appendGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), a.append(b).values());
	}
	
	@Test
	public void prependGivesJoinedResults() {
		Column<String> a = Column.of(ColumnType.String,"First");
		Column<String> b = Column.of(ColumnType.String,"Second");
		assertEquals(List.of("First","Second"), b.prepend(a).values());
	}
	
	@Test
	public void insertIntoSplitFirst() {
		Column<String> a = Column.of(ColumnType.String,"One","Two","Three");
		Column<String> b = Column.of(ColumnType.String,"A","B","C");
		assertEquals(List.of("One","Two","A","B","C","Three"), a.insert(b, 2).values());
	}
}
