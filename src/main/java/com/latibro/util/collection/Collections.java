package com.latibro.util.collection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Collections {

	private Collections() {
	}

	public static <C extends Collection<E>, E> C newInstance(Class<C> cls) throws InstantiationException {
		try {
			C result = (C) cls.newInstance();
			return result;
		} catch (IllegalAccessException e) {
			throw new InstantiationException(e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static <C extends Collection<E>, E> C clone(C input) throws CloneNotSupportedException {
		if (input instanceof Cloneable) {
				try {
					Method cloneMethod = input.getClass().getMethod("clone", (Class<?>) null);
					return (C) cloneMethod.invoke(input, (Object) null);
				} catch (NoSuchMethodException e) {
					throw new CloneNotSupportedException(e.toString());
				} catch (IllegalAccessException e) {
					throw new CloneNotSupportedException(e.toString());
				} catch (InvocationTargetException e) {
					throw new CloneNotSupportedException(e.toString());
				}
		}
		
		try {
			C result = (C) newInstance(input.getClass());
			result.addAll(input);
			return result;
		} catch (InstantiationException e) {
			throw new CloneNotSupportedException(e.toString());
		}
	}

	// where -> changes input Collection to only contain matches (return void)
	public static <C extends Collection<E>, E> void where(C collection, Matcher<? super E> matcher) {
		for (Iterator<E> iterator = collection.iterator(); iterator.hasNext();) {
			E entry = iterator.next();

			if (!matcher.match(entry)) {
				iterator.remove();
			}
		}
	}

	// select -> returns new Collection with matches
	public static <C extends Collection<E>, E> C select(C collection, Matcher<? super E> matcher) {
		try {
			@SuppressWarnings("unchecked")
			C result = (C) newInstance(collection.getClass());

			for (Iterator<E> iterator = collection.iterator(); iterator.hasNext();) {
				E entry = iterator.next();

				if (matcher.match(entry)) {
					result.add(entry);
				}
			}
			
			return result;
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		}
	}

	// first -> returns first element that matches
	public static <C extends Collection<E>, E> E first(C collection, Matcher<? super E> matcher) {
		for (Iterator<E> iterator = collection.iterator(); iterator.hasNext();) {
			E entry = iterator.next();

			if (matcher.match(entry)) {
				return entry;
			}
		}
		return null;
	}

	// last -> return last element that matches
	public static <C extends Collection<E>, E> E last(C collection, Matcher<? super E> matcher) {
		List<E> reversed = new ArrayList<E>(collection);
	    java.util.Collections.reverse(reversed);
	    return first(reversed, matcher);
	}

	// count -> returns the number of elements that matches
	public static <C extends Collection<E>, E> int count(C collection, Matcher<? super E> matcher) {
		return select(collection, matcher).size();
	}

	// any -> returns true if any elements matches
	public static <C extends Collection<E>, E> boolean any(C collection, Matcher<? super E> matcher) {
		return first(collection, matcher) != null;
	}

	// all -> returns true if all elements matches
	public static <C extends Collection<E>, E> boolean all(C collection, Matcher<? super E> matcher) {
		return count(collection, matcher) == collection.size();
	}

	// none -> returns true if no elements matches
	public static <C extends Collection<E>, E> boolean none(C collection, Matcher<? super E> matcher) {
		return first(collection, matcher) == null;
	}

}
