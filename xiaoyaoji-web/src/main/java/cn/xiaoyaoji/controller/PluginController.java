package cn.xiaoyaoji.controller;

import cn.xiaoyaoji.core.annotations.Ignore;
import cn.xiaoyaoji.core.common.Result;
import cn.xiaoyaoji.core.exception.PluginNotFoundException;
import cn.xiaoyaoji.core.exception.PluginTypeNotMatchException;
import cn.xiaoyaoji.core.plugin.*;
import cn.xiaoyaoji.core.plugin.doc.DocPlugin;
import cn.xiaoyaoji.core.util.AssertUtils;
import cn.xiaoyaoji.core.util.ConfigUtils;
import cn.xiaoyaoji.core.util.JsonUtils;
import cn.xiaoyaoji.core.util.StringUtils;
import cn.xiaoyaoji.entity.MapParameter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: zhoujingjie
 * @Date: 17/4/11
 */

@Ignore
@RestController
@RequestMapping("/plugin")
public class PluginController {


    /**
     * 插件http请求
     *
     * @param pluginId 插件id
     */
    @Ignore
    @RequestMapping("/req/{pluginId}/**")
    public void httpRequest(@PathVariable("pluginId") String pluginId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PluginInfo info = PluginManager.getInstance().getPluginInfo(pluginId);
        if (info == null) {
            response.setStatus(503);
            JsonUtils.write(response.getOutputStream(), new Result(Result.PLUGIN_NOT_FOUND, "plugin not found." + pluginId));
        } else {
            String path = request.getRequestURI().replace("/plugin/req/" + pluginId, "");
            Object result = info.getPlugin().httpRequest(path, request, response);
            if (result != null) {
                if (!(result instanceof Result)) {
                    result = new Result<>(true, result);
                }
                JsonUtils.write(response.getOutputStream(), result);
            }
        }
    }

    /**
     * 文档编辑页面
     *
     * @param pluginId 插件id
     */
    @Ignore
    @GetMapping("/doc/ep/{pluginId}")
    public void editPage(@PathVariable("pluginId") String pluginId, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        PluginInfo info = PluginManager.getInstance().getPluginInfo(pluginId);
        if (info == null) {
            throw new PluginNotFoundException("the plugin " + pluginId + " is not found");
        }
        String editPage = null;
        if (info.getPlugin() instanceof DocPlugin) {
            editPage = ((DocPlugin) info.getPlugin()).getEditPage();
        } else if (info.getPlugin() instanceof ProjectGlobalPlugin) {
            editPage = ((ProjectGlobalPlugin) info.getPlugin()).getEditPage();
        } else {
            throw new PluginTypeNotMatchException("the plugin type of " + pluginId + " is not docPlugin or ProjectGlobalPlugin");
        }

        String path = PluginUtils.getPluginSourceDir() + info.getRuntimeFolder() + "/web/" + editPage;
        request.setAttribute("pluginInfo", info);
        request.getRequestDispatcher(path).forward(request, response);
    }

    /**
     * 文档查看页面
     *
     * @param pluginId 插件id
     */
    @Ignore
    @GetMapping("/doc/vp/{pluginId}")
    public void viewPage(@PathVariable("pluginId") String pluginId, HttpServletResponse response, HttpServletRequest request) throws IOException, ServletException {
        PluginInfo info = PluginManager.getInstance().getPluginInfo(pluginId);
        if (info == null) {
            throw new PluginNotFoundException("the plugin " + pluginId + " is not found");
        }
        String viewPage = null;
        if (info.getPlugin() instanceof DocPlugin) {
            viewPage = ((DocPlugin) info.getPlugin()).getViewPage();
        } else if (info.getPlugin() instanceof ProjectGlobalPlugin) {
            viewPage = ((ProjectGlobalPlugin) info.getPlugin()).getViewPage();
        } else {
            throw new PluginTypeNotMatchException("the plugin type of " + pluginId + " is not docPlugin or ProjectGlobalPlugin");
        }
        String path = PluginUtils.getPluginSourceDir() + info.getRuntimeFolder() + "/web/" + viewPage;
        request.setAttribute("pluginInfo", info);
        request.getRequestDispatcher(path).forward(request, response);
    }


    /**
     * 只能访问web 和assets 两个目录
     *
     * @param pluginId
     * @param folder
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Ignore
    @RequestMapping("/{folder:web|assets}/{pluginId}/**")
    public void resource(@PathVariable("pluginId") String pluginId, @PathVariable("folder") String folder,
                         HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PluginInfo info = PluginManager.getInstance().getPluginInfo(pluginId);
        if (info == null) {
            throw new PluginNotFoundException("the plugin " + pluginId + " is not found");
        } else {
            String reqURI = request.getRequestURI();
            String path = reqURI.substring(reqURI.indexOf(pluginId) + pluginId.length() + 1);
            path = PluginUtils.getPluginSourceDir() + info.getRuntimeFolder() + "/" + folder + "/" + path;
            request.setAttribute("pluginInfo", info);
            request.getRequestDispatcher(path).forward(request, response);
        }
    }


    private void checkAuthorization(HttpSession session) {
        AssertUtils.isTrue(session.getAttribute("plugin.config.key") != null, "无操作权限");
    }

    @Ignore
    @GetMapping("/config")
    public ModelAndView config(@RequestParam(value = "id", required = false) String pluginId,
                               @RequestParam(value = "action", required = false) String action,
                               @RequestParam(value = "key", required = false) String key, HttpSession session
    ) {
        String _key = (String) session.getAttribute("plugin.config.key");
        if (_key == null) {
            AssertUtils.notNull(key, "无操作权限");
            AssertUtils.isTrue(key.equals(ConfigUtils.getProperty("plugin.config.key")), "无操作权限");
            session.setAttribute("plugin.config.key", key);
        }
        PluginInfo info = null;
        Map<Event, List<PluginInfo>> pluginInfos = PluginManager.getInstance().getPluginInfos();
        if (pluginId != null) {
            if ("reload".equals(action)) {
                PluginUtils.reloadPlugin(pluginId);
            } else if ("unload".equals(action)) {
                PluginUtils.destroyPlugin(pluginId);
            } else if ("delete".equals(action)) {
                PluginUtils.deletePlugin(pluginId);
            }
            info = PluginManager.getInstance().getPluginInfo(pluginId);
        }
        return new ModelAndView("/plugin/config")
                .addObject("config", info != null ? info.getConfig() : null)
                .addObject("pluginInfos", pluginInfos)
                .addObject("pluginId", pluginId);
    }

    /**
     * 插件配置
     *
     * @param pluginId     插件id
     * @param mapParameter 所有参数
     */
    @Ignore
    @PostMapping("/config")
    public ModelAndView updateConfig(@RequestParam("pluginId") String pluginId,
                                     MapParameter mapParameter, HttpSession session) {
        checkAuthorization(session);
        PluginInfo info = PluginManager.getInstance().getPluginInfo(pluginId);
        AssertUtils.notNull(info, "插件不存在");
        info.setConfig(mapParameter.getMap());
        info.getPlugin().init();
        return new ModelAndView("redirect:/plugin/config?id=" + pluginId);
    }

    /**
     * 上传插件
     *
     * @param file 插件文件
     */
    @Ignore
    @PostMapping("/upload")
    public ModelAndView uploadPlugin(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        checkAuthorization(request.getSession());
        AssertUtils.notNull(file, "请上传文件");
        AssertUtils.isTrue(file.getSize() > 0, "请上传正确的文件");
        String fileName = file.getOriginalFilename();
        AssertUtils.isTrue(fileName.endsWith(".zip"), "请上传application/zip");
        String outputDir = request.getServletContext().getRealPath(PluginUtils.getPluginSourceDir());
        File newfile = new File(outputDir + File.separator + fileName);
        if (newfile.exists()) {
            newfile = new File(outputDir + File.separator + StringUtils.code(3) + "_" + fileName);
        }
        File output = new File(newfile.getAbsolutePath().replace(".zip", ""));
        file.transferTo(newfile);
        PluginUtils.extractPlugin(newfile, output.getAbsolutePath());
        PluginUtils.loadPlugin(output);
        return new ModelAndView("redirect:/plugin/config");
    }


}
