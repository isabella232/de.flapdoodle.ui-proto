package de.flapdoodle.ui.tab.data;

import org.immutables.value.Value;
import org.immutables.value.Value.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

@Value.Immutable
public abstract class Column<T> {
	public abstract ColumnType<T> columnType();

	public abstract ImmutableList<T> values();
	
	protected boolean isEmpty() {
		return values().isEmpty();
	}
	
	@Check
	protected void check() {
		Preconditions.checkArgument(Iterables.all(values(), v -> v==null || columnType().isValidValue(v)),"invalid values: %s -> %s",columnType(),values());
	}
	
	public Column<T> part(int offset, int length) {
		return of(columnType(),values().subList(offset, offset+length)); 
	}
	
	public Column<T> part(int offset) {
		return part(offset, values().size()-offset); 
	}
	
	@VisibleForTesting
	static <T> Column<T> joinTwo(Column<T> a, Column<T> b) {
		Preconditions.checkNotNull(a, "a is not set");
		Preconditions.checkNotNull(b, "b is not set");
		if (a.isEmpty()) return b;
		if (b.isEmpty()) return a;
		
		return ImmutableColumn.<T>builder()
				.columnType(a.columnType())
				.addAllValues(a.values())
				.addAllValues(b.values())
				.build();
	}
	
	@VisibleForTesting
	Column<T> join(Column<T> prepend, Column<T> append) {
		if (prepend!=null && append!=null) {
			if (this.isEmpty()) return joinTwo(prepend, append);
			
			return ImmutableColumn.<T>builder()
					.columnType(columnType())
					.addAllValues(prepend.values())
					.addAllValues(values())
					.addAllValues(append.values())
					.build();
		}
		
		if (prepend != null) return joinTwo(prepend, this);
		if (append != null)	return joinTwo(this, append);
		return this;
	}
	
	public Column<T> append(Column<T> other) {
		return join(null,other);
	}
	
	public Column<T> prepend(Column<T> other) {
		return join(other,null);
	}
	
	public Column<T> insert(Column<T> other, int index) {
		return other.join(part(0,index),part(index));
	}
	
	public static <T> Column<T> of(ColumnType<T> columnType, T...values) {
		return ImmutableColumn.<T>builder()
				.columnType(columnType)
				.addValues(values)
				.build();
	}
	
	public static <T> Column<T> of(ColumnType<T> columnType, Iterable<T> values) {
		return ImmutableColumn.<T>builder()
				.columnType(columnType)
				.addAllValues(values)
				.build();
	}
}
