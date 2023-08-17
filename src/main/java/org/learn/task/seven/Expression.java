package org.learn.task.seven;

/**
 * Expression.
 *
 * @param <T> Generic Type.
 */
@FunctionalInterface
public interface Expression<T> {
  T getValue();
}
