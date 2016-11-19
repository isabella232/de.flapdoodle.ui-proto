package de.flapdoodle.ui.tab.data;

import javaslang.collection.LinkedHashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;

public class Columns {
	private Map<EntityId<Column>, Column> columns = LinkedHashMap.empty();

	public Set<EntityId<Column>> columns() {
		return columns.keySet();
	}
	
	public Option<Column> column(EntityId<Column> id) {
		return columns.get(id);
	}

	public <T> EntityId<Column> add(Column<T> column) {
		EntityId<Column> id = EntityId.generate(Column.class);
		if (columns.isEmpty()) {
			columns = columns.put(id, column);
		} else {
			Integer rows = columns.values()
					.map(c -> c.values().size())
					.append(column.values().size())
					.max().get();
			
			columns = columns.put(id, column)
				.mapValues(c -> c.values().size()<rows
						? c.append(rows-c.values().size(), null) : c);
		}
		return id;
	}
}
