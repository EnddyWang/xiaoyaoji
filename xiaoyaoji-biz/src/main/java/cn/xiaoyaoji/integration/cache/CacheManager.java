package cn.xiaoyaoji.integration.cache;

import cn.xiaoyaoji.core.util.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhoujingjie
 *         created on 2017/7/1
 */
public class CacheManager {

    private static CacheProvider cacheProvider;
    private static Logger logger = LoggerFactory.getLogger(CacheManager.class);
    static {
        try {
            CacheFactory cacheFactory = (CacheFactory) Class.forName(ConfigUtils.getProperty("cache.provider.factory")).newInstance();
            cacheProvider = cacheFactory.create();
        } catch (InstantiationException |ClassNotFoundException |IllegalAccessException e) {
            logger.error(e.getMessage(),e);
        }
    }

    public static CacheProvider getCacheProvider(){
        return cacheProvider;
    }

}
