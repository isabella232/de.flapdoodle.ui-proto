package de.flapdoodle.ui.components;

import org.immutables.value.Value;
import org.immutables.value.Value.Auxiliary;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Parameter;

import com.google.common.base.Preconditions;

@Value.Immutable
public abstract class Percent {
	
	@Parameter
	public abstract int value();
	
	@Check
	protected void check() {
		Preconditions.checkArgument(value()>=0,"value is smaller than 0: %s",value());
		Preconditions.checkArgument(value()<=100,"value is bigger than 100: %s",value());
	}
	
	public static Percent of(int value) {
		return ImmutablePercent.of(value);
	}

	@Auxiliary
	public double asDouble(double scale) {
		return scale*value()/100d;
	}
}
