package cn.xiaoyaoji.core.plugin;

import cn.xiaoyaoji.core.common.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 插件
 *
 * @author zhoujingjie
 * created on 2017/6/21
 */
public abstract class Plugin<T extends Plugin> {

    private PluginInfo<T> pluginInfo;

    public PluginInfo<T> getPluginInfo() {
        return pluginInfo;
    }

    public void setPluginInfo(PluginInfo<T> pluginInfo) {
        this.pluginInfo = pluginInfo;
    }

    /**
     * 插件初始化
     */
    public void init(){}

    /**
     * 插件卸载时触发
     */
    public void uninstall() {
    }

    /**
     * http request
     *
     * @param path     请求地址
     * @param request  request
     * @param response response
     * @return 响应任何非Result对象都会包装成Result对象。如果返回null则不处理
     * @see Result
     */
    public Object httpRequest(String path, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }
}
