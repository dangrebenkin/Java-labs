package Custom_JUnit.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class TestRunner {

    private static final int max_number_of_threads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws InvocationTargetException,
                                                    NoSuchMethodException,
                                                    InstantiationException,
                                                    IllegalAccessException {

        // парсинг аргументов
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

        // создание пула
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

        // создание списка классов с тестами
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

        // запуск тестов
        for (Class class_for_test : classes_with_tests) {
            Task task = new Task(class_for_test);
            executor.execute(task);
        }
        executor.shutdown();
    }
}

