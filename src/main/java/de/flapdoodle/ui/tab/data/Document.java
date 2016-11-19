package de.flapdoodle.ui.tab.data;

import com.google.common.base.Preconditions;

import javaslang.collection.LinkedHashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;

public class Document {
	private Map<EntityId<Columns>, Columns> columnsMap=LinkedHashMap.empty();
	
	public EntityId<Columns> add(Columns columns) {
		Preconditions.checkArgument(!columnsMap.containsValue(columns),"already added: %s",columns);
		EntityId<Columns> id = EntityId.generate(Columns.class);
		columnsMap=columnsMap.put(id, columns);
		return id;
	}

	public Option<Columns> columns(EntityId<Columns> id) {
		return columnsMap.get(id);
	}

	public Set<EntityId<Columns>> allColumns() {
		return columnsMap.keySet();
	}
}
