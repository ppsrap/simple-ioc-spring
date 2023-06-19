
import com.example.spring.aop.MessageServiceImpl;
import com.example.spring.aop.UserService;
import com.example.spring.aop.UserServiceImpl;
import com.example.spring.ioc.annonation.Inject;
import com.example.spring.ioc.annonation.Init;

import java.util.HashMap;
import java.util.Map;

public class IOCContainer {
    private Map<String, Object> beans = new HashMap<>();

    public void registerBean(String beanName, Class<?> beanClass) {
        try {
            Object beanInstance = beanClass.getDeclaredConstructor().newInstance();
            beans.put(beanName, beanInstance);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register bean: " + beanName, e);
        }
    }

    public <T> T getBean(String beanName, Class<T> beanClass) {
        Object bean = beans.get(beanName);
        if (bean == null) {
            throw new RuntimeException("Bean not found: " + beanName);
        }
        return beanClass.cast(bean);
    }

    /*performDependencyInjection()方法负责执行依赖注入，通过反射扫描Bean的字段和方法参数，查找并设置依赖项。*/
    public void performDependencyInjection() {
        for (Object bean : beans.values()) {
            performFieldInjection(bean);
            performMethodInjection(bean);
        }
    }

    private void performFieldInjection(Object bean) {
        Class<?> beanClass = bean.getClass();
        for (java.lang.reflect.Field field : beanClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                String fieldName = field.getName();
                Object dependency = beans.get(fieldName);
                if (dependency == null) {
                    throw new RuntimeException("Dependency not found: " + fieldName);
                }
                field.setAccessible(true);
                try {
                    field.set(bean, dependency);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to perform field injection: " + fieldName, e);
                }
            }
        }
    }

    private void performMethodInjection(Object bean) {
        Class<?> beanClass = bean.getClass();
        for (java.lang.reflect.Method method : beanClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Inject.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    String dependencyName = parameterType.getSimpleName();
                    Object dependency = beans.get(dependencyName);
                    if (dependency == null) {
                        throw new RuntimeException("Dependency not found: " + dependencyName);
                    }
                    arguments[i] = dependency;
                }
                method.setAccessible(true);
                try {
                    method.invoke(bean, arguments);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to perform method injection: " + method.getName(), e);
                }
            }
        }
    }

    /*initializeBeans()方法负责调用被@Init注解标识的初始化方法。*/
    public void initializeBeans() {
        for (Object bean : beans.values()) {
            invokeInitMethod(bean);
        }
    }

    private void invokeInitMethod(Object bean) {
        Class<?> beanClass = bean.getClass();
        for (java.lang.reflect.Method method : beanClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                method.setAccessible(true);
                try {
                    method.invoke(bean);
                } catch (Exception e) {
                    throw new RuntimeException("Failed to invoke init method: " + method.getName(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
        // 创建IOC容器实例
        IOCContainer container = new IOCContainer();

        // 注册bean到容器
        container.registerBean("userService", UserServiceImpl.class);
        container.registerBean("messageService", MessageServiceImpl.class);

        // 执行依赖注入
        container.performDependencyInjection();

        // 初始化bean
        container.initializeBeans();

        // 从容器中获取bean，并使用
        UserService userService = container.getBean("userService", UserService.class);
        userService.sayHello();
        userService.sendMessage();

        MessageServiceImpl messageService = container.getBean("messageService", MessageServiceImpl.class);
        messageService.sendMessage("111");
    }
}