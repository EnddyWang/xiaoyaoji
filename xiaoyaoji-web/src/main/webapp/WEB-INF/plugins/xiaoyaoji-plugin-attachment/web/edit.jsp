<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${ctx}/plugin/web/${pluginInfo.id}/attach.css?v=${pluginInfo.version}"/>
<div id="plugin-attachment" v-cloak>
    <div class="doc-http-attach">
        <input v-on:change="fileUpload" multiple type="file">
        <p>点击、拖拽可上传文件。单文件不能超过1M</p>
    </div>
    <br/>
    <div class="cb" v-if="attachments && attachments.length>0">
        <div class="doc-attach" v-for="item in attachments" v-bind:class="{'file':item.type=='FILE'}">
            <i class="iconfont icon-close" v-on:click="deleteFile(item)"></i>
            <a :href="fileAccess+item.url" v-if="item.type=='FILE'"
               target="_blank">{{item.fileName}}</a>
            <img v-if="item.type =='IMG'" v-bind:src="fileAccess+item.url"
                 :onclick="'window.open(\''+fileAccess+item.url+'\')'">
        </div>
    </div>
</div>
<script>xyj.curentPlugin = {id: '${pluginInfo.id}'}</script>
<script src="${ctx}/plugin/web/${pluginInfo.id}/edit.js?v=${pluginInfo.version}"></script>
