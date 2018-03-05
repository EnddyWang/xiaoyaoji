<%@ page import="cn.com.xiaoyaoji.core.plugin.PluginManager" %>
<%@ page import="cn.com.xiaoyaoji.core.plugin.Event" %>
<%@ page import="cn.com.xiaoyaoji.core.plugin.PluginInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
  User: zhoujingjie
  Date: 2018-03-05
  Time: 14:55
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String event = request.getParameter("event");
    String docType = request.getParameter("docType");
    Event e = Event.parse(event);
    if(e != null) {
        List<PluginInfo> plugins = PluginManager.getInstance().getPlugins(e);
        List<PluginInfo> list = new ArrayList<PluginInfo>();
        for(PluginInfo item:plugins){
            if(item.getSupportPageTypes().contains(docType)){
                list.add(item);
            }
        }
        request.setAttribute("plugins",list);
    }
%>