package de.flapdoodle.ui.tab.data;

import com.google.common.base.Preconditions;

import javaslang.Tuple;
import javaslang.collection.LinkedHashMap;
import javaslang.collection.Map;
import javaslang.collection.Set;
import javaslang.control.Option;

public class Columns {
	private Map<EntityId<Column>, Column> columnMap = LinkedHashMap.empty();

	public Set<EntityId<Column>> columns() {
		return columnMap.keySet();
	}
	
	public Option<Column> column(EntityId<Column> id) {
		return columnMap.get(id);
	}

	public EntityId<Column> add(Column column) {
		EntityId<Column> id = EntityId.generate(Column.class);
		if (columnMap.isEmpty()) {
			columnMap = columnMap.put(id, column);
		} else {
			Integer rows = columnMap.values()
					.map(c -> c.values().size())
					.append(column.values().size())
					.max().get();
			
			columnMap = columnMap.put(id, column)
				.mapValues(c -> c.values().size()<rows
						? c.append(rows-c.values().size(), null) 
						: c);
		}
		return id;
	}
	
	public void insert(EntityId<Column> id, int offset, Column column) {
		Preconditions.checkArgument(!column.isEmpty(),"column(%s) is empty: ",id,column);
		Preconditions.checkArgument(columnMap.containsKey(id),"columnId unknown",id,columnMap.keySet());
		
		columnMap = columnMap.map((key,c) -> key.equals(id) 
				? Tuple.of(key,c.insert(column, offset))
				: Tuple.of(key,c.insert(Column.of(c.columnType()).append(column.values().size(), null), offset)));
	};
}
