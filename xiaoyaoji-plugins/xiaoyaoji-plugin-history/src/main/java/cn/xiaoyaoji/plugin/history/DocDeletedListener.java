package cn.xiaoyaoji.plugin.history;

import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.event.DocCreatedEvent;
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
 * Date 2018-03-19
 */
public class DocDeletedListener implements ApplicationListener<DocCreatedEvent> {

    @Override
    public void onEvent(DocCreatedEvent event) {
        String docId = event.getDoc().getId();
        DataFactory.instance().process((connection, qr) -> qr.update(connection, "delete from plugin_history where docId=?", docId));
    }
}
