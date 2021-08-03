import com.example.spring.aop.*;
import org.junit.Test;

public class MyAOPTest {
    @Test
    public void aopTest(){
        // 1. 匿名函数实现切面逻辑
        MethodInvocation methodInvocation = () -> System.out.println("切入");
        // 2. 实例化一个bean对象
        HelloServiceImpl helloService = new HelloServiceImpl();
        // 3. 创建一个Advice
        BeforeAdvice beforeAdvice = new BeforeAdvice(helloService, methodInvocation);
        // 4. 使用SimpleAOP生成代理对象
        HelloService proxy = (HelloService) SimpleAOP.getProxy(helloService, beforeAdvice);
        proxy.sayHello();

    }
}
