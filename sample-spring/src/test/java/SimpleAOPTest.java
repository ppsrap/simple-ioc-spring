import com.example.spring.aop.*;
import org.junit.Test;

public class SimpleAOPTest {
    @Test
    public void getProxy() {
        MethodInvocation log_task_start = () -> System.out.println("log task start");
        HelloServiceImpl helloServiceImpl = new HelloServiceImpl();

        // 创建一个Advice
        Advice beforeAdvice = new BeforeAdvice(helloServiceImpl, log_task_start);
        HelloService helloServiceImplProxy = (HelloService) SimpleAOP.getProxy(helloServiceImpl, beforeAdvice);
        helloServiceImplProxy.sayHelloWorld();

    }
}
