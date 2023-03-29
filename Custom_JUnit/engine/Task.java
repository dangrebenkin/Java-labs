package Custom_JUnit.engine;

import Custom_JUnit.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class Task implements Runnable {
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
                test_status = String.format("Test %d status: FAILED (expected exception was not catched).", test_number);
            } else if (expected_exception_argument == Exception.class && catched_exception.getClass().getSuperclass() != Exception.class) {
                test_status = String.format("Test %d status: FAILED (expected exception was not catched).", test_number);
            }
        } else if (expected_exception_argument == null && exceptions_array.size() >= 1) {
            ArrayList<String> exceptions_string_array = new ArrayList<>();
            for (int i = 0; i < exceptions_array.size(); i++) {
                Exception old_format = exceptions_array.get(i);
                String new_format = String.valueOf(old_format.getCause());
                exceptions_string_array.add(new_format);
            }
            String exceptions_string = String.join(", ", exceptions_string_array);
            test_status = String.format("Test %d status: FAILED: %s", test_number, exceptions_string);
        }
        System.out.println(test_status);
    }
}
