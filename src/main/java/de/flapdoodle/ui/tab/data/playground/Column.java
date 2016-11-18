package de.flapdoodle.ui.tab.data.playground;

import de.flapdoodle.ui.tab.data.EntityId;

public interface Column {
	EntityId<Column> id();
	String name();
}
