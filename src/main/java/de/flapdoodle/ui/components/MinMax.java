package de.flapdoodle.ui.components;

import org.immutables.value.Value;

import javaslang.control.Option;

@Value.Immutable
public interface MinMax {
	Option<Double> min();
	Option<Double> max();

	public static MinMax min(double min) {
		return ImmutableMinMax.builder().min(min).build();
	}
	public static MinMax max(double max) {
		return ImmutableMinMax.builder().max(max).build();
	}
	public static MinMax of(double min, double max) {
		return ImmutableMinMax.builder().min(min).max(max).build();
	}
}
