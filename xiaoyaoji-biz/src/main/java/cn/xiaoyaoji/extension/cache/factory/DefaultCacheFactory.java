package cn.xiaoyaoji.extension.cache.factory;

import cn.xiaoyaoji.integration.cache.CacheProvider;
import cn.xiaoyaoji.extension.cache.provider.MemoryCacheProvider;
import cn.xiaoyaoji.integration.cache.CacheFactory;
import cn.xiaoyaoji.extension.cache.provider.MemoryCacheProvider;

/**
 * @author zhoujingjie
 *         created on 2017/5/18
 */
public class DefaultCacheFactory implements CacheFactory {
    @Override
    public CacheProvider create() {
        return new MemoryCacheProvider();
    }
}
