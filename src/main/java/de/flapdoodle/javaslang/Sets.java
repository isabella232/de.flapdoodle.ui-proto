package de.flapdoodle.javaslang;

import javaslang.collection.LinkedHashSet;
import javaslang.collection.Set;

public abstract class Sets {

	private Sets() {
		// no instance
	}
	
	public static <T> Set<T> of(java.util.Set<T> set) {
		return LinkedHashSet.<T>empty().addAll(set);
	}
}
