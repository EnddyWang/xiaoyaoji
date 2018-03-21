package cn.xiaoyaoji.listener;

import cn.xiaoyaoji.core.plugin.PluginInfo;
import cn.xiaoyaoji.core.plugin.PluginManager;
import cn.xiaoyaoji.core.plugin.ProjectPluginManager;
import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.bean.ProjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.List;

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
 * <p>
 * 系统启动成功后
 *
 * @author zhoujingjie
 * Date 2018-03-21
 */
@Service
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //加载插件到projectPluginManager中
        loadProjectPluginInfos();
    }

    /**
     * 加载插件到projectPluginManager中
     */
    private void loadProjectPluginInfos() {
        List<ProjectPlugin> plugins = DataFactory.instance().getProjectPlugins();
        for (ProjectPlugin item : plugins) {
            PluginInfo temp = PluginManager.getInstance().getPluginInfo(item.getPluginId());
            if (temp != null) {
                ProjectPluginManager.getInstance().getPluginInfos(item.getProjectId()).add(temp);
            } else {
                logger.error("the plugin {} isn't exists", item.getPluginId());
            }
        }

    }
}
