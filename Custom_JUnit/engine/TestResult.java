package Custom_JUnit.engine;

import java.lang.reflect.Method;

public class TestResult {

    private final Method testProcedure;

    private final TestResultStatus testStatus;

    private final Throwable testStackTraceHolder;

    // TODO: Write getters

    public TestResult(Method procedure,
                      TestResultStatus status,
                      Throwable information) {
        this.testProcedure = procedure;
        this.testStatus = status;
        this.testStackTraceHolder = information;
    }


}
