<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2 class="uk-modal-title">历史版本</h2>
<div id="doc-history">
    <table class="uk-table">
        <thead>
        <tr>
            <th>版本号</th>
            <th>修改说明</th>
            <th>修改人</th>
            <th>修改时间</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="item in history">
            <td><a :href="historyURL(item.docId,'${edit}',item.id)">{{item.id}}</a></td>
            <td class="doc-history-comment" :title="item.comment">{{item.comment?item.comment:'无'}}</td>
            <td>{{item.userName}}</td>
            <td>{{item.createTime}}</td>
        </tr>
        </tbody>
    </table>
</div>
<script src="${ctx}/plugin/assets/${pluginInfo.id}/view.js"></script>