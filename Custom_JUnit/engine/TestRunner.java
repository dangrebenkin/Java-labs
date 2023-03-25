package Custom_JUnit.engine;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    private static void run(Method test_method,
                            Object instance) {
        try {
            test_method.invoke(instance, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
            Object obj = class_for_test.getDeclaredConstructor().newInstance();

            final List<Method> before_annotated_methods = new ArrayList<>();
            final ArrayList<ArrayList<Method>> jobs = new ArrayList<>();
            final List<Method> after_annotated_methods = new ArrayList<>();

            for (Method method : class_for_test.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Before.class)) {
                    before_annotated_methods.add(method);
                } else if (method.isAnnotationPresent(Test.class)) {
                    ArrayList<Method> job = new ArrayList<>();
                    job.add(method);
                    jobs.add(job);
                } else if (method.isAnnotationPresent(After.class)) {
                    after_annotated_methods.add(method);
                }
            }

            if (jobs.size() != 0) {

                if (before_annotated_methods.size() != 0) {
                    for (int i = 0; i < jobs.size(); i++) {
                        ArrayList<Method> before_plus = new ArrayList<>(before_annotated_methods);
                        before_plus.addAll(jobs.get(i));
                        jobs.set(i, before_plus);
                    }
                }
                if (after_annotated_methods.size() != 0) {
                    for (int i = 0; i < jobs.size(); i++) {
                        ArrayList<Method> after_plus = jobs.get(i);
                        after_plus.addAll(after_annotated_methods);
                        jobs.set(i, after_plus);
                    }
                }
            }

            System.out.println(jobs);

        }

    }
}

