(function () {
    require(['vue','utils'], function (Vue) {
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
                docExtClick: function (pluginId, editMode) {
                    this.activePanelId = pluginId;
                    var $panel = $('#panel_'+pluginId);
                    if($panel.data('loaded')){
                        return;
                    }
                    $("loading").show();
                    var page = editMode?'/editpage/':'/viewpage/';
                    utils.ajax({
                        url: '/plugin'+page+pluginId,type:'get',
                        complete:function(){
                            $("loading").hide();
                        },
                        success:function(content){
                            $('#panel_'+pluginId).attr('data-loaded','1').html(content);
                        }
                    });
                    if (editMode) {
                        utils.get();
                    }else {

                    }
                }
            }
        })
    })
})();