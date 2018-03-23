<%--
  User: zhoujingjie
  Date: 2018-03-23
  Time: 17:57
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${!isXHR}">
    <jsp:include page="doc-header.jsp">
        <jsp:param name="event" value="${edit?'docEdit':'docView'}"/>
    </jsp:include>
    <jsp:include page="doc-sidebar.jsp"/>
    <jsp:include page="doc-left.jsp">
        <jsp:param name="view" value="true"/>
    </jsp:include>
    <div class="doc doc-content">
    <script>
        window.xyj = window.xyj || {};
        xyj.page = {
            event: "doc.edit",
            pageType: '${doc.type}',
            docId: '${doc.id}',
            projectId: '${project.id}',
            projectName: '${project.name}',
            editMode: false
        };
    </script>
    <div class="hide" id="loading">
        <div class="spinner">
            <div class="double-bounce1"></div>
            <div class="double-bounce2"></div>
        </div>
    </div>

    <div class="doc-full-width" id="doc-content">
    <div id="pandel_default" v-show="activePanelId == 'default'">
</c:if>
<c:if test="${editProjectGlobal}">
    <jsp:include page="../project/global/project-global.jsp"/>
</c:if>
<c:if test="${!editProjectGlobal && doc!=null }">
    <c:if test="${pluginInfo == null}">
        <jsp:include page="/WEB-INF/includes/doc-type-not-support.jsp"/>
    </c:if>
    <c:if test="${pluginInfo != null}">
        <c:if test="${edit}">
            <jsp:include page="/WEB-INF/plugins/${pluginInfo.runtimeFolder}/web/${pluginInfo.plugin.editPage}"/>
        </c:if>
        <c:if test="${edit == null}">
            <jsp:include page="/WEB-INF/plugins/${pluginInfo.runtimeFolder}/web/${pluginInfo.plugin.viewPage}"/>
        </c:if>
    </c:if>
</c:if>
<!-- loading start -->
</div>

<c:forEach items="${docExtPluginInfos}" var="item">
    <div id="panel_${item.id}" v-cloak v-show="activePanelId == '${item.id}'"></div>
</c:forEach>


<script>
    if (!window.requirejs) {
        location.reload();
    }
</script>
<c:if test="${!isXHR}">
    </div>
    </div>
    <%--</div>--%>
    <!-- loading end -->
    <script>
        window.xyj = window.xyj || {};
        xyj.page = {
            event: "docView",
            pageType: '${doc.type}',
            docId: '${doc.id}',
            projectId: '${project.id}',
            projectName: '${project.name}',
            editMode: false
        };
    </script>

    </body>
    </html>
</c:if>