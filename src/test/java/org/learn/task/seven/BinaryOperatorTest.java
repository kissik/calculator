package org.learn.task.seven;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BinaryOperatorTest {
  private static final Expression<Object> LEFT = Object::new;
  private static final Expression<Object> RIGHT = Object::new;
  private static final BiFunction<Object, Object, Object> FUNCTION = (x, y) -> new Object();
  private static final BiPredicate<Object, Object> PREDICATE = (x, y) -> false;
  private static final String MESSAGE = "Message.";

  @Test
  void testPredicateException() {
    BiPredicate<Object, Object> predicateMock = mock(BiPredicate.class);
    when(predicateMock.test(any(), any()))
        .thenThrow(new RuntimeException("Predicate Error."));
    final var binaryOperator =
        new BinaryOperator<>(LEFT, RIGHT, FUNCTION, predicateMock, MESSAGE) {};

    final var actualException = assertThrows(IllegalArgumentException.class,
        binaryOperator::getValue);

    assertEquals("Predicate Error.", actualException.getMessage());
  }

  @ParameterizedTest
  @MethodSource
  <T> void testUnHappyPath(Expression<T> left, Expression<T> right, BiFunction<T, T, T> operator,
                           BiPredicate<T, T> predicate,
                           String errorMessage, String expectedMessage) {
    BinaryOperator<T> binaryOperator =
        new BinaryOperator<>(left, right, operator, predicate, errorMessage) {};

    final var actualException = assertThrows(IllegalArgumentException.class,
        binaryOperator::getValue);

    assertEquals(expectedMessage, actualException.getMessage());
  }

  public static Stream<Arguments> testUnHappyPath() {
    return Stream.of(
        Arguments.of(null, RIGHT, FUNCTION, PREDICATE, MESSAGE, "Left operand is not presented."),
        Arguments.of(LEFT, null, FUNCTION, PREDICATE, MESSAGE, "Right operand is not presented."),
        Arguments.of(LEFT, RIGHT, null, PREDICATE, MESSAGE, "Operator is not presented."),
        Arguments.of(LEFT, RIGHT, FUNCTION, null, MESSAGE, "Predicate is not presented."),
        Arguments.of(LEFT, RIGHT, FUNCTION, PREDICATE, MESSAGE, "Message.")
    );
  }

}