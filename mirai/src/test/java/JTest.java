public class JTest {
    public static <T> void get(T t) {
        System.out.println(t.getClass());
    }

    public static void main(String[] args) {
        get("2");
    }
}
