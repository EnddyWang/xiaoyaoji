package cn.xiaoyaoji.core.plugin;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 * <p>
 * 项目全局插件
 *
 * @author zhoujingjie
 * Date 2018-04-04
 */
public abstract class ProjectGlobalPlugin extends Plugin<ProjectGlobalPlugin> {
    /**
     * @return 编辑页面
     */
    public String getEditPage() {
        return "edit.jsp";
    }

    /**
     * @return 查看页面
     */
    public String getViewPage() {
        return "view.jsp";
    }

    /**
     * 获取json数据
     *
     * @param projectId 项目id
     * @return json数据
     */
    public abstract String getJsonData(String projectId);
}
