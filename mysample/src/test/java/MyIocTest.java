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

        System.out.println("-------------------------------------------------");
        System.out.println(user.getName());
        System.out.println(user.getAge());
        System.out.println(user.getClass());
        System.out.println("-------------------------------------------------");
        System.out.println(table.getId());
        System.out.println(table.getTableName());
        System.out.println(table.getClass());
        System.out.println("-------------------------------------------------");
        System.out.println(dibean.table);
        System.out.println(dibean.user);
        System.out.println(dibean.getClass());
    }
}
