package cn.xiaoyaoji.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author: zhoujingjie
 * @Date: 16/9/14
 */
public class JsonUtils {
    public static String toString(Object data){
        return JSON.toJSONString(data, SerializerFeature.WriteDateUseDateFormat,SerializerFeature.BrowserCompatible);
    }

    public static void write(OutputStream os,Object data) throws IOException {
        JSON.writeJSONString(os,data,SerializerFeature.WriteDateUseDateFormat,SerializerFeature.BrowserCompatible);
    }
}
