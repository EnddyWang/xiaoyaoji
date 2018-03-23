package cn.xiaoyaoji.plugin.attachment;

import cn.xiaoyaoji.core.common.Result;
import cn.xiaoyaoji.core.plugin.Plugin;
import cn.xiaoyaoji.core.plugin.doc.DocEvPlugin;
import cn.xiaoyaoji.core.util.AssertUtils;
import cn.xiaoyaoji.core.util.StringUtils;
import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.event.ApplicationEventMulticaster;
import cn.xiaoyaoji.extension.file.FileUtils;
import cn.xiaoyaoji.extension.file.MetaData;
import cn.xiaoyaoji.plugin.attachment.model.Attachment;
import cn.xiaoyaoji.service.ServiceFactory;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
 * Date 2018-01-27
 */
public class AttachmentPlugin extends DocEvPlugin {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String tableName = "plugin_attachment";
    /**
     * 排除的contentType
     */

    private static Set<String> excludes;

    @Override
    public void init() {
        ApplicationEventMulticaster.instance().registerEvent(new DocDeletedListener());
        //数据库初始化
        Map<String, String> config = getPluginInfo().getConfig();
        tableName = config.getOrDefault("tableName", "plugin_attachment");
        excludes = new HashSet<>(Arrays.asList(config.getOrDefault("excludeContentTypes", "").split(",")));
        if (!DataFactory.instance().checkTableExists(tableName)) {
            logger.info("即将初始化{}", getPluginInfo().getId());
            //创建表
            String sql = "CREATE TABLE " + tableName + " (\n" +
                    "  `id` char(12) NOT NULL,\n" +
                    "  `url` varchar(1000) DEFAULT NULL,\n" +
                    "  `type` varchar(45) DEFAULT NULL,\n" +
                    "  `sort` int(11) DEFAULT NULL,\n" +
                    "  `relatedId` char(12) DEFAULT NULL,\n" +
                    "  `fileName` varchar(1000) DEFAULT NULL,\n" +
                    "  `createTime` datetime DEFAULT NULL,\n" +
                    "  `projectId` char(12) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `normal` (`relatedId`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
            DataFactory.instance().createTable(sql);

            //迁移之前的表
            DataFactory.instance().process(((connection, qr) -> qr.update(connection, "insert into " + tableName + " select * from attach")));

            //删除之前的表
            logger.info("初始化{}完成,请手动删除attach表", getPluginInfo().getId());
        }
    }

    /**
     * http request
     *
     * @param path     请求地址
     * @param request  request
     * @param response response
     */
    @Override
    public Object httpRequest(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        switch (path) {
            case "/get":
                return getAttachment(request, response);
            case "/upload":
                return upload(request, response);
            case "/delete":
                delete(request.getParameter("id"));
                return true;
            default:
                break;
        }
        return null;
    }

    private List<Attachment> getAttachment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String relatedId = request.getParameter("relatedId");
        return getAttachsByRelatedId(relatedId);
    }

    private Object upload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String projectId = request.getParameter("projectId");
        String relateId = request.getParameter("relateId");

        MultipartHttpServletRequest mhr = (MultipartHttpServletRequest) request;
        AssertUtils.notNull(mhr, "上传文件方式错误");
        List<MultipartFile> files = mhr.getFiles("files");
        AssertUtils.isTrue(files != null && files.size() > 0, "请上传文件");
        for (MultipartFile file : files) {
            String contentType = file.getContentType();
            //判断类型
            for (String exclude : excludes) {
                if (exclude.matches(contentType)) {
                    return new Result<>(false, "不允许上传该文件类型:" + exclude);
                }
            }
        }
        for (MultipartFile file : files) {
            MetaData md = FileUtils.upload(file);
            String path = md.getPath();
            Attachment temp = new Attachment();
            temp.setId(StringUtils.id());
            temp.setUrl(path);
            temp.setType(md.getType().name());
            temp.setSort(10);
            temp.setCreateTime(new Date());
            temp.setRelatedId(relateId);
            temp.setFileName(StringEscapeUtils.escapeHtml4(file.getOriginalFilename()));
            temp.setProjectId(projectId);
            ServiceFactory.instance().create(temp);
        }
        return true;
    }


    public List<Attachment> getAttachsByRelatedId(final String relatedId) {
        return DataFactory.instance().process((connection, qr) -> {
            return qr.query(connection, "select * from " + tableName + " where relatedId=? order by sort asc ,createtime asc", new BeanListHandler<>(Attachment.class), relatedId);
        });
    }

    private Attachment get(String attachmentId) {
        return DataFactory.instance().getById(Attachment.class, attachmentId);
    }

    public void delete(String id) {
        Attachment temp = get(id);
        if (temp != null) {
            try {
                FileUtils.delete(temp.getUrl());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        DataFactory.instance().delete(tableName, id);
    }

}
