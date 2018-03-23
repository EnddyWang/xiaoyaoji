package cn.xiaoyaoji.controller;

import cn.xiaoyaoji.core.annotations.Ignore;
import cn.xiaoyaoji.core.common.Constants;
import cn.xiaoyaoji.core.common.Result;
import cn.xiaoyaoji.core.common._HashMap;
import cn.xiaoyaoji.core.plugin.LoginPlugin;
import cn.xiaoyaoji.core.plugin.PlatformPluginManager;
import cn.xiaoyaoji.core.plugin.PluginInfo;
import cn.xiaoyaoji.core.util.AssertUtils;
import cn.xiaoyaoji.core.util.ConfigUtils;
import cn.xiaoyaoji.data.bean.User;
import cn.xiaoyaoji.service.ServiceFactory;
import cn.xiaoyaoji.utils.CacheUtils;
import cn.xiaoyaoji.utils.PasswordUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhoujingjie
 * @date 2016-06-03
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    private Cookie setCookie(String token,User user){
        CacheUtils.putUser(token, user);
        Cookie cookie = new Cookie(Constants.TOKEN_COOKIE_NAME,token);
        cookie.setPath("/");
        cookie.setMaxAge(ConfigUtils.getTokenExpires());
        return cookie;
    }


    @Ignore
    @PostMapping()
    public Object login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        AssertUtils.notNull(email, "用户名为空");
        AssertUtils.notNull(password, "密码为空");
        password = PasswordUtils.password(password);
        User user = ServiceFactory.instance().login(email, password);
        AssertUtils.notNull(user, "用户名或密码错误");
        if (user.getStatus().equals(User.Status.INVALID)) {
            return new Result(Result.ERROR, "invalid status");
        }
        String token = CacheUtils.token();
        response.addCookie(setCookie(token,user));
        return true;
    }

    /**
     * 插件登录地址
     * @param pluginId
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Ignore
    @RequestMapping("/plugin")
    @ResponseBody
    public Object plugin(@RequestParam("pluginId") String pluginId, HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        PluginInfo<LoginPlugin> loginPluginInfo =  PlatformPluginManager.getInstance().getLoginPlugin(pluginId);
        AssertUtils.isTrue(loginPluginInfo != null,"未找到插件"+pluginId);
        User loginUser = loginPluginInfo.getPlugin().doRequest(request);
        AssertUtils.notNull(loginUser,"登录失败");
        AssertUtils.isTrue(!User.Status.INVALID.equals(loginUser.getStatus()), "invalid status");
        String token = CacheUtils.token();
        response.addCookie(setCookie(token,loginUser));
        return new _HashMap<>().add("token",token);
    }

    /**
     * 第三方登录回调地址
     * @param pluginId
     * @param action            action不能有任何后缀
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Ignore
    @RequestMapping("/callback/{pluginId}/{action}")
    public void callback(@PathVariable("pluginId") String pluginId, @PathVariable("action") String action,
                         HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        PluginInfo<LoginPlugin> loginPluginInfo =  PlatformPluginManager.getInstance().getLoginPlugin(pluginId);
        if(loginPluginInfo == null){
            request.setAttribute("errorMsg","未找到插件"+pluginId);
            request.getRequestDispatcher("/error").forward(request,response);
            return;
        }
        loginPluginInfo.getPlugin().callback(action,request,response);
    }

}
