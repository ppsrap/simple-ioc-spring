import com.example.spring.ioc.beans.DIBean;
import com.example.spring.ioc.beans.Table;
import com.example.spring.ioc.beans.User;
import org.junit.Test;

public class MyIocTest {
    @Test
    public void test() throws Exception {
        // 创建上下文
        MySpringApplicationContext ctx = new MySpringApplicationContext("ioc.xml");
        // 获取对象
        User user = ctx.getBean("user", User.class);
        Table table = ctx.getBean("table", Table.class);
        DIBean dibean = ctx.getBean("dibean", DIBean.class);
        System.out.println(dibean.table);
        System.out.println(dibean.user);
    }
}
