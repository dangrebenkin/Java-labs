package Custom_JUnit.engine;

public class CustomExceptions {

    public static class NotEqualException extends Exception {
        public NotEqualException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class EqualException extends Exception {
        public EqualException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class IsNullException extends Exception {
        public IsNullException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class IsNotNullException extends Exception {
        public IsNotNullException(String errorMessage) {
            super(errorMessage);
        }
    }

}
