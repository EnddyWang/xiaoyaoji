package cn.xiaoyaoji.plugin.attachment;

import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.Handler;
import cn.xiaoyaoji.data.bean.Attach;
import cn.xiaoyaoji.data.bean.TableNames;
import cn.xiaoyaoji.event.DocDeletedEvent;
import cn.xiaoyaoji.integration.file.FileManager;
import cn.xiaoyaoji.listener.ApplicationListener;
import cn.xiaoyaoji.plugin.attachment.model.Attachment;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
public class DocDeletedListener implements ApplicationListener<DocDeletedEvent> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onEvent(DocDeletedEvent event) {
        String docId = event.getDoc().getId();
        //删除附件doc
        List<Attachment> attaches = getAttachsByRelatedId(docId);
        for (Attachment attach : attaches) {
            try {
                FileManager.getFileProvider().delete(attach.getUrl());
                DataFactory.instance().delete("plugin_attachment", attach.getId());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    public List<Attachment> getAttachsByRelatedId(final String relatedId) {
        return DataFactory.instance().process((connection, qr) -> qr.query(connection, "select * from plugin_attachment where relatedId=? order by sort asc ,createtime asc", new BeanListHandler<>(Attachment.class), relatedId));
    }

}
