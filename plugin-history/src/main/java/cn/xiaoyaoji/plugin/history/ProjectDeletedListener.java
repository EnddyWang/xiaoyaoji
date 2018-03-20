package cn.xiaoyaoji.plugin.history;

import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.bean.TableNames;
import cn.xiaoyaoji.event.ProjectEvent;
import cn.xiaoyaoji.listener.ApplicationListener;

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
public class ProjectDeletedListener implements ApplicationListener<ProjectEvent> {
    @Override
    public void onEvent(ProjectEvent event) {
        String projectId = event.getProject().getId();
        //删除文档历史
        DataFactory.instance().process((connection, qr) -> qr.update(connection, "delete from plugin_history where projectid =?", projectId));
    }
}
