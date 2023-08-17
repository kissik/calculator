package org.learn.task.seven;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Binary Plus Operator.
 */
public class BinaryPlusOperator extends BinaryOperator<Integer> {
  private static final BiFunction<Integer, Integer, Integer> PLUS = Integer::sum;
  private static final BiPredicate<Integer, Integer> CHECK = (x, y) -> {
    Long longX = Long.valueOf(x);
    Long longY = Long.valueOf(y);

    return !(Math.abs(longX + longY) > Integer.MAX_VALUE);
  };

  /**
   * Constructor.
   *
   * @param left  expression;
   * @param right expression;
   */
  public BinaryPlusOperator(Expression<Integer> left, Expression<Integer> right) {
    super(left, right, PLUS, CHECK, "The result value out of type range.");
  }
}
