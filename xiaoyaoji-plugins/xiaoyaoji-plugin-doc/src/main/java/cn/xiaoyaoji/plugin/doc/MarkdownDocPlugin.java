package cn.xiaoyaoji.plugin.doc;

import cn.xiaoyaoji.core.plugin.doc.DocPlugin;

/**
 * @author zhoujingjie
 *         created on 2017/6/21
 */
public class MarkdownDocPlugin extends DocPlugin {


    @Override
    public String getEditPage() {
        return "markdown/edit.jsp";
    }

    @Override
    public String getViewPage() {
        return "markdown/view.jsp";
    }

}
