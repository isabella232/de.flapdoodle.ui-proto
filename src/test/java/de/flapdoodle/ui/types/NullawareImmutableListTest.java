package de.flapdoodle.ui.types;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class NullawareImmutableListTest {

	@Test
	public void nullAllowed() {
		NullawareImmutableList<String> list = NullawareImmutableList.<String>builder()
			.add("Foo")
			.add(null)
			.build();
		
		assertEquals(2,list.size());
	}
	
	@Test
	public void sublistIsImplemented() {
		NullawareImmutableList<String> list = NullawareImmutableList.<String>builder()
				.add("Foo")
				.add(null)
				.build();
		
		assertEquals("Foo",list.subList(0, 1).get(0));
		assertNull(list.subList(1, 2).get(0));
	}
}
