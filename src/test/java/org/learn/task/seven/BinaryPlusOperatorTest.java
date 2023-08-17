package org.learn.task.seven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BinaryPlusOperatorTest {
  private static final Expression<Integer> TEN = () -> 10;
  private static final Expression<Integer> FIVE = () -> 5;
  private static final Expression<Integer> ONE = () -> 1;
  private static final Expression<Integer> MINUS_ONE = () -> -1;
  private static final Expression<Integer> MAX = () -> Integer.MAX_VALUE;
  private static final Expression<Integer> MIN = () -> Integer.MIN_VALUE;

  @Test
  public void testPlusHappyPath() {
    final var operator = new BinaryPlusOperator(TEN, FIVE);
    assertEquals(15, operator.getValue());
  }

  @ParameterizedTest
  @MethodSource
  public void testPlusUnHappyPath(
      Expression<Integer> left, Expression<Integer> right, String expectedMessage) {
    final var operator = new BinaryPlusOperator(left, right);
    final var actualException = assertThrows(IllegalArgumentException.class,
        operator::getValue);
    assertEquals(actualException.getMessage(), expectedMessage);
  }

  public static Stream<Arguments> testPlusUnHappyPath() {
    return Stream.of(
        Arguments.of(MAX, MAX, "The result value out of type range."),
        Arguments.of(MAX, ONE, "The result value out of type range."),
        Arguments.of(MIN, MINUS_ONE, "The result value out of type range.")
    );
  }

}
