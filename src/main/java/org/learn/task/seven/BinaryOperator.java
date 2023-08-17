package org.learn.task.seven;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Binary Operator.
 *
 * @param <T> Generic Type.
 */
public abstract class BinaryOperator<T> implements Expression<T> {
  private final Expression<T> left;
  private final Expression<T> right;
  private final BiFunction<T, T, T> operator;
  private final BiPredicate<T, T> predicate;
  private final String errorException;

  /**
   * Constructor.
   *
   * @param left     expression;
   * @param right    expression;
   * @param operator operator.
   */
  BinaryOperator(
      Expression<T> left,
      Expression<T> right,
      BiFunction<T, T, T> operator,
      BiPredicate<T, T> predicate,
      String errorException) {
    this.left = left;
    this.right = right;
    this.operator = operator;
    this.predicate = predicate;
    this.errorException = errorException;
  }

  @Override
  public T getValue() {
    if (left == null) {
      throw new IllegalArgumentException("Left operand is not presented.");
    }
    if (right == null) {
      throw new IllegalArgumentException("Right operand is not presented.");
    }
    if (predicate == null) {
      throw new IllegalArgumentException("Predicate is not presented.");
    }
    if (operator == null) {
      throw new IllegalArgumentException("Operator is not presented.");
    }
    try {
      if (predicate.test(left.getValue(), right.getValue())) {
        return operator.apply(left.getValue(), right.getValue());
      } else {
        throw new IllegalArgumentException(errorException);
      }
    } catch (Throwable exception) {
      throw new IllegalArgumentException(exception.getMessage(), exception);
    }
  }
}
