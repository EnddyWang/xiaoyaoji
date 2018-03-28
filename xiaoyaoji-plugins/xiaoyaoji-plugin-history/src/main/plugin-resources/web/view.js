(function(){
    require(['vue','utils'],function(Vue,utils){
        new Vue({
            el:'#doc-history',
            data:{
                history:[]
            },
            methods:{
                loadHistory: function () {
                    if (xyj.page.docId) {
                        var self = this;
                        self.loading.history = true;
                        utils.get('/doc/history/' + xyj.page.docId, {}, function (rs) {
                            self.loading.history = false;
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