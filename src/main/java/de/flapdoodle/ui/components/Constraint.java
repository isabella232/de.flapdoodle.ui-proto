package de.flapdoodle.ui.components;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

import javaslang.control.Either;

@Value.Immutable
public interface Constraint {
	@Parameter
	Either<Either<Weight,Percent>, MinMax> size();
	
	public static Constraint of(Weight weight) {
		return ImmutableConstraint.of(Either.left(Either.left(weight)));
	}
	
	public static Constraint of(Percent percent) {
		return ImmutableConstraint.of(Either.left(Either.right(percent)));
	}
	
	public static Constraint of(MinMax minMax) {
		return ImmutableConstraint.of(Either.right(minMax));
	}
}
