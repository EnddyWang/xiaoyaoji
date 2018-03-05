$(function () {
    require(['utils', 'vue'], function (utils, Vue) {

        window.addEventListener('message',function(e){
            if(e.data && e.data.type==='event'){
                app[e.data.method].apply(app,e.data.args);
            }
        });



        var app = new Vue({
            el: '#sidebar',
            data: {
                submitComment: '',
                projectName: xyj.page.projectName,
                history: [],
                g: {
                    ctx: xyj.ctx,
                    edit: xyj.page.editMode
                },
                projects: [],
                shareBox: null,
                shares: null,
                share: { //分享
                    name: null,
                    shareAll: 'YES',
                    docIds: null,
                    chosedIds: [],
                    password: null,
                    projectId: null
                },
                global: {
                    globalURL: '',
                    typeName: null,
                    environment: [],
                    status: []
                },
                rootDocs: null,
                loading: {
                    project: null,
                    history: null,
                    share: null,
                    env: null,
                    status: null
                },
                envModal: false,
            },
            watch: {
                "projectName": function () {
                    if (this.projectName !== xyj.page.projectName) {
                        utils.post('/project/' + xyj.page.projectId, {name: this.projectName}, function (rs) {
                            toastr.success('修改成功');
                        });
                    }
                }
            },
            mounted: function () {
                var self = this;

                $('body').on('keydown', function (e) {
                    //ctrl+s or command+s
                    if ((e.ctrlKey || e.metaKey) && e.which === 83) {
                        e.preventDefault();
                        self.submit();
                        return false;
                    }
                });

                this.loadProjects();
            },
            methods: {
                loadHistory: function () {
                    if (xyj.page.docId) {
                        var self = this;
                        self.loading.history = true;
                        utils.get('/doc/history/' + xyj.page.docId, {}, function (rs) {
                            self.loading.history = false;
                            self.history = rs.data;
                        });
                        UIkit.modal('#history-modal').show();
                    }
                },
                switchCommonly: function (item) {
                    if (item.commonlyUsed == 'YES') {
                        item.commonlyUsed = 'NO';
                        utils.post('/project/' + item.id + '/commonly.json', {isCommonlyUsed: 'NO'});
                    } else {
                        item.commonlyUsed = 'YES';
                        utils.post('/project/' + item.id + '/commonly.json', {isCommonlyUsed: 'YES'});
                    }

                },
                editpage: function () {
                    location.href = xyj.ctx + '/doc/' + xyj.page.docId + '/edit';
                },
                viewpage: function () {
                    location.href = xyj.ctx + '/doc/' + xyj.page.docId;
                },
                historyURL: function (docId, isEdit, historyId) {
                    if (isEdit) {
                        return xyj.ctx + '/doc/' + docId + '/edit?docHistoryId=' + historyId;
                    }
                    return xyj.ctx + '/doc/' + docId + '?docHistoryId=' + historyId;
                },
                showProject: function () {
                    $('#sidebar').addClass('layer');
                    this.loadProjects();
                },
                submit: function () {
                    if (_isGlobal_) {
                        window.submitProjectGlobal();
                    } else {
                        var doc = window.getDoc();
                        var url = '/doc/' + xyj.page.docId;
                        utils.post(url, {
                            name: doc.name,
                            comment: this.submitComment,
                            content: doc.content
                        }, function () {
                            toastr.success('操作成功');
                        });
                    }
                },
                loadProjects: function () {
                    var self = this;
                    self.loading.project = true;
                    utils.get('/project/list', {}, function (rs) {
                        self.loading.project = false;
                        self.projects = rs.data.projects;

                        window.postMessage({type: 'projects', data: rs.data.projects}, '*')
                    });
                },
                loadShares: function () {
                    this.loading.share = true;
                    this.shareBox = 'list';
                    var self = this;
                    utils.get('/share/project/' + xyj.page.projectId, {}, function (rs) {
                        self.loading.share = false;
                        rs.data.shares.forEach(function (item) {
                            item.editing = false;
                        });
                        self.shares = rs.data.shares;
                    });

                    //查询根文档
                    utils.get('/doc/root/' + xyj.page.projectId, {}, function (rs) {
                        self.rootDocs = rs.data.docs;
                    });

                    UIkit.modal('#share-modal').show();
                },
                deleteShare: function (item) {
                    UIkit.modal.confirm('是否确认删除?').then(function () {
                        utils.delete('/share/' + item.id, function () {
                            toastr.success('删除成功');
                        })
                    });
                },
                shareLockBlur: function (item) {
                    item.editing = false;
                    utils.post('/share/' + item.id, {password: item.password}, function () {
                        toastr.success('修改成功');
                    })
                },
                createShare: function () {
                    this.share.projectId = window.xyj.page.projectId;
                    if (this.share.chosedIds.length > 0) {
                        this.share.docIds = this.share.chosedIds.toString();
                    }
                    utils.post('/share', this.share, function () {
                        toastr.success('创建成功');
                        UIkit.modal('#share-modal').hide();
                    });
                },
                saveGlobalHttp: function () {
                    frames['globalWindow'].submitProjectGlobal();
                },
                loadGlobal: function (type) {
                    if (type === 'http') {
                        this.global.url = xyj.ctx + '/project/global/' + xyj.page.projectId;
                        this.global.typeName = '全局参数';
                    } else if (type === 'env') {
                        this.global.url = this.global.url = xyj.ctx + '/project/global/' + xyj.page.projectId + '/environments';
                        this.global.typeName = '环境变量';
                    } else if (type === 'status') {
                        this.global.url = this.global.url = xyj.ctx + '/project/global/' + xyj.page.projectId + '/status';
                        this.global.typeName = '全局状态';
                    }
                    UIkit.modal('#global-modal').show()
                },
                loadGlobalEnvironment: function () {
                    this.loading.env = true;
                    var self = this;
                    utils.get('/project/global/' + xyj.page.projectId + "/environments", {}, function (rs) {
                        self.loading.env = false;
                        self.global.environment = utils.toJSON(rs.data.global.environment);
                    });
                },
                loadGlobalStatus: function () {
                    this.loading.env = true;
                    var self = this;
                    utils.get('/project/global/' + xyj.page.projectId + "/environments", {}, function (rs) {
                        self.loading.status = false;
                        self.global.environment = utils.toJSON(rs.data.global.status);
                    });
                },
                closeGlobalModal:function(){
                    //UIKit.modal('#global-modal').hide();
                    location.reload();
                }
            }
        })
    })
});