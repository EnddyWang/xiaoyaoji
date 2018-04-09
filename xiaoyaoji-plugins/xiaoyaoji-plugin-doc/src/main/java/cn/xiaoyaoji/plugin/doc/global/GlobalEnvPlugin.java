package cn.xiaoyaoji.plugin.doc.global;

import cn.xiaoyaoji.core.plugin.ProjectGlobalPlugin;
import cn.xiaoyaoji.core.util.JsonUtils;
import cn.xiaoyaoji.data.DataFactory;
import org.apache.commons.dbutils.handlers.BeanHandler;

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
 * <p>
 * 环境变量
 *
 * @author zhoujingjie
 * Date 2018-04-04
 */
public class GlobalEnvPlugin extends ProjectGlobalPlugin {
    private String tableName = "plugin_global_env";

    @Override
    public void init() {
        if (!DataFactory.instance().checkTableExists(tableName)) {
            createTable();
        }
    }

    private void createTable() {
        DataFactory.instance().process((connection, qr) -> {
            return qr.update(connection, "CREATE TABLE " + tableName + " (\n" +
                    "`id`  int NOT NULL AUTO_INCREMENT ,\n" +
                    "`projectId`  char(12) CHARACTER SET utf8 NULL ,\n" +
                    "`content`  varchar(10000) CHARACTER SET utf8 NULL ,\n" +
                    "PRIMARY KEY (`id`),\n" +
                    "INDEX `projectId` (`projectId`) USING BTREE \n" +
                    ")\n" +
                    ";\n" +
                    "\n");
        });
    }

    private GlobalEnv getByProjectId(String projectId) {
        return DataFactory.instance().process((connection, qr) -> {
            return qr.query(connection, "select id,projectId,content from " + tableName + " where projectId=?", new BeanHandler<>(GlobalEnv.class), projectId);
        });
    }

    @Override
    public Object httpRequest(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if ("/get".equals(path)) {
            String projectId = request.getParameter("projectId");
            return getByProjectId(projectId);
        } else if ("/update".equals(path)) {
            String content = request.getParameter("content");
            String projectId = request.getParameter("projectId");
            DataFactory.instance().process((connection, qr) -> qr.update(connection, "insert into " + tableName + " (projectId,content) values(?,?) on duplicate key update content=?", projectId, content, content));
            return true;
        }
        throw new UnsupportedOperationException("不支持该地址" + path);
    }

    @Override
    public String getJsonData(String projectId) {
        return JsonUtils.toString(getByProjectId(projectId));
    }

    @Override
    public String getEditPage() {
        return "global-env/edit.jsp";
    }

    @Override
    public String getViewPage() {
        return "global-env/view.jsp";
    }
}
