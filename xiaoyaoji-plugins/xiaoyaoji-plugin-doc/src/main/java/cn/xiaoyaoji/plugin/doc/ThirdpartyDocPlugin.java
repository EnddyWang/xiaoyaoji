package cn.xiaoyaoji.plugin.doc;

import cn.xiaoyaoji.core.plugin.doc.DocPlugin;

/**
 * @author zhoujingjie
 *         created on 2017/6/22
 */
public class ThirdpartyDocPlugin extends DocPlugin {
    @Override
    public String getEditPage() {
        return "thirdparty/edit.jsp";
    }

    @Override
    public String getViewPage() {
        return "thirdparty/view.jsp";
    }

}
