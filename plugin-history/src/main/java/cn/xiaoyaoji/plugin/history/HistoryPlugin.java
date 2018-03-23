package cn.xiaoyaoji.plugin.history;

import cn.xiaoyaoji.core.plugin.doc.DocEvPlugin;
import cn.xiaoyaoji.core.util.ResultUtils;
import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.bean.TableNames;
import cn.xiaoyaoji.event.ApplicationEventMulticaster;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class HistoryPlugin extends DocEvPlugin {
    private String TABLE_NAME = "plugin_history";

    @Override
    public void init() {
        if (!DataFactory.instance().checkTableExists(TABLE_NAME)) {
            //创建表
            String sql = "CREATE TABLE `doc_history` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` varchar(200) DEFAULT NULL,\n" +
                    "  `sort` int(11) DEFAULT '100',\n" +
                    "  `type` varchar(100) DEFAULT NULL,\n" +
                    "  `content` longtext,\n" +
                    "  `createTime` datetime DEFAULT NULL,\n" +
                    "  `parentId` char(12) DEFAULT NULL,\n" +
                    "  `projectId` char(12) DEFAULT NULL,\n" +
                    "  `comment` varchar(1000) DEFAULT NULL,\n" +
                    "  `userId` char(12) DEFAULT NULL,\n" +
                    "  `docId` char(12) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `idx_doc_history_projectId_docId` (`docId`,`projectId`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4;";
            DataFactory.instance().createTable(sql);
        }
        ApplicationEventMulticaster.instance().registerEvent(new DocCreatedListener());
        ApplicationEventMulticaster.instance().registerEvent(new DocDeletedListener());
        ApplicationEventMulticaster.instance().registerEvent(new DocUpdatedListener());
    }

    @Override
    public Object httpRequest(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("get".equals(path)) {
            String docId = request.getParameter("docId");
            return ResultUtils.list(DataFactory.instance().process((connection, qr) -> {
                return qr.query(connection, "select h.*,u.nickname userName from " + TABLE_NAME + " h\n" +
                        "left join " + TableNames.USER + " u on u.id = h.userId \n" +
                        "where h.docId=?\n" +
                        "order by createTime desc limit 20 ", new BeanListHandler<>(DocHistory.class), docId);
            }));
        }
        return null;
    }
}
