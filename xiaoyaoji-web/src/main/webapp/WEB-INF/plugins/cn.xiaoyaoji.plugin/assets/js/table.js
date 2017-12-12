/**
 * User: zhoujingjie
 * Date: 17/5/30
 * Time: 16:20
 */

(function () {
    define(['utils'], function (utils) {
        return {
            //template:document.getElementById('response-headers-template').innerHTML,
            //props:['responseHeaders','editing','name'],
            mounted: function () {
                this.$on('sortUpdate', function (o) {

                    function findParent(arr, pid) {
                        if (pid == 0) {
                            return arr;
                        }
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i].id === pid) {
                                return arr[i].children;
                            }
                            if (arr[i].children && arr[i].children.length > 0) {
                                var d = findParent(arr[i].children, pid);
                                if (d) {
                                    return d;
                                }
                            }
                        }
                    }
                    var oldParent = findParent(this[this.name], o.startPid);
                    var newParent = findParent(this[this.name], o.endPid);
                    if (newParent && oldParent === newParent) {
                        newParent.move(o.oldIndex, o.index);
                    } else {
                        var newO = oldParent[o.oldIndex];
                        //删除oldParent的该对象
                        oldParent.splice(o.oldIndex, 1);
                        //插入到newParent中
                        newParent.splice(o.index, 0, newO);
                    }
                });
            },
            data: function () {
                return {parent: null}
            },
            methods: {
                removeRow: function (item, data) {
                    var index = data.indexOf(item);
                    data.splice(index, 1)
                },
                dragstart: function (parent) {
                    this.parent = parent;
                },
                insertRow: function (item) {
                    if (!this.name || this.name.indexOf('Args')) {
                        item.children.push({id: utils.generateUID(), require: 'true', type: 'string', children: []});
                    } else {
                        item.children.push({id: utils.generateUID(), require: 'true', children: []});
                    }
                    _initsort_(this.$root, this.name);
                },
                apiArgsColumnFold: function (e) {
                    var $dom = $(e.target);
                    var $next = $(e.target).parent().parent().parent().next();
                    if ($dom.hasClass('open')) {
                        $dom.removeClass('open');
                        $next.slideUp();
                    } else {
                        $dom.addClass('open');
                        $next.slideDown();
                    }
                }
            }
        }
    });
})();