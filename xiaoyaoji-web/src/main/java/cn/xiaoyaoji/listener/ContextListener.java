package cn.xiaoyaoji.listener;

import cn.xiaoyaoji.Application2;
import cn.xiaoyaoji.task.ScheduleManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zhoujingjie
 * @date 2016-07-26
 */
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Application2.started(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ScheduleManager.shutdown();
    }
}
