package cn.xiaoyaoji.core.plugin;

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

    private Map<Event, List<PluginInfo>> getProjectPluginInfoMap(String projectId) {
        Map<Event, List<PluginInfo>> map = projectPluginMap.get(projectId);
        if (map == null) {
            synchronized (instance) {
                map = projectPluginMap.get(projectId);
                if (map == null) {
                    map = new HashMap<>();
                    projectPluginMap.put(projectId,map);
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
        if(list == null){
            synchronized (instance){
                list = map.get(event);
                if(list == null){
                    list = new CopyOnWriteArrayList<>();
                    map.put(event,list);
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
}
