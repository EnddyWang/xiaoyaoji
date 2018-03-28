(function(){
    require(['vue','utils'],function(Vue,utils){
        new Vue({
            el:'#doc-history',
            data:{
                history:[]
            },
            created:function(){
                this.loadHistory();
            },
            methods:{
                loadHistory: function () {
                    if (xyj.page.docId) {
                        var self = this;
                        utils.get('/plugin/req/cn.xiaoyaoji.doc.history/get?docId=' + xyj.page.docId, {}, function (rs) {
                            self.history = rs.data;
                        });
                    }
                },
                historyURL: function (docId, isEdit, historyId) {
                    if (isEdit) {
                        return xyj.ctx + '/doc/' + docId + '/edit?docHistoryId=' + historyId;
                    }
                    return xyj.ctx + '/doc/' + docId + '?docHistoryId=' + historyId;
                }
            }
        })
    });
})();