package cn.xiaoyaoji.data.bean;

import cn.xiaoyaoji.core.annotations.Alias;

import java.util.Date;

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
@Alias("project_plugin")
public class ProjectPlugin {
    private Integer id;
    private String projectId;
    private String pluginId;
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public ProjectPlugin setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public ProjectPlugin setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getPluginId() {
        return pluginId;
    }

    public ProjectPlugin setPluginId(String pluginId) {
        this.pluginId = pluginId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ProjectPlugin setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
