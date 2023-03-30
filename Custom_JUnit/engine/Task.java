package Custom_JUnit.engine;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Task implements Runnable {

    final ArrayList<ArrayList<Method>> test_annotated_methods = new ArrayList<>();
    Object class_object;
    String class_name;

    public Task(Class class_for_test) throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {

        // объект класса с тестами
        class_object = class_for_test.getDeclaredConstructor().newInstance();
        class_name = class_for_test.getName();

        // поиск аннотированных методов из класса с тестами
        final List<Method> before_annotated_methods = new ArrayList<>();
        final List<Method> after_annotated_methods = new ArrayList<>();
        for (Method method : class_for_test.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                before_annotated_methods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                ArrayList<Method> test_annotated_method = new ArrayList<>();
                test_annotated_method.add(method);
                test_annotated_methods.add(test_annotated_method);
            } else if (method.isAnnotationPresent(After.class)) {
                after_annotated_methods.add(method);
            }
        }

        if (test_annotated_methods.size() != 0) {
            // добавление методов before и after (при наличии) в списки задач
            if (before_annotated_methods.size() != 0) {
                for (int i = 0; i < test_annotated_methods.size(); i++) {
                    ArrayList<Method> before_plus = new ArrayList<>(before_annotated_methods);
                    before_plus.addAll(test_annotated_methods.get(i));
                    test_annotated_methods.set(i, before_plus);
                }
            }
            if (after_annotated_methods.size() != 0) {
                for (int i = 0; i < test_annotated_methods.size(); i++) {
                    ArrayList<Method> after_plus = test_annotated_methods.get(i);
                    after_plus.addAll(after_annotated_methods);
                    test_annotated_methods.set(i, after_plus);
                }
            }
        } else {
            String warning_message = String.format("There is no @Test-annotated methods to execute in class %s.", class_name);
            System.out.println(warning_message);
            System.exit(0);
        }
    }

    @Override
    public void run() {

        int counter = 1;
        for (ArrayList<Method> methods_list : test_annotated_methods) {

            ArrayList<Exception> exceptions_array = new ArrayList<>();
            String test_status = String.format("Test %d of %s status: PASSED", counter, class_name);

            // проверяем, указано ли значение опционального аргумента в аннотации Test
            Class expected_exception_argument = null;
            try {
                for (Method class_method : methods_list) {
                    if (class_method.isAnnotationPresent(Test.class)) {
                        Class expected_value = class_method.getAnnotation(Test.class).expected_exception();
                        // в качестве аргумента можно указать и просто Exception.class и тогда ожидаемым исключением
                        // будет любое единственное исключение в exceptions_array
                        if (expected_value == Exception.class || expected_value.getSuperclass() == Exception.class) {
                            expected_exception_argument = expected_value;
                        }
                    }
                    class_method.invoke(class_object, null);
                }
            } catch (Exception e) {
                exceptions_array.add(e);
            }

            // если ожидаемого исключения не случилось или помимо него случились другие исключения, то тест провален
            if (expected_exception_argument != null && exceptions_array.size() == 1) {
                Exception catched_exception = exceptions_array.get(0);
                if (catched_exception.getCause().getClass() != expected_exception_argument) {
                    test_status = String.format("Test %d of %s status: FAILED (expected exception was not catched).", counter, class_name);
                } else if (expected_exception_argument == Exception.class && catched_exception.getClass().getSuperclass() != Exception.class) {
                    test_status = String.format("Test %d of %s status: FAILED (expected exception was not catched).", counter, class_name);
                }
            } else if (expected_exception_argument == null && exceptions_array.size() >= 1) {
                ArrayList<String> exceptions_string_array = new ArrayList<>();
                for (int i = 0; i < exceptions_array.size(); i++) {
                    Exception old_format = exceptions_array.get(i);
                    String new_format = String.valueOf(old_format.getCause());
                    exceptions_string_array.add(new_format);
                }
                String exceptions_string = String.join(", ", exceptions_string_array);
                test_status = String.format("Test %d of %s status: FAILED: %s", counter, class_name, exceptions_string);
            }
            System.out.println(test_status);
            counter++;
        }
    }
}
