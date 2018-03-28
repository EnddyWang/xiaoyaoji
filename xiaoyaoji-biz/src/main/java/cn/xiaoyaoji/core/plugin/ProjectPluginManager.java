package cn.xiaoyaoji.core.plugin;

import cn.xiaoyaoji.core.plugin.doc.DocPlugin;
import cn.xiaoyaoji.core.plugin.doc.DocExportPlugin;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
 * 跟项目相关的插件
 *
 * @author zhoujingjie
 * Date 2018-03-21
 */
public class ProjectPluginManager {
    private Map<String, Map<Event, List<PluginInfo>>> projectPluginMap;
    private static ProjectPluginManager instance = new ProjectPluginManager();

    private ProjectPluginManager() {
        projectPluginMap = new ConcurrentHashMap<>();
    }

    public static ProjectPluginManager getInstance() {
        return instance;
    }

    /**
     * 获取项目的插件
     *
     * @param projectId
     * @return
     */
    public Map<Event, List<PluginInfo>> getProjectPluginInfoMap(String projectId) {
        Map<Event, List<PluginInfo>> map = projectPluginMap.get(projectId);
        if (map == null) {
            synchronized (instance) {
                map = projectPluginMap.get(projectId);
                if (map == null) {
                    map = new HashMap<>();
                    projectPluginMap.put(projectId, map);
                }
            }
        }
        return map;
    }


    public List<PluginInfo> getPluginInfos(String projectId) {
        Map<Event, List<PluginInfo>> map = getProjectPluginInfoMap(projectId);
        List<PluginInfo> list = new ArrayList<>();
        for (List<PluginInfo> item : map.values()) {
            list.addAll(item);
        }
        return list;
    }


    public List<PluginInfo> getPluginInfos(String projectId, Event event) {
        Map<Event, List<PluginInfo>> map = getProjectPluginInfoMap(projectId);
        List<PluginInfo> list = map.get(event);
        if (list == null) {
            synchronized (instance) {
                list = map.get(event);
                if (list == null) {
                    list = new CopyOnWriteArrayList<>();
                    map.put(event, list);
                }
            }
        }
        return list;
    }

    public PluginInfo getPluginInfo(String projectId, String pluginId) {
        for (List<PluginInfo> item : getProjectPluginInfoMap(projectId).values()) {
            for (PluginInfo pluginInfoItem : item) {
                if (pluginInfoItem.getId().equals(pluginId)) {
                    return pluginInfoItem;
                }
            }
        }
        return null;
    }


    /**
     * 获取文档插件
     *
     * @param projectId 项目id
     * @return 文档插件
     */
    @SuppressWarnings("unchecked")
    public List<PluginInfo<DocPlugin>> getDocEvPlugins(String projectId) {
        List<PluginInfo> plugins = getPluginInfos(projectId, Event.doc);
        List<PluginInfo<DocPlugin>> temp = new ArrayList<>(plugins.size());
        for (PluginInfo item : plugins) {
            temp.add(item);
        }
        return temp;
    }


    /**
     * 获取导出插件
     *
     * @param pluginId
     * @return
     */
    public PluginInfo<DocExportPlugin> getExportPlugin(String pluginId) {
        List<PluginInfo> list = getPluginInfos(pluginId, Event.docExport);
        for (PluginInfo temp : list) {
            if (temp.getId().equals(pluginId)) {
                return temp;
            }
        }
        return null;
    }


}
