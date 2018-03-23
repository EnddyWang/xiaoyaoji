package cn.xiaoyaoji.core.plugin;

import cn.xiaoyaoji.core.plugin.doc.DocImportPlugin;

import java.util.ArrayList;
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
 * 系统平台插件
 *
 * @author zhoujingjie
 * Date 2018-03-21
 */
public class PlatformPluginManager {
    private static PlatformPluginManager instance = new PlatformPluginManager();

    private PlatformPluginManager() {

    }

    public static PlatformPluginManager getInstance() {
        return instance;
    }


    /**
     * 获取登录插件
     *
     * @return list
     */
    public List<PluginInfo<LoginPlugin>> getLoginPlugins() {
        List<PluginInfo> pluginInfos = PluginManager.getInstance().getPlugins(Event.login);
        List<PluginInfo<LoginPlugin>> temp = new ArrayList<>(pluginInfos.size());
        for (PluginInfo item : pluginInfos) {
            temp.add(item);
        }
        return temp;
    }

    /**
     * 获取所有导入插件
     */
    public List<PluginInfo<DocImportPlugin>> getImportPlugins() {
        List<PluginInfo> list = PluginManager.getInstance().getPlugins(Event.docImport);
        List<PluginInfo<DocImportPlugin>> temp = new ArrayList<>(list.size());
        for (PluginInfo item : list) {
            temp.add(item);
        }
        return temp;
    }

    /**
     * 获取导入插件
     *
     * @param pluginId 插件id
     * @return 插件
     */
    public PluginInfo<DocImportPlugin> getImportPlugin(String pluginId) {
        for (PluginInfo<DocImportPlugin> item : getImportPlugins()) {
            if (item.getId().equals(pluginId)) {
                return item;
            }
        }
        return null;
    }


    /**
     * 获取登录插件
     *
     * @param pluginId 插件id
     * @return 登录插件
     */
    public PluginInfo<LoginPlugin> getLoginPlugin(String pluginId) {
        for (PluginInfo<LoginPlugin> item : getLoginPlugins()) {
            if (item.getId().equals(pluginId)) {
                return item;
            }
        }
        return null;
    }

}
