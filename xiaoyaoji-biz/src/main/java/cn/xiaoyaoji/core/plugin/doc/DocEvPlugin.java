package cn.xiaoyaoji.core.plugin.doc;

import cn.xiaoyaoji.core.plugin.Plugin;

/**
 * doc edit or doc view
 *
 * @author zhoujingjie
 * created on 2017/6/21
 */
public abstract class DocEvPlugin extends Plugin<DocEvPlugin> {

    /**
     * @return 编辑页面
     */
    public abstract String getEditPage();

    /**
     * @return 查看页面
     */
    public abstract String getViewPage();

}
