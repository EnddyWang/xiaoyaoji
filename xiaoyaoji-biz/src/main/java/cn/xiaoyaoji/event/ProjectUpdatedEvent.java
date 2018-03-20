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
public class ProjectUpdatedEvent extends ApplicationEvent{
    private Project before,now;
    private User operator;

    public ProjectUpdatedEvent(Project before, Project now, User operator) {
        this.before = before;
        this.now = now;
        this.operator = operator;
    }

    public Project getBefore() {
        return before;
    }

    public ProjectUpdatedEvent setBefore(Project before) {
        this.before = before;
        return this;
    }

    public Project getNow() {
        return now;
    }

    public ProjectUpdatedEvent setNow(Project now) {
        this.now = now;
        return this;
    }

    public User getOperator() {
        return operator;
    }

    public ProjectUpdatedEvent setOperator(User operator) {
        this.operator = operator;
        return this;
    }
}
