package Custom_JUnit;

import Custom_JUnit.api.After;
import Custom_JUnit.api.Before;
import Custom_JUnit.api.Test;
import Custom_JUnit.engine.CustomExceptions;

import static Custom_JUnit.engine.Assertions.*;


public class Tests {


    @Before
    public void test_before() {
        String msg = "before test";
//        System.out.println(msg);
    }

    @Test()
    public void test0() throws CustomExceptions.NotEqualException {
        String i= "koi";
        String j= "koi";
        assertEquals(i,j);
    }

    @Test(expected_exception = CustomExceptions.NotEqualException.class)
    public void test1() throws CustomExceptions.EqualException {
        String i= "koi";
        String j= "koi";
        assertNotEquals(i,j);
    }

    @Test(expected_exception = Exception.class)
    public void test2() throws CustomExceptions.EqualException {
        String[] i= {"l", "k", "m"};
        String[] j= {"p", "k", "m"};
        assertNotEquals(i,j);
    }

    @Test(expected_exception = CustomExceptions.EqualException.class)
    public void test3() throws CustomExceptions.EqualException {
        String i = "jio";
        String j = "jio";
        assertNotEquals(i,j);
    }

    @Test(expected_exception = CustomExceptions.NotEqualException.class)
    public void test4() throws CustomExceptions.NotEqualException {
        int[] i = {1,0,3};
        int[] j = {1,2,3};
        assertArrayEquals(i,j);
    }

    @Test(expected_exception = CustomExceptions.EqualException.class)
    public void test5() throws CustomExceptions.EqualException {
        int[] i = {1,2,3};
        int[] j = {1,2,3};
        assertNotArrayEquals(i, j);
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
    public void test_after() {
        String msg = "after test";
//        System.out.println(msg);
    }

}
