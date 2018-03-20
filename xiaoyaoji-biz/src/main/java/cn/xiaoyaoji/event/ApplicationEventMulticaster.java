package cn.xiaoyaoji.event;

import cn.xiaoyaoji.listener.ApplicationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

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
    private static Logger logger = LoggerFactory.getLogger(ApplicationEventMulticaster.class);
    private Map<Class, Set<ApplicationListener>> listenerMap;

    private ApplicationEventMulticaster() {
        listenerMap = new HashMap<>();
    }

    public static ApplicationEventMulticaster instance() {
        return instance;
    }

    public <T extends ApplicationEvent> void registerEvent(ApplicationListener<T> listener) {
        Type[] types = listener.getClass().getGenericInterfaces();
        if(types == null){
            logger.error("unregisted listener {}", listener.getClass().getName());
            return;
        }
        Type annotation = types[0];
        if(!(annotation instanceof ParameterizedType)){
            logger.error("unregisted listener {}", listener.getClass().getName());
            return;
        }
        Type[] event = ((ParameterizedType) annotation).getActualTypeArguments();
        Class keyClazz = (Class) event[0];
        synchronized (instance) {
            Set<ApplicationListener> listeners = listenerMap.get(keyClazz);
            if (listeners == null) {
                listeners = Collections.synchronizedSet(new LinkedHashSet<>());
                listenerMap.put(keyClazz, listeners);
            }
            listeners.add(listener);
        }
    }

    public void multicastEvent(ApplicationEvent event) {
        Set<ApplicationListener> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            for (ApplicationListener listener : listeners) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    logger.error("multicastEvent failed",e);
                }
            }
        }
    }
}
