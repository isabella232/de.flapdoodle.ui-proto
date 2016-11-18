package de.flapdoodle.ui.tab.data.playground;

import com.google.common.base.Optional;

import de.flapdoodle.ui.tab.data.EntityId;

public interface Table {
	EntityId<Table> id();
	Optional<String> title();
}
