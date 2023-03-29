package Custom_JUnit.engine;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class TestRunner {

    private static final int max_number_of_threads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        // parsing arguments
        final Map<String, List<String>> params = new HashMap<>();
        List<String> options = null;
        for (final String a : args) {
            if (a.charAt(0) == '-') {
                if (a.length() < 2) {
                    System.err.println("Error at argument " + a);
                    return;
                }

                options = new ArrayList<>();
                params.put(a.substring(1), options);
            } else if (options != null) {
                options.add(a);
            } else {
                System.err.println("Illegal parameter usage!");
                return;
            }
        }
        // set executor and threads parameters
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_number_of_threads);
        try {
            int number_of_threads = Integer.parseInt(params.get("N").get(0));
            if (number_of_threads <= 0) {
                System.out.println("Wrong value of threads was set, the max number of threads will be used.");
            } else if (number_of_threads < max_number_of_threads) {
                executor.setCorePoolSize(number_of_threads);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("The max number of available core threads will be used.");
        }
        // set classes to test
        ArrayList<Class> classes_with_tests = new ArrayList<>();
        try {
            List<String> classes_with_tests_strings = params.get("lc");
            for (String class_name : classes_with_tests_strings) {
                Class new_format = Class.forName(class_name);
                classes_with_tests.add(new_format);
            }
            assert classes_with_tests.size() != 0;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        // running tests
        for (Class class_for_test : classes_with_tests) {
            // объект класса с тестами
            Object obj = class_for_test.getDeclaredConstructor().newInstance();

            // поиск аннотированных методов из класса с тестами
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

                // добавление методов before и after (при наличии) в списки задач
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

                // запуск задач по потокам с помощью executor
                int counter = 1;
                for (ArrayList<Method> test_task : jobs) {
                    Task task = new Task(test_task, obj, counter);
                    executor.execute(task);
                    counter++;
                }
            } else {
                String warning_message = String.format("There is no test methods to execute in class %s.", class_for_test.getName());
                System.out.println(warning_message);
            }
        }
        executor.shutdown();
    }
}

