<%--
  User: zhoujingjie
  Date: 17/4/12
  Time: 22:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${!isXHR}">
    <jsp:include page="doc-header.jsp"/>
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


</c:if>

<c:if test="${editProjectGlobal}">
    <jsp:include page="../project/global/project-global.jsp"/>
</c:if>
<c:if test="${!editProjectGlobal && doc!=null }">
    <c:if test="${pluginInfo == null}">
        <jsp:include page="/WEB-INF/includes/doc-type-not-support.jsp"/>
    </c:if>
    <c:if test="${pluginInfo != null}">
        <jsp:include page="/WEB-INF/plugins/${pluginInfo.runtimeFolder}/web/${pluginInfo.plugin.viewPage}"/>
    </c:if>
</c:if>
<!-- loading start -->


<script>
    if (!window.requirejs) {
        location.reload();
    }
</script>
<!-- 加载插件${plugins} -->
<jsp:include page="/WEB-INF/content/plugin/load.jsp">
    <jsp:param name="event" value="docView"/>
    <jsp:param name="docType" value="${doc.type}"/>
</jsp:include>
<c:forEach items="${plugins}" var="plugin">
    <c:set scope="request" var="currentPluginInfo" value="${plugin}"/>
    <jsp:include page="/WEB-INF/plugins/${plugin.runtimeFolder}/web/view.jsp"/>
</c:forEach>


<c:if test="${!isXHR}">
    </div>
    </div>
    <%--</div>--%>
    <!-- loading end -->

    <%--加载插件--%>
    <%--触发插件--%>
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