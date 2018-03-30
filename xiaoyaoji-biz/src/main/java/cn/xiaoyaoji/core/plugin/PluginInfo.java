package cn.xiaoyaoji.core.plugin;


import java.io.File;
import java.util.*;

/**
 * @author zhoujingjie
 * created on 2017/5/18
 */
public class PluginInfo<T extends Plugin> {
    private String id;
    //插件名称
    private String name;
    //插件简短名称
    private String shortName;
    //描述
    private String description;
    //作者
    private String author;
    //创建时间
    private String createTime;
    //插件核心类
    private String clazz;
    //当前版本
    private String version;
    //图标
    private Icon icon;

    private T plugin;
    private Dependency dependency;
    private List<Dependency> dependencies;
    //运行时文件夹
    private String runtimeFolder;
    //配置
    private Map<String, String> config;
    //运行时目录
    private File runtimeDirectory;

    /**
     * 支持的页面类型。文档的id
     */
    private Set<String> supportPageTypes;
    /**
     * 支持的事件
     */
    private Set<Event> events;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setEvent(Event event) {
        getEvents().add(event);
    }

    public T getPlugin() {
        return plugin;
    }

    public void setPlugin(T plugin) {
        this.plugin = plugin;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getRuntimeFolder() {
        return runtimeFolder;
    }

    public void setRuntimeFolder(String runtimeFolder) {
        this.runtimeFolder = runtimeFolder;
    }

    public Map<String, String> getConfig() {
        if (config == null) {
            config = new HashMap<>();
        }
        return config;
    }

    public void setConfig(Map<String, String> config) {
        this.config = config;
    }

    public void setRuntimeDirectory(File runtimeDirectory) {
        this.runtimeDirectory = runtimeDirectory;
    }

    public File getRuntimeDirectory() {
        return runtimeDirectory;
    }



    public Dependency getDependency() {
        return dependency;
    }

    public void setDependency(Dependency dependency) {
        this.dependency = dependency;
    }

    public Set<String> getSupportPageTypes() {
        if (supportPageTypes == null) {
            supportPageTypes = new HashSet<>();
        }
        return supportPageTypes;
    }

    public void setSupportPageTypes(Set<String> supportPageTypes) {
        this.supportPageTypes = supportPageTypes;
    }

    public Set<Event> getEvents() {
        if (events == null) {
            events = new HashSet<>();
        }
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public String getShortName() {
        if(shortName == null) {
            return getName();
        }
        return shortName;
    }

    public PluginInfo setShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PluginInfo<?> that = (PluginInfo<?>) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

