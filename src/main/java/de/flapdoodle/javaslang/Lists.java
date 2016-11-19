package de.flapdoodle.javaslang;

import javaslang.collection.List;

public abstract class Lists {
	
	private Lists() {
		// no instance
	}
	
	public static <T> List<T> of(Iterable<T> src) {
		return List.<T>empty().appendAll(src);
	}
}
