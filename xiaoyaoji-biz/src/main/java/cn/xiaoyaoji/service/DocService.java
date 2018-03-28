package cn.xiaoyaoji.service;

import cn.xiaoyaoji.core.common.DocType;
import cn.xiaoyaoji.core.common.Message;
import cn.xiaoyaoji.core.util.AssertUtils;
import cn.xiaoyaoji.core.util.ResultUtils;
import cn.xiaoyaoji.core.util.StringUtils;
import cn.xiaoyaoji.data.DataFactory;
import cn.xiaoyaoji.data.bean.Doc;
import cn.xiaoyaoji.event.ApplicationEventMulticaster;
import cn.xiaoyaoji.event.DocDeletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author: zhoujingjie
 * @Date: 17/4/16
 */
public class DocService {
    private static Logger logger = LoggerFactory.getLogger(DocService.class);
    private static DocService service;

    private DocService() {
    }

    static {
        service = new DocService();
    }

    public static DocService instance() {
        return service;
    }


    public Doc getDoc(String id) {
        return DataFactory.instance().getById(Doc.class, id);
    }


    // 创建默认分类
    public Doc createDefaultDoc(String projectId) {
        Doc doc = new Doc();
        doc.setId(StringUtils.id());
        doc.setName("默认文档");
        doc.setCreateTime(new Date());
        doc.setSort(0);
        doc.setProjectId(projectId);
        doc.setLastUpdateTime(new Date());
        doc.setParentId("0");
        doc.setType(DocType.SYS_DOC_MD.getTypeName());
        int rs = ServiceFactory.instance().create(doc);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        return doc;
    }

    public List<Doc> searchDocs(String text, String projectId) {
        return ResultUtils.list(DataFactory.instance().searchDocs(text, projectId));
    }

    public String getFirstDocId(String projectId) {
        return DataFactory.instance().getFirstDocId(projectId);
    }


    /**
     * 获取属性菜单的文档
     *
     * @param projectId 项目id
     * @return docs
     */
    public List<Doc> getProjectDocs(String projectId) {
        // 获取该项目下所有接口
        List<Doc> docs = ResultUtils.list(ServiceFactory.instance().getDocsByProjectId(projectId));
        return treeDocs(docs);
    }

    public List<Doc> getProjectDocs(String projectId, boolean full) {
        // 获取该项目下所有接口
        List<Doc> docs = ResultUtils.list(ServiceFactory.instance().getDocsByProjectId(projectId, full));
        return treeDocs(docs);
    }

    private List<Doc> treeDocs(List<Doc> docs) {
        Map<String, List<Doc>> docMap = new LinkedHashMap<>();
        //root
        docMap.put("0", new ArrayList<Doc>());
        //
        for (Doc doc : docs) {
            docMap.put(doc.getId(), doc.getChildren());
        }
        for (Doc doc : docs) {
            List<Doc> temp = docMap.get(doc.getParentId());
            if (temp != null) {
                temp.add(doc);
            }
        }
        return docMap.get("0");
    }


    public void getDocIdsByParentId(Set<String> ids, String parentId) {
        List<String> temp = ResultUtils.list(DataFactory.instance().getDocIdsByParentId(parentId));
        ids.addAll(temp);
        for (String id : temp) {
            getDocIdsByParentId(ids, id);
        }
    }

    public int deleteDoc(String id) {
        //需要优化
        Set<String> ids = new HashSet<>();
        getDocIdsByParentId(ids, id);
        ids.add(id);
        //删除数据
        int rs = deleteByIds(new ArrayList<>(ids));
        for (String temp : ids) {
            Doc doc = new Doc();
            doc.setId(temp);
            ApplicationEventMulticaster.instance().multicastEvent(new DocDeletedEvent(doc, null));
        }
        return rs;
    }

    private int deleteByIds(List<String> ids) {
        return DataFactory.instance().deleteByIds(Doc.class, ids);
    }

    public int copyDoc(String docId, String toProjectId) {
        return DataFactory.instance().copyDoc(docId, toProjectId);
    }

    public List<Doc> getDocsByParentId(String projectId, String parentId) {
        return ResultUtils.list(DataFactory.instance().getDocsByParentId(projectId, parentId));
    }
}
