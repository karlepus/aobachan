import karlepus.aobachan.api.annotation.AutoRegister;

@AutoRegister
public class A {
    public static void register() {
        System.out.println("注解处理器执行成功");
    }
}
