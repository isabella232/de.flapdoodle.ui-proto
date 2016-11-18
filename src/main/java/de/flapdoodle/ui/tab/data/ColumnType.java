package de.flapdoodle.ui.tab.data;

import java.time.LocalDateTime;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;
import org.immutables.value.Value.Style.BuilderVisibility;

import com.google.common.collect.ImmutableSet;

@Value.Immutable
@Value.Style(builderVisibility=BuilderVisibility.PACKAGE)
public abstract class ColumnType<T> {
	public static final ColumnType<String> String = of(String.class); 
	public static final ColumnType<LocalDateTime> Date = of(LocalDateTime.class); 
	public static final ColumnType<Integer> Integer = of(Integer.class); 
	public static final ColumnType<Double> FloatingPoint = of(Double.class);
	
	@Parameter
	public abstract Class<T> type();

	private static <T> ColumnType<T> of(Class<T> type) {
		return ImmutableColumnType.of(type);
	}

	public <T> boolean isValidValue(T v) {
		return (v==null || type().isInstance(v));
	}
}
