package cn.xiaoyaoji.controller;

import cn.xiaoyaoji.core.annotations.Ignore;
import cn.xiaoyaoji.core.common.DocType;
import cn.xiaoyaoji.core.common.Message;
import cn.xiaoyaoji.core.common._HashMap;
import cn.xiaoyaoji.core.plugin.*;
import cn.xiaoyaoji.core.util.AssertUtils;
import cn.xiaoyaoji.core.util.StringUtils;
import cn.xiaoyaoji.data.bean.Doc;
import cn.xiaoyaoji.data.bean.Project;
import cn.xiaoyaoji.data.bean.User;
import cn.xiaoyaoji.event.ApplicationEventMulticaster;
import cn.xiaoyaoji.event.DocCreatedEvent;
import cn.xiaoyaoji.event.DocUpdatedEvent;
import cn.xiaoyaoji.service.DocService;
import cn.xiaoyaoji.service.ProjectService;
import cn.xiaoyaoji.service.ServiceFactory;
import cn.xiaoyaoji.service.ServiceTool;
import cn.xiaoyaoji.core.plugin.PluginInfo;
import cn.xiaoyaoji.core.plugin.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: zhoujingjie
 * @Date: 16/7/13
 */
@RestController
@RequestMapping(value = {"/doc"})
public class DocController {
    private static Logger logger = LoggerFactory.getLogger(DocController.class);

    /**
     * 新增
     *
     * @return
     */
    @PostMapping
    public String createDoc(User user, Doc doc) {
        AssertUtils.isTrue(ServiceFactory.instance().checkUserHasProjectEditPermission(user.getId(), doc.getProjectId()), "无操作权限");
        if (org.apache.commons.lang3.StringUtils.isBlank(doc.getParentId())) {
            doc.setParentId("0");
        }
        doc.setId(StringUtils.id());
        if (org.apache.commons.lang3.StringUtils.isBlank(doc.getName())) {
            doc.setName("默认文档");
        }
        //默认markdown
        if (org.apache.commons.lang3.StringUtils.isBlank(doc.getType())) {
            doc.setType(DocType.SYS_DOC_MD.getTypeName());
        }
        doc.setLastUpdateTime(new Date());
        doc.setCreateTime(new Date());
        AssertUtils.notNull(doc.getProjectId(), "missing projectId");
        AssertUtils.notNull(doc.getParentId(), "missing parentId");
        int rs = ServiceFactory.instance().create(doc);
        AssertUtils.isTrue(rs > 0, "增加失败");
        ApplicationEventMulticaster.instance().multicastEvent(new DocCreatedEvent(doc, user));
        return doc.getId();
    }


    /**
     * 更新
     *
     * @param id id
     * @return
     */
    @PostMapping("{id}")
    public int update(@PathVariable("id") String id, Doc doc, User user, String comment) {
        AssertUtils.notNull(id, "missing id");
        Doc before = ServiceFactory.instance().getById(id, Doc.class);
        AssertUtils.notNull(before, "文档不存在或已删除");

        before.setId(null);
        doc.setId(id);
        AssertUtils.isTrue(ServiceFactory.instance().checkUserHasProjectEditPermission(user.getId(), before.getProjectId()), "无操作权限");
        doc.setLastUpdateTime(new Date());
        doc.setCreateTime(null);
        if (org.apache.commons.lang3.StringUtils.isBlank(doc.getName())) {
            doc.setName(before.getName());
        }
        int rs = ServiceFactory.instance().update(doc);
        AssertUtils.isTrue(rs > 0, "修改失败");
        ApplicationEventMulticaster.instance().multicastEvent(new DocUpdatedEvent(doc,before, user, comment));
        ProjectService.instance().updateLastUpdateTime(before.getProjectId());

        return rs;
    }


    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public int delete(@PathVariable("id") String id, User user) {
        AssertUtils.notNull(id, "missing id");
        Doc temp = ServiceFactory.instance().getById(id, Doc.class);
        AssertUtils.notNull(temp, "接口不存在或已删除");
        AssertUtils.isTrue(ServiceFactory.instance().checkUserHasProjectEditPermission(user.getId(), temp.getProjectId()), "无操作权限");
        int rs = DocService.instance().deleteDoc(id);
        AssertUtils.isTrue(rs > 0, "删除失败");
        ApplicationEventMulticaster.instance().multicastEvent(new DocCreatedEvent(temp, user));
        return rs;
    }

    @PostMapping("sort")
    public Object sort(@RequestParam("id") String id,
                       @RequestParam("parentId") String parentId,
                       @RequestParam("sorts") String sorts
    ) {
        AssertUtils.notNull(id, "参数为空");
        AssertUtils.notNull(parentId, "参数为空");
        AssertUtils.notNull(sorts, "参数为空");
        //更新parentId
        Doc doc = new Doc();
        doc.setId(id);
        doc.setParentId(parentId);
        ServiceFactory.instance().update(doc);

        String[] idsorts = sorts.split(",");
        int rs = ServiceFactory.instance().updateDocSorts(idsorts);
        AssertUtils.notNull(rs > 0, Message.OPER_ERR);
        return true;
    }


    /**
     * 预览文档
     *
     * @param docId
     * @param docHistoryId
     * @param user
     * @return
     */
    @GetMapping("{docId}")
    @Ignore
    public ModelAndView docView(@PathVariable String docId,
                                @RequestParam(value = "docHistoryId", required = false) String docHistoryId, User user,
                                HttpServletRequest request,
                                boolean editing
    ) {

        AssertUtils.notNull(docId, "参数丢失");
        Doc doc = null;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(docHistoryId)) {
            AssertUtils.isTrue(doc.getId().equals(docId), "数据无效");
        } else {
            doc = DocService.instance().getDoc(docId);
        }
        Map<Event, List<PluginInfo>> map = ProjectPluginManager.getInstance().getProjectPluginInfoMap(doc.getProjectId());
        map.putAll(PluginManager.getInstance().getPluginInfos());

        AssertUtils.notNull(doc, "文档不可见或已删除");
        //获取project
        Project project = ProjectService.instance().getProject(doc.getProjectId());
        AssertUtils.notNull(project, "项目不存在或者无访问权限");
        AssertUtils.isTrue(Project.Status.VALID.equals(project.getStatus()), "项目状态无效");
        if (org.apache.commons.lang3.StringUtils.isBlank(doc.getType())) {
            doc.setType(DocType.SYS_DOC_RICH_TEXT.getTypeName());
        }

        ServiceTool.checkUserHasAccessPermission(project, user);

        boolean editPermission = false;
        if (user != null) {
            //访问权限
            editPermission = ServiceFactory.instance().checkUserHasProjectEditPermission(user.getId(), doc.getProjectId());
            //AssertUtils.isTrue(editPermission || (!editPermission && !editing),"无操作权限");
        }

        List<PluginInfo> pluginInfos = ProjectPluginManager.getInstance().getPluginInfos(doc.getProjectId(), Event.doc);
        PluginInfo pluginInfo = null;
        for (PluginInfo info : pluginInfos) {
            if (doc.getType().equals(info.getId())) {
                pluginInfo = info;
                break;
            }
        }
        boolean isXHR = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        return new ModelAndView("/doc/doc")
                .addObject("project", project)
                .addObject("doc", doc)
                .addObject("user", user)
                .addObject("editPermission", editPermission)
                .addObject("projectGlobal", ProjectService.instance().getProjectGlobal(doc.getProjectId()))
                .addObject("pluginInfo", pluginInfo)
                .addObject("isXHR", isXHR)
                ;
    }

    /**
     * 编辑文档
     *
     * @param docId
     * @param docHistoryId
     * @param user
     * @return
     */
    @GetMapping("{docId}/edit")
    public ModelAndView docEdit(@PathVariable String docId,
                                @RequestParam(value = "docHistoryId", required = false) String docHistoryId, User user, HttpServletRequest request) {
        ModelAndView view = docView(docId, docHistoryId, user, request, true)
                .addObject("edit", true);
        return view;
    }

    @Ignore
    @GetMapping("/search")
    public Object search(@RequestParam String text, @RequestParam("projectId") String projectId, User user) {
        ServiceTool.checkUserHasAccessPermission(projectId, user);
        List<Doc> docs = DocService.instance().searchDocs(text, projectId);
        return new _HashMap<>()
                .add("docs", docs);
    }

    /**
     * @param projectId
     * @param user
     * @return
     */
    @GetMapping("/list/{projectId}")
    public Object getDocs(@PathVariable("projectId") String projectId, User user) {
        ServiceTool.checkUserHasAccessPermission(projectId, user);
        return DocService.instance().getProjectDocs(projectId);
    }

    /**
     * 复制文档
     *
     * @param projectId 项目id
     * @param docId     文档id
     * @param user
     * @return rs
     */
    @PostMapping("/copy")
    public Object copy(@RequestParam("projectId") String projectId,
                       @RequestParam(value = "toProjectId", required = false) String toProjectId,
                       @RequestParam("docId") String docId, User user) {
        ServiceTool.checkUserHasEditPermission(projectId, user);
        if (org.apache.commons.lang3.StringUtils.isBlank(toProjectId) || projectId.equals(toProjectId)) {
            toProjectId = null;
        } else {
            ServiceTool.checkUserHasEditPermission(toProjectId, user);
        }
        int rs = DocService.instance().copyDoc(docId, toProjectId);
        AssertUtils.isTrue(rs > 0, "操作失败");
        return rs;
    }

    /**
     * 查询顶级doc
     *
     * @param projectId
     * @param user
     * @return
     */
    @GetMapping("/root/{projectId}")
    public Object getRootDocs(@PathVariable("projectId") String projectId, User user) {
        ServiceTool.checkUserHasAccessPermission(projectId, user);
        List<Doc> docs = DocService.instance().getDocsByParentId(projectId, "0");
        return new _HashMap<>()
                .add("docs", docs);
    }
}
