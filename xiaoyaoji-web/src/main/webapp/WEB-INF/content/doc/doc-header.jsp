<%@ page import="cn.xiaoyaoji.core.plugin.Event" %>
<%@ page import="cn.xiaoyaoji.core.plugin.PluginInfo" %>
<%@ page import="cn.xiaoyaoji.core.plugin.ProjectPluginManager" %>
<%@ page import="cn.xiaoyaoji.data.bean.Project" %>
<%@ page import="java.util.List" %>
<%@ page import="cn.xiaoyaoji.core.plugin.ProjectGlobalPlugin" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  User: zhoujingjie
  Date: 17/4/27
  Time: 22:06
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String e = request.getParameter("event");
    Project project = (Project) request.getAttribute("project");
    List<PluginInfo> list = ProjectPluginManager.getInstance().getPluginInfos(project.getId(), Event.parse(e));
    request.setAttribute("docExtPluginInfos", list);


    //加载全局插件
    List<PluginInfo<ProjectGlobalPlugin>> infos = ProjectPluginManager.getInstance().getProjectGlobalPluginInfos(project.getId());
    Map<PluginInfo<ProjectGlobalPlugin>, String> data = new HashMap<>();
    for (PluginInfo<ProjectGlobalPlugin> item : infos) {
        data.put(item, item.getPlugin().getJsonData(project.getId()));
    }
    request.setAttribute("projectGlobalData", data);
%>


<!DOCTYPE html>
<html lang="zh-Hans">
<head>
    <title>${doc.name}-${project.name}</title>
    <link rel="stylesheet" href="${assets}/css/doc.css?v=${v}"/>
    <link rel="stylesheet" href="${assets}/css/icons.css?v=${v}"/>
    <jsp:include page="/WEB-INF/includes/meta.jsp"/>
    <jsp:include page="/WEB-INF/includes/js.jsp"/>
</head>
<body>
<div class="xd-header cb" id="xd-header">
    <c:if test="${sessionScope.user != null && editPermission}">
        <div class="fl">
            <ul class="x-ul horiz">
                <li>
                    <div class="x-li"><a>全局设置</a></div>
                    <div class="x-sub-ul">
                        <ul>
                            <c:forEach items="${projectGlobalData}" var="item">
                                <li><div class="x-li"><a>${item.key.name}</a></div></li>
                            </c:forEach>
                            <li v-on:click="sidebar('loadGlobal','http')">
                                <div class="x-li"><a>全局参数</a></div>
                            </li>
                            <li v-on:click="sidebar('loadGlobal','env')">
                                <div class="x-li"><a>环境变量</a></div>
                            </li>
                            <li v-on:click="sidebar('loadGlobal','status')">
                                <div class="x-li"><a>全局状态</a></div>
                            </li>
                        </ul>
                    </div>
                </li>
                <li>
                    <div class="x-li"><a>项目设置</a></div>
                    <div class="x-sub-ul">
                        <ul>
                            <li v-on:click="sidebar('loadShares')">
                                <div class="x-li"><a>项目分享</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/project/${project.id}/info">项目信息</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/project/${project.id}/transfer">项目转让</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/project/${project.id}/member">项目成员</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/project/${project.id}/export">导出项目</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/project/${project.id}/quit">退出项目</a></div>
                            </li>
                        </ul>
                    </div>
                </li>
                <c:if test="${!edit && editPermission}">
                    <li v-on:click="sidebar('editpage')">
                        <div class="x-li"><a><i class="iconfont icon-edit1"></i>编辑项目</a></div>
                    </li>
                </c:if>
                <c:if test="${docId != null && edit}">
                    <li v-on:click="sidebar('viewpage')">
                        <div class="x-li"><a><i class="iconfont icon-eye"></i>预览项目</a></div>
                    </li>
                    <li uk-toggle="target: #save-modal">
                        <div class="x-li"><a style="color: #1e87f0"><i class="iconfont icon-save"></i>保存</a></div>
                    </li>
                </c:if>
                <li>
                    <div class="x-li">|</div>
                </li>
                <li v-on:click="docExtClick('default','${edit}')">
                    <div class="x-li"><a style="color: #1e87f0">文档${docExtPluginInfos.size()}</a></div>
                </li>

                <c:forEach items="${docExtPluginInfos}" var="item">
                    <li v-on:click="docExtClick('${item.id}','${edit}')">
                        <div class="x-li"><a>${item.name}</a></div>
                    </li>
                </c:forEach>
                    <%--<li v-on:click="sidebar('loadHistory')">
                        <div class="x-li"><a>历史记录</a></div>
                    </li>
                    <li>
                        <div class="x-li"><a><i class="iconfont icon-edit1"></i>测试</a></div>
                    </li>
                    <li>
                        <div class="x-li"><a><i class="iconfont icon-edit1"></i>Mock</a></div>
                    </li>--%>
            </ul>
        </div>
    </c:if>
    <div class="fr">
        <ul class="x-ul horiz">
            <li>
                <div class="x-li"><a href="${ctx}/">主页</a></div>
            </li>
            <li>
                <div class="x-li"><a href="${ctx}/dashboard">控制台</a></div>
            </li>
            <li>
                <div class="x-li"><a href="http://www.xiaoyaoji.cn/donate" target="_blank">赞助作者</a></div>
            </li>
            <c:if test="${sessionScope.user != null}">
                <li>
                    <div class="x-li"><a><img src="${sessionScope.user.avatar}"
                                              class="user-account-logo">&nbsp;${sessionScope.user.nickname}</a></div>
                    <div class="x-sub-ul" style="right:0px;">
                        <ul>
                            <li>
                                <div class="x-li"><a href="${ctx}/profile">个人中心</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/profile/security">安全设置</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="${ctx}/help">帮助中心</a></div>
                            </li>
                            <li>
                                <div class="x-li"><a href="http://www.xiaoyaoji.cn/donate" target="_blank">请作者喝咖啡</a>
                                </div>
                            </li>
                            <li class="uk-nav-divider"></li>
                            <li>
                                <div class="x-li"><a href="${ctx}/logout">退出登录</a></div>
                            </li>
                        </ul>
                    </div>
                </li>
            </c:if>
            <c:if test="${sessionScope.user == null}">
                <li>
                    <div class="x-li"><a href="${ctx}/login">登录</a></div>
                </li>
                <li>
                    <div class="x-li"><a href="${ctx}/register">注册</a></div>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="xd-header-placeholder"></div>
<script src="${assets}/js/project/doc/doc.js?v=${v}"></script>
<%--<div class="doc">--%>
<script>
    (function () {
        window._isGlobal_ = '${editProjectGlobal}';
        window.xyj = window.xyj || {};
        xyj.page = xyj.page || {};
        $.extend(xyj.page, {
            event: "${edit?'docEdit':'docView'}",
            pageType: '${doc.type}',
            docId: '${doc.id}',
            projectId: '${project.id}',
            projectName: '${project.name}',
            editMode: ${edit?true:false}
        });
    })();
</script>
<script>
    <c:forEach items="${projectGlobalData}" var="item">
    <c:if test="${item.value}">
        (function(){
            xyj.page.global['${item.key.id}'] =${item.value};
        })();
    </c:if>
    </c:forEach>
</script>
<c:forEach items="${projectGlobalData}" var="item">
<div id="g-${item.key.id}" class="uk-modal-container" uk-modal v-cloak>
    <div class="uk-modal-dialog">
        <div class="uk-modal-header">
            <h2 class="uk-modal-title">${item.key.name}</h2>
            <button type="button" class="uk-modal-close-default uk-icon" v-on:click="closeGlobalModal">
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 14 14" ratio="1"><line fill="none" stroke="#000" stroke-width="1.1" x1="1" y1="1" x2="13" y2="13"></line><line fill="none" stroke="#000" stroke-width="1.1" x1="13" y1="1" x2="1" y2="13"></line></svg>
            </button>
        </div>
        <div class="uk-modal-body" id="g-${item.key.id}-body" uk-overflow-auto></div>
    </div>
</div>
</c:forEach>