package cn.xiaoyaoji.plugin.doc;

import cn.xiaoyaoji.core.plugin.doc.DocPlugin;

/**
 * @author zhoujingjie
 *         created on 2017/6/21
 */
public class RichtextDocPlugin extends DocPlugin {

    @Override
    public String getEditPage() {
        return "richtext/edit.jsp";
    }

    @Override
    public String getViewPage() {
        return "richtext/view.jsp";
    }

}
