<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="${ctx}/proxy/${currentPluginInfo.id}/web/attach.css?v=${currentPluginInfo.version}"/>
<div id="plugin-attachment" v-cloak>
    <ul uk-tab>
        <li class="uk-active"><a>附件</a></li>
    </ul>
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
<script>xyj.curentPlugin={id:'${currentPluginInfo.id}'}</script>
<script src="${ctx}/proxy/${currentPluginInfo.id}/web/edit.js?v=${currentPluginInfo.version}"></script>
