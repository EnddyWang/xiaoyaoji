package cn.xiaoyaoji.event;

import cn.xiaoyaoji.data.bean.Project;
import cn.xiaoyaoji.data.bean.User;

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
 * Date 2018-03-20
 */
public class ProjectDeletedEvent extends ApplicationEvent{
    private Project project;
    private User operator;

    public ProjectDeletedEvent(Project project, User operator) {
        this.project = project;
        this.operator = operator;
    }

    public Project getProject() {
        return project;
    }

    public ProjectDeletedEvent setProject(Project project) {
        this.project = project;
        return this;
    }

    public User getOperator() {
        return operator;
    }

    public ProjectDeletedEvent setOperator(User operator) {
        this.operator = operator;
        return this;
    }
}
