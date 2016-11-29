package de.flapdoodle.ui.components;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public interface Weight {
	
	@Parameter
	int value();
	
	public static Weight of(int value) {
		return ImmutableWeight.of(value);
	}

	default Percent asPercent(Weight left) {
		return Percent.of((100*left.value())/value());
	}
}
