package Custom_JUnit.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)

public @interface Before {
    // The code marked @Before is executed before each test,
    // if test class has ten tests, @Before code will be executed ten times.
}
