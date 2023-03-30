package Custom_JUnit.Example_tests;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;
import Custom_JUnit.engine.CustomExceptions;

import static Custom_JUnit.engine.Assertions.*;


public class Tests_2 {


    @Before
    public void test_before() throws CustomExceptions.NotEqualException {
        int i = 1;
        int j = 2;
        int result = i+j;
        assertEquals(3, result);

    }

    @Test(expected_exception = CustomExceptions.IsNotNullException.class)
    public void test6() throws CustomExceptions.IsNotNullException {
        int[] i = null;
        assertNotNullEquals(i);
    }

    @Test(expected_exception = CustomExceptions.IsNullException.class)
    public void test7() throws CustomExceptions.IsNullException {
        int[] i = null;
        assertNullEquals(i);
    }

    @After
    public void test_after() throws CustomExceptions.NotEqualException {
        int i = 1;
        int j = 2;
        int result = i+j;
        assertEquals(3, result);
    }

}
