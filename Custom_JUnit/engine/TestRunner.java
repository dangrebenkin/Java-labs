package Custom_JUnit.engine;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class TestRunner {

    private static final int max_number_of_threads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        // параметры из командной строки (пока указал вот так, чтобы тестировать код)
        int number_of_threads = 6; // -N

        // здесь должен быть парсинг списка классов классов с тестами (<tested-classes>)

        Class<Tests> class_for_test = Tests.class; // пусть пока это будет один класс

        // инициализация экзекьютера
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(max_number_of_threads);
        if (number_of_threads < 0) {
            System.out.println("Wrong number of threads was set, please set number of set bigger than 0.");
        } else if (number_of_threads < max_number_of_threads) {
            executor.setCorePoolSize(number_of_threads);
        }

        if (args.length == 0) {
            System.out.println("Please specify test class to run");
        } else {
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
                executor.shutdown();

            } else {
                System.out.println("There is no test methods to execute.");
            }
        }
    }

    private static class Task implements Runnable {
        private final Object class_object;
        private final ArrayList<Method> methods_list;
        private final int test_number;

        public Task(ArrayList<Method> methods_to_execute, Object instance, int test_id) {
            this.class_object = instance;
            this.methods_list = methods_to_execute;
            this.test_number = test_id;
        }

        @Override
        public void run() {
            ArrayList<Exception> exceptions_array = new ArrayList<>();
            String test_status = String.format("Test %d status: PASSED", test_number);
            try {
                for (Method class_method : methods_list) {
                    class_method.invoke(class_object, null);
                }
            } catch (Exception e) {
                exceptions_array.add(e);
            }
            if (exceptions_array.size() > 0) {
                test_status = String.format("Test %d status: FAILED: %s", test_number, exceptions_array);
            }

            System.out.println(test_status);
        }
    }
}

