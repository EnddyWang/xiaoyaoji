package cn.xiaoyaoji;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.Test;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author zhoujingjie
 * Date 2018-03-28
 */
public class TestApplication {

    @Test
    public void test() throws Exception {
        int port = 888;
        Server server = new Server(port);
        String rootPath = TestApplication.class.getClassLoader().getResource(".").toString();
        WebAppContext context = new WebAppContext("E:\\codes\\other\\xiaoyaoji\\xiaoyaoji-web\\src\\main\\webapp","/");
        server.setHandler(context);
        server.start();
        server.dumpStdErr();
        server.join();
    }
}
