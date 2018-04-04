package cn.xiaoyaoji.core.exception;

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
 *
 * @author zhoujingjie
 * Date 2018-04-04
 */
public class PluginTypeNotMatchException extends RuntimeException {
    public PluginTypeNotMatchException() {
        super();
    }

    public PluginTypeNotMatchException(String message) {
        super(message);
    }

    public PluginTypeNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginTypeNotMatchException(Throwable cause) {
        super(cause);
    }

    protected PluginTypeNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
