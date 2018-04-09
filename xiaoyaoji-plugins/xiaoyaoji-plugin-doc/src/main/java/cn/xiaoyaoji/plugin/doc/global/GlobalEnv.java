package cn.xiaoyaoji.plugin.doc.global;

import cn.xiaoyaoji.core.annotations.Alias;

import java.io.Serializable;

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
 * Date 2018-04-04
 */
@Alias("plugin_global_env")
public class GlobalEnv implements Serializable {
    private int id;
    private String projectId;
    private String content;

    public int getId() {
        return id;
    }

    public GlobalEnv setId(int id) {
        this.id = id;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public GlobalEnv setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public GlobalEnv setContent(String content) {
        this.content = content;
        return this;
    }
}
