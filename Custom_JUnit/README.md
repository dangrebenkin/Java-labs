## Custom JUnit 

#### Интерфейс командной строки:

java -jar custom_junit.jar -N <число потоков> -lc <название классов с тестами через пробел>

#### Пример:
```
java -jar custom_junit.jar -N 2 -lc Custom_JUnit.Example_tests.Tests_1 Custom_JUnit.Example_tests.Tests_2
```
#### Вывод:
```
Test 1 of Custom_JUnit.Example_tests.Tests_1 status: PASSED
Test 1 of Custom_JUnit.Example_tests.Tests_2 status: PASSED
Test 2 of Custom_JUnit.Example_tests.Tests_2 status: PASSED
Test 2 of Custom_JUnit.Example_tests.Tests_1 status: FAILED (expected exception was not catched).
Test 3 of Custom_JUnit.Example_tests.Tests_1 status: FAILED (expected exception was not catched).
Test 4 of Custom_JUnit.Example_tests.Tests_1 status: FAILED: Custom_JUnit.engine.CustomExceptions$EqualException: The objects are equal.
Test 5 of Custom_JUnit.Example_tests.Tests_1 status: FAILED: Custom_JUnit.engine.CustomExceptions$NotEqualException: The arrays are not equal.
Test 6 of Custom_JUnit.Example_tests.Tests_1 status: FAILED: Custom_JUnit.engine.CustomExceptions$EqualException: The arrays are equal.

```
