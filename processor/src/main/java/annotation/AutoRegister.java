package annotation;

import java.lang.annotation.*;

@SuppressWarnings("unused")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoRegister {
}
