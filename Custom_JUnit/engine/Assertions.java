package Custom_JUnit.engine;

import java.util.Objects;

public class Assertions {

    public static void assertEquals(Object expected, Object actual) throws CustomExceptions.NotEqualException {
        Class<?> expected_class = expected.getClass();
        Class<?> actual_class = actual.getClass();
        if (expected_class == actual_class) {
            if (expected_class.isArray()) {
                throw new CustomExceptions.NotEqualException("The objects are arrays. Use 'assertArrayEquals' assertion instead.");
            } else {
                if (expected != actual) {
                    throw new CustomExceptions.NotEqualException("Not equal objects.");
                }
            }
        } else {
            throw new CustomExceptions.NotEqualException("The objects are not from the same class, they can not be equal.");
        }
    }

    public static void assertNotEquals(Object expected, Object actual) throws CustomExceptions.EqualException {
        Class<?> expected_class = expected.getClass();
        Class<?> actual_class = actual.getClass();
        if (expected_class == actual_class) {
            if (expected_class.isArray()) {
                throw new CustomExceptions.EqualException("The objects are arrays. Use 'assertArrayNotEquals' assertion instead.");
            } else {
                if (expected == actual) {
                    throw new CustomExceptions.EqualException("The objects are equal.");
                }
            }
        }
    }

    public static void assertArrayEquals(Object expected, Object actual) throws CustomExceptions.NotEqualException {
        Class<?> expected_class = expected.getClass();
        Class<?> actual_class = actual.getClass();
        if (expected_class == actual_class) {
            if (expected_class.isArray()) {
                if (!Objects.deepEquals(expected, actual)) {
                    throw new CustomExceptions.NotEqualException("The arrays are not equal.");
                }
            } else {
                throw new CustomExceptions.NotEqualException("The objects are not arrays, use 'assertEquals' instead.");
            }
        } else {
            throw new CustomExceptions.NotEqualException("The objects are not from the same class, they can not be equal.");
        }

    }

    public static void assertNotArrayEquals(Object expected, Object actual) throws CustomExceptions.EqualException {
        Class<?> expected_class = expected.getClass();
        Class<?> actual_class = actual.getClass();
        if (expected_class == actual_class) {
            if (expected_class.isArray()) {
                if (Objects.deepEquals(expected, actual)) {
                    throw new CustomExceptions.EqualException("The arrays are equal.");
                }
            } else {
                throw new CustomExceptions.EqualException("The objects are not arrays, use 'assertNotEquals' instead.");
            }
        }
    }

    public static void assertNullEquals(Object obj) throws CustomExceptions.IsNullException {
        if (obj != null) {
            throw new CustomExceptions.IsNullException("The objects is null.");
        }
    }

    public static void assertNotNullEquals(Object obj) throws CustomExceptions.IsNotNullException {
        if (obj == null) {
            throw new CustomExceptions.IsNotNullException("The objects is not null.");
        }
    }

}

