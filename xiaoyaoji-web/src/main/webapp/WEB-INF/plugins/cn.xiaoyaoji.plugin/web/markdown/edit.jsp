<%--
  User: zhoujingjie
  Date: 2017/8/30
  Time: 15:16
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${cdn}/assets/simplemd/simplemde.min.css?v=${pluginInfo.version}"/>
<style>
    .editor-preview-side ul{
        padding-left:30px;
    }
    .editor-preview-side ul li{
        list-style:disc;
    }
</style>
<textarea class="hide" id="md-edit">${doc.content}</textarea>
<script>
    (function(){
        requirejs(['${cdn}/assets/simplemd/simplemde.min.js?v=${pluginInfo.version}'],function(SimpleMDE){
            var md = new SimpleMDE({
                element: document.getElementById("md-edit"),
                spellChecker: false,
                showIcons: ["code", "table","heading-1","heading-2","heading-3"],
            });

            window.getDoc = function(){
                return {content:md.value()}
            }
        });
    })();
</script>