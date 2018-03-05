package cn.com.xiaoyaoji.event;

import cn.com.xiaoyaoji.listener.ApplicationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Date 2018-03-05
 */
public class ApplicationEventMulticaster {
    private static ApplicationEventMulticaster instance = new ApplicationEventMulticaster();
    private Map<ApplicationEvent, List<ApplicationListener>> listenerMap;

    private ApplicationEventMulticaster() {
        listenerMap = new HashMap<>();
    }

    public static ApplicationEventMulticaster instance() {
        return instance;
    }

    public void register(ApplicationEvent event, ApplicationListener listener) {

        //listenerMap.put(event, listener);
    }

    public void onEvent(ApplicationEvent event){
    }
}
