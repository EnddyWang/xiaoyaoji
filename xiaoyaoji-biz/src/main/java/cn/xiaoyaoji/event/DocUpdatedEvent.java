package cn.xiaoyaoji.event;

import cn.xiaoyaoji.data.bean.Doc;
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
 * Date 2018-03-19
 */
public class DocUpdatedEvent extends ApplicationEvent {
    private Doc now, before;
    private User operator;
    private String comment;

    public DocUpdatedEvent(Doc now, Doc before, User operator) {
        this.now = now;
        this.before = before;
        this.operator = operator;
    }

    public DocUpdatedEvent(Doc now, Doc before, User operator, String comment) {
        this.now = now;
        this.before = before;
        this.operator = operator;
        this.comment = comment;
    }

    public User getOperator() {
        return operator;
    }

    public DocUpdatedEvent setOperator(User operator) {
        this.operator = operator;
        return this;
    }

    public Doc getBefore() {
        return before;
    }

    public DocUpdatedEvent setBefore(Doc before) {
        this.before = before;
        return this;
    }

    public Doc getNow() {
        return now;
    }

    public void setNow(Doc now) {
        this.now = now;
    }

    public String getComment() {
        return comment;
    }

    public DocUpdatedEvent setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
