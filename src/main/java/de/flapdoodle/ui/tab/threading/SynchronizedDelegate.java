package de.flapdoodle.ui.tab.threading;

import java.util.function.Consumer;
import java.util.function.Function;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public abstract class SynchronizedDelegate<T> {

	@Parameter
	protected abstract T delegate();
	
	public synchronized void call(Consumer<T> consumer) {
		consumer.accept(delegate());
	}
	
	public synchronized <R> R call(Function<T,R> call) {
		return call.apply(delegate());
	}
	
	public static <T> SynchronizedDelegate<T> of(T instance) {
		return ImmutableSynchronizedDelegate.of(instance);
	}
}
