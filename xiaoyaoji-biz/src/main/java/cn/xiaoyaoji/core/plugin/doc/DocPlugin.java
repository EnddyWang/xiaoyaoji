package cn.xiaoyaoji.core.plugin.doc;

import cn.xiaoyaoji.core.plugin.Plugin;

/**
 * doc edit or doc view
 *
 * @author zhoujingjie
 * created on 2017/6/21
 */
public abstract class DocPlugin extends Plugin<DocPlugin> {

    /**
     * @return 编辑页面
     */
    public String getEditPage(){
        return "edit.jsp";
    }

    /**
     * @return 查看页面
     */
    public String getViewPage(){
        return "view.jsp";
    }



}
