import com.example.spring.ioc.SimpleIoc;
import com.example.spring.ioc.User;
import org.junit.Test;

import java.net.URL;

public class SimpleIOTest {
    @Test
    public void getBean() throws Exception{
        String location = SimpleIoc.class.getClassLoader().getResource("ioc.xml").getFile();
        SimpleIoc bf = new SimpleIoc(location);
        User user = (User)bf.getBean("user");
        System.out.println(user);
    }

}
