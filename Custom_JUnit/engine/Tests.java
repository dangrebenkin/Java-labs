package Custom_JUnit.engine;

import Custom_JUnit.api.Test;
import java.util.logging.Logger;


public class Tests {

    private static final String TEST_CLASS_NAME = "testframe.engine.ToyTests";

    private static final Logger INVOCATION_LOGGER
            = Logger.getLogger(TEST_CLASS_NAME);

    private static boolean relay(boolean flag) {
        return flag;
    }

    @Test
    public void testThatShouldPass() {
        INVOCATION_LOGGER.entering(TEST_CLASS_NAME, "@Test");
        String msg = "This test should be reported as passing";
        System.out.println(msg);
    }

    @Test
    public void testThatShouldFail() {
        INVOCATION_LOGGER.entering(TEST_CLASS_NAME, "@Test");
        String msg = "This test should be reported as failing";
        System.out.println(msg);
        assert false : msg;
    }

}
