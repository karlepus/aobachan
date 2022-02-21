import org.reflections.Reflections;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class APITest {
    public static void main(String[] args) {
        var reflect = new Reflections("karlepus");
        var results = reflect.getTypesAnnotatedWith(Nullable.class);
        results.forEach(cls -> {
            try {
                cls.getMethod("run").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        });
    }
}
