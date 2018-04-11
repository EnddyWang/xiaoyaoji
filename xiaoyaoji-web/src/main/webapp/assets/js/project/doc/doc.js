(function () {
    require(['vue','utils'], function (Vue,utils) {
        //传递消息给sidebar.js 
        function pushMessage(method, args) {
            window.postMessage({type: 'event', method: method, args: args}, '*')
        }

        var app = new Vue({
            el: '#xd-header',
            data: {
                activePanelId: 'default'
            },
            methods: {
                sidebar: function (method) {
                    var newArgs = [];
                    if (arguments.length > 1) {
                        for (var i = 1; i < arguments.length; i++) {
                            newArgs[i - 1] = arguments[i];
                        }
                    }
                    pushMessage(method, newArgs);
                },
                loadGlobalPlugin:function(pluginId){
                    var dom = document.getElementById('g-'+pluginId);
                    if(!dom){
                        return;
                    }
                    var $panel = $(dom);
                    /*if($panel.attr('data-loaded')){
                        return;
                    }*/
                    UIkit.modal(dom).show();
                    var page = xyj.page.editMode?'/doc/ep/':'/doc/vp/';
                    $.ajax({
                        url: '/plugin'+page+pluginId+'/',type:'get',
                        complete:function(){
                        },
                        success:function(content){
                            $panel.attr('data-loaded','1');
                            document.getElementById('g-'+pluginId+'-body').innerHTML=content;
                        }
                    });
                },
                docExtClick: function (pluginId, editMode) {
                    $('.doc-panel').hide();
                    var id = 'panel_'+pluginId;
                    var dom = document.getElementById(id);
                    var $panel = $(dom);
                    $panel.show();
                    if($panel.data('loaded') || pluginId ==='default'){
                        return;
                    }
                    $("#loading").show();
                    var page = editMode?'/doc/ep/':'/doc/vp/';
                    $.ajax({
                        url: '/plugin'+page+pluginId+'/',type:'get',
                        complete:function(){
                            $("#loading").hide();
                        },
                        success:function(content){
                            $panel.attr('data-loaded','1').html(content);
                        }
                    });
                }
            }
        })
    })
})();