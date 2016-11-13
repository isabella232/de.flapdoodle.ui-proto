package de.flapdoodle.ui.tab.data.playground;

import com.google.common.base.Optional;

public interface Table {
	EntityId<Table> id();
	Optional<String> title();
}
