package Custom_JUnit.Example_tests;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;
import Custom_JUnit.engine.CustomExceptions;

import static Custom_JUnit.engine.Assertions.*;


public class Tests_1 {


    @Before
    public void test_before() throws CustomExceptions.NotEqualException {
        int i = 1;
        int j = 2;
        int result = i+j;
        assertEquals(3, result);

    }

    @Test()
    public void test1() throws CustomExceptions.NotEqualException {
        String i= "koi";
        String j= "koi";
        assertEquals(i,j);
    }

    @Test(expected_exception = CustomExceptions.NotEqualException.class)
    public void test2() throws CustomExceptions.EqualException {
        String i= "koi";
        String j= "koi";
        assertNotEquals(i,j);
    }

    @Test(expected_exception = Exception.class)
    public void test3() throws CustomExceptions.NotEqualException {
        String[] i= {"l", "k", "m"};
        String[] j= {"p", "k", "m"};
        assertEquals(i,j);
    }

    @Test()
    public void test4() throws CustomExceptions.EqualException {
        String i = "jio";
        String j = "jio";
        assertNotEquals(i,j);
    }

    @Test
    public void test5() throws CustomExceptions.NotEqualException {
        int[] i = {1,0,3};
        int[] j = {1,2,3};
        assertArrayEquals(i,j);
    }

    @Test
    public void test6() throws CustomExceptions.EqualException {
        int[] i = {1,2,3};
        int[] j = {1,2,3};
        assertNotArrayEquals(i, j);
    }

    @After
    public void test_after() throws CustomExceptions.NotEqualException {
        int i = 1;
        int j = 2;
        int result = i+j;
        assertEquals(3, result);
    }

}
