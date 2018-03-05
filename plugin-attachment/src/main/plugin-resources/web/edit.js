(function () {
    requirejs([
        'vue', 'utils'
    ], function (Vue, utils) {
        var requestURL = '/plugin/req/'+xyj.curentPlugin.id;
        var app = new Vue({
            el: '#plugin-attachment',
            data: {
                pluginId:null,
                fileAccess:xyj.fileAccess,
                attachments: []
            },
            created: function () {
                this.pluginId = null;
                this.loadData();
                if(!xyj.page.editMode){
                    $('#plugin-attachment').insertBefore($('#doc-demo'));
                }
            },
            methods: {
                loadData: function () {
                    var self = this;
                    utils.get(requestURL+'/get', {
                        relatedId: xyj.page.docId,
                        projectId: xyj.page.projectId
                    }, function (data) {
                        self.attachments = data.data;
                    });
                },
                deleteFile:function(item){
                    if(!confirm('是否确认删除')){
                        return;
                    }
                    var self=this;
                    utils.delete(requestURL+'/delete?id='+item.id,function(rs){
                        self.attachments.splice(self.attachments.indexOf(item));
                    })
                },
                fileUpload:function(e){
                    var files = e.target.files;
                    if(files.length === 0)
                        return false;
                    var fd = new FormData();
                    fd.append('relateId',xyj.page.docId);
                    for(var i=0;i<files.length;i++){
                        fd.append('files',files[i]);
                    }
                    fd.append("projectId",xyj.page.projectId);
                    var self = this;
                    utils.fileloader(requestURL+'/upload',fd,function(){
                        self.loadData();
                    });
                }
            }
        })
    });
})();