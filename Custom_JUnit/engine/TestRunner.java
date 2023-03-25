package Custom_JUnit.engine;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    static TestResult run(Method test,
                          Object instance)
    {
        return new TestResult(null, TestResultStatus.SKIPPED, null);
    }

    public static List<TestResult> run(String testClassName) {
        List<TestResult> results = new ArrayList<>();
        // TODO: Write tests for this
        return results;
    }

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        if (args.length == 0) {
            System.out.println("Please specify test class to run");
        } else {

//            Class<?> class_for_test = Class.forName(args[0]);
            Class<Tests> class_for_test = Tests.class;

            final List<Method> before_annotated_methods = new ArrayList<>();
            final List<Method> test_annotated_methods = new ArrayList<>();
            final List<Method> after_annotated_methods = new ArrayList<>();

            for (Method method : class_for_test.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    before_annotated_methods.add(method);
                } else if (method.isAnnotationPresent(Test.class)) {
                    test_annotated_methods.add(method);
                } else if (method.isAnnotationPresent(After.class)) {
                    after_annotated_methods.add(method);
                }
            }
            Object obj = class_for_test.getDeclaredConstructor().newInstance();


        }

    }
}
