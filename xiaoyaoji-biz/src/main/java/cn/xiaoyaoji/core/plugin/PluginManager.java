package cn.xiaoyaoji.core.plugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 插件管理
 *
 * @author zhoujingjie
 * created on 2017/6/21
 */
public class PluginManager {

    private Map<Event, List<PluginInfo>> pluginInfos;

    public static PluginManager instance;

    static {
        instance = new PluginManager();
    }

    private PluginManager() {
        pluginInfos = new LinkedHashMap<>();
    }


    public Map<Event, List<PluginInfo>> getPluginInfos() {
        return pluginInfos;
    }

    public static PluginManager getInstance() {
        return instance;
    }

    public void register(PluginInfo<?> pluginInfo) {
        for (Event e : pluginInfo.getEvents()) {
            List<PluginInfo> temp = pluginInfos.get(e);
            if (temp == null) {
                temp = new CopyOnWriteArrayList<>();
                pluginInfos.put(e, temp);
            }
            temp.add(pluginInfo);
        }
    }

    @SuppressWarnings("unchecked")
    public void unload(PluginInfo<?> pluginInfo) {
        for (Event e : pluginInfo.getEvents()) {
            List<PluginInfo> temp = pluginInfos.get(e);
            pluginInfo.getPlugin().uninstall();
            pluginInfo.setPlugin(null);
            if (temp != null) {
                temp.remove(pluginInfo);
            }
        }
    }


    List<PluginInfo> getPlugins(Event event) {
        List<PluginInfo> temp = this.pluginInfos.get(event);
        if (temp == null) {
            temp = new CopyOnWriteArrayList<>();
            this.pluginInfos.put(event, temp);
        }
        return temp;
    }



    public PluginInfo getPluginInfo(String pluginId) {
        for (List<PluginInfo> list : pluginInfos.values()) {
            for (PluginInfo item : list) {
                if (item.getId().equals(pluginId)) {
                    return item;
                }
            }
        }
        return null;
    }


}
