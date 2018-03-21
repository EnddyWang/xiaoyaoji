package cn.xiaoyaoji.core.plugin;

import cn.xiaoyaoji.core.plugin.doc.DocEvPlugin;
import cn.xiaoyaoji.core.plugin.doc.DocExportPlugin;
import cn.xiaoyaoji.core.plugin.doc.DocImportPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
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


    private List<PluginInfo> getPlugins(Event event) {
        List<PluginInfo> temp = this.pluginInfos.get(event);
        if (temp == null) {
            temp = new CopyOnWriteArrayList<>();
            this.pluginInfos.put(event, temp);
        }
        return temp;
    }


    public PluginInfo<DocExportPlugin> getExportPlugin(String pluginId) {
        List<PluginInfo> list = getPlugins(Event.docExport);
        for (PluginInfo temp : list) {
            if (temp.getId().equals(pluginId)) {
                return temp;
            }
        }
        return null;
    }

    public PluginInfo<DocImportPlugin> getImportPlugin(String pluginId) {
        List<PluginInfo> list = getPlugins(Event.docImport);
        for (PluginInfo temp : list) {
            if (temp.getId().equals(pluginId)) {
                return temp;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<PluginInfo<DocEvPlugin>> getDocEvPlugins() {
        List<PluginInfo> pluginInfos = getPlugins(Event.doc);
        List<PluginInfo<DocEvPlugin>> temp = new ArrayList<>(pluginInfos.size());
        for (PluginInfo item : pluginInfos) {
            temp.add(item);
        }
        return temp;
    }

    public List<PluginInfo<LoginPlugin>> getLoginPlugins() {
        List<PluginInfo> pluginInfos = getPlugins(Event.login);
        List<PluginInfo<LoginPlugin>> temp = new ArrayList<>(pluginInfos.size());
        for (PluginInfo item : pluginInfos) {
            temp.add(item);
        }
        return temp;
    }

    @SuppressWarnings("unchecked")
    public <T extends Plugin> PluginInfo<T> getPlugin(String pluginId, Event event) {
        List<PluginInfo> plugins = getPlugins(event);
        for (PluginInfo<T> pluginInfo : plugins) {
            if (pluginInfo.getId().equals(pluginId)) {
                return pluginInfo;
            }
        }
        return null;
    }

    public PluginInfo<LoginPlugin> getLoginPlugin(String pluginId) {
        return getPlugin(pluginId, Event.login);
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
