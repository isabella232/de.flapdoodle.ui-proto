package de.flapdoodle.ui.tab.data;

import java.util.UUID;

import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
public interface EntityId<T> {
	@Parameter
	Class<T> type();
	@Parameter
	UUID uuid();
	
	public static <T> EntityId<T> generate(Class<T> type) {
		return of(type,UUID.randomUUID());
	}
	
	public static <T> EntityId<T> of(Class<T> type, UUID uuid) {
		return ImmutableEntityId.of(type, uuid);
	}
}
