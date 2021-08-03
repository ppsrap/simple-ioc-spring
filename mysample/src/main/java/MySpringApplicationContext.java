import com.example.spring.ioc.utils.MyIoc;

public class MySpringApplicationContext {
    private String configName;
    
    public MySpringApplicationContext(String configName){
        this.configName = configName;
    }

    public <T> T getBean(String name, Class<T> c) throws Exception {
        String file = MySpringApplicationContext.class.getClassLoader().getResource("ioc.xml").getFile();
        MyIoc myIoc = new MyIoc(file);
        Object bean = myIoc.getBean(name);
        return (T) bean;
    }
}
