package de.flapdoodle.ui.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableListIterator;

public class NullawareImmutableList<E> implements List<E> {

	private List<E> list;

	private NullawareImmutableList(Iterable<E> src) {
		this.list=Lists.newArrayList(src);
	}
	
	public static <T> NullawareImmutableList<T> of(Iterable<T> src) {
		return new NullawareImmutableList<>(src);
	}

	public static <T> NullawareImmutableList<T> copyOf(T... values) {
		return of(Lists.newArrayList(values));
	}
	
	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public boolean contains(Object o) {
		return list.contains(o);
	}

	public Iterator<E> iterator() {
		return Iterators.unmodifiableIterator(list.iterator());
	}

	public Object[] toArray() {
		return list.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	public boolean add(E e) {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean remove(Object o) {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	public void clear() {
		throw new UnsupportedOperationException("immutable");
	}

	public boolean equals(Object o) {
		return list.equals(o);
	}

	public int hashCode() {
		return list.hashCode();
	}

	public E get(int index) {
		return list.get(index);
	}

	public E set(int index, E element) {
		throw new UnsupportedOperationException("immutable");
	}

	public void add(int index, E element) {
		throw new UnsupportedOperationException("immutable");
	}

	public E remove(int index) {
		return list.remove(index);
	}

	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	public NullawareImmutableList<E> subList(int fromIndex, int toIndex) {
		return NullawareImmutableList.of(list.subList(fromIndex, toIndex));
	}

	@Override
	public ListIterator<E> listIterator() {
		ListIterator<E> iterator = list.listIterator();
		return new UnmodifiableListIterator<E>() {

			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public E next() {
				return iterator.next();
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasPrevious();
			}

			@Override
			public E previous() {
				return iterator.previous();
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}
		};
	}

	public static class Builder<T> {
		
		List<T> src=Lists.newArrayList();
		
		public NullawareImmutableList<T> build() {
			return NullawareImmutableList.of(src);
		}

		public Builder<T> add(T value) {
			src.add(value);
			return this;
		}
		
		public Builder<T> addAll(Collection<T> values) {
			src.addAll(values);
			return this;
		}
	}
	
	public static <T> Builder<T> builder() {
		return new Builder<>();
	}

}
